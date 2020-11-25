package com.drools.example.helloworld;

import com.drools.example.AbstractDroolsTest;
import lombok.val;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloWorldTest extends AbstractDroolsTest {
    @Override
    protected String getRulesName() {
        return "ksession-rules-helloworld";
    }

    @Before
    public void setUp() {
        super.setUp();
        BasicConfigurator.configure();
        logger = Logger.getLogger(HelloWorldTest.class);
    }

    @Test
    public void helloWorldTypeHelloTest() {
        val helloWorld = new HelloWorld(HelloWorld.Type.HELLO);
        logger.debug(helloWorld);

        kieSession.insert(helloWorld);
        kieSession.fireAllRules();

        logger.debug(helloWorld);
        assertEquals(HelloWorld.Type.GOODBYE, helloWorld.getType());
    }

    @Test
    public void helloWorldTypeGoodByeTest() {
        val helloWorld = new HelloWorld(HelloWorld.Type.GOODBYE);
        logger.debug(helloWorld);

        kieSession.insert(helloWorld);
        kieSession.fireAllRules();

        logger.debug(helloWorld);
        assertEquals(helloWorld.getType(), HelloWorld.Type.GOODBYE);
    }
}