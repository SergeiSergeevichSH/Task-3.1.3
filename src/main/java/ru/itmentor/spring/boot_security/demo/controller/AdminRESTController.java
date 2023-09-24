package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.exception_hendling.NoSuchUserException;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRESTController {

    private final UserService userService;

    @Autowired
    public AdminRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return userList;
    }

    @PostMapping("/addusers")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь ["+user.getName()+"] создан и добавлен в БД");
    }

    @PatchMapping("/user/update/{id}")
    public void update( @PathVariable("id") int id, @RequestBody User updatedUser) {
        userService.update(id, updatedUser);
        throw new NoSuchUserException("Пользователь  с ID-[" +id + "] успешно обновлен в БД");
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>("Пользователь с ID-[" +id + "] успешно удален", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Произошла ошибка при удалении пользователя: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
