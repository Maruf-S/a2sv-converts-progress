FROM eclipse-temurin:17-jdk-alpine
ADD build/libs/*.jar dockerapp.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","dockerapp.jar"]
