package com.wot.services;

import com.wot.entitites.Client;
import com.wot.entitites.TaskAmount;
import com.wot.entitites.Task;
import com.wot.entitites.TaskStatus;
import com.wot.payloads.TaskStatusCode;
import com.wot.repositories.ClientAmountRepo;
import com.wot.repositories.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private ClientService clientService;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private PendingTasksService pendingTasksService;
    @Autowired
    private ClientAmountRepo clientAmountRepo;

    public List<Task> getTaskOfUser(Client client,String status) {
    	return taskRepo.getTaskOfUser(client.getId(),status);
    }

    public boolean getApprovedTaskForSaving(Task task, int id) {
        task.setClient(clientService.getClientById(id));
        task.setStatus(taskStatusService.getTaskStatusByStatus(TaskStatusCode.APPROVED));
        if (taskRepo.save(task) != null) {
            pendingTasksService.deleteTaskById(task.getId());
            return true;
        }
        return false;
    }


    public List<Task> getAllTasksByStatus(String status) {
        return taskRepo.getTaskByStatusName(status);
    }

    public boolean setClientAmountForTask(int taskId,int amount,String notes)
    {
       Optional<Task> task= taskRepo.findById(taskId);
       if(task.isPresent())
       {

          Task task1= task.get();
          task1.setStatus(taskStatusService.getTaskStatusByStatus(TaskStatusCode.COMPLETED));
           TaskAmount taskAmount=new TaskAmount();
           taskAmount.setAmount(amount);
           taskAmount.setNotes(notes);
           taskAmount.setTime(new Date());
           taskAmount.setTask(task1);
           clientAmountRepo.save(taskAmount);
           return  true;
       }
       return  false;
    }
}
