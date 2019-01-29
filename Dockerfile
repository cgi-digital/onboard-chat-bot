FROM anapsix/alpine-java
MAINTAINER James Loveday <james.loveday@cgi.com>
EXPOSE 8090

COPY /target/onboard-chat-bot-*.jar onboard-chat-bot.jar

CMD ["java", "-jar", "onboard-chat-bot.jar"]