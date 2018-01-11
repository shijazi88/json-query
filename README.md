# **Introduction**

The idea comes from building dynamic search api without hardcoded the fields or building multiple apis, also we found that Json is the best way to build query, because most of application can parse or generate it easily.

# **Example**
Let us assume we have Person table or entity, this table contains the following properties:
* id: Number
* name: String
* age: Number
* email: String
* createdDate: Date
* active: Boolean

| Criteria | Json Query  |
|  :-------------: | :-------------: | 
|  name = 'Safwan'  | `{"field":"name","operator":"=","value":"Safwan"}` | 
| date between '2010-01-20' AND '2016-01-01' |  `{"field":"createdDate","operator":"between","value":"2010-01-20" , "toValue":"2016-01-01","dateFormat":"yyyy-mm-dd"}` | 
|  name = 'Safwan' **AND** id > 4 | `{  "operator":"and", "conditions":[   {"field":"id", "operator":">", "value":4 },  {"field":"name", "operator":"=", "value":"Safwan"}]}` | 

# **Types of Json Query**
As we see in the previous examples, we can build simple queries or composite queries

![Query Types](https://github.com/shijazi88/json-query/blob/master/src/main/resources/images/query%20types.png)

Previous image displays the types of query:

* **Simple**: query contains one condition or set of conditions, but with same logical operator for example (c1 AND c2 AND c3).
* **Composite**: query contains multiple conditions with multiple logical operators for example (c1 AND (c2 OR c3))
