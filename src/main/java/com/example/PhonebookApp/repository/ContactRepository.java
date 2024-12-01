package com.example.PhonebookApp.repository;

import com.example.PhonebookApp.model.Contact;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact,String> {
}
