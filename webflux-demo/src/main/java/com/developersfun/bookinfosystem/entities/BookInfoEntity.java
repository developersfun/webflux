package com.developersfun.bookinfosystem.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "book_info")
public class BookInfoEntity {
    @Id
    private String id;
    @Field(name = "book_name")
    private String bookName;
    private String author;
    private String price;
    @Field(name = "amazon_url")
    private String sellerUrl;
}
