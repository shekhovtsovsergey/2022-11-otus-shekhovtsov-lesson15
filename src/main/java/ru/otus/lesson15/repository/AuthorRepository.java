package ru.otus.lesson15.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.lesson15.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
