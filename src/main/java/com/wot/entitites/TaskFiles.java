package com.wot.entitites;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskFiles {
    @Id
    private String id;
    private String type;
    private String name;
    private String originalFileName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "pending_task_id")
    private PendingTasks pendingTasks;


}
