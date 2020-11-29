package com.drools.example.measurement;

import com.drools.example.AbstractDroolsTest;
import com.drools.example.measurement.utils.TestingUtils;
import com.drools.example.utils.AgendaFilters;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MeasurementTest extends AbstractDroolsTest {
    @Test
    public void measurementNoSmokeLowTemperature() {
        val measurement = new Measurement(false, 20);
        kieSession.insert(measurement);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val fires = TestingUtils.countFires(kieSession);
        assertEquals(0, fires);
    }

    @Test
    public void measurementSmokeAndLowTemperatureTest() {
        val measurement = new Measurement(true, 20);
        kieSession.insert(measurement);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val fires = TestingUtils.countFires(kieSession);
        assertEquals(1, fires);
    }

    @Test
    public void measurementNoSmokeHighTemperatureTest() {
        val measurement = new Measurement(false, 180);
        kieSession.insert(measurement);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val fires = TestingUtils.countFires(kieSession);
        assertEquals(1, fires);
    }

    @Test
    public void measurementSmokeAndHighTemperatureTest() {
        val measurement = new Measurement(true, 180);
        kieSession.insert(measurement);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val fires = TestingUtils.countFires(kieSession);
        assertEquals(1, fires);
    }

    @Test
    public void manyAlarmingMeasurementsTest() {
        for (int i = 0; i < 10; i++) {
            val measurement = new Measurement(true, 180);
            kieSession.insert(measurement);
        }
        kieSession.fireAllRules();
        val fires = TestingUtils.countFires(kieSession);
        assertEquals(1, fires);
    }
}