package com.nera.demo.repository;

import com.nera.demo.entity.TransactionMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionMongoRepository extends MongoRepository<TransactionMongo, String> {
}
