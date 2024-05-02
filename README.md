# item crwaling

Spring server, MySQL -> Druid(예정)

### __to-do list__

[x] RestAPI get, put

[x] DB design

[x] kafka API logging(transaction) + monitoring (prometheus)

![img](./img/1.png)

![img](./img/2.png)

[] spark(api serving + *query tunning mission), DW for ad-hoc query

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
