package com.wot.repositories;

import com.wot.entitites.TaskStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepo extends JpaRepository<TaskStatus, Integer> {

    TaskStatus findByStatus(String status);

   
   


}
