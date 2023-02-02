package ru.otus.lesson15.repository;

public interface CommentRepositoryCustom {

    void updateById(String id, String authorName, String comment);

}
