package hu.bartl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrchestratorService {

    @Autowired
    private DescriptionDownloaderService downloaderService;

    @Autowired
    private FilePersistingService filePersistingService;

    public void run() {
        filePersistingService.writeToFile(downloaderService.scrapeAll());
    }
}
