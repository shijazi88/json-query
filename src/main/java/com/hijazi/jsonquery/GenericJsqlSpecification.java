package com.hijazi.jsonquery;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class GenericJsqlSpecification<T, V> implements Specification<T>
{

    private String field;
    private String dateFormat;
    private SearchOperation operator;
    private V value;
    private V toValue;


    public GenericJsqlSpecification(
        String field, SearchOperation operator, V value, String dateFormat)
    {
        super();
        this.dateFormat = dateFormat;
        this.field = field;
        this.operator = operator;
        this.value = value;
    }


    public GenericJsqlSpecification(
        String field, SearchOperation operator, V value, V toValue, String dateFormat)
    {
        super();
        this.dateFormat = dateFormat;
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.toValue = toValue;
    }


    @Override
    public Predicate toPredicate(
        Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder)
    {
        try
        {

            switch (operator)
            {
                case EQUAL:
                {
                    if (checkIfDate(root))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.equal(root.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.equal(root.get(field), value);
                    }
                }
                case NOT_EQUAL:
                {
                    if (checkIfDate(root))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.notEqual(root.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.notEqual(root.get(field), value);
                    }
                }
                case GREATER_THAN:
                {
                    if (checkIfDate(root))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.greaterThan(root.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.greaterThan(root.<String>get(field), value.toString());
                    }
                }
                case GREATER_THAN_OR_EQUAL:
                {
                    if (checkIfDate(root))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.greaterThanOrEqualTo(root.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.greaterThanOrEqualTo(root.<String>get(field), value.toString());
                    }
                }
                case LESS_THAN:
                {
                    if (checkIfDate(root))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.lessThan(root.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.lessThan(root.<String>get(field), value.toString());
                    }
                }
                case LESS_THAN_OR_EQUAL:
                {
                    if (checkIfDate(root))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.lessThanOrEqualTo(root.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.lessThanOrEqualTo(root.<String>get(field), value.toString());
                    }
                }
                case IN:
                    if (checkIfDate(root))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return root.<Date>get(field).in(((List) value).stream().map(x ->

                        {
                            try
                            {
                                return formatter.parse(x.toString());
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                            return null;
                        }).collect(Collectors.toList()));

                    }
                    else
                    {
                        final Class<? extends Object> type = root.get(field).getJavaType();
                        switch (type.getSimpleName())
                        {
                            case "Integer":
                               return root.<Integer>get(field).in((List<Integer>)value);
                            case "Long":
                                return root.<Long>get(field).in((List<Long>)value);
                            case "Double":
                                return root.<Double>get(field).in((List<Double>)value);
                            case "BigDecimal":
                                return root.<BigDecimal>get(field).in((List<BigDecimal>)value);                            
                            case "String":
                                return root.get(field).in(value);

                        }
                    }

                case IS_NULL:
                    return root.get(field).isNull();
                case IS_NOT_NULL:
                    return root.get(field).isNotNull();
                case NOT_IN:
                    if (checkIfDate(root))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.not(root.<Date>get(field).in(((List) value).stream().map(x ->

                        {
                            try
                            {
                                return formatter.parse(x.toString());
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                            return null;
                        }).collect(Collectors.toList())));

                    }
                    else
                    {
                        return builder.not(root.get(field).in(value));

                    }
                case BETWEEN:
                    if (checkIfDate(root))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.between(root.<Date>get(field), formatter.parse(value.toString()), formatter.parse(toValue.toString()));
                    }
                    else
                    {
                        final Class<? extends Object> type = root.get(field).getJavaType();
                        switch (type.getSimpleName())
                        {
                            case "Integer":
                                return builder.between(root.<Integer>get(field),(Integer) value, (Integer)toValue);
                            case "Long":
                                return builder.between(root.<Long>get(field),(Long) value, (Long)toValue);
                            case "Double":
                                return builder.between(root.<Double>get(field),(Double) value, (Double)toValue);
                            case "BigDecimal":
                                return builder.between(root.<BigDecimal>get(field),(BigDecimal) value, (BigDecimal)toValue);
                            case "String":
                                return builder.between(root.<String>get(field),(String) value, (String)toValue);
                            default:
                                break;
                        }
                            
                    }

            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    private boolean checkIfDate(Root<T> root)
    {
        final Class<? extends Object> type = root.get(field).getJavaType();

        if (type.getSimpleName().equals("Date") || type.getSimpleName().equals("DateTime") || type.getSimpleName().equals("LocalDate"))
        {
            return true;
        }

        return false;
    }

}
