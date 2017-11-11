package com.hijazi.jsonquery;

import com.fasterxml.jackson.annotation.JsonCreator;
public enum SearchOperation
{
    IS_NULL("isNull","null"),    
    LIKE("LIKE", ":"),
    NOT_LIKE("NOT_LIKE", "!:"),
    EQUAL("=", "=="), 
    NOT_EQUAL("!=", "<>"), GREATER_THAN(">", "gt"),
    GREATER_THAN_OR_EQUAL(">=", "gt="), LESS_THAN("<", "lt"),
    LESS_THAN_OR_EQUAL("<=", "lt="),
    AND("and", "&"), OR("or", "|"), IN("in"), NOT_IN("out"), 
    BETWEEN("BETWEEN"), 
    IS_NOT_NULL("notNull","not_null");

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
            if (entry.getValue().toLowerCase().equals(value.toLowerCase()) || (entry.getAlias()!=null && entry.getAlias().toLowerCase().equals(value.toLowerCase())))
            {
                return entry;
            }
        }
        return null;
    }
}
