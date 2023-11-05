#!/bin/sh
echo http://localhost:9412/
kubectl port-forward service/dapr-dev-zipkin 9412:9411
