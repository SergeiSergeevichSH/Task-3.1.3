package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.exception_hendling.NoSuchUserException;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRESTController {

    private final UserService userService;

    @Autowired
    public AdminRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/addusers")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь ["+user.getName()+"] создан и добавлен в БД");
    }

    @PatchMapping("/user/update/{id}")
    public void updateUser(@PathVariable("id") int id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
        throw new NoSuchUserException("Пользователь  с ID-[" +id + "] успешно обновлен в БД");
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("Пользователь с ID-[" +id + "] успешно удален", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Произошла ошибка при удалении пользователя: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok("Аутентификация прошла успешно!");
        } else {
            return ResponseEntity.status(401).body("Ошибка аутентификации");
        }
    }
}
