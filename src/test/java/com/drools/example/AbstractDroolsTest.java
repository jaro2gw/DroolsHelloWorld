package com.drools.example;

import lombok.val;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

abstract public class AbstractDroolsTest {
    protected Logger     logger;
    protected KieSession kieSession;

    @Before
    public void setUp() {
        val kieServices  = KieServices.Factory.get();
        val kieContainer = kieServices.getKieClasspathContainer();
        val clazz        = this.getClass();
        BasicConfigurator.configure();
        logger = Logger.getLogger(clazz);
        val name = clazz.getPackageName();
        kieSession = kieContainer.newKieSession(name);
        kieSession.setGlobal("logger", logger);
    }

    @After
    public void tearDown() {
        logger = null;
        kieSession.dispose();
        kieSession.destroy();
    }
}
