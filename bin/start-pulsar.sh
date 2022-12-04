#!/bin/bash

INFRA_DIR="../infrastructure"


docker compose --project-name persistence --file $INFRA_DIR/pulsar.yaml up -d

################################################
# Wait 15 seconds for Pulsar to start
################################################
sleep 15

################################################
# Create namespace and set policies
################################################
echo "Creating the AMQP exchange...."
docker exec -it pulsar-1 /pulsar/bin/pulsar-admin namespaces create -b 1 public/vhost1
docker exec -it pulsar-1 /pulsar/bin/pulsar-admin namespaces set-retention -s 100M -t 2d  public/vhost1