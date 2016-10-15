# account money transfer REST service

by default service contains all accounts in-memory

## runnig service

```Shell
mvn clean install

java -jar target/money-transfer-0.0.1-SNAPSHOT.jar
```
service will start on 8080 port

## requests example

check whether service started 
```Shell
curl -v "http://localhost:8080/accounting/status"
```

create account
```Shell
curl -v -X POST "http://localhost:8080/accounting/create"
```

add money to account id=1
```Shell
curl -v -X POST "http://localhost:8080/accounting/add/money?id=1&amount=1000"
```

transfer from account id=1 to account id=2 money 100
```Shell
curl -v -X POST "http://localhost:8080/accounting/transfer?from=1&to=2&amount=100"
```

get account id=1
```Shell
curl -v "http://localhost:8080/accounting/account?id=1"
```
