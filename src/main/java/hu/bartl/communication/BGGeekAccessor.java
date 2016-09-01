package hu.bartl.communication;

import hu.bartl.model.BoardGameDescription;
import hu.bartl.model.BoardGameDescriptionContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class BGGeekAccessor {

    private final static String BOARDGAME_INFO_URL = "http://boardgamegeek.com/xmlapi/boardgame/{id}";

    @Autowired
    private RestTemplate restTemplate;

    public Optional<BoardGameDescription> getBoardGameDescription(int id) {
        try {

            BoardGameDescriptionContainer container = restTemplate.getForObject(BOARDGAME_INFO_URL, BoardGameDescriptionContainer.class, id);
            BoardGameDescription boardGameDescription = container.getBoardGameDescription();
            if (boardGameDescription != null) {
                boardGameDescription.setId(id);
            }
            return Optional.of(boardGameDescription);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
