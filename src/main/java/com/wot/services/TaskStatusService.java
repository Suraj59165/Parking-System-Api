package com.wot.services;

import com.wot.entitites.TaskStatus;
import com.wot.repositories.TaskStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {
    @Autowired
    private TaskStatusRepo taskStatusRepo;

    //saving the task status first time if not saved
    public boolean saveAllTaskStatus(List<TaskStatus> allTaskStatus) {
        taskStatusRepo.saveAll(allTaskStatus);
        return true;
    }
    


    public void saveAllTaskStatus(TaskStatus taskStatus) {
        taskStatusRepo.save(taskStatus);
    }


    
    public TaskStatus getTaskStatusByStatus(String status)
    {
    	return taskStatusRepo.findByStatus(status);
    }


}
