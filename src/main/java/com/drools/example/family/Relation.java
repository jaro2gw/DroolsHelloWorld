package com.drools.example.family;

import lombok.Data;

@Data
public class Relation {
    public enum Type {
        PARENT,
        FATHER,
        MOTHER,
        GRANDPARENT,
        GRANDFATHER,
        GRANDMOTHER,
        SIBLING,
        BROTHER,
        SISTER,
        BROTHER_OF_FATHER,
        BROTHER_OF_MOTHER,
        COUSIN
    }

    private Person p1;
    private Person p2;
    private Type   type;

    public Relation(Person p1, Person p2, Type type) {
        this.p1 = p1;
        this.p2 = p2;
        this.type = type;

        p1.getRelations().add(this);
        p2.getRelations().add(this);
    }

    @Override
    public String toString() {
        return p1 + " is a " + type + " of " + p2;
    }
}
