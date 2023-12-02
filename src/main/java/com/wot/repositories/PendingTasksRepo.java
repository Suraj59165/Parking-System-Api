package com.wot.repositories;

import com.wot.entitites.PendingTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PendingTasksRepo extends JpaRepository<PendingTasks, Integer> {


    @Query("select u from PendingTasks u where u.client.id=:id")
    List<PendingTasks> getTaskOfCurrentUser(@Param(value = "id") int id);


    PendingTasks findIncomingTaskDetailsById(int id);
    
    @Query("select u from PendingTasks u where u.status.status=:status ")
    List<PendingTasks> getAllTasksByStatus(@Param(value = "status") String status);

    @Query("select u from  PendingTasks  u where u.client.email=:email")
    List<PendingTasks> getPendingTaskOfClientByEmail(@Param(value = "email") String email);
}
