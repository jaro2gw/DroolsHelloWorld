package com.drools.example.computer.component;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class AbstractComponent {
    private final String name;

    protected AbstractComponent(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
