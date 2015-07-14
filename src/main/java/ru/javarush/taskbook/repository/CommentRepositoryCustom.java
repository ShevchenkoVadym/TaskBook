package ru.javarush.taskbook.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import ru.javarush.taskbook.model.Comment;

/**
 * Created by Ilya on 02.12.2014.
 */
@NoRepositoryBean
public interface CommentRepositoryCustom {

    Comment updateCommentText(String id, String text);
    Comment updateCommentComments(String id, String newCommentID);
    Comment  updateCommentLikesInc(String id);
    Comment  updateCommentLikesDec(String id);
}
