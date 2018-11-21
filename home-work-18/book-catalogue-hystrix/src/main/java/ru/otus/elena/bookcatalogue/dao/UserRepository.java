package ru.otus.elena.bookcatalogue.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.elena.bookcatalogue.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

}
