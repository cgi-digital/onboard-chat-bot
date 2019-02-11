package onboard.chat.bot.response;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostEphemeralRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostEphemeralResponse;
import com.github.seratch.jslack.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import onboard.chat.bot.model.IncomingMessage;

import java.io.IOException;

/**
 * Class ResponseHandler
 * <p>
 * This class handles the incoming message and sends the response back to the slack channel.
 */
@Slf4j
public class ResponseHandler {

    /**
     * Function replyToHelp
     * <p>
     * This function is a simple help response, the user types help into the interface and the bot responds using
     * this function.
     *
     * @param slack   The slack object to be used
     * @param message The incoming message object to be responded to
     * @param channel The channel to send the response to
     * @param text    The text to be used as the response text that the user will see
     * @return ChatPostEphemeralResponse The chat object returned to the caller
     * @throws IOException
     * @throws SlackApiException
     */
    public static ChatPostEphemeralResponse replyToHelp(Slack slack, IncomingMessage message, Channel channel, String text) throws IOException, SlackApiException {
        log.info("Reply to request for help");
        ChatPostEphemeralResponse response = slack.methods().chatPostEphemeral(
                ChatPostEphemeralRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("Hey <@" + message.event.user + ">, I feel your pain so here is how we can interact\n" +
                                text)
                        .user(message.event.user)
                        .asUser(false)
                        .build());
        response.setOk(true);
        return response;
    }

    /**
     * Function sendReply
     * <p>
     * This function handles the replies to the user where the warranted reply is neither a request for help or a greeting
     *
     * @param slack   The slack object to be used
     * @param message The incoming message object to be responded to
     * @param channel The channel to send the response to
     * @param text    The text to be used as the response text that the user will see
     * @return ChatPostEphemeralResponse The chat object returned to the caller
     * @throws IOException
     * @throws SlackApiException
     */
    public static ChatPostEphemeralResponse sendReply(Slack slack, IncomingMessage message, Channel channel, String text) throws IOException, SlackApiException {
        log.info("Incoming message with message contents {}", message.event.text);
        ChatPostEphemeralResponse response = slack.methods().chatPostEphemeral(
                ChatPostEphemeralRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("<@" + message.event.user + ">, " + text)
                        .user(message.event.user)
                        .asUser(false)
                        .build());
        response.setOk(true);
        return response;
    }

    /**
     * Function replyToGreeting
     *
     * This function is used to reply to the user when the issue a greeting request to the system.
     *
     * @param slack The slack object to use
     * @param message The incoming message object to respond to
     * @param channel The channel to send the response to
     * @return ChatPostEphemeralResponse The chat object returned to the caller
     * @throws IOException
     * @throws SlackApiException
     */
    public static ChatPostEphemeralResponse replyToGreeting(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException {
        ChatPostEphemeralResponse response = slack.methods().chatPostEphemeral(
                ChatPostEphemeralRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("Hey <@" + message.event.user + "> what can I do for you?")
                        .user(message.event.user)
                        .asUser(false)
                        .build());
        response.setOk(true);
        return response;
    }
}
