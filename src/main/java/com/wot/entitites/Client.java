package com.wot.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
public class Client {
    @Id
    @Column(name = "client_id")
    private int id;
    private String name;
    private String email;
    private String crm;
    private String password;
    private String phone;
    private String address;
    private String orgName;
    private String contactperson;
    private String rupees;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private List<Task> tasklist;


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private List<PendingTasks> pendingTasks;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles = new ArrayList<Roles>();

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "assigned_manager")
    private Manager manager;

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<ClientAccountBalance> clientAccountBalances;


}
