package ru.otus.lesson15.events;


import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.repository.AuthorRepository;
import ru.otus.lesson15.repository.GenreRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        val book = event.getSource();
        if (book.getAuthor() != null) {
            if (Objects.isNull(book.getAuthor().getId())) {
                authorRepository.save(book.getAuthor());
            }
        }
        if (book.getGenre() != null) {
            if (Objects.isNull(book.getGenre().getId())) {
                genreRepository.save(book.getGenre());
            }
        }
    }
}
