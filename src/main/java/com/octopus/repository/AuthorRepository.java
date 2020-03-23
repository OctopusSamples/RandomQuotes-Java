package com.octopus.repository;

import com.octopus.entity.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Author findById(long id);
}
