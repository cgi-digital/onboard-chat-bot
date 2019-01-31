package onboard.chat.bot;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.conversations.ConversationsCloseRequest;
import com.github.seratch.jslack.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import onboard.chat.bot.questiontypes.QuestionType;

import java.io.IOException;

@Slf4j
public class ResponseEngine {

    public static void handleReply(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException, InterruptedException {
        log.info("Handling reply to incoming message");
        val incomingText = message.event.text.toLowerCase();
        if (message.event.type.equals("app_mention")) {
            if (incomingText.contains("hi") || incomingText.contains("hello")) {
                log.info("Message is a greeting, starting the conversation");
                ResponseHandler.replyToGreeting(slack, message, channel);
            }
        }

        if (message.event.type.equals("message") && message.event.subtype == null) {
            if (incomingText.contains("help")) {
                ResponseHandler.replyToHelp(slack, message, channel, ChatResponses.HELP_RESPONSE);
            }
            if (incomingText.contains("thank")) {
                ResponseHandler.sendReply(slack, message, channel, ChatResponses.THANK_YOU_RESPONSE);
                slack.methods().conversationsClose(
                        ConversationsCloseRequest.builder().token(message.token).channel(channel.getId()).build());
            }
            if (incomingText.contains("human")) {
                ResponseHandler.sendReply(slack, message, channel, ChatResponses.HUMAN_RESPONSE);
            } else {
                val questionType = new QuestionType();
                questionType.filterAndRespond(slack, message, channel, incomingText);
            }
        }
    }
}
