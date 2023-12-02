package com.wot.repositories;

import com.wot.entitites.Task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TaskRepo extends JpaRepository<Task, Integer> {
	@Query("select u from Task u where u.client.id=:id and u.status.status=:status")
	List<Task> getTaskOfUser(@Param(value = "id") int clientId,@Param(value = "status") String status);


	@Query("select  u from Task  u where u.status.status=:name")
    List<Task> getTaskByStatusName(@Param(value = "name") String name);


}
