package com.example.practicelab.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Projection {
    private int year;
    private double startingAmount;
    private double interest;
    private double endingBalance;
}
