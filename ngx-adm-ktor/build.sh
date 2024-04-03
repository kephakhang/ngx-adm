#!/bin/sh

export JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto

export PATH=$JAVA_HOME/bin:$PATH

export BASE="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED"

gradle clean

gradle build $BASE --exclude-task test --stacktrace


