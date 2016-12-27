package hu.bartl.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public final static String UPDATE_QUEUE = "update-queue";
    private final static String UPDATE_EXCHANGE = "update-exchange";

    @Bean
    public Queue queue() {
        return new Queue(UPDATE_QUEUE, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(UPDATE_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(UPDATE_QUEUE);
    }

    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMessageConverter(new JsonMessageConverter());
        return rabbitTemplate;
    }
}
