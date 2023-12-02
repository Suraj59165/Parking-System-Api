package com.wot.entitites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private long amount;
    private String notes;
    private String orderId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date time;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Task task;
}
