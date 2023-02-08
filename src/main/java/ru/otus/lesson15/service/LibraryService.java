package ru.otus.lesson15.service;


import ru.otus.lesson15.domain.Author;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.domain.Comment;
import ru.otus.lesson15.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {

    List<Author> getAllAuthors();

    Author saveAuthor(Author author);

    void deleteAuthorById(String id);

    List<Genre> getAllGenres();

    Genre saveGenre(Genre genre);

    void deleteGenreById(String id);

    long booksCount();

    Book saveBook(Book book);

    Optional<Book> getBookById(String id);

    List<Book> getAllBooks();

    List<Book> getAllBooksByAuthor(Author author);

    List<Book> getAllBooksByGenre(Genre genre);

    List<Book> getAllBooksByAuthorAndGenre(Author author, Genre genre);

    void deleteBookById(String id);

    Comment saveComment(Comment comment);

    Optional<Comment> getCommentById(String id);

    List<Comment> getAllCommentsByBook(Book book);

    void updateCommentById(String id, String authorName, String comment);

    void deleteCommentById(String id);

}
