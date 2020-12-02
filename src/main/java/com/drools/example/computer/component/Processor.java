package com.drools.example.computer.component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Processor extends AbstractComponent {
    private final Socket socket;

    public Processor(String name, Socket socket) {
        super(name);
        this.socket = socket;
    }
}
