package com.wot.repositories;

import com.wot.entitites.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepo extends JpaRepository<Manager, Integer> {

    // for authenticating the manager
    Manager getManagerByEmail(String email);

    Manager getManagerById(int id);


}
