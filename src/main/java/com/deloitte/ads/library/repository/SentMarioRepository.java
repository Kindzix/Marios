package com.deloitte.ads.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface SentMarioRepository extends CrudRepository<SentMario, Long> {
    SentMario findByUuid(UUID uuid);

    Set<SentMario> findByRecipientsContains(User recipient);

    Set<SentMario> findBySender(User sender);
}
