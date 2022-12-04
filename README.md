# AMQP on Pulsar Demo

This project allows you to quickly create a Pulsar cluster that have the AoP protocol handler preconfigured to run inside a Docker container. It also includes a simple Java code example that interacts with Apache Pulsar using AMQP.

## Prerequisites 

These scripts assume that you have the following development tools available in your $PATH. If this is NOT the case, please install them before attempting to use the provided shell scripts.

- Apache Maven
- A JDK version 11 or greater
- Docker Desktop

## Setup
The project includes two bash scripts that automate the demonstration process. Therefore, the first step is to make them executable by using the following steps:
  1. `cd bin`
  2. Using the following command: `chmod a+x *.sh` to change the permissions

## Getting Started
- The `bin/start-pulsar.sh` script must be run first, to complete the following steps:
  1. Uses `docker-compose` to bring up an AMQP enabled Pulsar cluster 
  2. Uses the Pulsar Admin API to create the `public/vhost1` namespace and set the retention policies.

## Running the test    
- The `bin/test.sh` script can then be run to confirm the AMQP functionality of Pulsar
  1. Builds an executable JAR file from the projects java code
  2. Runs the executable JAR file to publish and consumer data over Pulsar's AMQP port
  3. When you are finished run the `bin/stop-pulsar.sh` command to tear down the cluster

## Changing the Pulsar broker configuration settings
If you want to modify anuy of the configuration settings, you can can simply add an entry to the `environment` stanza of the `infrastructure/pulsar.yaml` file that is prepended with the prefix `PULSAR_PREFIX_` followed by the name of the property you want to override.


