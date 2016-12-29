package hu.bartl.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationProvider {

    @Value("${basicinfo.timeOut}")
    private int timeout;

    @Value("${basicinfo.startIndex}")
    private int startIndex;

    @Value("${basicinfo.items}")
    private int itemCount;

    @Value("${basicinfo.chunksize}")
    private int chunkSize;

    public int getTimeout() {
        return timeout;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getChunkSize() {
        return chunkSize;
    }
}
