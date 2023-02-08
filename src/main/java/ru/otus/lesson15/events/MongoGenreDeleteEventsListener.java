package ru.otus.lesson15.events;


import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.lesson15.domain.Genre;
import ru.otus.lesson15.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class MongoGenreDeleteEventsListener extends AbstractMongoEventListener<Genre> {
    private final BookRepository bookRepository;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        if (bookRepository.existsByGenre_Id(id)) {
            throw new RuntimeException("Нельзя удалить жанр с id=" + id + ", пока существуют с ним книги в библиотеке");
        }
    }
}
