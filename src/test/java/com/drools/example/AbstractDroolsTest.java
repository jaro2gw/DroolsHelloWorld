package com.drools.example;

import lombok.val;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

abstract public class AbstractDroolsTest {
    protected Logger     logger;
    protected KieSession kieSession;

    abstract protected String getRulesName();

    @Before
    public void setUp() {
        val kieServices  = KieServices.Factory.get();
        val kieContainer = kieServices.getKieClasspathContainer();
        val name = getRulesName();
        kieSession = kieContainer.newKieSession(name);
    }
}
