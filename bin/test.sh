#!/bin/bash

################################################
# Build the Executable Jar file
################################################
mvn clean package -f ../pom.xml

################################################
# Run the test program
################################################
java -jar ../target/AoP-1.0-jar-with-dependencies.jar