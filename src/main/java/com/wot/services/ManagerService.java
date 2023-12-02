package com.wot.services;

import com.wot.entitites.Client;
import com.wot.entitites.Manager;
import com.wot.entitites.Task;
import com.wot.repositories.ManagerRepo;
import com.wot.repositories.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepo managerRepo;
    @Autowired
    private TaskRepo taskRepo;


    public List<Manager> getAllManagers() {
        return managerRepo.findAll();
    }

    public Manager getManagerById(int id) {
        return managerRepo.getManagerById(id);
    }

    public Manager getCurrentLoggedInManager(String email) {
        return managerRepo.getManagerByEmail(email);
    }

    public void saveManagers(List<Manager> managers) {
        managerRepo.saveAll(managers);
    }

    public void saveSingleManager(Manager manager) {
        managerRepo.save(manager);
    }

    public List<Task> getAllApprovedTaskOfManagerClient(String email, String status) {
        List<Task> tasks = new ArrayList<>();
        Manager manager = this.getCurrentLoggedInManager(email);
        for (Client client : manager.getClient()) {
            for (Task task : taskRepo.getTaskOfUser(client.getId(), status)) {
                tasks.add(task);
            }
        }
        return tasks;


    }


}
