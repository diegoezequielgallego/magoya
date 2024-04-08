package com.nera.demo.repository;

import com.nera.demo.dto.AccountDTO;
import com.nera.demo.entity.AccountMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountMongoRepository extends MongoRepository<AccountMongo, String> {
}
