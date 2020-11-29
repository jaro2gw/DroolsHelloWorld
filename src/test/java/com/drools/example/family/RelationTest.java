package com.drools.example.family;

import com.drools.example.AbstractDroolsTest;
import com.drools.example.family.utils.TestingUtils;
import com.drools.example.utils.AgendaFilters;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.*;

public class RelationTest extends AbstractDroolsTest {
    @Test
    public void relationTest() {
        val p1       = new Person("p1");
        val p2       = new Person("p2");
        val relation = new Relation(p1, p2, Relation.Type.PARENT);
        assertTrue(p1.getRelations().contains(relation));
        assertTrue(p2.getRelations().contains(relation));
    }

    @Test
    public void fatherTest() {
        val father   = new Person("f", Person.Gender.MALE);
        val child    = new Person("c");
        val relation = new Relation(father, child, Relation.Type.PARENT);
        kieSession.insert(relation);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val exists = TestingUtils.existsRelation(kieSession, father, child, Relation.Type.FATHER);
        assertTrue(exists);
    }

    @Test
    public void motherTest() {
        val mother   = new Person("m", Person.Gender.FEMALE);
        val child    = new Person("c");
        val relation = new Relation(mother, child, Relation.Type.PARENT);
        kieSession.insert(relation);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val exists = TestingUtils.existsRelation(kieSession, mother, child, Relation.Type.MOTHER);
        assertTrue(exists);
    }

    @Test
    public void grandparentTest() {
        val gparent = new Person("g");
        val parent  = new Person("p");
        val child   = new Person("c");
        val r1      = new Relation(gparent, parent, Relation.Type.PARENT);
        val r2      = new Relation(parent, child, Relation.Type.PARENT);
        kieSession.insert(r1);
        kieSession.insert(r2);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val exists = TestingUtils.existsRelation(kieSession, gparent, child, Relation.Type.GRANDPARENT);
        assertTrue(exists);
    }

    @Test
    public void grandfatherTest() {
        val gfather  = new Person("g", Person.Gender.MALE);
        val child    = new Person("c");
        val relation = new Relation(gfather, child, Relation.Type.GRANDPARENT);
        kieSession.insert(relation);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val exists = TestingUtils.existsRelation(kieSession, gfather, child, Relation.Type.GRANDFATHER);
        assertTrue(exists);
    }

    @Test
    public void grandmotherTest() {
        val gmother  = new Person("g", Person.Gender.FEMALE);
        val child    = new Person("c");
        val relation = new Relation(gmother, child, Relation.Type.GRANDPARENT);
        kieSession.insert(relation);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val exists = TestingUtils.existsRelation(kieSession, gmother, child, Relation.Type.GRANDMOTHER);
        assertTrue(exists);
    }

    @Test
    public void siblingTest() {
        val parent   = new Person("p");
        val sibling1 = new Person("s1");
        val sibling2 = new Person("s2");
        val r1       = new Relation(parent, sibling1, Relation.Type.PARENT);
        val r2       = new Relation(parent, sibling2, Relation.Type.PARENT);
        kieSession.insert(r1);
        kieSession.insert(r2);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val e1 = TestingUtils.existsRelation(kieSession, sibling1, sibling2, Relation.Type.SIBLING);
        assertTrue(e1);
        val e2 = TestingUtils.existsRelation(kieSession, sibling2, sibling1, Relation.Type.SIBLING);
        assertTrue(e2);
        val e3 = TestingUtils.existsRelation(kieSession, sibling1, sibling1, Relation.Type.SIBLING);
        assertFalse(e3);
        val e4 = TestingUtils.existsRelation(kieSession, sibling2, sibling2, Relation.Type.SIBLING);
        assertFalse(e4);
    }

    @Test
    public void brotherTest() {
        val brother  = new Person("b", Person.Gender.MALE);
        val sibling  = new Person("s");
        val relation = new Relation(brother, sibling, Relation.Type.SIBLING);
        kieSession.insert(relation);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val exists = TestingUtils.existsRelation(kieSession, brother, sibling, Relation.Type.BROTHER);
        assertTrue(exists);
    }

    @Test
    public void sisterTest() {
        val sister   = new Person("s1", Person.Gender.FEMALE);
        val sibling  = new Person("s2");
        val relation = new Relation(sister, sibling, Relation.Type.SIBLING);
        kieSession.insert(relation);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val exists = TestingUtils.existsRelation(kieSession, sister, sibling, Relation.Type.SISTER);
        assertTrue(exists);
    }

