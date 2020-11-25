package com.drools.example.measurement;

import com.drools.example.AbstractDroolsTest;
import lombok.val;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MeasurementTest extends AbstractDroolsTest {
    @Override
    protected String getRulesName() {
        return "ksession-rules-measurement";
    }

    @Before
    public void setUp() {
        super.setUp();
        BasicConfigurator.configure();
        logger = Logger.getLogger(MeasurementTest.class);
    }

    private long countFires() {
        return kieSession.getObjects()
                         .stream()
                         .filter(object -> object instanceof Fire)
                         .count();
    }

    @Test
    public void measurementNoSmokeLowTemperatureTest() {
        val measurement = new Measurement(false, 20);
        kieSession.insert(measurement);
        kieSession.fireAllRules();

        val count = countFires();
        assertEquals(0L, count);
    }

    @Test
    public void measurementSmokeAndLowTemperatureTest() {
        val measurement = new Measurement(true, 20);
        kieSession.insert(measurement);
        kieSession.fireAllRules();

        val count = countFires();
        assertEquals(1L, count);
    }

    @Test
    public void measurementNoSmokeHighTemperatureTest() {
        val measurement = new Measurement(true, 180);
        kieSession.insert(measurement);
        kieSession.fireAllRules();

        val count = countFires();
        assertEquals(1L, count);
    }

    @Test
    public void measurementSmokeAndHighTemperatureTest() {
        val measurement = new Measurement(true, 180);
        kieSession.insert(measurement);
        kieSession.fireAllRules();

        val count = countFires();
        assertEquals(1L, count);
    }
}