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
public class UserRESTController {


    private final UserService userService;

    @Autowired
    public UserRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User getById(@PathVariable int id) {

        User user = userService.getById(id);

        if (user == null) {
            throw new NoSuchUserException("Пользователь  с таким ID" + id + "в БД не найден");
        }
        return user;
    }


}
