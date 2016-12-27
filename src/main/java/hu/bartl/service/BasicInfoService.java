package hu.bartl.service;

import hu.bartl.communication.BGGeekAccessor;
import hu.bartl.configuration.ConfigurationProvider;
import hu.bartl.configuration.TTConfigurationProvider;
import hu.bartl.model.bggeek.BoardGameDescription;
import hu.bartl.repository.BoardGameFlatDescription;
import hu.bartl.repository.DescriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.function.Function;

import static hu.bartl.configuration.RabbitConfiguration.UPDATE_QUEUE;

@Service
public class BasicInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(BasicInfoService.class);

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Autowired
    private BGGeekAccessor bgGeekAccessor;

    @Autowired
    private Function<BoardGameDescription, BoardGameFlatDescription> descriptionMapper;

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private  RabbitTemplate rabbitTemplate;

    @Autowired
    private TTConfigurationProvider ttConfigurationProvider;


    public void scrapeAll() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        downloadDescriptions();
        stopWatch.stop();
        LOG.info("Downloading and persisting {} items took {} seconds.", configurationProvider.getItemCount(), stopWatch.getTotalTimeSeconds());
    }

    private void downloadDescriptions() {
        int checkedItems = 0;

        while (checkedItems <= configurationProvider.getItemCount()) {
            StopWatch chunkTimer = new StopWatch();
            chunkTimer.start();

            List<BoardGameDescription> descriptions = bgGeekAccessor.getBoardGameDescriptions(checkedItems, configurationProvider.getChunkSize());
            descriptions.stream().map(descriptionMapper::apply).forEach(desc -> {
                descriptionRepository.insert(desc);
                if (ttConfigurationProvider.isUpdateRemote()) {
                    rabbitTemplate.convertAndSend(UPDATE_QUEUE, desc);
                }

                LOG.info("Description downloaded for {} ({})", desc.getName(), desc.getBggId());
            });
            chunkTimer.stop();
            try {
                Thread.sleep(Math.max(configurationProvider.getTimeout(), chunkTimer.getTotalTimeMillis()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkedItems = getCheckedItemsCount(checkedItems, descriptions);
        }
    }

    private int getCheckedItemsCount(int checkedItems, List<BoardGameDescription> descriptions) {
        int incrementByChunkSize = checkedItems + configurationProvider.getChunkSize();
        if (descriptions.size() > 0) {
            return Math.max(incrementByChunkSize, descriptions.get(descriptions.size() -1).getId());
        } else {
            return incrementByChunkSize;
        }
    }
}
