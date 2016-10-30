package hu.bartl.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DescriptionRepository extends MongoRepository<BoardGameFlatDescription, ObjectId> {

    BoardGameFlatDescription findByBggId(int bggId);
}
