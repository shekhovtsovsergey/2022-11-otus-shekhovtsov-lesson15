package ru.otus.lesson15.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.lesson15.domain.Comment;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public void updateById(String id, String authorName, String comment) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("authorName", authorName)
                .set("comment", comment);
        mongoTemplate.updateFirst(query, update, Comment.class);
    }
}
