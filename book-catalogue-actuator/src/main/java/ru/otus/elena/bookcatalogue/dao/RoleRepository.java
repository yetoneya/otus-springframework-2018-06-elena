package ru.otus.elena.bookcatalogue.dao;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.elena.bookcatalogue.domain.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}