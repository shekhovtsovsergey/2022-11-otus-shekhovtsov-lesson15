package ru.otus.lesson15.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.lesson15.domain.Genre;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.mongodb.core.query.Query.query;
import java.util.List;
import java.util.Optional;


@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@DisplayName("Repository для работы с жанрами должно")
class GenreRepositoryTest {
    private static final int EXPECTED_GENRES_COUNT = 4;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("возвращать ожидаемый жанр по его id")
    void findById() {
        Genre expectedGenre = mongoOperations.findOne(query(where("name").is("Novel")), Genre.class);
        String expectedGenreId = expectedGenre.getId();
        Optional<Genre> actualGenre = genreRepository.findById(expectedGenreId);
        assertThat(actualGenre).isPresent().get()
                .hasFieldOrPropertyWithValue("id", expectedGenreId)
                .hasFieldOrPropertyWithValue("name", expectedGenre.getName());
    }

    @Test
    @DisplayName("возвращать все жанры")
    void findAll() {
        List<Genre> allGenres = genreRepository.findAll();
        assertThat(allGenres).hasSize(EXPECTED_GENRES_COUNT);
    }
}