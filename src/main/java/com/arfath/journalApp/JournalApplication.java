package com.arfath.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager uCanNameAnything(MongoDatabaseFactory dbFactory){   // this methods you can write it in another package as config and class transaction  config and annotate with @configuration and @EnableTransactionManagement else here is also okay so your wish
        return new MongoTransactionManager(dbFactory);
    } //spring will search for bean which implements PlaformTransactionManager doesnt matter whats the name of method

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
} 