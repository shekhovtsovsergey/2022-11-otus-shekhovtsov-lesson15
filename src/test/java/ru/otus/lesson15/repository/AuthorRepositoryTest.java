package ru.otus.lesson15.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.lesson15.domain.Author;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@DisplayName("Repository для работы с авторами должно")
class AuthorRepositoryTest {
    private static final int EXPECTED_AUTHORS_COUNT = 3;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("возвращать ожидаемого автора по его id")
    void findById() {
        Author expectedAuthor = mongoOperations.findOne(query(where("name").is("Tolstoy")), Author.class);
        String expectedAuthorId = expectedAuthor.getId();
        Optional<Author> actualAuthor = authorRepository.findById(expectedAuthorId);
        assertThat(actualAuthor).isPresent().get()
                .hasFieldOrPropertyWithValue("id", expectedAuthorId)
                .hasFieldOrPropertyWithValue("name", expectedAuthor.getName());
    }

    @Test
    @DisplayName("возвращать всех авторов")
    void findAll() {
        List<Author> allAuthors = authorRepository.findAll();
        assertThat(allAuthors).hasSize(EXPECTED_AUTHORS_COUNT);
    }
}