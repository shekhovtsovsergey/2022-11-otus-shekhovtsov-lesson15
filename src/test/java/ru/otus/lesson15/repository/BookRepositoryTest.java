package ru.otus.lesson15.repository;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.lesson15.domain.Author;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.domain.Genre;
import java.util.List;
import java.util.Optional;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.data.mongodb.core.query.Query.query;


@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@DisplayName("Repository для работы с книгами должно")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookRepositoryTest {
    private static final int EXPECTED_BOOKS_COUNT = 5;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("возвращать ожидаемое количество книг в БД")
    void count() {
        long count = bookRepository.count();
        assertThat(count).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    @Order(Integer.MAX_VALUE)
    @DisplayName("обновляет данные книги в БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void saveMerge() {
        Book longWalkBook = mongoOperations.findOne(query(where("name").is("Long walk")), Book.class);
        String longWalkId = longWalkBook.getId();
        longWalkBook.setName("testName");
        Author pushkinAuthor = mongoOperations.findOne(query(where("name").is("Pushkin")), Author.class);
        Genre comedyGenre = mongoOperations.findOne(query(where("name").is("Comedy")), Genre.class);
        longWalkBook.setAuthor(pushkinAuthor);
        longWalkBook.setGenre(comedyGenre);
        bookRepository.save(longWalkBook);
        Book actualBook = mongoOperations.findOne(query(where("id").is(longWalkId)), Book.class);
        assertAll(() -> assertThat(actualBook).extracting(Book::getName).isEqualTo("testName"),
                () -> assertThat(actualBook).extracting(Book::getAuthor).extracting(Author::getId).isEqualTo(pushkinAuthor.getId()),
                () -> assertThat(actualBook).extracting(Book::getGenre).extracting(Genre::getId).isEqualTo(comedyGenre.getId()));
    }

    @Test
    @Order(Integer.MAX_VALUE - 1)
    @DisplayName("добавлять книгу в БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void savePersist() {
        Book expectedBook = new Book(null,
                "Spring in Action",
                mongoOperations.findOne(query(where("name").is("King")), Author.class),
                mongoOperations.findOne(query(where("name").is("Horror")), Genre.class));
        Book savedBook = bookRepository.save(expectedBook);
        assertThat(expectedBook.equals(savedBook));
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по ее id")
    void findById() {
        Book expectedBook = mongoOperations.findOne(query(where("name").is("Kristina")), Book.class);
        String expectedBookId = expectedBook.getId();
        Optional<Book> actualBook = bookRepository.findById(expectedBookId);
        assertThat(actualBook).isPresent().get()
                .hasFieldOrPropertyWithValue("id", expectedBookId)
                .hasFieldOrPropertyWithValue("name", expectedBook.getName());
    }

    @Test
    @DisplayName("возвращать все книги")
    void findAll() {
        List<Book> allBooks = bookRepository.findAll();
        assertThat(allBooks).hasSize(EXPECTED_BOOKS_COUNT);
    }

    @Test
    @DisplayName("возвращать все книги по автору")
    void findAllByAuthor() {
        String pushkinId = mongoOperations.findOne(query(where("name").is("Pushkin")), Author.class).getId();
        List<Book> actualBooks = bookRepository.findAllByAuthor_Id(pushkinId);
        assertAll(() -> assertThat(actualBooks).hasSize(2),
                () -> assertThat(actualBooks.stream()).allMatch(book -> book.getAuthor().getId().equals(pushkinId)));
    }

    @Test
    @DisplayName("возвращать true если существует книга по автору или наоборот")
    void existsByAuthor() {
        String pushkinId = mongoOperations.findOne(query(where("name").is("Pushkin")), Author.class).getId();
        boolean bookExists = bookRepository.existsByAuthor_Id(pushkinId);
        boolean bookNotExists = bookRepository.existsByAuthor_Id("lalalala");
        assertAll(() -> assertThat(bookExists).isTrue(),
                () -> assertThat(bookNotExists).isFalse());
    }

    @Test
    @DisplayName("возвращать все книги по жанру")
    void findAllByGenre() {
        String horrorId = mongoOperations.findOne(query(where("name").is("Horror")), Genre.class).getId();
        List<Book> actualBooks = bookRepository.findAllByGenre_Id(horrorId);
        assertAll(() -> assertThat(actualBooks).hasSize(2),
                () -> assertThat(actualBooks.stream()).allMatch(book -> book.getGenre().getId().equals(horrorId)));
    }

    @Test
    @DisplayName("возвращать true если существует книга по жанру или наоборот")
    void existsByGenre() {
        String horrorId = mongoOperations.findOne(query(where("name").is("Horror")), Genre.class).getId();
        boolean bookExists = bookRepository.existsByGenre_Id(horrorId);
        boolean bookNotExists = bookRepository.existsByGenre_Id("lalalala");
        assertAll(() -> assertThat(bookExists).isTrue(),
                () -> assertThat(bookNotExists).isFalse());
    }

    @Test
    @DisplayName("возвращать все книги по автору и жанру")
    void findAllByAuthorAndGenre() {
        String kingId = mongoOperations.findOne(query(where("name").is("King")), Author.class).getId();
        String horrorId = mongoOperations.findOne(query(where("name").is("Horror")), Genre.class).getId();
        List<Book> actualBooks = bookRepository.findAllByAuthor_IdAndGenre_Id(kingId, horrorId);
        assertAll(() -> assertThat(actualBooks).hasSize(2),
                () -> assertThat(actualBooks.stream()).allMatch(book -> book.getAuthor().getId().equals(kingId)
                        && book.getGenre().getId().equals(horrorId)));
    }

    @Test
    @DisplayName("удаляет книгу по id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteById() {
        String ruslanAndLudmilaId = mongoOperations.findOne(query(where("name").is("Ruslan and Ludmila")), Book.class).getId();
        bookRepository.deleteById(ruslanAndLudmilaId);
        List<Book> allBooks = mongoOperations.findAll(Book.class);
        assertAll(() -> assertThat(allBooks).hasSize(EXPECTED_BOOKS_COUNT - 1),
                () -> assertThat(allBooks.stream()).noneMatch(book -> book.getId().equals(ruslanAndLudmilaId)));
    }

}