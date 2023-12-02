package com.wot.repositories;

import com.wot.entitites.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepo extends JpaRepository<Client, Integer> {

    //for authenticating the client
    Client getClientByEmail(String username);

    //for finding the clients which are allocated to managers
    @Query("select u from Client u where u.manager.id=:mid")
    List<Client> getClientsForManagers(@Param(value = "mid") int id);

    Client getClientById(int id);


}
