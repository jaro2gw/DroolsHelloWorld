package com.drools.example.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Phone {
    public static final String EMERGENCY_NUMBER = "112";

    private String number;
}
