package ru.itmentor.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.exception_hendling.NoSuchUserException;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository usersRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }
    @Override
    public User getById(int id) {
        Optional<User> userOptional = usersRepository.findById(id);
        System.out.println("userOptional = " + userOptional);
        return userOptional.orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        return (User) usersRepository.getUserByUsername(username);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        Set<Role> userRoles = new HashSet<>();
        for (Role roleName : user.getRoles()) {
            Role existingRole = roleRepository.findByRole(roleName.getRole());
            if (existingRole != null) {
                userRoles.add(existingRole);
            }
        }
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUser(int id, User user) {
        Optional<User> existingUser = usersRepository.findById(id);
        System.out.println("existingUser = " + existingUser);
        if (existingUser.isEmpty()) {
            throw new NoSuchUserException("Пользователь  с таким ID " + id + " в БД не найден");
        }
        User userToUpdate = existingUser.get();
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setAge(user.getAge());
        usersRepository.save(userToUpdate);
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        if (!usersRepository.existsById(id)) {
            throw new NoSuchUserException("Пользователь с ID-[" + id + "] в БД не найден");
        }
        usersRepository.deleteById(id);
    }
}
