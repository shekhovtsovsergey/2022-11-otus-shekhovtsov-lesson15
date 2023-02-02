package ru.otus.lesson15.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.lesson15.domain.Author;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.domain.Comment;
import ru.otus.lesson15.domain.Genre;
import ru.otus.lesson15.repository.AuthorRepository;
import ru.otus.lesson15.repository.BookRepository;
import ru.otus.lesson15.repository.CommentRepository;
import ru.otus.lesson15.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthorById(String id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void deleteGenreById(String id) {
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long booksCount() {
        return bookRepository.count();
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooksByAuthor(Author author) {
        return bookRepository.findAllByAuthor_Id(author.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooksByGenre(Genre genre) {
        return bookRepository.findAllByGenre_Id(genre.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooksByAuthorAndGenre(Author author, Genre genre) {
        return bookRepository.findAllByAuthor_IdAndGenre_Id(author.getId(), genre.getId());
    }

    @Override
    @Transactional
    public void deleteBookById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentsByBook(Book book) {
        return commentRepository.findAllByBook_Id(book.getId());
    }

    @Override
    @Transactional
    public void updateCommentById(String id, String authorName, String comment) {
        commentRepository.updateById(id, authorName, comment);
    }

    @Override
    @Transactional
    public void deleteCommentById(String id) {
        commentRepository.deleteById(id);
    }
}
