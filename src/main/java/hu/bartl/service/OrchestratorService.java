package hu.bartl.service;

import hu.bartl.model.BoardGameDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

@Service
public class OrchestratorService {

    private static final Logger LOG = LoggerFactory.getLogger(OrchestratorService.class);

    @Autowired
    private DescriptionDownloaderService downloaderService;

    @Autowired
    private PersistingService persistingService;

    public void run() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<BoardGameDescription> descriptions = downloaderService.scrapeAll();
        persistingService.persist(descriptions);
        stopWatch.stop();
        LOG.info("Downloading and persisting {} items took {} seconds.", descriptions.size(), stopWatch.getTotalTimeSeconds());
    }
}
