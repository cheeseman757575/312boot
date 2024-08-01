package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String name);

    @EntityGraph(attributePaths = "roles")
    @NonNull
    Optional<User> findById(@NonNull Long id);

    @EntityGraph(attributePaths = "roles")
    @Query("SELECT DISTINCT u FROM User u")
    @NonNull
    List<User> findAll();

//    @EntityGraph(attributePaths = "roles")
//    Optional<User> findUserWithRolesByUserName(String name);
}
