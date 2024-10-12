package org.example.repository.interfaces;

import org.example.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    User update(User user);

    Boolean delete(User user);
}
