package hu.bartl.service;

import hu.bartl.repository.BoardGameFlatDescription;
import hu.bartl.repository.DescriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.collectionToDelimitedString;

@Service
public class IdService {

    private static final Logger LOG = LoggerFactory.getLogger(IdService.class);

    @Autowired
    private DescriptionRepository descriptionRepository;

    public String getNextIdsString(int startIndex, int items) {
        return collectionToDelimitedString(getIds(startIndex, items), ",");
    }

    private List<Integer> getIds(int startIndex, int items) {
        List<Integer> ids = new ArrayList<>();
        int idCandidate = startIndex;
        while (ids.size() < items) {
            Optional<BoardGameFlatDescription> byBggId = descriptionRepository.findByBggId(idCandidate);
            if (!byBggId.isPresent()) {
                ids.add(idCandidate);
            }
            idCandidate++;
        }
        LOG.info("Founded ids which are not present in the database: {}.", ids);
        return ids;
    }
}
