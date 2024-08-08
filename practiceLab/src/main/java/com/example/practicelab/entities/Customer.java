package com.example.practicelab.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int customerNumber;
    private String name;
    private double deposit;
    private int years;
    private String savingsType;

    @Temporal(TemporalType.DATE)
    private Date createdAt;
}
