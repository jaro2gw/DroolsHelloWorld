package com.drools.example.measurement;

import com.drools.example.AbstractDroolsTest;
import lombok.val;
import org.junit.Test;

import static com.drools.example.measurement.TestingUtils.countFireTrucks;
import static com.drools.example.measurement.TestingUtils.countFires;
import static org.junit.Assert.assertEquals;

public class FireTruckTest extends AbstractDroolsTest {
    @Override
    protected String getRulesName() {
        return "ksession-rules-measurement";
    }

    @Test
    public void noFireTest() {
        val truck = new FireTruck();
        kieSession.insert(truck);
        kieSession.fireAllRules();
        val fires  = countFires(kieSession);
        val trucks = countFireTrucks(kieSession);
        assertEquals(0, fires);
        assertEquals(0, trucks);
    }

    @Test
    public void oneFireTest() {
        val fire  = new Fire();
        val truck = new FireTruck();
        kieSession.insert(fire);
        kieSession.insert(truck);
        kieSession.fireAllRules();
        val fires  = countFires(kieSession);
        val trucks = countFireTrucks(kieSession);
        assertEquals(0, fires);
        assertEquals(0, trucks);
    }

    @Test
    public void manyFiresTest() {
        val number = 10;
        for (int i = 0; i < number; i++) {
            val fire = new Fire();
            kieSession.insert(fire);
        }
        val truck = new FireTruck();
        kieSession.insert(truck);
        kieSession.fireAllRules();
        val fires  = countFires(kieSession);
        val trucks = countFireTrucks(kieSession);
        assertEquals(0, fires);
        assertEquals(0, trucks);
    }
}