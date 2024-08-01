package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Transactional
    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }
    @Transactional
    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }
    @Transactional
    @Override
    public void updateUser(int id, String newName, String newLastName) {
        User user = entityManager.find(User.class, id);
        if(user != null){
            user.setUserName(newName);
            user.setLastName(newLastName);
        }

    }
    @Transactional
    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);

    }
}

