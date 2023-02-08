package ru.otus.lesson15.mongock.changelog;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.lesson15.domain.Author;
import ru.otus.lesson15.domain.Book;
import ru.otus.lesson15.domain.Comment;
import ru.otus.lesson15.domain.Genre;
import ru.otus.lesson15.repository.AuthorRepository;
import ru.otus.lesson15.repository.BookRepository;
import ru.otus.lesson15.repository.CommentRepository;
import ru.otus.lesson15.repository.GenreRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
    private Author tolstoyAuthor;
    private Author kingAuthor;
    private Author pushkinAuthor;

    private Genre novelGenre;
    private Genre comedyGenre;
    private Genre detectiveGenre;
    private Genre horrorGenre;

    private Book kristinaBook;
    private Book longWalkBook;
    private Book romanceAndTheWorldBook;
    private Book tsarSaltanBook;
    private Book ruslanAndLudmilaBook;

    @ChangeSet(order = "000", id = "dropDb", author = "shekhovtsov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "shekhovtsov", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        tolstoyAuthor = repository.save(new Author(null, "Tolstoy"));
        kingAuthor = repository.save(new Author(null, "King"));
        pushkinAuthor = repository.save(new Author(null, "Pushkin"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "shekhovtsov", runAlways = true)
    public void initGenres(GenreRepository repository) {
        novelGenre = repository.save(new Genre(null, "Novel"));
        comedyGenre = repository.save(new Genre(null, "Comedy"));
        detectiveGenre = repository.save(new Genre(null, "Detective"));
        horrorGenre = repository.save(new Genre(null, "Horror"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "shekhovtsov", runAlways = true)
    public void initBooks(BookRepository repository) {
        kristinaBook = repository.save(new Book(null, "Kristina", kingAuthor, horrorGenre));
        longWalkBook = repository.save(new Book(null, "Long walk", kingAuthor, horrorGenre));
        romanceAndTheWorldBook = repository.save(new Book(null, "Romance and the world", tolstoyAuthor, novelGenre));
        tsarSaltanBook = repository.save(new Book(null, "Tsar Saltan", pushkinAuthor, comedyGenre));
        ruslanAndLudmilaBook = repository.save(new Book(null, "Ruslan and Ludmila", pushkinAuthor, detectiveGenre));
    }

    @ChangeSet(order = "004", id = "initComments", author = "shekhovtsov", runAlways = true)
    public void initComments(CommentRepository repository) {
        repository.save(new Comment(null, "Петр 1", "Ничего не понял, но очень интересно", kristinaBook));
        repository.save(new Comment(null, "Местный хипстер Никита", "Ничего не понял, пойду лучше выпью смузи и сразу в барбишоп", kristinaBook));
        repository.save(new Comment(null, "Студент Серега", "Отличная увлекательная книга", kristinaBook));
        repository.save(new Comment(null, "Петр 1", "Ничего не понял, но очень интересно", longWalkBook));
        repository.save(new Comment(null, "Студент Санек", "Прочитал взахлеб, посоветую соседу в общаге Сереге", longWalkBook));
        repository.save(new Comment(null, "Петр 1", "Книга очень интересная, жаль мало текста", romanceAndTheWorldBook));
        repository.save(new Comment(null, "Петр 1", "Прекрасно! Няни читают наследникам", tsarSaltanBook));
        repository.save(new Comment(null, "Вася школьник", "Задали в школе, спасибо, еле нашел", ruslanAndLudmilaBook));
    }
}
