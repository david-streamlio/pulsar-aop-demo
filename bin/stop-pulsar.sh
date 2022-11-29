#!/bin/bash

INFRA_DIR="../infrastructure"

docker compose --project-name persistence --file $INFRA_DIR/pulsar.yaml down
