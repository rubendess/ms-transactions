# Realtime Statistics

The main use case for our API is to calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is called every time a transaction is made. It is also the sole input of this rest API. The other one returns the statistic based of the transactions of the last 60 seconds.

-------------
Main Technologies
-------------
> - Java 8
> - Spring boot 2.0.2
> - Spring data
> - Elasticsearch (https://www.elastic.co/)
> - Junit
> - Gradle

-------------
Variables
-------------

> - **port** port to run the application (default is 6060)
> - **bancktransaction.time-limit-seconds** seconds limit to create new transactions and last seconds of statistic (default is 60)
> - **spring.data.elasticsearch.cluster-nodes** host:port (localhost:9300)
> - **spring.data.elasticsearch.cluster-name** the-cluster-name
> - **spring.data.elasticsearch.repositories.enabled: true**
> - **elasticsearch.islocal** indicate whether it is a local cluster (true or false)


-------------
Run
-------------

#### Local

To Run this project in Local environment, execute the following command: **gradle bootRun**

#### Production

To Run this project in Production environment, execute the following commands:

> - gradle build
> - java -jar project.jar

PS: The project **(.jar)** will be in **build/libs** folder.

-------------
REST API
-------------
##### Health
> http://localhost:6060/actuator/health

#####1. Every Time a new transaction happened, this endpoint will be called

> - POST /transactions

JSON body:


    {
        "amount": 2626.26,
        "timestamp": 1528595815780    
    }

Where:
* amount - is a double specifying the transaction amount
* timestamp - is a long specifying the transaction time in epoch in millis in UTC time zone (this is not current
      timestamp)

\
Returns: Empty body with either 201 or 204.
* 201 - in case of success
* 204 - if transaction is older than 60 seconds


>curl -X POST http://**localhost:6060**/transactions 
-H 'cache-control: no-cache' 
-H 'content-type: application/json'
  -d '{
	"amount": 2626.26,
    "timestamp": 1528595815780
}'

##### 2. This is the main endpoint of this task. It returns the statistic based on the transactions which happened in the last 60 seconds

> - GET /statistics

Returns:

    {
        "sum": 20000,
        "avg": 20,
        "max": 20,
        "min": 20,
        "count": 1000
    }

Where:
* **sum** is a double specifying the total sum of transaction value in the last 60 seconds
* **avg** is a double specifying the average amount of transaction value in the last 60 seconds
* **max** is a double specifying single highest transaction value in the last 60 seconds
* **min** is a double specifying single lowest transaction value in the last 60 seconds
* **count** is a long specifying the total number of transactions happened in the last 60 seconds

> curl -X GET 
  http://**localhost:6060**/statistics 
  -H 'cache-control: no-cache' 
  -H 'postman-token: 2c2a8658-a07f-75d5-8e36-6c2b08b0764c'