package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserDAO {
    List<User> getAllUsers ();

    User getUserById (int id);

    void saveUser (User user);

    void updateUser (int id, String newName, String newLastName);

    void deleteUser(int id);
}
