package ru.otus.lesson15.events;


import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.lesson15.domain.Author;
import ru.otus.lesson15.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class MongoAuthorDeleteEventsListener extends AbstractMongoEventListener<Author> {
    private final BookRepository bookRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        if (bookRepository.existsByAuthor_Id(id)) {
            throw new RuntimeException("Нельзя удалить автора с id=" + id + ", пока существуют с ним книги в библиотеке");
        }
    }
}
