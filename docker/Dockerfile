FROM openjdk:11
ENV PATH=$PATH:/
ARG JAR_FILE=*.jar
COPY builders-service.jar builders-service.jar
COPY ./start-app.sh start-app.sh
ENTRYPOINT ["bash","start-app.sh"]