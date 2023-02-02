package ru.otus.lesson15.shell;


import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.lesson15.domain.Author;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.domain.Comment;
import ru.otus.lesson15.domain.Genre;
import ru.otus.lesson15.service.LibraryService;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommands {
    private final LibraryService libraryService;

    @ShellMethod(value = "Get all authors command", key = {"authors"})
    public String authors() {
        List<Author> authors = libraryService.getAllAuthors();
        return String.format("Все авторы библиотеки : %s", authors);
    }

    @ShellMethod(value = "Insert author command", key = {"insertAuthor"})
    public void insertAuthor(@ShellOption String authorName) {
        libraryService.saveAuthor(new Author(null, authorName));
    }

    @ShellMethod(value = "Delete author by id", key = {"deleteAuthorById"})
    public void deleteAuthorById(@ShellOption String authorId) {
        libraryService.deleteAuthorById(authorId);
    }

    @ShellMethod(value = "Get all genres command", key = {"genres"})
    public String genres() {
        List<Genre> genres = libraryService.getAllGenres();
        return String.format("Все жанры библиотеки : %s", genres);
    }

    @ShellMethod(value = "Insert genre command", key = {"insertGenre"})
    public void insertGenre(@ShellOption String genreName) {
        libraryService.saveGenre(new Genre(null, genreName));
    }

    @ShellMethod(value = "Delete genre by id", key = {"deleteGenreById"})
    public void deleteGenreById(@ShellOption String genreId) {
        libraryService.deleteGenreById(genreId);
    }

    @ShellMethod(value = "Get books count", key = {"booksCount"})
    public long count() {
        return libraryService.booksCount();
    }

    @ShellMethod(value = "Insert book command", key = {"insertBook"})
    public void insertBook(@ShellOption String bookName,
                           @ShellOption String authorId, @ShellOption String authorName,
                           @ShellOption String genreId, @ShellOption String genreName) {
        libraryService.saveBook(new Book(null, bookName, new Author(authorId, authorName), new Genre(genreId, genreName)));
    }

    @ShellMethod(value = "Get books by id", key = {"bookById"})
    public String getBookById(@ShellOption String bookId) {
        Optional<Book> book = libraryService.getBookById(bookId);
        return book.isPresent() ? String.format("Вы взяли книгу : %s", book.get()) : String.format("Книги с id: %s не существует", bookId);
    }

    @ShellMethod(value = "Get all books", key = {"allBooks"})
    public String getAllBooks() {
        List<Book> allBooks = libraryService.getAllBooks();
        return String.format("Все книги библиотеки : %s", allBooks);
    }

    @ShellMethod(value = "Get books by author id", key = {"booksByAuthorId"})
    public String getAllBooksByAuthor(@ShellOption String authorId) {
        List<Book> allBooksByAuthor = libraryService.getAllBooksByAuthor(new Author(authorId, null));
        return String.format("Вы взяли следующие книги по автору : %s", allBooksByAuthor);
    }

    @ShellMethod(value = "Get books by genre id", key = {"booksByGenreId"})
    public String getAllBooksByGenre(@ShellOption String genreId) {
        List<Book> allBooksByGenre = libraryService.getAllBooksByGenre(new Genre(genreId, null));
        return String.format("Вы взяли следующие книги по жанру : %s", allBooksByGenre);
    }

    @ShellMethod(value = "Get books by author id and genre id", key = {"booksByAuthorIdAndGenreId"})
    public String getAllBooksByAuthorAndGenre(@ShellOption String authorId, @ShellOption String genreId) {
        List<Book> allBooksByAuthorAndGenre = libraryService.getAllBooksByAuthorAndGenre(
                new Author(authorId, null),
                new Genre(genreId, null));
        return String.format("Вы взяли следующие книги по автору и жанру : %s", allBooksByAuthorAndGenre);
    }

    @ShellMethod(value = "Update book", key = {"updateBook"})
    public void updateBook(@ShellOption String id,
                           @ShellOption String bookName,
                           @ShellOption String authorId,
                           @ShellOption String authorName,
                           @ShellOption String genreId,
                           @ShellOption String genreName) {
        libraryService.saveBook(new Book(id, bookName, new Author(authorId, authorName), new Genre(genreId, genreName)));
    }

    @ShellMethod(value = "Delete book by id", key = {"deleteBookById"})
    public void deleteBookById(@ShellOption String bookId) {
        libraryService.deleteBookById(bookId);
    }

    @ShellMethod(value = "Insert comment command", key = {"insertComment"})
    public void insertComment(@ShellOption String bookId, @ShellOption String authorName, @ShellOption String comment) {
        libraryService.saveComment(new Comment(null, authorName, comment, new Book(bookId, null, null, null)));
    }

    @ShellMethod(value = "Get comments by book id", key = {"commentsByBookId"})
    public String getAllCommentsByBookId(@ShellOption String bookId) {
        List<Comment> allCommentsByBook = libraryService.getAllCommentsByBook(new Book(bookId, null, null, null));
        return String.format("Вы взяли следующие комментарии по книге : %s", allCommentsByBook);
    }

    @ShellMethod(value = "Update comment by id", key = {"updateComment"})
    public void updateCommentById(@ShellOption String id, @ShellOption String authorName, @ShellOption String comment) {
        libraryService.updateCommentById(id, authorName, comment);
    }

    @ShellMethod(value = "Delete comment by id", key = {"deleteCommentById"})
    public void deleteCommentById(@ShellOption String commentId) {
        libraryService.deleteCommentById(commentId);
    }

}
