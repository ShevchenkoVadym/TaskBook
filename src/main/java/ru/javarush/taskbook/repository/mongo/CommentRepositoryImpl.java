package ru.javarush.taskbook.repository.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.javarush.taskbook.model.Comment;
import ru.javarush.taskbook.repository.CommentRepositoryCustom;

/**
 * Created by Ilya on 02.12.2014.
 */
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @Autowired
    private MongoTemplate MT;

    @Override
    public Comment updateCommentText(String id, String text) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("text",text).currentTimestamp("modifyedDate");
        return MT.findAndModify(query,update,new FindAndModifyOptions().returnNew(true),Comment.class);
    }

    @Override
    public Comment updateCommentComments(String id, String newCommentID) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().push("comments", newCommentID);
        return MT.findAndModify(query,update,new FindAndModifyOptions().returnNew(true),Comment.class);
    }

    @Override
    public Comment updateCommentLikesDec(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("likes",-1);
        return MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Comment.class);
    }

    @Override
    public Comment updateCommentLikesInc(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("likes",1);
        return MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Comment.class);
    }


}
