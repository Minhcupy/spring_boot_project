Start application:
./gradlew bootRun;
./gradlew bootRun -x test

Build application:
./gradlew clean build;
./gradlew clean build -x test

Build docker image:
docker build -t <account>/springboot_project:0.9.0 .;
docker build -t minh2472004/springboot_project:0.9.0 .

Push docker image to Docker Hub:
docker image push <account>/springboot_project:0.9.0;
docker image push minh2472004/springboot_project:0.9.0


Create network:
docker network create springboot-network;

Start MySQL in springboot-network:
docker run --network springboot-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=24072004 -d mysql:8.0.41-debian      

Run your application in springboot-network:
docker run --name springboot_project --network springboot-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql:3306/springboot_project springboot_project:0.0.1
