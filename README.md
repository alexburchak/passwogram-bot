# PasswoGram Bot project

## Prerequisites

To build the project, you need to have these installed:

* JDK 11 - download it from [here](https://www.oracle.com/technetwork/java/javase/downloads/).
* Gradle 5 (optionally) - follow installation instructions [here](https://gradle.org/install/).

## Configuring project

Create file *gradle.properties* in the project build directory with contents:

```properties
release.useAutomaticVersion=true
```

Here,
* *release.useAutomaticVersion* forces release plugin to automatically increment project version. 

## Building the project

To build the project run

```sh
$ gradle clean build
```

Gradle will generate and compile sources, process resources, create JAR and ZIP artifacts, run unit tests.

Generated *JAR* artifacts have manifest entries like these:

    Manifest-Version: 1.0
    Implementation-Vendor: Alexander Burchak
    Implementation-Version: 1.0.0-SNAPSHOT
    Implementation-Title: PasswoGram Bot
    Built-Date: 2018-12-12T12:34:56+0300
    Built-By: al
    Built-Host: chopper
    Built-JDK: 1.8.0_161
    Main-Class: org.springframework.boot.loader.JarLauncher
    Start-Class: org.alexburchak.passwogram.bot.PasswoGramBot

### Releasing the project

When it is time to make release, run from the command line:

```sh
$ gradle release
```

The command above, except of checks that there are no local changes, not pushed into the *origin* remote, creates commit for *SNAPSHOT* version (e.g. *1.0.0-SNAPSHOT*) increment to the nearest release version (e.g. *1.0.0*), and another commit to increment version to the next *SNAPSHOT* version (e.g. *1.0.1-SNAPSHOT*).

There will also be tag created with prefix *release-* (e.g. *release-1.0.0*).

In case of minor or major version increment, specify *release.releaseVersion* and *release.newVersion* parameters:

```sh
$ gradle release -Prelease.releaseVersion=1.0.0 -Prelease.newVersion=1.1.0-SNAPSHOT
```

## Deploying the project

The bot application can be deployed as Spring Boot application runnable with *java -jar*, with embedded Tomcat.

### Bot configuration

Main application configuration file called *passwogram-bot.yml*. It defines following important sections:

* *spring* - standard Spring configuration properties (more details [here](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)).
* *passwogram-bot* - application own properties, i.e. integrations configuration etc.

```yaml
passwogram-bot:
  api-key: "123456789:AAbbCCddEEffGGhhIIjjKKllMMnnOOppQQ="
```

Here, we have following properties:
* *api-key* - API key given to you by [BotFather](https://telegram.me/BotFather). See [Creating new bot](https://core.telegram.org/bots#creating-a-new-bot) for more details 

### Deploying with embedded Web server

This approach assumes there is pre-defined folder structure (location of files from the distribution, not shown here, is configured through configuration file *passwogram-bot.yml*):

Main configuration and logger configuration files are automatically found by the application.

```sh
$ unzip -q passwogram-bot/build/distributions/passwogram-bot.zip -d /tmp/passwogram-bot

$ tree /tmp/passwogram-bot
/tmp/passwogram-bot
├── bin
│   └── passwogram-bot.sh
├── conf
│   ├── logback.xml
│   └── passwogram-bot.yml
└── lib
    └── passwogram-bot.jar

3 directories, 4 files
```

Here is an example configuration file:

```yaml
spring:
  application:
    name: passwogram-bot

server:
  port: 8888
  servlet:
    context-path: /passwogram

passwogram-bot:
  api-key: "123456789:AAbbCCddEEffGGhhIIjjKKllMMnnOOppQQ="
```

Now, you can run the server with *passwogram-bot.sh*.

### Deploying to Pivotal Cloud Foundry

First, login to Cloud Foundry with command line *cf* tool: 

```sh
$ cf login -a https://api.run.pivotal.io
API endpoint: https://api.run.pivotal.io

Email> <YOUR EMAIL>

Password> 
Authenticating...
OK
...
```

Now, you need to create configuration files in *passwogram-bot/src/main/resources/*. To do this, you may just copy them from *passwogram-bot/src/main/conf* 

```sh
$ cp passwogram-bot/src/main/resources/passwogram-bot.yml passwogram-bot/src/main/resources/application.yml
$ cp passwogram-bot/src/main/resources/logback.xml passwogram-bot/src/main/resources/logback.xml
```

or create files as stated below.
 
Application configuration file *passwogram-bot/src/main/resources/application.yml* may have following content:

```yaml
passwogram-bot:
  api-key: "123456789:AAbbCCddEEffGGhhIIjjKKllMMnnOOppQQ="
```

Logback configuration file *passwogram-bot/src/main/resources/logback.xml* may have following content:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration>
    <appender name="consoleAppender" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%X{ctx}] [%t] %-5p [%c{1}] %m%n</pattern>
        </encoder>
    </appender>

    <logger name="org.alexburchak.passwogram" level="DEBUG"/>

    <root level="INFO">
        <appender-ref ref="consoleAppender"/>
    </root>
</configuration>
```

Next, push changes to Cloud Foundry:

```sh
$ cf push passwogram-bot
Pushing from manifest to org alexburchak.org / space development as alexburchak@gmail.com...
Using manifest file manifest.yml
Deprecation warning: Use of 'buildpack' attribute in manifest is deprecated in favor of 'buildpacks'. Please see http://docs.cloudfoundry.org/devguide/deploy-apps/manifest.html#deprecated for alternatives and other app manifest deprecations. This feature will be removed in the future.

Getting app info...
Updating app with these attributes...
  name:                passwogram-bot
  path:                .../passwogram-bot/build/libs/passwogram-bot.jar
  buildpacks:
    java_buildpack
  disk quota:          1G
  health check type:   port
  instances:           1
  memory:              768M
  stack:               cflinuxfs2
  env:
    SPRING_PROFILES_ACTIVE
  routes:
    passwogram-bot-patient-hartebeest.cfapps.io
    passwogram-bot.cfapps.io

Updating app passwogram-bot...
Mapping routes...
Comparing local files to remote cache...
Packaging files to upload...
Uploading files...
 324.75 KiB / 324.75 KiB [=========================================================================================================================================================================================================================================] 100.00% 1s

...

Waiting for app to start...
   Cell 2c755f28-7f03-4a12-871c-7b1e60306330 successfully destroyed container for instance ecd2b866-d3dc-4a8f-9a3f-1a3f4f279c74

name:              passwogram-bot
requested state:   started
routes:            passwogram-bot-patient-hartebeest.cfapps.io, passwogram-bot.cfapps.io
last uploaded:     Thu 10 Jan 14:00:57 EET 2019
stack:             cflinuxfs2
buildpacks:        java_buildpack

type:            web
instances:       1/1
memory usage:    768M
start command:   JAVA_OPTS="-agentpath:$PWD/.java-buildpack/open_jdk_jre/bin/jvmkill-1.16.0_RELEASE=printHeapHistogram=1 -Djava.io.tmpdir=$TMPDIR -Djava.ext.dirs=$PWD/.java-buildpack/container_security_provider:$PWD/.java-buildpack/open_jdk_jre/lib/ext
                 -Djava.security.properties=$PWD/.java-buildpack/java_security/java.security $JAVA_OPTS" && CALCULATED_MEMORY=$($PWD/.java-buildpack/open_jdk_jre/bin/java-buildpack-memory-calculator-3.13.0_RELEASE -totMemory=$MEMORY_LIMIT -loadedClasses=14884
                 -poolType=metaspace -stackThreads=250 -vmOptions="$JAVA_OPTS") && echo JVM Memory Configuration: $CALCULATED_MEMORY && JAVA_OPTS="$JAVA_OPTS $CALCULATED_MEMORY" && MALLOC_ARENA_MAX=2 SERVER_PORT=$PORT eval exec $PWD/.java-buildpack/open_jdk_jre/bin/java
                 $JAVA_OPTS -cp $PWD/. org.springframework.boot.loader.JarLauncher
     state     since                  cpu    memory           disk           details
#0   running   2019-01-10T12:01:30Z   0.0%   168.3M of 768M   144.5M of 1G   
```

Check if service is available:

```sh
curl http://passwogram-bot.cfapps.io/actuator/info
{"version":"1.0.0-SNAPSHOT","builtJDK":"11.0.1","builtBy":"al","builtDate":"2019-01-10T12:30:41+0200","builtHost":"pepper"}
```

Ask for new password in Telegram application:

```
> Alexander
1

> PasswoGram
QeiZA8#28ch
```
