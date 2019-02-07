FROM anapsix/alpine-java
MAINTAINER James Loveday <james.loveday@cgi.com>
EXPOSE 8090

COPY /target/onboard-chat-bot-*.jar onboard-chat-bot.jar
RUN mkdir ./resources
COPY /src/main/resources/resources.xml ./resources/resources.xml

CMD ["java", "-jar", "onboard-chat-bot.jar"]