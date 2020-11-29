package com.drools.example.utils;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.kie.api.runtime.rule.AgendaFilter;

import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class AgendaFilters {
    public AgendaFilter noRule(String name) {
        return match -> {
            val rule = match.getRule().getName();
            return !Objects.equals(rule, name);
        };
    }

    public AgendaFilter noInitRule() {
        return noRule("INIT");
    }

    public AgendaFilter composite(AgendaFilter... filters) {
        return match -> Arrays.stream(filters).allMatch(filter -> filter.accept(match));
    }
}
