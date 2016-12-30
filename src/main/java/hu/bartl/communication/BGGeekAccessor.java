package hu.bartl.communication;

import hu.bartl.model.bggeek.BoardGameDescription;
import hu.bartl.model.bggeek.BoardGameDescriptionContainer;
import hu.bartl.repository.DescriptionRepository;
import hu.bartl.service.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class BGGeekAccessor {

    private static final Logger LOG = LoggerFactory.getLogger(BGGeekAccessor.class);
    private static final String BOARDGAME_INFO_URL = "https://boardgamegeek.com/xmlapi2/thing?type=boardgame&stats=1&id={id}";

    @Autowired
    private IdService idService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RetryTemplate retryTemplate;

    public List<BoardGameDescription> getBoardGameDescriptions(List<Integer> ids) {
        LOG.info("Synchronizing items: {}", ids);
        String idsString = StringUtils.collectionToDelimitedString(ids, ",");

        return retryTemplate.execute((RetryCallback<List<BoardGameDescription>, ResourceAccessException>) context -> {
            BoardGameDescriptionContainer container = restTemplate.getForObject(BOARDGAME_INFO_URL, BoardGameDescriptionContainer.class, idsString);
            List<BoardGameDescription> result = new ArrayList<>();
            if (container != null && container.getBoardGameDescriptions() != null) {
                result.addAll(container.getBoardGameDescriptions());
            }
            return result;
        });
    }
}
