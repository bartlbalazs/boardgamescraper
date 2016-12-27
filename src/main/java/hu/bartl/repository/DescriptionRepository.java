package hu.bartl.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DescriptionRepository extends MongoRepository<BoardGameFlatDescription, ObjectId> {

    Optional<BoardGameFlatDescription> findByBggId(int bggId);
}
