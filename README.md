<!-- ABOUT THE PROJECT -->

## About The Travel Payouts Project

![Main Page Screen Shot][main-page-screenshot]

This project contain solution on testing task from https://github.com/KosyanMedia/travelpayouts_RoR_test with the
addition of the developer's imagination.

Project contains:

+ Table with information about users.
  ![User Page Screen Shot][user-page-screenshot]
+ Table with information about partnership programs.
  ![Programs Page Screen Shot][programs-page-screenshot]
+ Dialog in which you can change subscription status </br>
  ![Change Status Dialog Screen Shot][change-status-dialog-screenshot]
+ Dialog in which you can block user's program </br>
  ![Block Program Dialog Screen Shot][block-program-dialog-screenshot]

### Built With

* [![Java][Java]][java-url]
* [![Spring Boot][Spring_Boot]][spring-url]
* [![Liquibase][Liquibase]][liquibase-url]
* [![PostgreSQL][postgres]][postgres-url]
* [![JavaScript][JS]][js-url]
* [![React][React.js]][React-url]
* [![Boostrap][Boostrap]][boostrap-url]
* [![Docker][Docker]][docker-url]
* [![Gradle][Gradle]][gradle-url]

## Getting Started

# Travel Payouts

For starting application prefer docker-compose, but you also can use gradle.
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

## Contact

[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- MARKDOWN LINKS & IMAGES -->

[main-page-screenshot]: readme_image/main_page.PNG

[user-page-screenshot]: readme_image/users_page.PNG

[programs-page-screenshot]: readme_image/programs_page.PNG

[change-status-dialog-screenshot]: readme_image/change_status_dialog.PNG

[block-program-dialog-screenshot]: readme_image/block_user_dialog.PNG

[Java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white

[java-url]: https://docs.oracle.com/en/java/

[Spring_Boot]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white

[spring-url]: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

[Liquibase]: https://img.shields.io/badge/-Liquibase-White?style=for-the-badge

[liquibase-url]: https://docs.liquibase.com/workflows/liquibase-community/using-jdbc-url-in-liquibase.html

[postgres]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white

[postgres-url]: https://www.postgresql.org/

[JS]: https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black

[js-url]: https://developer.mozilla.org/en-US/docs/Web/JavaScript

[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB

[React-url]: https://reactjs.org/

[Docker]: https://img.shields.io/badge/-Docker-fff?style=for-the-badge&logo=Docker

[docker-url]: https://docs.docker.com/

[Boostrap]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white

[boostrap-url]: https://react-bootstrap.github.io/getting-started/introduction

[Gradle]: https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white

[gradle-url]: https://docs.gradle.org/current/userguide/userguide.html

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/kkarpekina