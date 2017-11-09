package com.hijazi.jsonquery;

public enum SearchOperation
{
    EQUAL("EQUAL"), NOT_EQUAL("NOT_EQUAL"), GREATER_THAN("GREATER_THAN"),
    GREATER_THAN_OR_EQUAL("GREATER_THAN_OR_EQUAL"), LESS_THAN("LESS_THAN"),
    LESS_THAN_OR_EQUAL("LESS_THAN_OR_EQUAL"),
    AND("AND"), OR("OR"), IN("IN"), NOT_IN("NOT_IN"), LIKE("LIKE")
    ,BETWEEN("BETWEEN"),IS_NULL("IS_NULL"),IS_NOT_NULL("IS_NOT_NULL");
    

    private String value;


    private SearchOperation(String value)
    {
        this.value = value;
    }


    public String getValue()
    {
        return this.value;
    }

}
