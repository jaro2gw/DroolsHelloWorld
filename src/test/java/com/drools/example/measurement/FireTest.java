package com.drools.example.measurement;

import com.drools.example.AbstractDroolsTest;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static com.drools.example.measurement.TestingUtils.countEmergencyPhones;
import static com.drools.example.measurement.TestingUtils.retractInitialFire;
import static org.junit.Assert.assertEquals;

public class FireTest extends AbstractDroolsTest {
    @Override
    protected String getRulesName() {
        return "ksession-rules-measurement";
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        retractInitialFire(kieSession);
    }

    @Test
    public void noFireTest() {
        kieSession.fireAllRules();
        val phones = countEmergencyPhones(kieSession);
        assertEquals(1, phones);
    }

    @Test
    public void oneFireTest() {
        val fire = new Fire();
        kieSession.insert(fire);
        kieSession.fireAllRules();
        val phones = countEmergencyPhones(kieSession);
        assertEquals(2, phones);
    }

    @Test
    public void manyFiresTest() {
        val number = 10;
        for (int i = 0; i < number; i++) {
            val fire = new Fire();
            kieSession.insert(fire);
        }
        kieSession.fireAllRules();
        val phones = countEmergencyPhones(kieSession);
        assertEquals(number + 1, phones);
    }
}