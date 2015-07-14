package ru.javarush.taskbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.javarush.taskbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findById(int id);

    @Query(value = "SELECT email FROM users", nativeQuery = true)
    List<String> findAllEmails();

    @Query(value = "SELECT email FROM users INNER JOIN roles ON users.id = roles.user_id WHERE role_id = 0 OR role_id = 1", nativeQuery = true)
    List<String> findAdminModerEmails();

    Page<User> findByUsernameContaining(String nameQuery, Pageable pageable);

}
