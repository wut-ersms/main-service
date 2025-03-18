# Main Service
## Use SDKManRC
https://sdkman.io/usage/
### Set Java version
```
sdk env
```

## Run Docker
```
docker compose -f docker-compose-dev.yaml up
```

## Build app

```
./gradlew clean build
```

## Run app
```
./gradlew bootRun
```
Or simply from Intellij. Run Application class (No build is needed).

## IntelliJ Kafka Plugin
https://plugins.jetbrains.com/plugin/21704-kafka

## Add to topic
http://localhost:8080/addToTopic/alerts?message=Test  
http://localhost:8080/addToTopic/mails?message=Test

## Listen at topic
http://localhost:8080/consumer/alerts  
http://localhost:8080/consumer/mails