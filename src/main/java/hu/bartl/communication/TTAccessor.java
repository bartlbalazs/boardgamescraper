package hu.bartl.communication;


import hu.bartl.configuration.TTConfiguration;
import hu.bartl.repository.BoardGameFlatDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import static hu.bartl.configuration.RabbitConfiguration.UPDATE_QUEUE;

@Service
public class TTAccessor {

    @Autowired
    private TTConfiguration configuration;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(TTAccessor.class);

    @RabbitListener(queues = UPDATE_QUEUE)
    public void onMessage(BoardGameFlatDescription description) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        updateRemote(description);
        stopWatch.stop();
        Thread.sleep(Math.max(0, configuration.getApiTimeout() - stopWatch.getTotalTimeMillis()));
    }

    private void updateRemote(BoardGameFlatDescription description) {
        description.setId(null);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", configuration.getApiKey());


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<BoardGameFlatDescription> request = new HttpEntity<>(description, headers);

        restTemplate.postForLocation(configuration.getApiEndpoint(), request);
        LOG.info("Remote updated with value: {}", description.toString());
    }
}
