package com.developersfun.bookinfosystem.config;

import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class MongoConfiguration {

    @Bean
    public ReactiveMongoDatabaseFactory mongoDbFactory() {
        return new SimpleReactiveMongoDatabaseFactory(MongoClients.create("mongodb://localhost:27017"),"book_review");
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(){
        return new ReactiveMongoTemplate(mongoDbFactory());
    }

//    @Bean
//    public KafkaTemplate kafkaTemplate(){
////        KafkaProperties kafkaProperties = KafkaProperties.
//
//        return new KafkaTemplate()
//    }
//
//    @Bean public KafkaListener kafkaListener(){
//        return null;
//    }
}