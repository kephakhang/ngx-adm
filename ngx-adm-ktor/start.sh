#!/bin/sh

export BASE="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED"
#java  -server -Xms4g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+UseStringDeduplication -jar ./build/libs/ngx-server.jar
java  -server $BASE -Xms4g -Xmx4g -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+UseStringDeduplication -jar ./build/libs/ngx-adm-ktor-0.0.1.jar

# tmux new -d -s admin "/usr/bin/java -jar -Xms1024m -Xmx1536m /home/YoungPlusSoft/ngx-server.jar"

# tail -f logs/run.log
