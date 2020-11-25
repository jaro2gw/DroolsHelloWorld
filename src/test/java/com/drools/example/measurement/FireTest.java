package com.drools.example.measurement;

import com.drools.example.AbstractDroolsTest;
import lombok.val;
import org.junit.Test;

import static com.drools.example.measurement.TestingUtils.countEmergencyPhones;
import static org.junit.Assert.assertEquals;

public class FireTest extends AbstractDroolsTest {
    @Override
    protected String getRulesName() {
        return "ksession-rules-measurement";
    }

    @Test
    public void noFireTest() {
        val count = countEmergencyPhones(kieSession);
        assertEquals(0, count);
    }

    @Test
    public void oneFireTest() {
        val fire = new Fire();
        kieSession.insert(fire);
        kieSession.fireAllRules();
        val count = countEmergencyPhones(kieSession);
        assertEquals(1, count);
    }

    @Test
    public void manyFiresTest() {
        val number = 10;
        for (int i = 0; i < number; i++) {
            val fire = new Fire();
            kieSession.insert(fire);
        }
        kieSession.fireAllRules();
        val count = countEmergencyPhones(kieSession);
        assertEquals(number, count);
    }
}