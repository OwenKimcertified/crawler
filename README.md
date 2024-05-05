# item crwaling

Spring server, MySQL -> Druid(scheduled)

### __to-do list__

[x] RestAPI get, put

[x] MongoDB Meta data Schema

[x] kafka API logging(transaction) + monitoring (prometheus)

![img](./img/1.png)

![img](./img/2.png)

### Network load test

![img](./img/3.png)

Shutting down a container(broker) increases the network usage of another broker.

[] spark(api serving + *query tunning mission), DW for ad-hoc query

[] Airflow(automation) 

[] day, week, month query

[] recommendor system

### Spring

for RestAPI

Controller, Dto, Service Logic(MVC pattern) and dirty checking

### Python 3.9

[x] Crwaling async code (playwright) More pages, More performance

ㄴ  10% performance improvement when crawling 5 pages.



Break point
- gil
- thread
- processor
- async, coroutine( .create_task .gather)
