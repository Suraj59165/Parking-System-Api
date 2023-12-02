package com.wot.repositories;

import com.wot.entitites.PendingClients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PendingClientsRepo extends JpaRepository<PendingClients, Integer> {

    @Query("select u from PendingClients u")
    List<PendingClients> getAllClientForApproval();


}
