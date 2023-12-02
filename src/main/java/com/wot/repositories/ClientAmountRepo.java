package com.wot.repositories;

import com.wot.entitites.Task;
import com.wot.entitites.TaskAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAmountRepo extends JpaRepository<TaskAmount,Integer> {
}
