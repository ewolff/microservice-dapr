# How to Run

This is a step-by-step guide how to run the example:

## Install Docker

Install Docker and [Docker Compose](https://docs.docker.com/compose/install/).

## Install Dapr

Install [Dapr for your local environment](https://docs.dapr.io/getting-started/install-dapr-selfhost/).

For the sake of this demo, we will use a local environment and a
Kubernetes environment later.

## Build the Applications

This step is optional. You can skip this part and
proceed to "Run the Containers".

* The example is implemented in Java. See
   https://www.java.com/en/download/help/download_options.xml about how
   to download Java. The
   examples need to be compiled so you need to install a JDK (Java
   Development Kit). A JRE (Java Runtime Environment) is not
   sufficient. After the installation you should be able to execute
   `java` and `javac` on the command line.
   You need at least Java 10. 
   
Change to the directory `microservice-dapr-demo` and run `./mvnw clean
package` (macOS / Linux) or `mvnw.cmd clean package` (Windows). This will take a while:

```
[~/microservice-dapr/microservice-dapr-demo]./mvnw clean package
....
[INFO] The original artifact has been renamed to /home/wolff/microservice-dapr/microservice-dapr-demo/microservice-dapr-order/target/microservice-dapr-order-0.0.1-SNAPSHOT.jar.original
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for microservice-dapr 0.0.1-SNAPSHOT:
[INFO]
[INFO] microservice-dapr .................................. SUCCESS [  4.424 s]
[INFO] microservice-dapr-shipping ......................... SUCCESS [ 33.489 s]
[INFO] microservice-dapr-invoicing ........................ SUCCESS [ 21.327 s]
[INFO] microservice-dapr-order ............................ SUCCESS [ 24.298 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:26 min
[INFO] Finished at: 2023-10-24T14:59:35+02:00
[INFO] ------------------------------------------------------------------------
```

If this does not work:

* Ensure that `settings.xml` in the directory `.m2` in your home
directory contains no configuration for a specific Maven repo. If in
doubt: delete the file.

* The tests use some ports on the local machine. Make sure that no
server runs in the background.

* Skip the tests: `./mvnw clean package -Dmaven.test.skip=true` or
  `mvnw.cmd clean package -Dmaven.test.skip=true` (Windows).

* In rare cases dependencies might not be downloaded correctly. In
  that case: Remove the directory `repository` in the directory `.m2`
  in your home directory. Note that this means all dependencies will
  be downloaded again.

## Run the application

* Go to the directory `postgres` and start the database:

```
[~/microservice-dapr/microservice-dapr-demo/postgres]docker-compose up
Starting postgres ... done
Attaching to postgres
postgres |
postgres | PostgreSQL Database directory appears to contain a database; Skipping initialization
postgres |
postgres | 2023-10-24 11:59:55.243 UTC [1] LOG:  starting PostgreSQL 14.0 (Debian 14.0-1.pgdg110+1) on x86_64-pc-linux-gnu, compiled by gcc (Debian 10.2.1-6) 10.2.1 20210110, 64-bit
postgres | 2023-10-24 11:59:55.243 UTC [1] LOG:  listening on IPv4 address "0.0.0.0", port 5432
postgres | 2023-10-24 11:59:55.243 UTC [1] LOG:  listening on IPv6 address "::", port 5432
postgres | 2023-10-24 11:59:55.246 UTC [1] LOG:  listening on Unix socket "/var/run/postgresql/.s.PGSQL.5432"
postgres | 2023-10-24 11:59:55.249 UTC [27] LOG:  database system was shut down at 2023-10-24 11:59:48 UTC
postgres | 2023-10-24 11:59:55.251 UTC [1] LOG:  database system is ready to accept connections
```

* Go to the directory `microservice-dapr-demo` and start the
  order microservice using the `dapr` command line tool:

```
[~/microservice-dapr/microservice-dapr-demo]dapr run -f dapr-order.yaml
...
== APP - order == 2023-10-29T11:39:13.390Z  INFO 1 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 13 endpoint(s) beneath base path '/actuator'
== APP - order == 2023-10-29T11:39:13.423Z  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http) with context path ''
== APP - order == 2023-10-29T11:39:13.431Z  INFO 1 --- [           main] com.ewolff.microservice.order.OrderApp   : Started OrderApp in 2.634 seconds (process running for 2.865)
== APP - order == 2023-10-29T11:39:13.525Z  INFO 1 --- [nio-8081-exec-2] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
== APP - order == 2023-10-29T11:39:13.525Z  INFO 1 --- [nio-8081-exec-2] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
== APP - order == 2023-10-29T11:39:13.526Z  INFO 1 --- [nio-8081-exec-2] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
```

* Go to the directory `microservice-dapr-demo` and start the
  other microservices using the `dapr` command line tool:

```
[~/microservice-dapr/microservice-dapr-demo]dapr run -f dapr-other.yaml
...
== APP - shipping == 2023-10-24T15:00:46.346+02:00 TRACE 11392 --- [   scheduling-1] c.e.m.shipping.poller.ShippingPoller     : saving shipping 8160289206694953496
== APP - shipping == 2023-10-24T15:00:46.435+02:00  INFO 11392 --- [   scheduling-1] c.e.m.shipping.ShipmentServiceImpl       : Shipment id 8160289206694953496 already exists - ignored
== APP - shipping == 2023-10-24T15:00:46.439+02:00 TRACE 11392 --- [   scheduling-1] c.e.m.shipping.poller.ShippingPoller     : Last-Modified header 2023-10-24T12:42:44Z
== APP - invoicing == 2023-10-24T15:01:15.584+02:00 TRACE 11319 --- [   scheduling-1] c.e.m.invoicing.poller.InvoicePoller     : no new data
== APP - shipping == 2023-10-24T15:01:16.454+02:00 TRACE 11392 --- [   scheduling-1] c.e.m.shipping.poller.ShippingPoller     : no new data
```

* Open `index.html`. It contains links to the web applications.

## Dashboard

* Run `dapr dashboard` to access the dashboard
* `dapr configurations` prints out the current configuration

## Tracing

* The built-in Zipkin can be accessed at
  [http://localhost:9411](http://localhost:9411). No further
  configuration needed.

## Kubernetes

Not: This will pull the Docker images from the public DockerHub
repository.

* Install Dapr on Kubernetes as explained
  [here](https://docs.dapr.io/operations/hosting/kubernetes/kubernetes-deploy/).
  After installing Dapr CLI (see above) it should be `dapr init -k
  --dev`.
* Deploy the infrastructure with `kubectl apply -f infrastructure.yaml`.
* Run the order microservices using `dapr run -k -f dapr-order.yaml`.
* Run the other microservices using `dapr run -k -f dapr-other.yaml`.
* Deploy the services using `kubectl apply -f service.yaml`.
* Open [http://localhost:80/](http://localhost:80/) to use the application.

## Tracing on Kubernetes

* Run the application using `dapr run -k -f dapr.yaml`.
* Open a portforward from localhost to the Zipkin service in the
  Kubernetes cluster: `kubectl port-forward service/dapr-dev-zipkin
  9411:9411` or `zipkin.sh`.
* You can access Zipkin at [http://localhost:9411/](http://localhost:9411/).

Troubleshooting

* Double check that tracing is enabled using `dapr configurations -k`.
* Double check that the service `dapr-dev-zipkin` is running with
  `kubectl get services`.
* You can check the configurations using the dashboard `dapr -k
  dashboard` and look at the configuration. A Zipkin endpoint should
  be configured.

## Metrics on Kubernetes

* [Install Helm](https://helm.sh/docs/intro/install/)
* Install Prometheus with `./install-prometheus.sh`.
* `kubectl get pods -n dapr-monitoring` should output the Prometheus
  Pods now.
* Install Grafana with `./install-grafana.sh`.
* This will output the admin password that we will also need later.
* Start `grafana.sh` or use `kubectl port-forward svc/grafana 3000:80
  -n dapr-monitoring` to make Grafana accessible on the local machine.
* Open [http://localhost:3000](http://localhost:3000).
* Log in with the user name `admin` and the admin password from above.
* Configure Prometheus as a datasource
  * Toggle the menu with the three bars at the top left
  * Select `Connections`
  * Select `Add new connection`
  * Select `Prometheus`
  * Click on `Add new data source` at the top right.
  * Select`Dapr` as the `Name`
  * `Default` should be on.
  * The `Prometheus server URL` will be
    `http://dapr-prom-prometheus-server.dapr-monitoring`
  * Select `Skip TLS Verify`.
  * Click `Save & Test` to make sure the connection works.
* You can import Grafana dashboards from the [Dapr
  project](https://github.com/dapr/dapr/tree/master/grafana). The
  Sidecar metrics provide some information about the services.
  * Download the JSON files from the [Dapr
  project](https://github.com/dapr/dapr/tree/master/grafana).
  * Go to the [Grafana home page](http://localhost:3000).
  * Click on `Dashboards`
  * Click on `New` at the to right.
  * Click on `Import`
  * Click on `Upload dashboard JSON file`
  * Select the downloaded JSON file.
  * Repeat if you want to import more dashboards.
* You can now access the dashboards from Home - Dashboard.
* You can add some load with `./load.sh "-X POST http://localhost/shipping/poll"`
  
This is based on the [metrics
documentation](https://docs.dapr.io/operations/observability/metrics/)
at the Dapr website.

Troubleshooting

* Double check that matrics are enabled using `dapr configurations
  -k`.
  
You can uninstall Grafana with `helm uninstall grafana -n
dapr-monitoring` and Prometheus with `helm uninstall dapr-prom -n
dapr-monitoring`.

## Resilience: Circuit Breaker

We will run the shipping microservices with a circuit breaker. As the
shipping microservice will be unable to access the order microservice,
the circuit breaker will trip. This will run locally i.e. without
Kubernetes.

* Start the shipping microservices with the circuit breaker enabled:
  `./shipping-circuit-breaker.sh`
* Create some load ` ./load.sh "-X POST http://localhost:8083/poll"`
* Note how the circuit breaker will be in half-open state after some
  time during the next polling and then again in open state. You will
  find the output in the terminal window for the shipping microservice.
* Run the order microservice: `dapr run -f dapr-order.yaml`
* Now the circuit break should change from half-open to close now.

## Resilience: Retry

* Start the shipping microservices with the circuit breaker enabled:
  `./shipping-retry.sh`
* Create some load ` ./load.sh "-X POST http://localhost:8083/poll"`
* Note that accessing the order microservice is being retried. You will
  find the output in the terminal window for the shipping microservice.
* Run the order microservice: `dapr run -f dapr-order.yaml`
* The log shows that the order service is available again and no more
  retries occur.
