package com.drools.example.measurement.utils;

import com.drools.example.measurement.Fire;
import com.drools.example.measurement.FireTruck;
import com.drools.example.measurement.Phone;
import lombok.experimental.UtilityClass;
import org.kie.api.runtime.KieSession;

@UtilityClass
public class TestingUtils {
    public long countFires(KieSession kieSession) {
        return kieSession.getObjects(object -> object instanceof Fire)
                         .size();
    }

    public long countEmergencyPhones(KieSession kieSession) {
        return kieSession.getObjects(object -> object instanceof Phone)
                         .stream()
                         .map(object -> (Phone) object)
                         .map(Phone::getNumber)
                         .filter(Phone.EMERGENCY_NUMBER::equals)
                         .count();
    }

    public long countFireTrucks(KieSession kieSession) {
        return kieSession.getObjects(object -> object instanceof FireTruck)
                         .size();
    }
}
