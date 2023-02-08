package ru.otus.lesson15.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.lesson15.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
