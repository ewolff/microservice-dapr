#!/bin/sh
docker tag microservice-dapr-postgres:1 ewolff/microservice-dapr-postgres:latest
docker tag microservice-dapr-shipping:1 ewolff/microservice-dapr-shipping:latest
docker tag microservice-dapr-invoicing:1 ewolff/microservice-dapr-invoicing:latest
docker tag microservice-dapr-order:1 ewolff/microservice-dapr-order:latest
docker push ewolff/microservice-dapr-postgres:latest
docker push ewolff/microservice-dapr-shipping:latest
docker push ewolff/microservice-dapr-invoicing:latest
docker push ewolff/microservice-dapr-order:latest
