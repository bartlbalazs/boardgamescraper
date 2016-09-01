package hu.bartl.service;

import hu.bartl.communication.BGGeekAccessor;
import hu.bartl.model.BoardGameDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class DescriptionDownloaderService {

    private static final Logger LOG = LoggerFactory.getLogger(DescriptionDownloaderService.class);

    @Value("${downloader.maxAttempts}")
    private int MAX_ATTEMPTS;


    @Value("${downloader.timeOut}")
    private int TIMEOUT;

    @Autowired
    private BGGeekAccessor bgGeekAccessor;


    public List<BoardGameDescription> scrapeAll() {

        List<BoardGameDescription> descriptions = new ArrayList<>();
        List<Integer> missingDescriptions = new ArrayList<>();

        StopWatch timer = new StopWatch();
        timer.start();

        for (int i = 0; i <= MAX_ATTEMPTS; i++) {

            if (i % 10 == 0) {
                LOG.info("progress: " + i);
            }

            try {
                Thread.sleep(TIMEOUT);
                Optional<BoardGameDescription> description = bgGeekAccessor.getBoardGameDescription(i);
                if (description.isPresent()) {
                    descriptions.add(description.get());
                } else {
                    missingDescriptions.add(i);
                }
            } catch (InterruptedException ignored) {
            }
        }

        long notGames = descriptions.stream().filter(d -> d.isSubtypemismatch()).count();

        timer.stop();
        LOG.info("finished in " + timer.getTotalTimeSeconds() + " seconds.");
        LOG.info("Total attempts: " + MAX_ATTEMPTS);
        LOG.info("Downloaded descriptions: " + descriptions.size());
        LOG.info("Not a game descriptions: " + notGames);
        LOG.info("Missing descriptions: " + missingDescriptions.size());
        return descriptions.stream().filter(d -> !d.isSubtypemismatch() && !isEmpty(d.getName())).collect(Collectors.toList());
    }
}
