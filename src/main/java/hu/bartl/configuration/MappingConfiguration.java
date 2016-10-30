package hu.bartl.configuration;

import hu.bartl.model.bggeek.BoardGameDescription;
import hu.bartl.repository.BoardGameFlatDescription;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MappingConfiguration {

    @Bean
    public Function<BoardGameDescription, BoardGameFlatDescription> descriptionMapper() {

        return (BoardGameDescription boardGameDescription) -> {
            BoardGameFlatDescription result = new BoardGameFlatDescription();
            result.setId(new ObjectId());
            result.setBggId(boardGameDescription.getId());
            result.setName(boardGameDescription.getName().getValue());
            result.setImage(boardGameDescription.getImage());
            result.setThumbnail(boardGameDescription.getThumbnail());
            if (boardGameDescription.getMaxplayers() != null) {
                result.setMaxPlayers(boardGameDescription.getMaxplayers().getValue());
            }
            if (boardGameDescription.getMinplayers() != null) {
                result.setMinPlayers(boardGameDescription.getMinplayers().getValue());
            }
            if (boardGameDescription.getPlayingtime() != null) {
                result.setPlayingTime(boardGameDescription.getPlayingtime().getValue());
            }
            if (boardGameDescription.getYearpublished() != null) {
                result.setYearPublished(boardGameDescription.getYearpublished().getValue());
            }
            if (boardGameDescription.getStatistics() != null && boardGameDescription.getStatistics().getRating() != null) {
                result.setRating(boardGameDescription.getStatistics().getRating().getAverage().getValue());
            }
            return result;
        };
    }
}
