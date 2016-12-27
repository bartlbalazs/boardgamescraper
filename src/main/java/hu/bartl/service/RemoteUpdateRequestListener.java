package hu.bartl.service;

import hu.bartl.communication.TTAccessor;
import hu.bartl.configuration.TTConfigurationProvider;
import hu.bartl.repository.BoardGameFlatDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import static hu.bartl.configuration.RabbitConfiguration.UPDATE_QUEUE;

@Service
public class RemoteUpdateRequestListener {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteUpdateRequestListener.class);

    @Autowired
    private TTConfigurationProvider configuration;

    @Autowired
    private TTAccessor ttAccessor;

    @RabbitListener(queues = UPDATE_QUEUE)
    public void onMessage(BoardGameFlatDescription description) {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            ttAccessor.updateRemote(description);
            stopWatch.stop();
            Thread.sleep(Math.max(0, configuration.getApiTimeout() - stopWatch.getTotalTimeMillis()));
        } catch (Exception e) {
            LOG.error("Failed updating the remote with description: {},\ncause: {}", description, e.getMessage());
        }
    }
}
