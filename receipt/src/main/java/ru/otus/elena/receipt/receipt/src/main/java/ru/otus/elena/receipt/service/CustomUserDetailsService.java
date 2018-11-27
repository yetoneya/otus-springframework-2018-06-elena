package ru.otus.elena.receipt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.elena.receipt.dao.RoleRepository;
import ru.otus.elena.receipt.dao.UserRepository;
import ru.otus.elena.receipt.domain.Role;
import ru.otus.elena.receipt.domain.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired    
    private UserRepository userRepository;
    @Autowired
private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {           
            List<GrantedAuthority> authorities = new ArrayList<>();
            Set<Role>r=user.getRoles();
            user.getRoles().forEach(role->authorities.add(new  SimpleGrantedAuthority(role.getRole())));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }
}
