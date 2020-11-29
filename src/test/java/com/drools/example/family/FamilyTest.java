package com.drools.example.family;

import com.drools.example.AbstractDroolsTest;
import com.drools.example.family.utils.TestingUtils;
import lombok.val;
import org.junit.Test;

import static com.drools.example.family.utils.TestingUtils.countCousins;
import static com.drools.example.family.utils.TestingUtils.countPeople;
import static org.junit.Assert.assertEquals;

public class FamilyTest extends AbstractDroolsTest {
    @Test
    public void everyoneIsHereTest() {
        kieSession.fireAllRules();
        val total = countPeople(kieSession);
        assertEquals(18, total);
    }

    @Test
    public void malesAreHereTest() {
        kieSession.fireAllRules();
        val males = countPeople(kieSession, Person.Gender.MALE);
        assertEquals(10, males);
    }

    @Test
    public void femalesAreHereTest() {
        kieSession.fireAllRules();
        val females = countPeople(kieSession, Person.Gender.FEMALE);
        assertEquals(8, females);
    }

    @Test
    public void unclesQueryTest() {
        kieSession.fireAllRules();
        val results = kieSession.getQueryResults("UNCLES");
        val uncles  = results.size();
        assertEquals(8, uncles);
    }

    @Test
    public void cousinTest() {
        kieSession.fireAllRules(TestingUtils.cousinRule("ALL"));
        val cousins = countCousins(kieSession);
        assertEquals(16, cousins);
    }
}
