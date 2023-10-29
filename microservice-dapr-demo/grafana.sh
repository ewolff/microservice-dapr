#!/bin/sh
echo http://localhost:3000/
kubectl port-forward svc/grafana 3000:80 -n dapr-monitoring
