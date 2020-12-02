package com.drools.example.computer;

import com.drools.example.AbstractDroolsTest;
import org.junit.Test;

public class ComputerTest extends AbstractDroolsTest {
    @Test
    public void promptTest() {
        kieSession.insert(new Computer());
        kieSession.fireAllRules();
    }
}