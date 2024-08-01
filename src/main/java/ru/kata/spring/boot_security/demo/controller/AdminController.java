package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private final UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/users/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/create_user";
    }
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "admin/save_user";
    }

        @GetMapping("/users/edit/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public String editUserForm(@PathVariable int id, Model model) {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "admin/edit_user";
        }

    @PostMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user.getUserName(), user.getLastName());
        return "/admin/users";
    }


    @GetMapping("/users/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "/admin/users";
    }











}
