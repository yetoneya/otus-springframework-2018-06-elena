package ru.otus.elena.bookcatalogue.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.elena.bookcatalogue.dao.UserRepository;
import ru.otus.elena.bookcatalogue.domain.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired    
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {           
            List<GrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role->authorities.add(new  SimpleGrantedAuthority(role)));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }
}
