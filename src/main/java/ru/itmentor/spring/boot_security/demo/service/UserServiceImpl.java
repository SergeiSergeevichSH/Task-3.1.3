package ru.itmentor.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public User getById(int id) {

        Optional<User> userOptional = usersRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return (User) usersRepository.getUserByUsername(username);
    }

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Set<Role> userRoles = user.getRoles();
//        List<String> roleNames = new ArrayList<>();
//        for (Role role : userRoles) {
//            roleNames.add(role.getRole());
//        }
//        user.setRoles(userRoles);
        usersRepository.save(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        Optional<User> existingUser = usersRepository.findById(user.getId());
        existingUser.ifPresent(userToUpdate -> {
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setRoles(user.getRoles());
            usersRepository.save(userToUpdate);
        });
    }
    @Transactional
    @Override
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

}
