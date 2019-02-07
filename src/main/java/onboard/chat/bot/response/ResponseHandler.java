package onboard.chat.bot.response;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostEphemeralRequest;
import com.github.seratch.jslack.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import onboard.chat.bot.IncomingMessage;

import java.io.IOException;

@Slf4j
public class ResponseHandler {

    public static void replyToHelp(Slack slack, IncomingMessage message, Channel channel, String text) throws IOException, SlackApiException {
        log.info("Reply to request for help");
        slack.methods().chatPostEphemeral(
                ChatPostEphemeralRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("Hey <@" + message.event.user + ">, I feel your pain so here is how we can interact\n" +
                                text)
                        .user(message.event.user)
                        .asUser(false)
                        .build());
    }

    public static void sendReply(Slack slack, IncomingMessage message, Channel channel, String text) throws IOException, SlackApiException {
        log.info("Incoming message with message contents {}", message.event.text);
        slack.methods().chatPostEphemeral(
                ChatPostEphemeralRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("<@" + message.event.user + ">, " + text)
                        .user(message.event.user)
                        .asUser(false)
                        .build());
    }

    public static void replyToGreeting(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException {
        slack.methods().chatPostEphemeral(
                ChatPostEphemeralRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("Hey <@" + message.event.user + "> what can I do for you?")
                        .user(message.event.user)
                        .asUser(false)
                        .build());
    }
}
