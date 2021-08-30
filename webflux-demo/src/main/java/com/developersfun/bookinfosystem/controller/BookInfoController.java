package com.developersfun.bookinfosystem.controller;

import com.developersfun.bookinfosystem.entities.BookInfoEntity;
import com.developersfun.bookinfosystem.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Date;

@RestController
@RequestMapping("/book")
public class BookInfoController {

    @Autowired
    private BookInfoService bookInfoService;

    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "BookInfoController Running. Tapped at " + new Date().toString();
    }

    @GetMapping("/info")
    public Flux<BookInfoEntity> getAllBooksInfo(){
        return bookInfoService.getAllBooks();
    }

    @GetMapping("/test")
    public BookInfoEntity test(){
        return new BookInfoEntity();
    }
    @PostMapping("/")
    public ResponseEntity<Void> addBook(@RequestBody BookInfoEntity bookInfoEntity){
        bookInfoService.save(bookInfoEntity);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


}
