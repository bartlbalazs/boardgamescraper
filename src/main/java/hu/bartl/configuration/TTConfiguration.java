package hu.bartl.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TTConfiguration {

    @Value("${api.ednpoint}")
    private String apiEndpoint;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.timeout}")
    private int apiTimeout;

    @Value("${api.updateRemote}")
    private boolean updateRemote;

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getApiTimeout() {
        return apiTimeout;
    }

    public boolean isUpdateRemote() {
        return updateRemote;
    }
}
