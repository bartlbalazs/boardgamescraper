package hu.bartl.service;

import hu.bartl.communication.AlternateNameAccessor;
import hu.bartl.repository.BoardGameFlatDescription;
import hu.bartl.repository.DescriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlternateNameService {

    @Value("${alternatename.timeOut}")
    private int timeout;

    @Autowired
    private AlternateNameAccessor alternateNameAccessor;

    @Autowired
    private DescriptionRepository repository;

    private static final Logger LOG = LoggerFactory.getLogger(AlternateNameService.class);

    public void onBoardgamePersisted(Integer bggId) {
        try {
            if (bggId != null) {
                Thread.sleep(timeout);
                LOG.info("Message received: {}", bggId);
                List<String> alternateNames = alternateNameAccessor.getAlternateNames(bggId);
                BoardGameFlatDescription description = repository.findByBggId(bggId);
                description.setAlternateNames(alternateNames);
                LOG.info("Alternatenames for {} are: {}", description.getName(), alternateNames);
                repository.save(description);
                LOG.info("{} ({}) updated.", description.getName(), description.getBggId());
            }
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
        }
    }
}
