#!/bin/sh

export PASSWOGRAM_BOT_HOME=`dirname $0`/..
CLASSPATH=$PASSWOGRAM_BOT_HOME/conf`find $PASSWOGRAM_BOT_HOME/lib/ -name passwogram-bot*.war -exec echo -n ':'{} \;`

java -Djava.security.egd=file:/dev/urandom \
     -classpath $CLASSPATH \
     org.springframework.boot.loader.JarLauncher \
     --spring.config.location=$PASSWOGRAM_BOT_HOME/conf/passwogram-bot.yml \
     --logging.config=$PASSWOGRAM_BOT_HOME/conf/logback.xml
