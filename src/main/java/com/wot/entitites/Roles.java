package com.wot.entitites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @Column(name = "role_id")
    private int id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<Client> clients = new ArrayList<Client>();

    @ManyToMany(mappedBy = "roles")
    private List<Manager> managers;

	    

}
