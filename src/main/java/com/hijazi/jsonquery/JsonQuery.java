package com.hijazi.jsonquery;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hijazi.jsonquery.exceptions.QueryNotValid;

@JsonInclude(value = Include.NON_NULL)
public class JsonQuery
{
    String field;
    private SearchOperation operator;

    private Object value;

    private Object toValue;

    private String dateFormat;

    private List<JsonQuery> conditions;


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

     String toSql() throws QueryNotValid
    {
        if(! this.validate()){
            throw new QueryNotValid();
        }
        String query= "Select * from Table where ";
        query+= generateWhere(this);
        return query;
    }
    
    private String generateWhere(JsonQuery query){
        
        
        if(query.getOperator().isLogicaloperator())
        {
            String result="";
            int i=0;
            for (JsonQuery jsonQuery : query.getconditions())
            {
                if(i==0)
                {
                    result+= " ( " + generateWhere(jsonQuery) + " ) ";

                }
                else
                {
                    result+= query.getOperator().getValue() +"  ( " + generateWhere(jsonQuery) + " ) ";
                    
                }
                i++;
            }
            return result;
        }
        else
        {
            if(query.getOperator() == SearchOperation.BETWEEN )
            {
                return "( " + query.getField() + " " + query.getOperator().getValue() +  " '" + query.getValue() + "' AND '"+ query.getToValue() + "' ) ";
            }
            else
            {
                return query.getField() + " " + query.getOperator().getValue() +  " " + query.getValue()  ;
            }
        }
    }
    
    public boolean validate(){
        
        if(this.getOperator() == null)
        {
            return false;
        }
        
        
        if(!this.getOperator().isLogicaloperator())
        {
            if(this.getField() == null)
            {
                return false;
            }
            
            if(!this.getOperator().needValue() && this.getValue() == null)
            {
                return false;
            }
            
            
            if(this.getOperator() == SearchOperation.BETWEEN && this.getToValue() == null)
            {
                return false;
            }
        }
        else
        {
           if(this.getconditions() == null || this.getconditions().size() == 0)
           {
               return false;
           }
           else
           {
               for (JsonQuery jsonQuery : this.getconditions())
                {
                    if(!jsonQuery.validate())
                    {
                        return false;
                    }
                }
           }
        }
        return true;
    }

    public List<JsonQuery> getconditions()
    {
        return conditions;
    }

    private JsonQuery(Builder builder)
    {
        this.field = builder.field;
        this.operator = builder.operator;
        this.value = builder.value;
        this.toValue = builder.toValue;
        this.dateFormat = builder.dateFormat;
        this.conditions = builder.conditions;
    }

    public JsonQuery()
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
        private List<JsonQuery> conditions = new ArrayList<JsonQuery>();


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




        public Builder withConditions(List<JsonQuery> conditions)
        {
            this.conditions = conditions;
            return this;
        }


        public Builder addCondition(JsonQuery condition)
        {
            if (this.conditions == null)
                this.conditions = new ArrayList<JsonQuery>();
            this.conditions.add(condition);
            return this;
        }


        public Builder addDateCondition(String field, Object value, SearchOperation operator, String dateFormat)
        {
            if (this.conditions == null)
                this.conditions = new ArrayList<JsonQuery>();

            JsonQuery condition =
                JsonQuery.builder().withConditions(null).withField(field).withValue(value).withOperator(operator).withDateFormat(dateFormat).build();
            this.conditions.add(condition);
            return this;
        }


        public Builder addSimpleCondition(String field, Object value, SearchOperation operator)
        {
            if (this.conditions == null)
                this.conditions = new ArrayList<JsonQuery>();

            JsonQuery condition = JsonQuery.builder().withConditions(null).withField(field).withValue(value).withOperator(operator).build();
            this.conditions.add(condition);
            return this;
        }


        public JsonQuery build()
        {
            return new JsonQuery(this);
        }
    }

}
