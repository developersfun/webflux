package com.developersfun.bookinfosystem.service;

import com.developersfun.bookinfosystem.entities.BookInfoEntity;
import com.developersfun.bookinfosystem.repo.BookInfoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class BookInfoService {

    @Autowired
    private BookInfoRepo bookInfoRepo;

    public Flux<BookInfoEntity> getAllBooks() {
        log.info("Getting all books info");
        return bookInfoRepo.findAll();
    }

    public void save(BookInfoEntity bookInfoEntity) {
        log.info("Saving entity {}", bookInfoEntity.getId());
        Mono<BookInfoEntity> entity = bookInfoRepo.save(bookInfoEntity).publish(e-> e.delayElement(Duration.ZERO));
        if(entity==null){
            throw new RuntimeException("Entity cannot be saved");
        }
        log.info("Saved entity {}", entity.block().getId());
    }
}
