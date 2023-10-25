#!/bin/sh
docker build --tag=microservice-dapr-postgres:1 postgres
docker build --tag=microservice-dapr-apache:1 apache
docker build --tag=microservice-dapr-shipping:1 microservice-dapr-shipping
docker build --tag=microservice-dapr-invoicing:1 microservice-dapr-invoicing
docker build --tag=microservice-dapr-order:1 microservice-dapr-order
