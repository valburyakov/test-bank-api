docker run --name mysql -e MYSQL_ROOT_PASSWORD=admin -p 3306:3306 -d mysql:5.6

create database test_bank;

Build docker image: gradle jibDockerBuild
Run in Docker: docker-compose up

*Swagger*:

http://localhost:8080/swagger-ui.html#/
