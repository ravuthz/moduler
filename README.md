# User Management REST API

## Using technology

- Generate by: [Spring Initialzer](https://start.spring.io/) with [Gradle build tool](https://gradle.org)
- Java Framework: [Spring boot version 1.5.6.RELEASE](https://projects.spring.io/spring-boot/)
- Database: [Hibernate](http://hibernate.org/) with
 [PostgreSQL](https://www.postgresql.org/) or
 [H2](http://www.h2database.com/html/main.html)
- Library:
    + Project Lombok
    + Spring Data-JPA
    + Spring Data-REST
    + Spring Security JWT
    + Spring Security OAUTH2
    + [Srpingfox Swagger 2 (v2.7.0)](https://springfox.github.io/springfox/docs/current/#gradle)
    + [Springfox Swagger UI 2 (v2.7.0)](https://springfox.github.io/springfox/docs/current/#springfox-swagger-ui)
    + [Springfox Swagger Data REST (v2.7.0)](https://springfox.github.io/springfox/docs/current/#gradle-2)
- IDE: [Idea IntelliJ 2017.1](https://www.jetbrains.com/idea/whatsnew/#v2017-1)

## Setup this project

### Clone Project
``
git clone https://github.com/ravuthz/moduler.git
``

### Build and Run Project
```
cd moduler
gradle build -x test
gradle bootRun
```

### Start Applcation with port 9999

[http://localhost:9999](http://localhost:9999)

[http://localhost:9999/swagger-ui.html](http://localhost:9999/swagger-ui.html)

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)
