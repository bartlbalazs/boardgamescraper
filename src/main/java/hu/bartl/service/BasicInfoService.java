package hu.bartl.service;

import hu.bartl.communication.BGGeekAccessor;
import hu.bartl.model.bggeek.BoardGameDescription;
import hu.bartl.repository.BoardGameFlatDescription;
import hu.bartl.repository.DescriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.function.Function;

import static hu.bartl.configuration.RabbitConfiguration.QUEUE_NAME;

@Service
public class BasicInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(BasicInfoService.class);

    @Value("${basicinfo.timeOut}")
    private int timeout;

    @Value("${basicinfo.items}")
    private int itemCount;

    @Value("${basicinfo.chunksize}")
    private int chunkSize;

    @Autowired
    private BGGeekAccessor bgGeekAccessor;

    @Autowired
    private Function<BoardGameDescription, BoardGameFlatDescription> descriptionMapper;

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void scrapeAll() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 1; i <= itemCount; i += chunkSize) {
            List<BoardGameDescription> descriptions = bgGeekAccessor.getBoardGameDescriptions(i, chunkSize);

            descriptions.stream().map(descriptionMapper::apply).forEach(desc -> {
                descriptionRepository.insert(desc);
                LOG.info("Description downloaded for {} ({})", desc.getName(), desc.getBggId());
                rabbitTemplate.convertAndSend(QUEUE_NAME, desc.getBggId());
            });
        }
        stopWatch.stop();
        LOG.info("Downloading and persisting {} items took {} seconds.", itemCount, stopWatch.getTotalTimeSeconds());
    }
}
