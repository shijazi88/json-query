package com.hijazi.jsonquery;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(value=Include.NON_NULL)
public class JsonQuery<V>
{
    // condition
     String field;
    private SearchOperation operator;

    // condition or operation
    private V value;
    
    private V toValue;

    private String dateFormat;
    
    private boolean leaf;
    private List<JsonQuery<V>> conditions;
    
    
    
    public V getToValue()
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


    public V getValue()
    {
        return value;
    }


    public boolean isLeaf()
    {
        return leaf;
    }


    public List<JsonQuery<V>> getconditions()
    {
        return conditions;
    }


    @Generated("SparkVools")
    private JsonQuery(Builder<V> builder)
    {
        this.field = builder.field;
        this.operator = builder.operator;
        this.value = builder.value;
        this.toValue = builder.toValue;
        this.leaf = builder.leaf;
        this.dateFormat= builder.dateFormat;
        this.conditions = builder.conditions;
    }
    
    public JsonQuery()
    {
    }


    /**
     * Creates builder to build {@link Node}.
     * @return created builder
     */
    @Generated("SparkVools")
    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * Builder to build {@link Node}.
     */
    @Generated("SparkVools")
    public static final class Builder<V>
    {
        private String field;
        private SearchOperation operator;
        private V value;
        private V toValue;
        private boolean leaf;
        private String dateFormat;
        private List<JsonQuery<V>> conditions = new ArrayList<JsonQuery<V>>();


        private Builder()
        {}


        public Builder<V> withField(String field)
        {
            this.field = field;
            return this;
        }
        public Builder<V> withDateFormat(String dateFormat)
        {
            this.dateFormat = dateFormat;
            return this;
        }


        public Builder<V> withOperator(SearchOperation operator)
        {
            this.operator = operator;
            return this;
        }
        public Builder<V> and()
        {
            this.operator = SearchOperation.AND;
            return this;
        }
        public Builder<V> or()
        {
            this.operator = SearchOperation.OR;
            return this;
        }
        public Builder<V> like()
        {
            this.operator = SearchOperation.LIKE;
            return this;
        }
        public Builder<V> greaterVhank()
        {
            this.operator = SearchOperation.GREATER_THAN;
            return this;
        }
        public Builder<V> lessVhan()
        {
            this.operator = SearchOperation.LESS_THAN;
            return this;
        }
        public Builder<V> equal()
        {
            this.operator = SearchOperation.EQUAL;
            return this;
        }

        public Builder<V> withValue(V value)
        {
            this.value = value;
            return this;
        }

        public Builder<V> withToValue(V toValue)
        {
            this.toValue = toValue;
            return this;
        }


        public Builder<V> withLeaf(boolean leaf)
        {
            this.leaf = leaf;
            return this;
        }


        public Builder<V> withConditions(List<JsonQuery<V>> conditions)
        {
            this.conditions = conditions;
            return this;
        }
        public Builder<V> addCondition(JsonQuery<V> condition)
        {
            if(this.conditions == null)
                this.conditions = new ArrayList<JsonQuery<V>>();
            this.conditions.add(condition);
            return this;
        }
        public Builder<V> addDateCondition(String field,V value,SearchOperation operator,String dateFormat)
        {
            if(this.conditions == null)
                this.conditions = new ArrayList<JsonQuery<V>>();
            
            JsonQuery<V> condition=JsonQuery.builder().withConditions(null).withLeaf(true).withField(field).withValue(value).withOperator(operator).withDateFormat(dateFormat).build();
            this.conditions.add(condition);
            return this;
        }
        public Builder<V> addSimpleCondition(String field,V value,SearchOperation operator)
        {
            if(this.conditions == null)
                this.conditions = new ArrayList<JsonQuery<V>>();
            
            JsonQuery<V> condition=JsonQuery.builder().withConditions(null).withLeaf(true).withField(field).withValue(value).withOperator(operator).build();
            this.conditions.add(condition);
            return this;
        }


        public JsonQuery<V> build()
        {
            return new JsonQuery<V>(this);
        }
    }

}
