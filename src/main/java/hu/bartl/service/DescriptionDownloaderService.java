package hu.bartl.service;

import hu.bartl.communication.BGGeekAccessor;
import hu.bartl.model.BoardGameDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DescriptionDownloaderService {

    @Value("${downloader.timeOut}")
    private int timeout;

    @Value("${downloader.items}")
    private int itemCount;

    @Value("${downloader.chunksize}")
    private int chunkSize;

    @Autowired
    private BGGeekAccessor bgGeekAccessor;


    public List<BoardGameDescription> scrapeAll() {

        List<BoardGameDescription> result = new ArrayList<>(itemCount + chunkSize);
        for (int i = 1; i <= itemCount; i += chunkSize) {
            List<BoardGameDescription> descriptions = bgGeekAccessor.getBoardGameDescriptions(i, chunkSize);
            result.addAll(descriptions);
        }
        return result;
    }
}
