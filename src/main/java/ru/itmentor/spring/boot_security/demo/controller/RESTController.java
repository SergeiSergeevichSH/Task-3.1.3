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
public class RESTController {

    private final UserService userService;

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return userList;
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable int id) {

        User user = userService.getById(id);

        if (user == null) {
            throw new NoSuchUserException("Пользователь  с таким ID" + id + "в БД не найден");
        }
        return user;
    }

    @PostMapping("/addusers")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь создан и добавлен в БД");
    }

//    @PatchMapping("/users/{id}")
//    public ResponseEntity<String> editUser(@PathVariable("id") int id, @RequestBody User updatedUser) {
//        User existingUser = userService.getById(id);
//        if (existingUser == null) {
//            return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
//        }
//        existingUser.setName(updatedUser.getName());
//        existingUser.setSurname(updatedUser.getSurname());
//        existingUser.setAge(updatedUser.getAge());
//
//        try {
//            userService.update(existingUser);
//            return new ResponseEntity<>("Пользователь успешно обновлен", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Произошла ошибка при обновлении пользователя: " + e.getMessage(),
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PatchMapping("/users/{id}")
    public void editUser(@PathVariable("id") int id, @RequestBody User updatedUser) {
        User existingUser = userService.getById(id);
        if (existingUser == null) {
            throw new NoSuchUserException("Пользователь  с таким ID " + id + " в БД не найден");
        }

        existingUser.setName(updatedUser.getName());
        existingUser.setSurname(updatedUser.getSurname());
        existingUser.setAge(updatedUser.getAge());

        userService.update(existingUser);
        throw new NoSuchUserException("Пользователь  с ID " + id + " успешно обновлен в БД");

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        User existingUser = userService.getById(id);

        if (existingUser == null) {
            return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
        }

        try {
            userService.delete(id);
            return new ResponseEntity<>("Пользователь успешно удален", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Произошла ошибка при удалении пользователя: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
