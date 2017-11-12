package com.hijazi.jsonquery;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hijazi.jsonquery.exceptions.QueryNotValid;


public class MongoSqlVisitor<T> implements JsonSqlVisitor<T>
{

    @Override
    public Specification<T> compositeQuery(JsonQuery node) throws QueryNotValid
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Specification<T> simpleQuery(List<SimpleJsonQuery> node, boolean isAnd) throws QueryNotValid
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Specification<T> simpleAndQuery(List<SimpleJsonQuery> node) throws QueryNotValid
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Specification<T> simpleOrQuery(List<SimpleJsonQuery> node) throws QueryNotValid
    {
        // TODO Auto-generated method stub
        return null;
    }
    

}
