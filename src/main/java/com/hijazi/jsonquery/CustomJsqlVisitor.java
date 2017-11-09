package com.hijazi.jsonquery;

import org.springframework.data.jpa.domain.Specification;


public class CustomJsqlVisitor<T,V>
{
    private GenericJsqlSpecBuilder<T,V> builder;

    public CustomJsqlVisitor()
    {
        builder = new GenericJsqlSpecBuilder<T,V>();
    }

    public Specification<T> visit(JsonQuery node)
    {
        return builder.createSpecification(node);
    }

    public String toSql(JsonQuery node)
    {
        return "Not implemented yet.. :)";
    }

}
