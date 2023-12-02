package com.wot.entitites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientAccountBalance {
    @Id
    private String id;
    private String accountNumber;
    private String balance;
    private String mode;
    @ManyToOne()
    private Client client;
}
