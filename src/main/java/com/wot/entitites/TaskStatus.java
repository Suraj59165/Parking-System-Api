package com.wot.entitites;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatus {
    @Id
    private int id;
    private String status;

    @JsonIgnore
    @OneToOne(mappedBy = "status")
    private Task task;
    
    @JsonIgnore
    @OneToOne(mappedBy = "status")
    private PendingTasks pendingTasks;
    
	
}
