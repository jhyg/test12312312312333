## How to run

- Run axon server and mysql firstly

```
cd infra
docker-compose up
```

## Build common API & Run each service

'''
cd common-api
mvn install
cd ..

cd order
mvn spring-boot:run
cd ..

cd delivery
mvn spring-boot:run
cd ..

cd showview
mvn spring-boot:run
cd ..

'''
