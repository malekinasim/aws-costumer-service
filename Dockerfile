FROM public.ecr.aws/docker/library/eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar","app.jar"]