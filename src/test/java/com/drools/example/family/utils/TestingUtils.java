package com.drools.example.family.utils;

import com.drools.example.family.Person;
import com.drools.example.family.Relation;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import java.util.Objects;
import java.util.stream.Stream;

@UtilityClass
public class TestingUtils {
    public Stream<Person> peopleStream(KieSession kieSession) {
        val peopleDirectly = kieSession.getObjects(object -> object instanceof Person)
                                       .stream()
                                       .map(object -> (Person) object);
        val peopleIndirectly = kieSession.getObjects(object -> object instanceof Relation)
                                         .stream()
                                         .map(object -> (Relation) object)
                                         .flatMap(relation -> Stream.of(relation.getP1(), relation.getP2()))
                                         .distinct();
        return Stream.concat(peopleDirectly, peopleIndirectly)
                     .distinct();
    }

    public long countPeople(KieSession kieSession) {
        return peopleStream(kieSession)
                .count();
    }

    public long countPeople(KieSession kieSession, Person.Gender gender) {
        return peopleStream(kieSession)
                .filter(person -> Objects.equals(person.getGender(), gender))
                .count();
    }

    public boolean existsRelation(KieSession kieSession, Person p1, Person p2, Relation.Type type) {
        return kieSession.getObjects(object -> object instanceof Relation)
                         .stream()
                         .map(object -> (Relation) object)
                         .filter(relation -> Objects.equals(relation.getP1(), p1))
                         .filter(relation -> Objects.equals(relation.getP2(), p2))
                         .anyMatch(relation -> Objects.equals(relation.getType(), type));
    }

    public Stream<QueryResultsRow> queryResultsRowStream(QueryResults queryResults) {
        val builder = Stream.<QueryResultsRow>builder();
        queryResults.forEach(builder);
        return builder.build();
    }

    public long countUnclesOf(QueryResults queryResults, Person child) {
        return queryResultsRowStream(queryResults).map(row -> row.get("relation"))
                                                  .filter(object -> object instanceof Relation)
                                                  .map(object -> (Relation) object)
                                                  .filter(relation -> Objects.equals(relation.getP2(), child))
                                                  .count();
    }

    public AgendaFilter cousinRule(String suffix) {
        return match -> {
            val name = match.getRule().getName();
            if (name.startsWith("COUSIN")) return name.endsWith(suffix);
            else return true;
        };
    }

    public long countCousins(KieSession kieSession) {
        return kieSession.getObjects(object -> object instanceof Relation)
                         .stream()
                         .map(object -> (Relation) object)
                         .filter(relation -> Objects.equals(relation.getType(), Relation.Type.COUSIN))
                         .count();
    }
}
