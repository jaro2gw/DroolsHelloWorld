package com.drools.example.measurement;

import com.drools.example.AbstractDroolsTest;
import lombok.val;
import org.junit.Test;

import static com.drools.example.measurement.TestingUtils.countFires;
import static com.drools.example.measurement.TestingUtils.retractInitialFire;
import static org.junit.Assert.assertEquals;

public class MeasurementTest extends AbstractDroolsTest {
    @Override
    protected String getRulesName() {
        return "ksession-rules-measurement";
    }

    @Test
    public void measurementNoSmokeLowTemperature() {
        retractInitialFire(kieSession);
        val measurement = new Measurement(false, 20);
        kieSession.insert(measurement);
        kieSession.fireAllRules();
        val fires = countFires(kieSession);
        assertEquals(0, fires);
    }

    @Test
    public void measurementSmokeAndLowTemperatureTest() {
        retractInitialFire(kieSession);
        val measurement = new Measurement(true, 20);
        kieSession.insert(measurement);
        kieSession.fireAllRules();
        val fires = countFires(kieSession);
        assertEquals(1, fires);
    }

    @Test
    public void measurementNoSmokeHighTemperatureTest() {
        retractInitialFire(kieSession);
        val measurement = new Measurement(false, 180);
        kieSession.insert(measurement);
        kieSession.fireAllRules();
        val fires = countFires(kieSession);
        assertEquals(1, fires);
    }

    @Test
    public void measurementSmokeAndHighTemperatureTest() {
        retractInitialFire(kieSession);
        val measurement = new Measurement(true, 180);
        kieSession.insert(measurement);
        kieSession.fireAllRules();
        val fires = countFires(kieSession);
        assertEquals(1, fires);
    }

    @Test
    public void manyAlarmingMeasurementsTest() {
        for (int i = 0; i < 10; i++) {
            val measurement = new Measurement(true, 180);
            kieSession.insert(measurement);
        }
        kieSession.fireAllRules();
        val fires = countFires(kieSession);
        assertEquals(1, fires);
    }
}