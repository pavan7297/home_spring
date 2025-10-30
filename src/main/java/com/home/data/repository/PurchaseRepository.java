package com.home.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.home.data.model.Purchase;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {
}
