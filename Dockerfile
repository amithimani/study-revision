FROM openjdk:17-alpine
COPY target/*.jar study-revision.jar
ENTRYPOINT ["java","-jar","/study-revision.jar"]