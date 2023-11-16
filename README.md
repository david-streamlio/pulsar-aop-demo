# AMQP on Pulsar Demo

This project allows you to quickly create a Pulsar cluster that have the AoP protocol handler preconfigured
to run inside a Docker container. It also includes a simple Java code example that interacts with Apache Pulsar 
using AMQP.

## Prerequisites 

These scripts assume that you have the following development tools available in your $PATH. If this is NOT the 
case, please install them before attempting to use the provided shell scripts.

- Apache Maven
- A JDK version 17 or greater
- Docker Desktop

## Setup
The project includes two bash scripts that automate the demonstration process. Therefore, the first step is to make 
them executable by using the following steps:
  1. `cd bin`
  2. Using the following command: `chmod a+x *.sh` to change the permissions

## Getting Started
- The `bin/start-pulsar.sh` script must be run first, to complete the following steps:
  1. Uses `docker-compose` to bring up an AMQP enabled Pulsar cluster 
  2. Uses the Pulsar Admin API to create the `public/vhost1` namespace and set the retention policies.

## Containerizing the application with BuildPacks
- In order to have the same code running with different versions of the RabbitMQ client, you can follow these steps
  1. Update the `amqp-client.version` property in the pom.xml file to the version you want
  2. Also update the `RABBIT_CLIENT_VERSION` variable in the `RabbitMQDemo' class to match the version used.
  3. Containerize the application with `pack build aop-demo:vX.X.0 --path . --builder paketobuildpacks/builder-jammy-tiny` , where vX.X.X matches the client version

## Running the test    
- The `bin/test.sh` script can then be run to confirm the AMQP functionality of Pulsar
  1. Builds an executable JAR file from the projects java code
  2. Runs the executable JAR file to publish and consumer data over Pulsar's AMQP port
  3. When you are finished run the `bin/stop-pulsar.sh` command to tear down the cluster

## Changing the Pulsar broker configuration settings
If you want to modify any of the configuration settings, you can simply add an entry to the `environment` stanza 
of the `infrastructure/pulsar.yaml` file that is prepended with the prefix `PULSAR_PREFIX_` followed by the name 
of the property you want to override.


