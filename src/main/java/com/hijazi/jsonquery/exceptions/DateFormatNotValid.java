package com.hijazi.jsonquery.exceptions;

public class DateFormatNotValid extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DateFormatNotValid()
    {
        String message="date format not found";
        new DateFormatNotValid(message);
    }

    public DateFormatNotValid(String message)
    {
        super(message);
    }
    
    
}
