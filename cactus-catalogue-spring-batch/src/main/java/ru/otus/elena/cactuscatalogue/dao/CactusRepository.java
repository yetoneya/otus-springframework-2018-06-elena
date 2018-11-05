package ru.otus.elena.cactuscatalogue.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.elena.cactuscatalogue.domain.Cactus;

@Repository
public interface CactusRepository extends MongoRepository<Cactus, Long>{
     
    public List<Cactus> findByCactusname(String cactusname);
    
}
