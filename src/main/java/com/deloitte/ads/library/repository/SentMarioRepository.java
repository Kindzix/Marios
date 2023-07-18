package com.deloitte.ads.library.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SentMarioRepository extends CrudRepository<SentMario, Long> {
    @Query("SELECT sm FROM SENT_MARIO sm JOIN sm.recipients r WHERE r.email = :recipientEmail")
    Set<SentMario> findByRecipientsEmail(String recipientEmail);

    @Query("SELECT sm FROM SENT_MARIO sm JOIN sm.mario m JOIN sm.sender s WHERE s.email = :senderEmail")
    Set<SentMario> findBySenderEmail(String senderEmail);
}


