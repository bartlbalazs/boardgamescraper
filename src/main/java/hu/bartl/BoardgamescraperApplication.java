package hu.bartl;

import hu.bartl.service.OrchestratorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BoardgamescraperApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BoardgamescraperApplication.class, args);
        context.getBean(OrchestratorService.class).run();
    }
}
