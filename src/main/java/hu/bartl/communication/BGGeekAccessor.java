package hu.bartl.communication;

import hu.bartl.model.bggeek.BoardGameDescription;
import hu.bartl.model.bggeek.BoardGameDescriptionContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class BGGeekAccessor {

    private static final Logger LOG = LoggerFactory.getLogger(BGGeekAccessor.class);
    private static final String BOARDGAME_INFO_URL = "http://boardgamegeek.com/xmlapi2/thing?type=boardgame&stats=1&id={id}";

    @Autowired
    private RestTemplate restTemplate;

    public List<BoardGameDescription> getBoardGameDescriptions(int startIndex, int items) {
        LOG.info("Downloading items {}-{}", startIndex, startIndex + items);
        List<BoardGameDescription> result = new ArrayList<>();
        try {
            BoardGameDescriptionContainer container = restTemplate.getForObject(BOARDGAME_INFO_URL, BoardGameDescriptionContainer.class, getIds(startIndex, items));
            result.addAll(container.getBoardGameDescriptions());
        } catch (Exception ignored) {
            LOG.error(ignored.getMessage());
        }
        return result;
    }

    private String getIds(int startIndex, int items) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(startIndex, startIndex + items).forEach(i -> sb.append(i + ","));
        return sb.toString();
    }
}
