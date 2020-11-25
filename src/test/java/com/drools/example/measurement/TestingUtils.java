package com.drools.example.measurement;

import lombok.val;
import org.kie.api.runtime.KieSession;

public final class TestingUtils {
    public static void retractInitialFire(KieSession kieSession) {
        kieSession.fireAllRules();
        val truck = new FireTruck();
        kieSession.insert(truck);
        kieSession.fireAllRules();
    }

    public static long countFires(KieSession kieSession) {
        return kieSession.getObjects()
                         .stream()
                         .filter(object -> object instanceof Fire)
                         .count();
    }

    public static long countEmergencyPhones(KieSession kieSession) {
        return kieSession.getObjects()
                         .stream()
                         .filter(object -> object instanceof Phone)
                         .map(object -> (Phone) object)
                         .map(Phone::getNumber)
                         .filter(Phone.EMERGENCY_NUMBER::equals)
                         .count();
    }

    public static long countFireTrucks(KieSession kieSession) {
        return kieSession.getObjects()
                         .stream()
                         .filter(object -> object instanceof FireTruck)
                         .count();
    }
}
