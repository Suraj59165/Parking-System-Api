package com.wot.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @Column(name = "task_id")
    private int id;
    private String tasktype;
    private String count;
    private String requirements;
    private String referencestyle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date deadline;
    private String charges;
    private String attachment;
    private String query;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    private TaskStatus status;

    @OneToOne(mappedBy = "task",fetch = FetchType.EAGER)
    private TaskAmount taskAmount;

	
}
