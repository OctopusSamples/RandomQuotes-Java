package com.octopus.repository;

import com.octopus.entity.Quote;
import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Quote, Long> {
    Quote findById(long id);
}
