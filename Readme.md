# Product search engine

Upload products from a csv file to the data base and search for a product.

## Installation

run
```bash
mvn clean install

docker build -t springio/gs-spring-boot-docker .

docker run -p 8080:8080 springio/gs-spring-boot-docker

```

## To upload a csv file

```
POST
http://localhost:8080/upload
```
## To upload a csv file from the attached file src/main/resources/data.csv

```
POST
http://localhost:8080/saveToDB
```
## Example for query param

```
GET
http://localhost:8080/product
http://localhost:8080/product?type=phone&min_price=800&max_price=900
http://localhost:8080/product?type=subscription&min_price=100&max_price=500
```