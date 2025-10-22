package com.paklog.yard.infrastructure.persistence.repository;

import com.paklog.yard.infrastructure.persistence.document.TrailerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface MongoTrailerRepository extends MongoRepository<TrailerDocument, String> {
    Optional<TrailerDocument> findByTrailerNumber(String trailerNumber);
    List<TrailerDocument> findByStatus(String status);
    List<TrailerDocument> findByCarrierId(String carrierId);
}
