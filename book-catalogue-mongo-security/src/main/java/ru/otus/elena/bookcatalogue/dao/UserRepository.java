package ru.otus.elena.bookcatalogue.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.elena.bookcatalogue.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

}
