package ru.otus.elena.bookcatalogue.domain;

import java.time.LocalDate;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private LocalDate birthday;
    private String password;
    @DBRef
    private Set<Role> roles;

    public User() {
    }

    public User(String username, LocalDate birthday, String password, Set<Role> roles) {
        this.username = username;
        this.birthday = birthday;
        this.password = password;
        this.roles = roles;
    }

    public User(String id, String username, LocalDate birthday, String password, Set<Role> roles) {
        this.id=id;
        this.username = username;
        this.birthday = birthday;
        this.password = password;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
