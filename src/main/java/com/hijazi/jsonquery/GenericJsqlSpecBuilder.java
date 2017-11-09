package com.hijazi.jsonquery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specifications;


public class GenericJsqlSpecBuilder<T,V>
{


    public Specifications<T> createSpecification(JsonQuery<V> node)
    {
        if (node.isLeaf())
        {
            // create node as condition
            return createConditionalSpecification(node);
        }
        else
        {
         // create node as AND or OR combination
            return createLogicalSpecification(node);
        }
    }


    public Specifications<T> createLogicalSpecification(JsonQuery<V> logicalNode)
    {
        List<Specifications<T>> specs = new ArrayList<Specifications<T>>();
        Specifications<T> temp;
        for (JsonQuery<V> node : (List<JsonQuery<V>>)logicalNode.getconditions())
        {
            temp = createSpecification(node);
            if (temp != null)
            {
                specs.add(temp);
            }
        }

        Specifications<T> result = specs.get(0);
        if (logicalNode.getOperator() == SearchOperation.AND)
        {
//            for (int i = 1; i < specs.size(); i++)
//            {
                result = Specifications.where(specs.get(0)).and(specs.get(1));
//            }
        }
        else if (logicalNode.getOperator() == SearchOperation.OR)
        {
//            for (int i = 1; i < specs.size(); i++)
//            {
                result = Specifications.where(specs.get(0)).or(specs.get(1));
//            }
        }

        return result;
    }


    public Specifications<T> createConditionalSpecification(JsonQuery<V> comparisonNode)
    {
        if(comparisonNode.getToValue() == null)
        {
            Specifications<T> result = Specifications.where(
                new GenericJsqlSpecification<T,V>(
                    comparisonNode.getField(),
                    comparisonNode.getOperator(),
                    (V) comparisonNode.getValue(),comparisonNode.getDateFormat()));
            return result;
        }
        else
        {
            Specifications<T> result = Specifications.where(
                new GenericJsqlSpecification<T,V>(
                    comparisonNode.getField(),
                    comparisonNode.getOperator(),
                    (V) comparisonNode.getValue(), (V) comparisonNode.getToValue(),comparisonNode.getDateFormat()));
            return result;
        }
       
    }

}
