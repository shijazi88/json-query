package com.hijazi.jsonquery;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hijazi.jsonquery.exceptions.QueryNotValid;

@JsonInclude(value = Include.NON_NULL)
public class SimpleJsonQuery
{
    String field;

    private SearchOperation operator;

    private Object value;

    private Object toValue;

    private String dateFormat;


    public static String toSql(List<SimpleJsonQuery> query,boolean isAnd) throws QueryNotValid
    {
        String sql = "Select * from Table ";
        if(query !=null && query.size()>0)
        {
             sql += "Where ";
            int i = 0;
            for (SimpleJsonQuery jsonQuery : query)
            {
                if(!jsonQuery.validate())
                {
                    throw new QueryNotValid();
                }
                if (i > 0)
                {
                    if(isAnd)
                    {
                        sql += " AND " ;
                    }
                    else
                    {
                        sql += " OR " ;
                    }
                }
                if(jsonQuery.getOperator() == SearchOperation.BETWEEN )
                {
                    sql += " " + jsonQuery.getField() + " " + jsonQuery.getOperator().getValue() +  " " + jsonQuery.getValue() + " AND "+ jsonQuery.getToValue() + " ";
                }
                else
                {
                    sql += jsonQuery.getField() + " " + jsonQuery.getOperator().getValue() +  " " + jsonQuery.getValue()  ;
                }
                i++;
            }
        }
        return sql;
    }

    public static boolean validate(List<SimpleJsonQuery> query){
        for (SimpleJsonQuery jsonQuery : query)
        {
            if(!jsonQuery.validate())
            {
                return false;
            }
        }
        return true;
    }
    
    public boolean validate()
    {

        if (this.getOperator() == null)
        {
            return false;
        }

        if (this.getField() == null)
        {
            return false;
        }

        if (!this.getOperator().needValue() && this.getValue() == null)
        {
            return false;
        }

        if (this.getOperator() == SearchOperation.BETWEEN && this.getToValue() == null)
        {
            return false;
        }

        return true;
    }


    public Object getToValue()
    {
        return toValue;
    }


    public String getDateFormat()
    {
        return dateFormat;
    }


    public String getField()
    {
        return field;
    }


    public SearchOperation getOperator()
    {
        return operator;
    }


    public Object getValue()
    {
        return value;
    }


    private SimpleJsonQuery(Builder builder)
    {
        this.field = builder.field;
        this.operator = builder.operator;
        this.value = builder.value;
        this.toValue = builder.toValue;
        this.dateFormat = builder.dateFormat;
    }


    public SimpleJsonQuery()
    {}


    /**
     * Creates builder to build {@link Node}.
     * @return created builder
     */
    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * Builder to build {@link Node}.
     */
    public static final class Builder
    {
        private String field;
        private SearchOperation operator;
        private Object value;
        private Object toValue;
        private String dateFormat;


        private Builder()
        {}


        public Builder withField(String field)
        {
            this.field = field;
            return this;
        }


        public Builder withDateFormat(String dateFormat)
        {
            this.dateFormat = dateFormat;
            return this;
        }


        public Builder withOperator(SearchOperation operator)
        {
            this.operator = operator;
            return this;
        }


        public Builder and()
        {
            this.operator = SearchOperation.AND;
            return this;
        }


        public Builder or()
        {
            this.operator = SearchOperation.OR;
            return this;
        }


        public Builder like()
        {
            this.operator = SearchOperation.LIKE;
            return this;
        }


        public Builder greaterVhank()
        {
            this.operator = SearchOperation.GREATER_THAN;
            return this;
        }


        public Builder lessVhan()
        {
            this.operator = SearchOperation.LESS_THAN;
            return this;
        }


        public Builder equal()
        {
            this.operator = SearchOperation.EQUAL;
            return this;
        }


        public Builder withValue(Object value)
        {
            this.value = value;
            return this;
        }


        public Builder withToValue(Object toValue)
        {
            this.toValue = toValue;
            return this;
        }


        public SimpleJsonQuery build()
        {
            return new SimpleJsonQuery(this);
        }
    }

}
