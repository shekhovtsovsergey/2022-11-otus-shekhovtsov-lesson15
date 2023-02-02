package ru.otus.lesson15.shell;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ru.otus.lesson15.domain.Author;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.domain.Comment;
import ru.otus.lesson15.domain.Genre;
import ru.otus.lesson15.service.LibraryService;
import static org.mockito.Mockito.verify;


@SpringBootTest
@DisplayName("класс LibraryCommands")
class LibraryCommandsTest {

    @Autowired
    private Shell shell;

    @MockBean
    private LibraryService libraryService;

    private static final String COMMAND_AUTHORS = "authors";
    private static final String COMMAND_INSERT_AUTHOR = "insertAuthor";
    private static final String COMMAND_DELETE_AUTHOR_BY_ID = "deleteAuthorById";
    private static final String COMMAND_GENRES = "genres";
    private static final String COMMAND_INSERT_GENRE = "insertGenre";
    private static final String COMMAND_DELETE_GENRE_BY_ID = "deleteGenreById";
    private static final String COMMAND_BOOKS_COUNT = "booksCount";
    private static final String COMMAND_INSERT_BOOK = "insertBook";
    private static final String COMMAND_BOOK_BY_ID = "bookById";
    private static final String COMMAND_ALL_BOOKS = "allBooks";
    private static final String COMMAND_BOOKS_BY_AUTHOR_ID = "booksByAuthorId";
    private static final String COMMAND_BOOKS_BY_GENRE_ID = "booksByGenreId";
    private static final String COMMAND_BOOKS_BY_BY_AUTHOR_ID_AND_GENRE_ID = "booksByAuthorIdAndGenreId";
    private static final String COMMAND_UPDATE_BOOK = "updateBook";
    private static final String COMMAND_DELETE_BOOK_BY_ID = "deleteBookById";
    private static final String COMMAND_INSERT_COMMENT = "insertComment";
    private static final String COMMAND_COMMENTS_BY_BOOK_ID = "commentsByBookId";
    private static final String COMMAND_UPDATE_COMMENT = "updateComment";
    private static final String COMMAND_DELETE_COMMENT_BY_ID = "deleteCommentById";

    @Test
    @DisplayName("корректно вызывает libraryService.getAllAuthors")
    void authors() {
        shell.evaluate(() -> COMMAND_AUTHORS);
        verify(libraryService).getAllAuthors();
    }

    @Test
    @DisplayName("корректно вызывает libraryService.saveAuthor")
    void insertAuthor() {
        shell.evaluate(() -> COMMAND_INSERT_AUTHOR + " xxx");
        verify(libraryService).saveAuthor(new Author(null, "xxx"));
    }

    @Test
    @DisplayName("корректно вызывает libraryService.deleteAuthorById")
    void deleteAuthorById() {
        shell.evaluate(() -> COMMAND_DELETE_AUTHOR_BY_ID + " 1");
        verify(libraryService).deleteAuthorById("1");
    }

    @Test
    @DisplayName("корректно вызывает libraryService.getAllGenres")
    void genres() {
        shell.evaluate(() -> COMMAND_GENRES);
        verify(libraryService).getAllGenres();
    }

    @Test
    @DisplayName("корректно вызывает libraryService.saveGenre")
    void insertGenre() {
        shell.evaluate(() -> COMMAND_INSERT_GENRE + " xxx");
        verify(libraryService).saveGenre(new Genre(null, "xxx"));
    }

    @Test
    @DisplayName("корректно вызывает libraryService.deleteGenreById")
    void deleteGenreById() {
        shell.evaluate(() -> COMMAND_DELETE_GENRE_BY_ID + " 1");
        verify(libraryService).deleteGenreById("1");
    }

    @Test
    @DisplayName("корректно вызывает libraryService.booksCount()")
    void count() {
        shell.evaluate(() -> COMMAND_BOOKS_COUNT);
        verify(libraryService).booksCount();
    }

    @Test
    @DisplayName("корректно вызывает libraryService.saveBook")
    void insertBook() {
        shell.evaluate(() -> COMMAND_INSERT_BOOK + " bookName 1 a1 1 g1");
        verify(libraryService).saveBook(new Book(null, "bookName", new Author("1", "a1"), new Genre("1", "g1")));
    }

    @Test
    @DisplayName("корректно вызывает libraryService.getBookById")
    void getBookById() {
        shell.evaluate(() -> COMMAND_BOOK_BY_ID + " 1");
        verify(libraryService).getBookById("1");
    }

    @Test
    @DisplayName("корректно вызывает libraryService.getAllBooks")
    void getAllBooks() {
        shell.evaluate(() -> COMMAND_ALL_BOOKS);
        verify(libraryService).getAllBooks();
    }

    @Test
    @DisplayName("корректно вызывает libraryService.getAllBooksByAuthor")
    void getAllBooksByAuthor() {
        shell.evaluate(() -> COMMAND_BOOKS_BY_AUTHOR_ID + " 1");
        verify(libraryService).getAllBooksByAuthor(new Author("1", null));
    }

    @Test
    @DisplayName("корректно вызывает libraryService.getAllBooksByGenre")
    void getAllBooksByGenre() {
        shell.evaluate(() -> COMMAND_BOOKS_BY_GENRE_ID + " 1");
        verify(libraryService).getAllBooksByGenre(new Genre("1", null));
    }

    @Test
    @DisplayName("корректно вызывает libraryService.getAllBooksByAuthorAndGenre")
    void getAllBooksByAuthorAndGenre() {
        shell.evaluate(() -> COMMAND_BOOKS_BY_BY_AUTHOR_ID_AND_GENRE_ID + " 1 1");
        verify(libraryService).getAllBooksByAuthorAndGenre(new Author("1", null), new Genre("1", null));
    }

    @Test
    @DisplayName("корректно вызывает libraryService.saveBook")
    void updateBook() {
        shell.evaluate(() -> COMMAND_UPDATE_BOOK + " 1 bookName 2 2a 3 3g");
        verify(libraryService).saveBook(new Book("1", "bookName", new Author("2", "2a"), new Genre("3", "3g")));
    }

    @Test
    @DisplayName("корректно вызывает libraryService.deleteBookById")
    void deleteBookById() {
        shell.evaluate(() -> COMMAND_DELETE_BOOK_BY_ID + " 1");
        verify(libraryService).deleteBookById("1");
    }

    @Test
    @DisplayName("корректно вызывает libraryService.saveComment")
    void insertComment() {
        shell.evaluate(() -> COMMAND_INSERT_COMMENT + " 1 authorName comment");
        verify(libraryService).saveComment(new Comment(null, "authorName", "comment", new Book("1", null, null, null)));
    }

    @Test
    @DisplayName("корректно вызывает libraryService.getAllCommentsByBook")
    void getAllCommentsByBookId() {
        shell.evaluate(() -> COMMAND_COMMENTS_BY_BOOK_ID + " 1");
        verify(libraryService).getAllCommentsByBook(new Book("1", null, null, null));
    }

    @Test
    @DisplayName("корректно вызывает libraryService.updateCommentById")
    void updateCommentById() {
        shell.evaluate(() -> COMMAND_UPDATE_COMMENT + " 1 authorName comment");
        verify(libraryService).updateCommentById("1", "authorName", "comment");
    }

    @Test
    @DisplayName("корректно вызывает libraryService.deleteCommentById")
    void deleteCommentById() {
        shell.evaluate(() -> COMMAND_DELETE_COMMENT_BY_ID + " 1");
        verify(libraryService).deleteCommentById("1");
    }

}
