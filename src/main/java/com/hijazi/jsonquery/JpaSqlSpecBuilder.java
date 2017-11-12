package com.hijazi.jsonquery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specifications;

public class JpaSqlSpecBuilder<T>
{

    Specifications<T> generateCompositeJpaSpecification(JsonQuery node)
    {

        if (node.getOperator() == SearchOperation.AND || node.getOperator() == SearchOperation.OR)
        {
            // create node as AND or OR combination
            return createLogicalSpecification(node);
        }
        else
        {
            // create node as condition
            return createConditionalSpecification(node);

        }
    }
    
    Specifications<T> generateSimpleJpaSpecification(List<SimpleJsonQuery> simpleConditions, boolean isAnd)
    {
        if (simpleConditions == null || simpleConditions.size() == 0)
            return null;

        System.out.println(simpleConditions.size());
        Specifications<T> specs = createSimpleConditionalSpecification(simpleConditions.get(0));

        for (int i = 1; i < simpleConditions.size(); i++)
        {
            if (isAnd)
            {
                specs = specs.and(new JpaSqlSpecification<T>(
                    simpleConditions.get(i).getField(),
                    simpleConditions.get(i).getOperator(),
                    simpleConditions.get(i).getValue(), simpleConditions.get(i).getDateFormat()));
            }
            else
            {
                specs = specs.or(new JpaSqlSpecification<T>(
                    simpleConditions.get(i).getField(),
                    simpleConditions.get(i).getOperator(),
                    simpleConditions.get(i).getValue(), simpleConditions.get(i).getDateFormat()));
            }
        }
        return specs;
    }


    private Specifications<T> createLogicalSpecification(JsonQuery logicalNode)
    {
        List<Specifications<T>> specs = new ArrayList<Specifications<T>>();
        Specifications<T> temp;
        for (JsonQuery node : (List<JsonQuery>) logicalNode.getconditions())
        {
            temp = generateCompositeJpaSpecification(node);
            if (temp != null)
            {
                specs.add(temp);
            }
        }

        Specifications<T> result = specs.get(0);
        if (logicalNode.getOperator() == SearchOperation.AND)
        {
            result = Specifications.where(specs.get(0)).and(specs.get(1));
        }
        else if (logicalNode.getOperator() == SearchOperation.OR)
        {
            result = Specifications.where(specs.get(0)).or(specs.get(1));
        }
        return result;
    }


    private Specifications<T> createConditionalSpecification(JsonQuery comparisonNode)
    {
        if (comparisonNode.getToValue() == null)
        {
            Specifications<T> result = Specifications.where(
                new JpaSqlSpecification<T>(
                    comparisonNode.getField(),
                    comparisonNode.getOperator(),
                    comparisonNode.getValue(), comparisonNode.getDateFormat()));
            return result;
        }
        else
        {
            Specifications<T> result = Specifications.where(
                new JpaSqlSpecification<T>(
                    comparisonNode.getField(),
                    comparisonNode.getOperator(),
                    comparisonNode.getValue(), comparisonNode.getToValue(), comparisonNode.getDateFormat()));
            return result;
        }

    }


    private Specifications<T> createSimpleConditionalSpecification(SimpleJsonQuery comparisonNode)
    {
        if (comparisonNode.getToValue() == null)
        {
            Specifications<T> result = Specifications.where(
                new JpaSqlSpecification<T>(
                    comparisonNode.getField(),
                    comparisonNode.getOperator(),
                    comparisonNode.getValue(), comparisonNode.getDateFormat()));
            return result;
        }
        else
        {
            Specifications<T> result = Specifications.where(
                new JpaSqlSpecification<T>(
                    comparisonNode.getField(),
                    comparisonNode.getOperator(),
                    comparisonNode.getValue(), comparisonNode.getToValue(), comparisonNode.getDateFormat()));
            return result;
        }

    }

}
