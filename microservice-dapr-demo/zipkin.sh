#!/bin/sh
echo http://localhost:9411/
kubectl port-forward service/dapr-dev-zipkin 9411:9411
