package com.wot.repositories;

import com.wot.entitites.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Roles findByName(String name);


}
