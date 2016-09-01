package hu.bartl.service;

import com.google.gson.Gson;
import hu.bartl.model.BoardGameDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FilePersistingService {

    private static final Logger LOG = LoggerFactory.getLogger(FilePersistingService.class);

    @Autowired
    private Gson gson;

    public void writeToFile(List<BoardGameDescription> descriptions) {
        LOG.info("write descriptions#" + descriptions.hashCode() + " ...");

        String json = gson.toJson(descriptions);
        try {
            Files.write(Paths.get(getFileName()), json.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            LOG.error("persisting failed.", e);
        }
    }

    private String getFileName() {
        return "./boardGames-" + LocalDate.now().format(DateTimeFormatter.ISO_DATE)+".json";
    }
}
