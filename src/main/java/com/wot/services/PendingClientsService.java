package com.wot.services;

import com.wot.entitites.PendingClients;
import com.wot.repositories.PendingClientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingClientsService {

    @Autowired
    private PendingClientsRepo pendingClientsRepo;

    public List<PendingClients> getAllClientsForApproval() {
        return pendingClientsRepo.getAllClientForApproval();
    }

    public boolean saveIncomingClientDetails(PendingClients pendingClients)
    {
       if(pendingClients !=null)
       {
           pendingClientsRepo.save(pendingClients);
           return  true;
       }
       return  false;
    }

    public void deleteClientById(int id)
    {
        pendingClientsRepo.deleteById(id);
    }


}
