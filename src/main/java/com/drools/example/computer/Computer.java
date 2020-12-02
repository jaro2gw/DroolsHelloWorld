package com.drools.example.computer;

import com.drools.example.computer.component.*;
import lombok.Data;

@Data
public class Computer {
    private MainBoard   mainBoard;
    private Processor   processor;
    private GraphicCard graphicCard;
    private SoundCard   soundCard;
    private HardDisk    hardDisk;
}
