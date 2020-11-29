package com.drools.example.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Measurement {
    private boolean smoke;
    private double  temperature;
}
