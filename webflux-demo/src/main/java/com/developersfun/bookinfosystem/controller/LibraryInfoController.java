package com.developersfun.bookinfosystem.controller;

import com.developersfun.bookinfosystem.entities.BookInfoEntity;
import com.developersfun.bookinfosystem.entities.LibraryInfoEntity;
import com.developersfun.bookinfosystem.repo.LibraryInfoRepo;
import com.developersfun.bookinfosystem.service.LibraryInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("/library")
@Slf4j
public class LibraryInfoController {

//    @Autowired
//    private KafkaListener kafkaListener;

    @Autowired
    private LibraryInfoService libraryInfoService;

    @Autowired
    private LibraryInfoRepo libraryInfoRepo;

    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "LibraryInfoController Running. Tapped at " + new Date().toString();
    }

    @GetMapping(value = "/info", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LibraryInfoEntity> getAllLibraryInfo(){
        return libraryInfoService.getAllLibraryDetails();
    }

    @GetMapping(value = "/info2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @KafkaListener(topics = "lib", groupId = "foo")
    public Flux<String> getAllLibraryInfo2(String msg){
        msg="Test";
        return Flux.just(msg).delayElements(Duration.ofMillis(100));
    }

    @GetMapping("/test")
    public LibraryInfoEntity test(){
        return new LibraryInfoEntity();
    }

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody LibraryInfoEntity libraryInfoEntity){
        libraryInfoService.save(libraryInfoEntity);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LibraryInfoEntity> addBookFlux(@RequestBody LibraryInfoEntity libraryInfoEntity){
        return libraryInfoService.libraryInfoEntityFlux(libraryInfoEntity);
    }


}
