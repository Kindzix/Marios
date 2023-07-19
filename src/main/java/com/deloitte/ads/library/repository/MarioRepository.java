package com.deloitte.ads.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MarioRepository extends CrudRepository<Mario, Long> {
    boolean existsByType(String type);
    Mario findByUuid(UUID uuid);
}
