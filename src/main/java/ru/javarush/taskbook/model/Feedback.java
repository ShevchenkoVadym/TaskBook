package ru.javarush.taskbook.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by prima on 15.03.15.
 */
public class Feedback {
    @JsonProperty("requester")
    private String requester;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("message")
    private String message;

    public Feedback(){}

    public Feedback(String requester, String subject, String message) {
        this.requester = requester;
        this.subject = subject;
        this.message = message;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
