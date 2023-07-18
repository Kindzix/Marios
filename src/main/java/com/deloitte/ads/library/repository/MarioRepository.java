package com.deloitte.ads.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarioRepository extends CrudRepository<Mario, Long> {
    boolean existsByType(String type);
}
