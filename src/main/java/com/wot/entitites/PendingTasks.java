package com.wot.entitites;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class PendingTasks {
    @Id
    @Column(name = "incoming_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String tasktype;
    private String count;
    private String requirements;
    private String referencestyle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date deadline;
    private String charges;
    private String query;
    private String attachment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status")
    private TaskStatus status;

    
    @OneToMany(mappedBy = "pendingTasks", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TaskFiles> taskFiles;


}
