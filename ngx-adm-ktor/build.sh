#!/bin/sh

export JAVA_HOME=/home/kepha/.jdks/corretto-17.0.3
export PATH=$JAVA_HOME/bin:$PATH

gradle clean

gradle build --exclude-task test --stacktrace


