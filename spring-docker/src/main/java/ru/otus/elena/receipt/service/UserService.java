package ru.otus.elena.receipt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.elena.receipt.dao.RoleRepository;
import ru.otus.elena.receipt.dao.UserRepository;
import ru.otus.elena.receipt.domain.Role;
import ru.otus.elena.receipt.domain.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(String username, String password, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void saveUser(String username, String password) {
        Set<Role> roles = new HashSet<>();
        Role roleUser = roleRepository.findByRole("ROLE_USER");
        roles.add(roleUser);
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void saveAdmin(String username, String password) {
        Set<Role> roles = new HashSet<>();
        Role roleUser = roleRepository.findByRole("ROLE_USER");
        Role roleAdmin = roleRepository.findByRole("ROLE_ADMIN");
        roles.add(roleUser);
        roles.add(roleAdmin);
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
