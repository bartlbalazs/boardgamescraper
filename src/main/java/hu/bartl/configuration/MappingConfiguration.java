package hu.bartl.configuration;

import hu.bartl.model.bggeek.BoardGameDescription;
import hu.bartl.model.bggeek.NameType;
import hu.bartl.repository.BoardGameFlatDescription;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Configuration
public class MappingConfiguration {

    @Bean
    public Function<BoardGameDescription, BoardGameFlatDescription> descriptionMapper() {

        return (BoardGameDescription boardGameDescription) -> {
            BoardGameFlatDescription result = new BoardGameFlatDescription();
            result.setId(new ObjectId());
            result.setBggId(boardGameDescription.getId());
            result.setName(choosePrimary(boardGameDescription));
            result.setAlternateNames(getAlterNames(boardGameDescription));
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

    private String choosePrimary(BoardGameDescription boardGameDescription) {
        Optional<NameType> primary = boardGameDescription.getNames().stream().filter(n -> "primary".equals(n.getType())).findFirst();
        NameType nameType = primary.orElse(boardGameDescription.getNames().get(0));
        return nameType.getValue();
    }

    private List<String> getAlterNames(BoardGameDescription boardGameDescription) {
        String primary = choosePrimary(boardGameDescription);
        List<String> alterNames = boardGameDescription.getNames().stream().map(NameType::getValue).filter(a -> !primary.equals(a)).collect(toList());
        return alterNames;
    }
}
