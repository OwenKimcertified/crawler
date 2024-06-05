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

### kafka error log

![img](./img/4.png)

disconnect error checking

__Airflow(crawling automation)__ 

- spring server, mongodb connection refused [fail]

[x] Crontab script scheduling. (linux)

- automation crawling

- window task scheduler (window)

[x] Airflow data pipeline

![img](./img/5.png)

![img](./img/6.png)

- batch processing(to BigQuery)

- use commit, update next migration point

spark(api serving + *query tunning mission)

- day, week, month -> serving

[] DW for ad-hoc query

[] recommend system

### Spring

for RestAPI

Controller, Dto, Service Logic(MVC pattern)

dirty checking, MVC, single pattern

### Python 3.9

[x] Crwaling async code (playwright) More pages, More performance

ㄴ  10% performance improvement when crawling 5 pages.

### BERT(sequence), word2vec(word) recommend system -ing 

- a/b test 

Break point
- gil 
- thread
- processor
- async, coroutine( .create_task .gather)

--------------------------------------------------------------
크롤링이 막혔음.. 여러가지 수정을 해보았지만 안돼서 일단 보류