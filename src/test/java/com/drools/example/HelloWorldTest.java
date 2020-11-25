package com.drools.example;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;

import static org.junit.Assert.assertEquals;

public class HelloWorldTest {
    private Logger logger;

    @Before
    public void setUp() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(HelloWorldTest.class);
    }

    @Test
    public void executeHelloWorldRules() {
        var kservices  = KieServices.Factory.get();
        var kcontainer = kservices.getKieClasspathContainer();
        var ksession   = kcontainer.newKieSession("ksession-rules");

        var helloWorld = new HelloWorld(HelloWorld.Type.HELLO);
        logger.debug(helloWorld);

        ksession.insert(helloWorld);
        ksession.fireAllRules();

        logger.debug(helloWorld);
        assertEquals(helloWorld.getType(), HelloWorld.Type.GOODBYE);
    }
}