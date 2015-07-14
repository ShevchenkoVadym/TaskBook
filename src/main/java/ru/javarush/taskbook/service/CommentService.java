package ru.javarush.taskbook.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.javarush.taskbook.model.Comment;
import ru.javarush.taskbook.repository.CommentRepository;

import java.util.List;

/**
 * Created by Ilya on 01.12.2014.
 */
public interface CommentService{

    <S extends Comment> List<S> save(Iterable<S> entites);

    List<Comment> findAll();

    List<Comment> findAll(Sort sort);

    Page<Comment> findAll(Pageable pageable);

    <S extends Comment> S save(S entity);

    Comment findOne(String s);

    boolean exists(String s);

    Iterable<Comment> findAll(Iterable<String> strings);

    long count();

    void delete(String s);

    void delete(Comment entity);

    void delete(Iterable<? extends Comment> entities);

    void deleteAll();

    Comment updateCommentText(String id, String text);

    Comment updateCommentComments(String id, String newCommentID);

    Comment updateCommentLikesInc(String id);

    Comment updateCommentLikesDec(String id);
}
