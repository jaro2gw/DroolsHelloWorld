package com.drools.example.measurement;

import com.drools.example.AbstractDroolsTest;
import lombok.val;
import org.junit.Test;

import static com.drools.example.measurement.TestingUtils.countFires;
import static org.junit.Assert.assertEquals;

public class MeasurementTest extends AbstractDroolsTest {
    @Override
    protected String getRulesName() {
        return "ksession-rules-measurement";
    }

    @Test
    public void measurementNoSmokeLowTemperatureTest() {
        val measurement = new Measurement(false, 20);
        kieSession.insert(measurement);
        kieSession.fireAllRules();
        val count = countFires(kieSession);
        assertEquals(0, count);
    }

    @Test
    public void measurementSmokeAndLowTemperatureTest() {
        val measurement = new Measurement(true, 20);
        kieSession.insert(measurement);
        kieSession.fireAllRules();
        val count = countFires(kieSession);
        assertEquals(1, count);
    }

    @Test
    public void measurementNoSmokeHighTemperatureTest() {
        val measurement = new Measurement(true, 180);
        kieSession.insert(measurement);
        kieSession.fireAllRules();
        val count = countFires(kieSession);
        assertEquals(1, count);
    }

    @Test
    public void measurementSmokeAndHighTemperatureTest() {
        val measurement = new Measurement(true, 180);
        kieSession.insert(measurement);
        kieSession.fireAllRules();
        val count = countFires(kieSession);
        assertEquals(1, count);
    }
}