package ru.otus.elena.bookcatalogue.service;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.elena.bookcatalogue.dao.UserRepository;
import ru.otus.elena.bookcatalogue.domain.User;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public void saveUser(String username, String password, Set<String> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRoles(roles);
        userRepository.save(user);
    }
    
    public void deleteUserById(String id){
        userRepository.deleteById(id);
    }
    
    public List<User> findAll(){
        return userRepository.findAll();
    }

}
