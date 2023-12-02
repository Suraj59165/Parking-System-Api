package com.wot.security;

import com.wot.entitites.Client;
import com.wot.entitites.Manager;
import com.wot.repositories.ClientRepo;
import com.wot.repositories.ManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailServiceImp implements UserDetailsService {
    @Autowired
    private ClientRepo clientRepository;
    @Autowired
    private ManagerRepo managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.getClientByEmail(username);
        Manager manager = managerRepository.getManagerByEmail(username);

        if (client != null) {

            return new CustomUserDetails(client);
        } else if (manager != null) {
            return new CustomUserDetails(manager);
        } else {
            throw new UsernameNotFoundException("not available");

        }

    }

}
