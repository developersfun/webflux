package com.developersfun.bookinfosystem.repo;

import com.developersfun.bookinfosystem.entities.BookInfoEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookInfoRepo extends ReactiveMongoRepository<BookInfoEntity, String> {
}
