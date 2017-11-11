package com.hijazi.jsonquery;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
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
            Path<T> p = null;
            //check if child relation
            if (field.contains("."))
            {
                String[] fields = field.split("\\.");
                p = root.get(fields[0]);
                field = fields[1];
            }
            else
            {
                p = root;
            }

            // check the operation
            switch (operator)
            {
                case EQUAL:
                {
                    if (checkIfDate(p))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.equal(p.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.equal(p.get(field), value);
                    }

                }
                case NOT_EQUAL:
                {
                    if (checkIfDate(p))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.notEqual(p.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.notEqual(p.get(field), value);
                    }
                }
                case GREATER_THAN:
                {
                    if (checkIfDate(p))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.greaterThan(p.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.greaterThan(p.<String>get(field), value.toString());
                    }
                }
                case GREATER_THAN_OR_EQUAL:
                {
                    if (checkIfDate(p))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.greaterThanOrEqualTo(p.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.greaterThanOrEqualTo(p.<String>get(field), value.toString());
                    }
                }
                case LESS_THAN:
                {
                    if (checkIfDate(p))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.lessThan(p.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.lessThan(p.<String>get(field), value.toString());
                    }
                }
                case LESS_THAN_OR_EQUAL:
                {
                    if (checkIfDate(p))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.lessThanOrEqualTo(p.<Date>get(field), formatter.parse(value.toString()));
                    }
                    else
                    {
                        return builder.lessThanOrEqualTo(p.<String>get(field), value.toString());
                    }
                }
                case IN:
                    if (checkIfDate(p))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return p.<Date>get(field).in(((List) value).stream().map(x ->

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
                        final Class<? extends Object> type = p.get(field).getJavaType();
                        switch (type.getSimpleName())
                        {
                            case "Integer":
                                return p.<Integer>get(field).in((List<Integer>) value);
                            case "Long":
                                return p.<Long>get(field).in((List<Long>) value);
                            case "Double":
                                return p.<Double>get(field).in((List<Double>) value);
                            case "BigDecimal":
                                return p.<BigDecimal>get(field).in((List<BigDecimal>) value);
                            case "String":
                                return p.in(value);

                        }
                    }

                case IS_NULL:
                    System.out.println(field);
                    return p.isNull();
                case IS_NOT_NULL:
                    return p.isNotNull();
                case NOT_IN:
                    if (checkIfDate(p))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.not(p.<Date>get(field).in(((List) value).stream().map(x ->

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
                        return builder.not(p.in(value));

                    }
                case BETWEEN:
                    if (checkIfDate(p))
                    {
                        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
                        return builder.between(p.<Date>get(field), formatter.parse(value.toString()), formatter.parse(toValue.toString()));
                    }
                    else
                    {
                        final Class<? extends Object> type = p.get(field).getJavaType();
                        switch (type.getSimpleName())
                        {
                            case "Integer":
                                return builder.between(p.<Integer>get(field), (Integer) value, (Integer) toValue);
                            case "Long":
                                return builder.between(p.<Long>get(field), (Long) value, (Long) toValue);
                            case "Double":
                                return builder.between(p.<Double>get(field), (Double) value, (Double) toValue);
                            case "BigDecimal":
                                return builder.between(p.<BigDecimal>get(field), (BigDecimal) value, (BigDecimal) toValue);
                            case "String":
                                return builder.between(p.<String>get(field), (String) value, (String) toValue);
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


    private boolean checkIfDate(Path<T> root)
    {
        final Class<? extends Object> type = root.get(field).getJavaType();

        if (type.getSimpleName().equals("Date") || type.getSimpleName().equals("DateTime") || type.getSimpleName().equals("LocalDate"))
        {
            return true;
        }

        return false;
    }

}
