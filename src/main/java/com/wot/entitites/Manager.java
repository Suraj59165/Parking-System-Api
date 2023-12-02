package com.wot.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Manager {
    @Id
    private int id;
    private String name;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles = new ArrayList<Roles>();

    @OneToMany(mappedBy = "manager")
    private List<Client> client = new ArrayList<Client>();

	    

}
