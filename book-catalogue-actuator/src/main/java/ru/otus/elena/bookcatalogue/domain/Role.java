package ru.otus.elena.bookcatalogue.domain;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
public class Role {

    @Id
    private String id;
    @Indexed(unique = true)
    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Role other = (Role) obj;
        return Objects.equals(role, other.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(role); 
    }
    

}
