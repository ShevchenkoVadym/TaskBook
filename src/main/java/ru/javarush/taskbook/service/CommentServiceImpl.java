package ru.javarush.taskbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.javarush.taskbook.model.Comment;
import ru.javarush.taskbook.repository.CommentRepository;

import java.util.List;

/**
 * Created by Ilya on 30.11.2014.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository repository;

    @Override
    public <S extends Comment> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public <S extends Comment> List<S> save(Iterable<S> entites) {
        return repository.save(entites);
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Comment> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Comment findOne(String id) {
        return repository.findOne(id);
    }

    @Override
    public boolean exists(String id) {
        return repository.exists(id);
    }

    @Override
    public Iterable<Comment> findAll(Iterable<String> ids) {
        return repository.findAll(ids);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public void delete(Comment entity) {
        repository.delete(entity);
    }

    @Override
    public void delete(Iterable<? extends Comment> entities) {
        repository.delete(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Comment updateCommentText(String id, String text) {
        return repository.updateCommentText(id,text);
    }

    @Override
    public Comment updateCommentComments(String id, String newCommentID) {
        return repository.updateCommentComments(id, newCommentID);
    }
    @Override
    public Comment updateCommentLikesInc(String id) {
        return repository.updateCommentLikesInc(id);
    }

    @Override
    public Comment updateCommentLikesDec(String id) {
        return repository.updateCommentLikesDec(id);
    }
}
