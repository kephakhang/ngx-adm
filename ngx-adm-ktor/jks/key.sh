ls #!/bin/sh

keytool -genkey -v -keystore key.jks -alias youngplussoft -keyalg RSA -keysize 2048 -validity 10000
keytool -importkeystore -srckeystore key.jks -destkeystore key.jks -deststoretype pkcs12
