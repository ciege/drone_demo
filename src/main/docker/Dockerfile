FROM openjdk:8-jre-alpine

# add directly the war
ADD *.war /app.war

EXPOSE 8080
CMD java -Djava.security.egd=file:/dev/./urandom -jar /app.war
