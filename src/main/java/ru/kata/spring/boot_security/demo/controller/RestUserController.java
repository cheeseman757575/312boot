package ru.kata.spring.boot_security.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.TableGenerator;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "API для управления пользователями")
public class RestUserController {

    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Запрос всех юзеров", description = "Получение всех юзеров")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createdUser( @RequestBody User user) {
        User userCr = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCr);
    }

        @GetMapping("{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<User> getUserById(@PathVariable Long id, User user) {
            User userGet = userService.getUserById(id);

            return ResponseEntity.status(HttpStatus.OK).body(userGet);
        }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> updateUser(@PathVariable Long id, User user) {
       User userUp = userService.updateUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(userUp);
    }


    @DeleteMapping ("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return (ResponseEntity<String>) ResponseEntity.status(HttpStatus.NO_CONTENT);

    }








}
