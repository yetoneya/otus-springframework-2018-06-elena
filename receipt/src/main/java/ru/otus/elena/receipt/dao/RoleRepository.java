package ru.otus.elena.receipt.dao;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.elena.receipt.domain.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}