# How to Run

This is a step-by-step guide how to run the example:

## Install Docker

Install Docker and [Docker Compose](https://docs.docker.com/compose/install/).

## Install Dapr

Install [Dapr for your local environment](https://docs.dapr.io/getting-started/install-dapr-selfhost/).

For the sake of this demo, we will use a local environment only. Dapr
can also run on top of e.g. Kubernetes in a variety of production
environments.

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

* Go to the directory `microservice-dapr-demo` and start the
  application usind the `dapr` command line tool:

```
[~/microservice-dapr/microservice-dapr-demo]dapr run --run-file dapr.yaml
...
== APP - shipping == 2023-10-24T15:00:46.346+02:00 TRACE 11392 --- [   scheduling-1] c.e.m.shipping.poller.ShippingPoller     : saving shipping 8160289206694953496
== APP - shipping == 2023-10-24T15:00:46.435+02:00  INFO 11392 --- [   scheduling-1] c.e.m.shipping.ShipmentServiceImpl       : Shipment id 8160289206694953496 already exists - ignored
== APP - shipping == 2023-10-24T15:00:46.439+02:00 TRACE 11392 --- [   scheduling-1] c.e.m.shipping.poller.ShippingPoller     : Last-Modified header 2023-10-24T12:42:44Z
== APP - invoicing == 2023-10-24T15:01:15.584+02:00 TRACE 11319 --- [   scheduling-1] c.e.m.invoicing.poller.InvoicePoller     : no new data
== APP - shipping == 2023-10-24T15:01:16.454+02:00 TRACE 11392 --- [   scheduling-1] c.e.m.shipping.poller.ShippingPoller     : no new data
```

* Open `index.html`. It contains links to the web applications.
