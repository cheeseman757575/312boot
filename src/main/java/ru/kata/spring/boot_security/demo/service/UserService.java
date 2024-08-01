package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User getUserById(int id);

    void saveUser (User user);

    User updateUser(int id, String newName, String newLastname);

    void deleteUser(int id);
}
