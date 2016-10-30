package hu.bartl.communication;

import hu.bartl.model.geekdo.AlternateNameWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlternateNameAccessor {

    private static final Logger LOG = LoggerFactory.getLogger(AlternateNameAccessor.class);
    private static final String GEEKDO_URL = "http://api.geekdo.com/api/geekitems?nosession=1&objectid={id}&objecttype=thing&subtype=boardgame";

    @Autowired
    private RestTemplate restTemplate;

    public List<String> getAlternateNames(int bggId) {
        List<String> result = new ArrayList<>();
        AlternateNameWrapper alternateNameWrapper = restTemplate.getForObject(GEEKDO_URL, AlternateNameWrapper.class, bggId);
        if (alternateNameWrapper.getItem() != null && alternateNameWrapper.getItem().getAlternatenames() != null) {
            alternateNameWrapper.getItem().getAlternatenames().stream().forEach(a -> result.add(a.getName()));
        }
        return result;
    }
}
