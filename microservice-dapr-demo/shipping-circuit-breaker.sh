#!/bin/sh
dapr run --app-id shipping --resources-path ./circuit-breaker/ --app-protocol http --dapr-http-port 3500 -- java -jar ./microservice-dapr-shipping/target/microservice-dapr-shipping-0.0.1-SNAPSHOT.jar
