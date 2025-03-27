# Main Service
## Use SDKManRC
https://sdkman.io/usage/
### Set Java version
```
sdk env
```

### On Windows (not tested, not recommended)
In Intellij in "Project Structure"
- Set SDK to i.e. temurin-17
- Set language level to java 17 LTS
- Set JAVA_HOME env for jdk >= 17

## Run Docker
```
docker compose -f docker-compose-dev.yaml up
```

## Commands
### Build app

```
./gradlew clean build
```

### Run app
```
./gradlew bootRun
```
Or simply from Intellij. Run Application class (No build is needed).

### Build Docker Image
https://bmuschko.github.io/gradle-docker-plugin/current/user-guide/#spring_boot_application_plugin
```
./gradlew dockerBuildImage
```

## Swagger
http://localhost:8091/swagger-ui/index.html

### Annotations
@Tag - use on class level to describe controller  
@Operation - use on method level to describe endpoint  
@ApiResponse - use to define response codes

## Kafka
### IntelliJ Kafka Plugin
https://plugins.jetbrains.com/plugin/21704-kafka

### Add to topic
http://localhost:8091/addToTopic/alerts?message=Test  
http://localhost:8091/addToTopic/mails?message=Test

### Listen at topic
http://localhost:8091/consumer/alerts  
http://localhost:8091/consumer/mails