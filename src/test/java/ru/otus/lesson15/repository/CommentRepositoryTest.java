package ru.otus.lesson15.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.domain.Comment;
import java.util.List;
import java.util.Optional;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.data.mongodb.core.query.Query.query;


@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@DisplayName("Repository для работы с комментариями должно")
class CommentRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("добавлять комментарий в БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void save() {
        Comment expectedComment = new Comment(null,
                "Борис",
                "Ну ничего себе рассказ, давайте еще",
                mongoOperations.findOne(query(where("name").is("Long walk")), Book.class));
        Comment savedComment = commentRepository.save(expectedComment);
        assertThat(expectedComment.equals(savedComment));
    }

    @Test
    @DisplayName("возвращать ожидаемый комментарий по ее id")
    void findById() {
        Comment petr1LongWalkComment = mongoOperations.findOne(query(where("comment").is("Ничего не понял, но очень интересно")), Comment.class);
        String petr1LongWalkCommentId = petr1LongWalkComment.getId();
        Optional<Comment> actualComment = commentRepository.findById(petr1LongWalkCommentId);
        assertThat(actualComment).isPresent().get()
                .hasFieldOrPropertyWithValue("id", petr1LongWalkCommentId)
                .hasFieldOrPropertyWithValue("book", petr1LongWalkComment.getBook())
                .hasFieldOrPropertyWithValue("authorName", "Петр 1")
                .hasFieldOrPropertyWithValue("comment", "Ничего не понял, но очень интересно");
    }

    @Test
    @DisplayName("возвращать все комментарии по книге")
    void findAllByBook() {
        Book kristinaBook = mongoOperations.findOne(query(where("name").is("Kristina")), Book.class);
        String kristinaBookId = kristinaBook.getId();
        List<Comment> actualComments = commentRepository.findAllByBook_Id(kristinaBookId);
        assertAll(() -> assertThat(actualComments).hasSize(3),
                () -> assertThat(actualComments.stream()).allMatch(c -> c.getBook().getId().equals(kristinaBookId)));
    }

    @Test
    @DisplayName("удалять все комментарии по книге")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteAllByBook() {
        Book kristinaBook = mongoOperations.findOne(query(where("name").is("Kristina")), Book.class);
        String kristinaBookId = kristinaBook.getId();
        commentRepository.deleteAllByBook_Id(kristinaBookId);
        List<Comment> actualComments = mongoOperations.find(query(where("book.id").is(kristinaBookId)), Comment.class);
        assertThat(actualComments).isEmpty();
    }

    @Test
    @DisplayName("обновляет данные комментария по id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateById() {
        Comment petr1LongWalkComment = mongoOperations.findOne(query(where("comment").is("Ничего не понял, но очень интересно")), Comment.class);
        String petr1LongWalkCommentId = petr1LongWalkComment.getId();
        commentRepository.updateById(petr1LongWalkCommentId, "testAuthorName", "testComment");
        Comment actualComment = mongoOperations.findOne(query(where("id").is(petr1LongWalkCommentId)), Comment.class);
        assertAll(() -> assertThat(actualComment).extracting(Comment::getBook).isEqualTo(petr1LongWalkComment.getBook()),
                () -> assertThat(actualComment).extracting(Comment::getAuthorName).isEqualTo("testAuthorName"),
                () -> assertThat(actualComment).extracting(Comment::getComment).isEqualTo("testComment"));
    }

}