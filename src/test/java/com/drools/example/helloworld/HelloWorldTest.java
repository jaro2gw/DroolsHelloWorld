package com.drools.example.helloworld;

import com.drools.example.AbstractDroolsTest;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloWorldTest extends AbstractDroolsTest {
    @Test
    public void helloWorldTypeHelloTest() {
        val helloWorld = new HelloWorld(HelloWorld.Type.HELLO);
        kieSession.insert(helloWorld);
        kieSession.fireAllRules();
        assertEquals(HelloWorld.Type.GOODBYE, helloWorld.getType());
    }

    @Test
    public void helloWorldTypeGoodByeTest() {
        val helloWorld = new HelloWorld(HelloWorld.Type.GOODBYE);
        kieSession.insert(helloWorld);
        kieSession.fireAllRules();
        assertEquals(HelloWorld.Type.GOODBYE, helloWorld.getType());
    }
}