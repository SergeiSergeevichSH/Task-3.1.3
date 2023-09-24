package ru.itmentor.spring.boot_security.demo.configs;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Component
public class DataInitializer implements CommandLineRunner {

    private final UserServiceImpl userService;
    private final EntityManager entityManager;

    @Autowired
    public DataInitializer(UserServiceImpl userService,
                           EntityManager entityManager) {
        this.userService = userService;
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void run(String... args) {

        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        Session session = entityManager.unwrap(Session.class);

        session.save(roleUser);
        session.save(roleAdmin);

        User user1 = new User();
        user1.setName("Антон");
        user1.setSurname("Антонов");
        user1.setUsername("anton");
        user1.setAge(30);
        user1.setPassword("4321");
        user1.setRoles(new HashSet<>(Set.of(roleUser)));

        User admin = new User();
        admin.setName("Сергей");
        admin.setSurname("Сергеев");
        admin.setUsername("shish81");
        admin.setAge(35);
        admin.setPassword("1234");
        admin.setRoles(new HashSet<>(Set.of(roleAdmin,roleUser)));

        userService.saveUser(admin);
        userService.saveUser(user1);

    }
}


