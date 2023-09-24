package ru.itmentor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itmentor.spring.boot_security.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails getUserByUsername(@Param("username") String username);
}
