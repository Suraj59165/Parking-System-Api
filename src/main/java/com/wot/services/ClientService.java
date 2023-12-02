package com.wot.services;

import com.wot.entitites.Client;

import com.wot.entitites.PendingClients;
import com.wot.entitites.PendingTasks;

import com.wot.entitites.Task;
import com.wot.payloads.RoleStatusCode;
import com.wot.repositories.*;

import java.io.IOException;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private PendingClientsService pendingClientsService;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private RoleService roleService;
    @Autowired
    @Lazy
    private FileHandlingService fileHandlingService;
    @Autowired
    private PendingTasksService pendingTasksService;
    @Autowired
    private TaskRepo taskRepo;


    // sending users to database for approval
    public Boolean sendClientForApproval(PendingClients pendingClients) {
        return pendingClientsService.saveIncomingClientDetails(pendingClients);

    }

    // sending tasks to database for approval
    public Boolean saveClientTask(PendingTasks pendingTasks, String email, MultipartFile[] files) throws IOException {
        if (fileHandlingService.saveClientTasks(files, email, pendingTasks)) {
            return  true;
        } else return false;

    }

    // for saving the user data for login which is approved by the manager
    public boolean savingApprovedClientData(Client client, String managerId) {
        if (client != null && managerId != null) {
            client.setManager(managerService.getManagerById(Integer.parseInt(managerId)));
            client.setRoles(List.of(roleService.getRoleForUser(RoleStatusCode.CLIENT)));

            if (clientRepo.save(client) != null) {
                pendingClientsService.deleteClientById(client.getId());
                return true;
            }
           else return  false;


        }
        else return false;
    }




    public Client getCurrentUser(String name) {
        return clientRepo.getClientByEmail(name);
    }

    public Client getClientById(int id) {
        return clientRepo.getClientById(id);
    }

    public List<PendingTasks> getAllPendingTasksOfClient(String email)
    {
        return  pendingTasksService.getPendingTaskOfClientByEmail(email);
    }

    public List<Task> getAllCompletedTaskForPayment(String name)
    {
        return taskRepo.getTaskByStatusName(name);
    }

}
