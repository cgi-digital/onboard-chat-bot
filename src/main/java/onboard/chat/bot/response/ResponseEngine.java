package onboard.chat.bot.response;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsCloseRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostEphemeralResponse;
import com.github.seratch.jslack.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import onboard.chat.bot.ChatResponses;
import onboard.chat.bot.model.IncomingMessage;
import onboard.chat.bot.questiontypes.QuestionType;

import java.io.IOException;

/**
 * Class ResponseEngine
 * <p>
 * This class handles the reply to the incoming message object it takes the incoming message object and processes the message
 * for delimiters such as app_mention and message tags. The behaviour is determined from those tags.
 * and depending on the tag the response is either to reply to help, close the conversation or pass to the next in the
 * chain to get the required response, if that fails or cannot return a desired response the catch all function UNABLE_TO_ANSWER
 * is used.
 */
@Slf4j
public class ResponseEngine {

    /**
     * Function handleReply
     * <p>
     * Function takes the slack object, message object and channel object and either replies to the incoming message
     * or passes the message onto the next object in the chain.
     *
     * @param slack   The slack object to use
     * @param message The message object to be processed or responded to
     * @param channel The channel to send the response to
     * @throws IOException
     * @throws SlackApiException
     */
    public void handleReply(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException {
        log.info("Handling reply to incoming message");
        val incomingText = message.event.text.toLowerCase();

        if (message.event.type.equals("message") && message.event.subtype == null) {
            if (incomingText.contains("help")) {
                ResponseHandler.replyToHelp(slack, message, channel, ChatResponses.HELP_RESPONSE);
            } else if (incomingText.contains("thank")) {
                ResponseHandler.sendReply(slack, message, channel, ChatResponses.THANK_YOU_RESPONSE);
                log.info("Closing the conversation, thanks keyword sent");
                slack.methods().conversationsClose(
                        ConversationsCloseRequest.builder().token(message.token).channel(channel.getId()).build());
            } else if (incomingText.contains("human")) {
                ResponseHandler.sendReply(slack, message, channel, ChatResponses.HUMAN_RESPONSE);
            } else {
                val questionType = new QuestionType();
                ChatPostEphemeralResponse response = questionType.filterAndRespond(slack, message, channel, incomingText);
                if (!response.isOk()) {
                    ResponseHandler.sendReply(slack, message, channel, ChatResponses.UNABLE_TO_ANSWER);
                }
            }
        }
    }
}
