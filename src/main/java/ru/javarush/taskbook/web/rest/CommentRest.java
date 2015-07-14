package ru.javarush.taskbook.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.javarush.taskbook.logger.LoggerWrapper;
import ru.javarush.taskbook.model.Comment;
import ru.javarush.taskbook.service.CommentService;
import java.util.List;

/**
 * Created by Ilya on 01.12.2014.
 */
@RestController
@RequestMapping("/rest/comments")
public class CommentRest {

    private static final LoggerWrapper LOG = LoggerWrapper.get(CommentRest.class);

    @Autowired
    private CommentService service;

    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comment> getAll() {
        LOG.info("getAll");
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment get(@PathVariable("id") String id) {
        LOG.info("get {}", id);
        return service.findOne(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(Comment comment) {
        LOG.info("save ", comment);
        service.save(comment);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id){
        LOG.info("delete ", id);
        service.delete(service.findOne(id));
    }
}
