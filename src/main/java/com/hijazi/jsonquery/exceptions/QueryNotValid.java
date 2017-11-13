package com.hijazi.jsonquery.exceptions;

public class QueryNotValid extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public QueryNotValid()
    {
        String message="query not found";
        new QueryNotValid(message);
    }

    public QueryNotValid(String message)
    {
        super(message);
    }
    
    
}
