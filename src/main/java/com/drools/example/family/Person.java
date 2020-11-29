package com.drools.example.family;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Person {
    public enum Gender {
        UNKNOWN,
        MALE,
        FEMALE
    }

    private final Collection<Relation> relations = new HashSet<>();
    private final String               name;
    private       Gender               gender    = Gender.UNKNOWN;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
               gender == person.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender);
    }
}
