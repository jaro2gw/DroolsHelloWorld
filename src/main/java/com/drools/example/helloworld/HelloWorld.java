package com.drools.example.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HelloWorld {
    public enum Type {
        HELLO, GOODBYE
    }

    private Type type;
}
