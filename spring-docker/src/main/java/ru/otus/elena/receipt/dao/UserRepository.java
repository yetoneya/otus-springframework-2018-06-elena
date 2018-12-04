package ru.otus.elena.receipt.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.elena.receipt.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

}
