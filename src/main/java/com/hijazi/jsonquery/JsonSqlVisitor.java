package com.hijazi.jsonquery;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hijazi.jsonquery.exceptions.QueryNotValid;

public interface JsonSqlVisitor<T>
{
    public Specification<T> compositeQuery(JsonQuery node) throws QueryNotValid;

    public Specification<T> simpleQuery(List<SimpleJsonQuery> node, boolean isAnd) throws QueryNotValid;

    public Specification<T> simpleAndQuery(List<SimpleJsonQuery> node) throws QueryNotValid;

    public Specification<T> simpleOrQuery(List<SimpleJsonQuery> node) throws QueryNotValid;

}
