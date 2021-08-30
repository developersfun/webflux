package com.developersfun.bookinfosystem.service;

import com.developersfun.bookinfosystem.entities.BookInfoEntity;
import com.developersfun.bookinfosystem.entities.LibraryInfoEntity;
import com.developersfun.bookinfosystem.repo.BookInfoRepo;
import com.developersfun.bookinfosystem.repo.LibraryInfoRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class LibraryInfoService {

    @Autowired
    private LibraryInfoRepo libraryInfoRepo;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Flux<LibraryInfoEntity> getAllLibraryDetails() {
        log.info("Getting all books info");
        return Flux.zip(libraryInfoRepo.findAll(),
                Flux.interval(Duration.ofMillis(100)),
                (key, value) -> key);
    }

    public void save(LibraryInfoEntity libraryInfoEntity) {
        libraryInfoEntity.setId(UUID.randomUUID().toString());
        libraryInfoRepo.save(libraryInfoEntity).map(m-> m.getId())
                .subscribe(ent -> log.info("Saved Entity : {}", ent));

//        kafkaTemplate.send("lib",
//                new ObjectMapper().convertValue(libraryInfoRepo.findAll()
//                        .collectList().flatMapMany(Flux::just), String.class));

        //        return Flux.fromStream(libraryInfoService.getAllLibraryDetails().toStream().map(e->e));
//        return Flux.fromStream(Stream.generate(()->libraryInfoService.getAllLibraryDetails()).map(m->m));
//        return libraryInfoRepo.findAll().toStream();
//        return Flux.fromStream(libraryInfoService.getAllLibraryDetails().toStream(2)).delayElements(Duration.ofMillis(100));
//        return libraryInfoService.getAllLibraryDetails().subscribeOn(Schedulers.parallel());
        try {
            kafkaTemplate.send("lib", new ObjectMapper().writeValueAsString(libraryInfoEntity));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //.publish(e-> e.delayElement(Duration.ZERO));
//        if(entity==null){
//            throw new RuntimeException("LibraryInfoEntity cannot be saved");
//        }
    }

    @KafkaListener(topics = "lib", groupId = "foo")
    public String newObjectFromKafka(String msg){
        //LibraryInfoEntity libraryInfoEntity = new ObjectMapper().convertValue(msg, LibraryInfoEntity.class);
        log.info("Library {} received.", msg);
        return msg;
    }

    public Flux<LibraryInfoEntity> libraryInfoEntityFlux(LibraryInfoEntity libraryInfoEntity){
        libraryInfoEntity.setId(UUID.randomUUID().toString());
        return libraryInfoRepo.save(libraryInfoEntity).flux();
    }
}
