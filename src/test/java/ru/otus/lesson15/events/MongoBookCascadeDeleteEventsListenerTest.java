package ru.otus.lesson15.events;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.domain.Comment;
import ru.otus.lesson15.repository.BookRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@ComponentScan({"ru.otus.lesson15.repository", "ru.otus.lesson15.events"})
@DisplayName("Удаление комментариев при наличии с ними удаляемой книги")
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class MongoBookCascadeDeleteEventsListenerTest {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldDeleteBookWithCommentsWithHim() {
        Book book = mongoOperations.findOne(query(where("name").is("Kristina")), Book.class);
        String bookId = book.getId();
        bookRepository.delete(book);
        assertThat(mongoOperations.findOne(query(where("book.id").is(bookId)), Comment.class)).isNull();
    }
}