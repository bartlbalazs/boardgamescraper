package hu.bartl.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DescriptionRepository extends MongoRepository<BoardGameFlatDescription, String> {

}
