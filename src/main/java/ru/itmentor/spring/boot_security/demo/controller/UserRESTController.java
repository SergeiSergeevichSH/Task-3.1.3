package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.exception_hendling.NoSuchUserException;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;


@RestController
@RequestMapping()
public class UserRESTController {

    private final UserService userService;

    @Autowired
    public UserRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public User getById(@PathVariable int id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new NoSuchUserException("Пользователь  с таким ID " + id + " в БД не найден");
        }
        return user;
    }
}
