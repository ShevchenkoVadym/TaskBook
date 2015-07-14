package ru.javarush.taskbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.javarush.taskbook.model.Task;

import java.util.List;

/**
 * Created by Ilya on 30.11.2014.
 */
public interface TaskRepository extends MongoRepository<Task, String>, TaskRepositoryCustom {
}
