package com.drools.example.computer.component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MainBoard extends AbstractComponent {
    private final Socket  socket;
    private final boolean graphicsIntegrated;
    private final boolean soundIntegrated;

    public MainBoard(String name, Socket socket, boolean graphicsIntegrated, boolean soundIntegrated) {
        super(name);
        this.socket = socket;
        this.graphicsIntegrated = graphicsIntegrated;
        this.soundIntegrated = soundIntegrated;
    }
}
