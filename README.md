# TimeTrackerBackend

For the Docker image creation, run the following in the project root directory:
   > ./mvnw package && java -jar target/timetrackerbackend-spring-boot-docker-0.1.0.jar

   > docker build -t alinagraf/timetrackerbackend:v1.0 .


To start the container, use:
  > docker run -it -e TIME_TRACKER_URL='http://localhost:8080' -p 8082:8082 -d --name docker-timetracker-java  alinagraf/timetrackerbackend:v1.0
  