    @Test
    public void brotherOfFatherTest() {
        val brother = new Person("b", Person.Gender.MALE);
        val father  = new Person("f", Person.Gender.MALE);
        val child   = new Person("c");
        val r1      = new Relation(brother, father, Relation.Type.BROTHER);
        val r2      = new Relation(father, child, Relation.Type.FATHER);
        kieSession.insert(r1);
        kieSession.insert(r2);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val exists = TestingUtils.existsRelation(kieSession, brother, child, Relation.Type.BROTHER_OF_FATHER);
        assertTrue(exists);
    }

    @Test
    public void brotherOfMotherTest() {
        val brother = new Person("b", Person.Gender.MALE);
        val mother  = new Person("m", Person.Gender.FEMALE);
        val child   = new Person("c");
        val r1      = new Relation(brother, mother, Relation.Type.BROTHER);
        val r2      = new Relation(mother, child, Relation.Type.MOTHER);
        kieSession.insert(r1);
        kieSession.insert(r2);
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val exists = TestingUtils.existsRelation(kieSession, brother, child, Relation.Type.BROTHER_OF_MOTHER);
        assertTrue(exists);
    }

    @Test
    public void unclesQueryTest1() {
        val number = 10;
        val child  = new Person("c");
        for (int i = 0; i < number; i++) {
            val person   = new Person("p" + i);
            val relation = new Relation(person, child, Relation.Type.BROTHER_OF_FATHER);
            kieSession.insert(relation);
        }
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val results = kieSession.getQueryResults("UNCLES");
        val count   = TestingUtils.countUnclesOf(results, child);
        assertEquals(number, count);
    }

    @Test
    public void unclesQueryTest2() {
        val number = 10;
        val child  = new Person("c");
        for (int i = 0; i < number; i++) {
            val person   = new Person("p" + i);
            val relation = new Relation(person, child, Relation.Type.BROTHER_OF_MOTHER);
            kieSession.insert(relation);
        }
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val results = kieSession.getQueryResults("UNCLES");
        val count   = TestingUtils.countUnclesOf(results, child);
        assertEquals(number, count);
    }

    @Test
    public void unclesQueryTest3() {
        val number = 10;
        val child  = new Person("c");
        for (int i = 0; i < number; i++) {
            val person   = new Person("p0" + i);
            val relation = new Relation(person, child, Relation.Type.BROTHER_OF_FATHER);
            kieSession.insert(relation);
        }
        for (int i = 0; i < number; i++) {
            val person   = new Person("p1" + i);
            val relation = new Relation(person, child, Relation.Type.BROTHER_OF_MOTHER);
            kieSession.insert(relation);
        }
        kieSession.fireAllRules(AgendaFilters.noInitRule());
        val results = kieSession.getQueryResults("UNCLES");
        val count   = TestingUtils.countUnclesOf(results, child);
        assertEquals(number * 2, count);
    }

    @Test
    public void cousinTest1() {
        val cousin1  = new Person("c1");
        val cousin2  = new Person("c2");
        val sibling1 = new Person("s1");
        val sibling2 = new Person("s2");
        val r1       = new Relation(sibling1, cousin1, Relation.Type.PARENT);
        val r2       = new Relation(sibling2, cousin2, Relation.Type.PARENT);
        val r3       = new Relation(sibling1, sibling2, Relation.Type.SIBLING);
        val r4       = new Relation(sibling2, sibling1, Relation.Type.SIBLING);
        kieSession.insert(r1);
        kieSession.insert(r2);
        kieSession.insert(r3);
        kieSession.insert(r4);
        kieSession.fireAllRules(AgendaFilters.composite(AgendaFilters.noInitRule(), TestingUtils.cousinRule("#1")));
        val e1 = TestingUtils.existsRelation(kieSession, cousin1, cousin2, Relation.Type.COUSIN);
        assertTrue(e1);
        val e2 = TestingUtils.existsRelation(kieSession, cousin2, cousin1, Relation.Type.COUSIN);
        assertTrue(e2);
    }

