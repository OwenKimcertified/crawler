### RECOMMEND SERVICE

![img](./img/sketch1.png)
![img](./img/sketch2.png)


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

[] recommend system

Content-Based Filtering

kafka -> logstash -> es

word2vec, BERT

real time recommend

### Spring

for RestAPI

Controller, Dto, Service Logic(MVC pattern)

dirty checking, MVC, single pattern

### Python 3.9

[x] Crwaling async code (playwright) More pages, More performance

ㄴ  10% performance improvement when crawling 5 pages.

### BERT(sequence)

차원 수 768에 대한 예외 처리, 

API 작성하여 임베딩 요청하기.

### JPA 

persistence

### Break point
- gil 
- thread
- processor
- async, coroutine( .create_task .gather)

--------------------------------------------------------------

### Django embedding sever 

문장, 단어, 댓글 임배딩 서버군 완성. 

ES 인덱스에 저장된 document 활용하여 검색 기반 추천 서비스 로직 작성.

### 수정 사항

1. 상품의 정보가 제대로 수집되지 않던 부분을 수정 

하나의 locator에 많은 정보가 담겨있는 경우, 인덱스를 일치시킬 수 없던 문제를 해결.

2. 추천 알고리즘 고도화를 위한 logging.

ㄴ 어떤 검색어가 입력되었을 때 어떤 상품이 추천되었는지에 관한 계산 로직 logging (협업 기반 필터링을 위해 미리 구현.)

ㄴ 1개의 세그먼트에 대하여 n개가 발생할 수 있어, 복잡도는 상품의 개수(m)와 비례하여 n * m 으로 선형적으로 resource 증가하는 부분을 m으로 개선

3. Embedding server군의 status code, 관련 사항을 로깅.

4. parallel consumer를 도입하여 파티션의 추가 없이 처리량(throughput) 증가. (작업 중)

ㄴ thread pool size를 조정하여 비동기 처리 시 파티션에 n개의 쓰레드가 offset에 접근 후 메세지 소비, 멱등성(idempotence) 문제를 해결.

ㄴ 트랜잭션 관련 기능을 구현
