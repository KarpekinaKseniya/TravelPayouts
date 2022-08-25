# Travel Payouts

For starting application prefer docker-compose, but you also can use gradle.
Also configure Heroku web link you can see at column about(the site goes to sleep when not used for a long time, so you
may have to wait).
</br>
Backend side coverage by unit and integration tests.
</br>
For looking tests coverage you can use gradle command(Report is in /build/reports/jacoco/test/html/index.html)

````
gradle jacocoTestReport
````

### Gradle Instruction

If you want to generate db by liquibase scripts you should change liquibase flag and change database configs for your
own in application.properties as below

````
spring.liquibase.enabled=true
````

For starts project use commands

````
gradle clean
````

````
gradle bootRun
````

### Docker Instruction

For generated gradle build folder

````
gradle clean build jar
````

Build and Run Docker Images

````
docker-compose up
````

Rebuild Docker Images

````
docker-compose build
````

Local:

+ http://localhost:8080/
+ http://localhost:8080/swagger-ui/index.html

Task took from https://github.com/KosyanMedia/travelpayouts_RoR_test