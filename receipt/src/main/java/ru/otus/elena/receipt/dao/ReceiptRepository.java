package ru.otus.elena.receipt.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.elena.receipt.domain.Receipt;

@Repository
public interface ReceiptRepository extends MongoRepository<Receipt, String> {

    @Query(value = "{'type': {$regex : ?0}}")
    public List<Receipt> findByType(String type);

    @Query(value = "{'name': {$regex : ?0}}")
    public List<Receipt> findByName(String name);

    @Query(value = "{'component': {$regex : ?0}}")
    public List<Receipt> findByComponent(String component);

    @Query(value = "{'type': {$regex : ?0}, 'name': {$regex : ?1} }")
    public List<Receipt> findByTypeAndName(String type, String name);

    @Query(value = "{'type': {$regex : ?0}, 'component': {$regex : ?1}}")
    public List<Receipt> findByTypeAndComponent(String type, String component);

    @Query(value = "{'name': {$regex : ?0}, 'component': {$regex : ?1}}")
    public List<Receipt> findByNameAndComponent(String name, String component);

    @Query(value = "{'type': {$regex : ?0}, 'name': {$regex : ?1}, 'component': {$regex : ?2}}")
    public List<Receipt> findByTypeAndNameAndComponent(String type, String name, String component);

}
