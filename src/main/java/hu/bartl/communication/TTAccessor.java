package hu.bartl.communication;


import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bartl.configuration.TTConfigurationProvider;
import hu.bartl.repository.BoardGameFlatDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TTAccessor {

    @Autowired
    private TTConfigurationProvider configuration;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(TTAccessor.class);

    @Retryable(interceptor = "remoteRetryInterceptor")
    public void updateRemote(BoardGameFlatDescription description) {
        try {
            description.setId(null);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", configuration.getApiKey());

            String descriptionString = objectMapper.writeValueAsString(description);
            HttpEntity<String> request = new HttpEntity<>(descriptionString, headers);


            ResponseEntity<Void> response = restTemplate.postForEntity(configuration.getApiEndpoint(), request, Void.class);
            if (response.getStatusCode() != HttpStatus.CREATED) {
                LOG.error("Remote updated with value: {} failed.", description.toString());
                throw new RemoteAccessException();
            }

            LOG.info("Remote updated with value: {}", description.toString());
        } catch (Exception e) {
            throw new RemoteAccessException(e);
        }
    }
}
