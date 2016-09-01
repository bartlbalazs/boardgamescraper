package hu.bartl.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BasicConfiguration {

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory, new MongoMappingContext());
        converter.setTypeMapper(typeMapper);

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        return mongoTemplate;
    }
}
