package com.developersfun.bookinfosystem.repo;

import com.developersfun.bookinfosystem.entities.BookInfoEntity;
import com.developersfun.bookinfosystem.entities.LibraryInfoEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;

public interface LibraryInfoRepo extends ReactiveMongoRepository<LibraryInfoEntity, UUID> {

}