    @Test
    public void cousinTest2() {
        val cousin1 = new Person("c1");
        val cousin2 = new Person("c2");
        val cousin3 = new Person("c3");
        val uncle1  = new Person("u1", Person.Gender.MALE);
        val uncle2  = new Person("u2", Person.Gender.MALE);
        val r1      = new Relation(uncle1, cousin1, Relation.Type.FATHER);
        val r2      = new Relation(uncle1, cousin2, Relation.Type.BROTHER_OF_FATHER);
        val r3      = new Relation(uncle2, cousin3, Relation.Type.FATHER);
        val r4      = new Relation(uncle2, cousin2, Relation.Type.BROTHER_OF_MOTHER);
        kieSession.insert(r1);
        kieSession.insert(r2);
        kieSession.insert(r3);
        kieSession.insert(r4);
        kieSession.fireAllRules(AgendaFilters.composite(AgendaFilters.noInitRule(), TestingUtils.cousinRule("#2")));
        val e1 = TestingUtils.existsRelation(kieSession, cousin1, cousin2, Relation.Type.COUSIN);
        assertTrue(e1);
        val e2 = TestingUtils.existsRelation(kieSession, cousin2, cousin1, Relation.Type.COUSIN);
        assertTrue(e2);
        val e3 = TestingUtils.existsRelation(kieSession, cousin2, cousin3, Relation.Type.COUSIN);
        assertTrue(e3);
        val e4 = TestingUtils.existsRelation(kieSession, cousin3, cousin2, Relation.Type.COUSIN);
        assertTrue(e4);
        val e5 = TestingUtils.existsRelation(kieSession, cousin1, cousin3, Relation.Type.COUSIN);
        assertFalse(e5);
        val e6 = TestingUtils.existsRelation(kieSession, cousin3, cousin1, Relation.Type.COUSIN);
        assertFalse(e6);
    }

    @Test
    public void cousinTest3() {
        val cousin1 = new Person("c1");
        val cousin2 = new Person("c2");
        val cousin3 = new Person("c3");
        val gparent = new Person("g");
        val r1      = new Relation(gparent, cousin1, Relation.Type.GRANDPARENT);
        val r2      = new Relation(gparent, cousin2, Relation.Type.GRANDPARENT);
        val r3      = new Relation(gparent, cousin3, Relation.Type.GRANDPARENT);
        val r4      = new Relation(cousin1, cousin2, Relation.Type.SIBLING);
        val r5      = new Relation(cousin2, cousin1, Relation.Type.SIBLING);
        kieSession.insert(r1);
        kieSession.insert(r2);
        kieSession.insert(r3);
        kieSession.insert(r4);
        kieSession.insert(r5);
        kieSession.fireAllRules(AgendaFilters.composite(AgendaFilters.noInitRule(), TestingUtils.cousinRule("#3")));
        val e1 = TestingUtils.existsRelation(kieSession, cousin1, cousin2, Relation.Type.COUSIN);
        assertFalse(e1);
        val e2 = TestingUtils.existsRelation(kieSession, cousin2, cousin1, Relation.Type.COUSIN);
        assertFalse(e2);
        val e3 = TestingUtils.existsRelation(kieSession, cousin1, cousin3, Relation.Type.COUSIN);
        assertTrue(e3);
        val e4 = TestingUtils.existsRelation(kieSession, cousin3, cousin1, Relation.Type.COUSIN);
        assertTrue(e4);
        val e5 = TestingUtils.existsRelation(kieSession, cousin2, cousin3, Relation.Type.COUSIN);
        assertTrue(e5);
        val e6 = TestingUtils.existsRelation(kieSession, cousin3, cousin2, Relation.Type.COUSIN);
        assertTrue(e6);
    }

    @Test
    public void cousinTest4() {
        val gparent  = new Person("g");
        val sibling1 = new Person("s1");
        val sibling2 = new Person("s2");
        val cousin1  = new Person("c1");
        val cousin2  = new Person("c2");
        val r1       = new Relation(gparent, sibling1, Relation.Type.PARENT);
        val r2       = new Relation(gparent, sibling2, Relation.Type.PARENT);
        val r3       = new Relation(sibling1, cousin1, Relation.Type.PARENT);
        val r4       = new Relation(sibling2, cousin2, Relation.Type.PARENT);
        kieSession.insert(r1);
        kieSession.insert(r2);
        kieSession.insert(r3);
        kieSession.insert(r4);
        kieSession.fireAllRules(AgendaFilters.composite(AgendaFilters.noInitRule(), TestingUtils.cousinRule("#4")));
        val e1 = TestingUtils.existsRelation(kieSession, cousin1, cousin2, Relation.Type.COUSIN);
        assertTrue(e1);
        val e2 = TestingUtils.existsRelation(kieSession, cousin2, cousin1, Relation.Type.COUSIN);
        assertTrue(e2);
        val e3 = TestingUtils.existsRelation(kieSession, cousin1, cousin1, Relation.Type.COUSIN);
        assertFalse(e3);
        val e4 = TestingUtils.existsRelation(kieSession, cousin2, cousin2, Relation.Type.COUSIN);
        assertFalse(e4);
    }
}