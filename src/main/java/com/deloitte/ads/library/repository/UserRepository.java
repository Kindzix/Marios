package com.deloitte.ads.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByUuid(UUID uuid);
    boolean existsByEmail(String email);

    User findByFirstName(String olga);
}
