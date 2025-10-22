package com.paklog.yard.domain.repository;

import com.paklog.yard.domain.aggregate.Trailer;
import com.paklog.yard.domain.valueobject.TrailerStatus;
import java.util.*;

public interface TrailerRepository {
    Trailer save(Trailer trailer);
    Optional<Trailer> findById(String id);
    Optional<Trailer> findByTrailerNumber(String trailerNumber);
    List<Trailer> findAll();
    List<Trailer> findByStatus(TrailerStatus status);
    List<Trailer> findByCarrierId(String carrierId);
    List<Trailer> findActiveTrailers();
    void deleteById(String id);
}
