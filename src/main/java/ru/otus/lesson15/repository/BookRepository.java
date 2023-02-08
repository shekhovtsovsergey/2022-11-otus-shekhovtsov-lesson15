package ru.otus.lesson15.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.lesson15.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByAuthor_Id(String authorId);

    boolean existsByAuthor_Id(String authorId);

    List<Book> findAllByGenre_Id(String genreId);

    boolean existsByGenre_Id(String genreId);

    List<Book> findAllByAuthor_IdAndGenre_Id(String authorId, String genreId);

}
