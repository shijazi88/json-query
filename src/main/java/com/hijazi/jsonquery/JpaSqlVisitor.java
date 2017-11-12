package com.hijazi.jsonquery;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hijazi.jsonquery.exceptions.QueryNotValid;


public class JpaSqlVisitor<T> implements JsonSqlVisitor<T>
{
    private JpaSqlSpecBuilder<T> builder;

    public JpaSqlVisitor()
    {
        builder = new JpaSqlSpecBuilder<T>();
    }

    @Override
    public Specification<T> compositeQuery(JsonQuery node) throws QueryNotValid
    {
        if(! node.validate()){
            throw new QueryNotValid();
        }
        System.out.println(node.toSql());
        return builder.generateCompositeJpaSpecification(node);
    }
    @Override
    public Specification<T> simpleAndQuery(List<SimpleJsonQuery> node)
    {
        return builder.generateSimpleJpaSpecification(node,true);
    }
    @Override
    public Specification<T> simpleQuery(List<SimpleJsonQuery> node, boolean isAnd) throws QueryNotValid
    {
        if(! SimpleJsonQuery.validate(node)){
            throw new QueryNotValid();
        }
        System.out.println(SimpleJsonQuery.toSql(node, isAnd));
        return builder.generateSimpleJpaSpecification(node,isAnd);
    }
    @Override
    public Specification<T> simpleOrQuery(List<SimpleJsonQuery> node)
    {
        return builder.generateSimpleJpaSpecification(node,false);
    }

}
