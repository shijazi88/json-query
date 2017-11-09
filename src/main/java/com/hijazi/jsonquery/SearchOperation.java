package com.hijazi.jsonquery;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SearchOperation
{
    EQUAL("=", "=="), NOT_EQUAL("!=", "<>"), GREATER_THAN(">", "gt"),
    GREATER_THAN_OR_EQUAL(">=", "gt="), LESS_THAN("<", "lt"),
    LESS_THAN_OR_EQUAL("<=", "lt="),
    AND("and", "&"), OR("or", "|"), IN("in"), NOT_IN("out"), LIKE("LIKE", ":"), BETWEEN("BETWEEN"), IS_NULL("null"), IS_NOT_NULL("not_null");

    private String value;
    private String alias;


    private SearchOperation(String value, String alias)
    {
        this.value = value;
        this.alias = alias;
    }


    private SearchOperation(String value)
    {
        this.value = value;
    }


    public String getAlias()
    {
        return alias;
    }


    public String getValue()
    {
        return this.value;
    }


    @JsonCreator
    public static SearchOperation forValue(String value)
    {
        for (SearchOperation entry : SearchOperation.values())
        {
            if (entry.getValue().equals(value.toLowerCase()) || entry.getAlias().equals(value.toLowerCase()))
                return entry;
        }
        
        System.out.println("null------");
        return null;
    }


    @JsonValue
    public String toValue()
    {
        for (SearchOperation entry : SearchOperation.values())
        {
            if (entry.getValue().equals(this.getValue()) || entry.getAlias().equals(this.getAlias()))
                return entry.getValue();
        }
        System.out.println("null------");

        return null; // or fail
    }

}
