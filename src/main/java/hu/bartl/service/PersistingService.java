package hu.bartl.service;

import hu.bartl.model.BoardGameDescription;
import hu.bartl.repository.BoardGameFlatDescription;
import hu.bartl.repository.DescriptionRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PersistingService {

    private static final Logger LOG = LoggerFactory.getLogger(PersistingService.class);

    @Autowired
    private DescriptionRepository descriptionRepository;

    public void persist(List<BoardGameDescription> descriptions) {
        DescriptionMapper descriptionMapper = new DescriptionMapper();
        List<BoardGameFlatDescription> flatDescriptions = descriptions.stream().map(descriptionMapper::apply).collect(Collectors.toList());
        descriptionRepository.insert(flatDescriptions);
    }

    private class DescriptionMapper implements Function<BoardGameDescription, BoardGameFlatDescription> {
        @Override
        public BoardGameFlatDescription apply(BoardGameDescription boardGameDescription) {
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
        }
    }
}
