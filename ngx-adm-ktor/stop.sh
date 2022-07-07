#!/bin/sh

curl -v --resolve --insecure -I https://127.0.0.1:8080/ktor/app/shutdown
#curl -I http://127.0.0.1:8080/ktor/app/shutdown
