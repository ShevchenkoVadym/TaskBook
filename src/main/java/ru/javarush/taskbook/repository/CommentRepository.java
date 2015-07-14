package ru.javarush.taskbook.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.javarush.taskbook.model.Comment;

/**
 * Created by Ilya on 30.11.2014.
 */
public interface CommentRepository extends MongoRepository<Comment,String>, CommentRepositoryCustom {

}
