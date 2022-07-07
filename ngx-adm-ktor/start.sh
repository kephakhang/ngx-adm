#!/bin/sh

java  -server -Xms4g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+UseStringDeduplication -jar ./build/libs/ngx-server.jar

# tmux new -d -s admin "/usr/bin/java -jar -Xms1024m -Xmx1536m /home/YoungPlusSoft/ngx-server.jar"

# tail -f logs/run.log
