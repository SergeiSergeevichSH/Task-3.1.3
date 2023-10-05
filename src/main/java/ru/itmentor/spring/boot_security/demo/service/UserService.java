package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getById(int id);

//    User getByUsername(String username);

    void saveUser(User user);

    void deleteUser(int id);

    void updateUser(int id, User user);
}
