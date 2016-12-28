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
    private IdService idService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        int synchronizedItems = 0;

        while (synchronizedItems <= configurationProvider.getItemCount()) {
            StopWatch chunkTimer = new StopWatch();
            chunkTimer.start();
            List<Integer> ids = idService.getIds(synchronizedItems + 1, configurationProvider.getChunkSize());

            List<BoardGameDescription> descriptions = bgGeekAccessor.getBoardGameDescriptions(ids);
            descriptions.stream().map(descriptionMapper::apply).forEach(desc -> {
                descriptionRepository.insert(desc);
                if (ttConfigurationProvider.isUpdateRemote()) {
                    rabbitTemplate.convertAndSend(UPDATE_QUEUE, desc);
                }

                LOG.info("Description downloaded for {} ({})", desc.getName(), desc.getBggId());
            });
            chunkTimer.stop();
            try {
                int millisToMinTimeout = configurationProvider.getTimeout() - configurationProvider.getTimeout();
                if (millisToMinTimeout > 0) {
                    Thread.sleep(millisToMinTimeout);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronizedItems = ids.get(ids.size() - 1);
        }
    }

    private int getCheckedItemsCount(int checkedItems, List<BoardGameDescription> descriptions) {
        int incrementByChunkSize = checkedItems + configurationProvider.getChunkSize();
        if (descriptions.size() > 0) {
            return Math.max(incrementByChunkSize, descriptions.get(descriptions.size() - 1).getId());
        } else {
            return incrementByChunkSize;
        }
    }
}
