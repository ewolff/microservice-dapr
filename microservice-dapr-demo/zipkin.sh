#!/bin/sh
kubectl port-forward service/dapr-dev-zipkin 9411:9411
