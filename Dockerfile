FROM openjdk:11
ADD /target/*.jar builders-service.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -jar builders-service.jar