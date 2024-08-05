package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {  //CRUD управление пользователем
    List<User> getAllUser();

    Optional<User> findById(Long id);

    User saveUser (User user);

    User updateUser(User user);

    Optional<String> deleteUser(Long id);

    User getUserById(Long id);
}
