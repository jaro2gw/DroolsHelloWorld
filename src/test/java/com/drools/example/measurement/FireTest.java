package com.drools.example.measurement;

import com.drools.example.AbstractDroolsTest;
import com.drools.example.utils.AgendaFilters;
import lombok.val;
import org.junit.Test;

import static com.drools.example.measurement.utils.TestingUtils.countEmergencyPhones;
import static org.junit.Assert.assertEquals;

public class FireTest extends AbstractDroolsTest {
    @Test
    public void noFireTest() {
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val phones = countEmergencyPhones(kieSession);
        assertEquals(0, phones);
    }

    @Test
    public void oneFireTest() {
        val fire = new Fire();
        kieSession.insert(fire);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val phones = countEmergencyPhones(kieSession);
        assertEquals(1, phones);
    }

    @Test
    public void manyFiresTest() {
        val number = 10;
        for (int i = 0; i < number; i++) {
            val fire = new Fire();
            kieSession.insert(fire);
        }
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val phones = countEmergencyPhones(kieSession);
        assertEquals(number, phones);
    }
}