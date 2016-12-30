package hu.bartl.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
public class RetryConfiguration {

    @Autowired
    private TTConfigurationProvider ttConfigurationProvider;

    private static final int MAX_ATTEMPTS = 10;

    @Bean
    public RetryOperationsInterceptor remoteRetryInterceptor(RetryTemplate retryTemplate) {
        RetryOperationsInterceptor interceptor = new RetryOperationsInterceptor();
        interceptor.setRetryOperations(retryTemplate);
        return interceptor;
    }

    @Bean
    public RetryTemplate remoteRetryTemplate(RetryPolicy remoteRetryPolicy, BackOffPolicy remoteBackOffPolicy) {
        RetryTemplate template = new RetryTemplate();
        template.setRetryPolicy(remoteRetryPolicy);
        template.setBackOffPolicy(remoteBackOffPolicy);
        return template;
    }

    @Bean
    public RetryPolicy remoteRetryPolicy() {
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        policy.setMaxAttempts(MAX_ATTEMPTS);
        return policy;
    }

    @Bean
    public BackOffPolicy remoteBackOffPolicy() {
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(ttConfigurationProvider.getApiTimeout());
        policy.setMultiplier(2.5);
        policy.setMaxInterval(90000);
        return policy;
    }
}
