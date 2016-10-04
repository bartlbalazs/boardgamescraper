package hu.bartl.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Document(collection = "boardgame")
public interface DescriptionRepository extends MongoRepository<BoardGameFlatDescription, ObjectId> {

}
