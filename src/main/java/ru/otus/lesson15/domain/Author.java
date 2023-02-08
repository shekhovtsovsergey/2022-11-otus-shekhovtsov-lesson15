package ru.otus.lesson15.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    private String id;

    private String name;
}
