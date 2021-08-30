package com.developersfun.bookinfosystem.entities;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.UUID;

@Data
@Document(collection = "library_info")
public class LibraryInfoEntity implements Serializable {
    @Id
    private String id;
    @Field(name = "library_name")
    private String libraryName;
    private String location;
    @Field(name = "country_code")
    private String countryCode;
    private Double latitude;
    private Double longitude;

}
