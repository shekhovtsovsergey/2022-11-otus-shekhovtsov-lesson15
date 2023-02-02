package ru.otus.lesson15.events;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.lesson15.domain.Author;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.domain.Genre;
import ru.otus.lesson15.repository.BookRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@ComponentScan({"ru.otus.lesson15.repository", "ru.otus.lesson15.events"})
@DisplayName("Сохранение нового автора и жанра вместе с книгой")
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class MongoBookCascadeSaveEventsListenerTest {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldSaveAuthorAndGenreWithBook() {
        int authorsSize = mongoOperations.findAll(Author.class).size();
        int genreSize = mongoOperations.findAll(Genre.class).size();
        Book book = new Book(null, "newName", new Author(null, "newAuthor"), new Genre(null, "newGenre"));
        bookRepository.save(book);
        assertAll(() -> assertThat(mongoOperations.findAll(Author.class).size()).isEqualTo(authorsSize + 1),
                () -> assertThat(mongoOperations.findAll(Genre.class).size()).isEqualTo(genreSize + 1));
    }
}