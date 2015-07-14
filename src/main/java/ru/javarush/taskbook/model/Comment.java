
package ru.javarush.taskbook.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Document(collection = "comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String author;
    private String text;
    private Date creationDate;
    private Date modifyedDate;
    private int likes;
    private ArrayList<String> comments; //id collection not text

    public Comment(){}
    public Comment(String author, String text, Date creationDate, Date modifyedDate, int likes, ArrayList<String> comments) {
        this.author = author;
        this.text = text;
        this.creationDate = creationDate;
        this.modifyedDate = modifyedDate;
        this.likes = likes;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifyedDate() {
        return modifyedDate;
    }

    public void setModifyedDate(Date modifyedDate) {
        this.modifyedDate = modifyedDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (likes != comment.likes) return false;
        if (!author.equals(comment.author)) return false;
        if (comments != null ? !comments.equals(comment.comments) : comment.comments != null) return false;
        if (!creationDate.equals(comment.creationDate)) return false;
        if (!id.equals(comment.id)) return false;
        if (modifyedDate != null ? !modifyedDate.equals(comment.modifyedDate) : comment.modifyedDate != null)
            return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (modifyedDate != null ? modifyedDate.hashCode() : 0);
        result = 31 * result + likes;
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                ", modifyedDate=" + modifyedDate +
                ", likes=" + likes +
                ", comments=" + comments.toString() +
                '}';
    }
}
