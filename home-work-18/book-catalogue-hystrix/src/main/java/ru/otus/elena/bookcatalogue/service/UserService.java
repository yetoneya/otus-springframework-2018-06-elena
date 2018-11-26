package ru.otus.elena.bookcatalogue.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.elena.bookcatalogue.dao.RoleRepository;
import ru.otus.elena.bookcatalogue.dao.UserRepository;
import ru.otus.elena.bookcatalogue.domain.Role;
import ru.otus.elena.bookcatalogue.domain.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(String username, LocalDate birthday, String password, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setBirthday(birthday);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void saveUser(String username, String birthday, String password) {
        Set<Role> roles = new HashSet<>();
        Role roleUser = roleRepository.findByRole("ROLE_USER");
        roles.add(roleUser);
        LocalDate date = LocalDate.parse(birthday);
        LocalDate checkDate = LocalDate.now().minusYears(18);
        if (checkDate.isAfter(date)) {
            Role roleAdult = roleRepository.findByRole("ROLE_ADULT");
            roles.add(roleAdult);
        }
        User user = new User();
        user.setUsername(username);
        user.setBirthday(date);
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

    public void refreshUser(String id) {
        User user = userRepository.findById(id).get();
        if (user.getBirthday().isBefore(LocalDate.now().minusYears(18))) {
            Role roleAdult = roleRepository.findByRole("ROLE_ADULT");
            user.getRoles().add(roleAdult);
            userRepository.save(user);
        }
    }

}
