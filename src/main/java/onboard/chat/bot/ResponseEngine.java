package onboard.chat.bot;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;

@Slf4j
public  class ResponseEngine {

    public static final String ONBOARD = "@onboard";

    public static void handleReply(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException, InterruptedException {
        log.info("Handling reply to incoming message");
        val incomingText = message.event.text.toLowerCase();
        if (message.event.type.equals("app_mention")) {
            if (incomingText.contains("hi") || incomingText.contains("hello")) {
                log.info("Message is a greeting, starting the conversation");
                replyToGreeting(slack, message, channel);
            }
        }

        if (message.event.type.equals("message") && message.event.subtype == null) {
            if (incomingText.contains("how do i") && incomingText.contains("timesheet")) {
                replyToQuestion(slack, message, channel, ChatResponses.TIMESHEET_RESPONSE);
            } else if (incomingText.contains("how do i")) {
                replyToQuestion(slack, message, channel, ChatResponses.HOW_DO_I_QUESTION_RESPONSE);
            }
            if (incomingText.contains("help")) {
                replyToHelp(slack, message, channel, ChatResponses.HELP_RESPONSE);
            }
            if(incomingText.contains("thanks") || incomingText.contains("thank you")) {
                sendReply(slack, message, channel, ChatResponses.THANK_YOU_RESPONSE);
            }
            if(incomingText.contains("who")) {
                sendReply(slack, message, channel, ChatResponses.UNABLE_TO_ANSWER);
            }
            if(incomingText.contains("what") || incomingText.contains("why")) {
                sendReply(slack, message, channel, ChatResponses.DEFAULT_RESPONSE);
            }
            if(incomingText.contains("where") && incomingText.contains("cv") && incomingText.contains("templates")) {
                sendStandardThinkingResponse(slack, message, channel, ChatResponses.CV_RESPONSE);
            } else if(incomingText.contains("where") && incomingText.contains("course")) {
                sendStandardThinkingResponse(slack, message, channel, ChatResponses.COURCES_RESPONSE);
            } else if(incomingText.contains("where")) {
                sendReply(slack, message, channel, ChatResponses.UNABLE_TO_ANSWER);
            }
        }
    }

    private static void replyToHelp(Slack slack, IncomingMessage message, Channel channel, String text) throws IOException, SlackApiException {
        log.info("Reply to request for help");
        slack.methods().chatPostMessage(
                ChatPostMessageRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("Hey <@" + message.event.user + ">, I feel your pain so here is how we can interact\n" +
                                text)
                        .mrkdwn(true)
                        .asUser(false)
                        .username(ONBOARD)
                        .build());
    }

    private static void sendReply(Slack slack, IncomingMessage message, Channel channel, String text) throws IOException, SlackApiException {
        log.info("Incoming message with message contents {}", message.event.text);
        slack.methods().chatPostMessage(
                ChatPostMessageRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text(text)
                        .asUser(false)
                        .username(ONBOARD)
                        .build());
    }

    private static void replyToGreeting(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException {
        slack.methods().chatPostMessage(
                ChatPostMessageRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("Hey <@" + message.event.user + "> what can I do for you?").mrkdwn(true)
                        .asUser(false)
                        .username(ONBOARD)
                        .build());
    }

    private static void replyToQuestion(Slack slack, IncomingMessage message, Channel channel, String text) throws IOException, SlackApiException, InterruptedException {
        log.info("Reply to greeting, contents of the incoming message are {}", message.event.text);
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(
                ChatPostMessageRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("_Let's see what I can find_...").mrkdwn(true)
                        .asUser(false)
                        .username(ONBOARD)
                        .build());
        assert postResponse.isOk();

        Thread.sleep(1000);

        slack.methods().chatPostMessage(
                ChatPostMessageRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text(text).mrkdwn(true)
                        .asUser(false)
                        .username(ONBOARD)
                        .build());
    }

    private static void sendStandardThinkingResponse(Slack slack, IncomingMessage message, Channel channel, String text) throws IOException, SlackApiException, InterruptedException {
        log.info("Reply to CV templates, contents of the incoming message are {}", message.event.text);
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(
                ChatPostMessageRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text("Well for most things the CGI portal is your first port of call\n" +
                                "This is no different, getting the information for you now").mrkdwn(true)
                        .asUser(false)
                        .username(ONBOARD)
                        .build());
        assert postResponse.isOk();

        Thread.sleep(1000);

        log.info("Sending message, response to incoming message {}", message.event.text);
        slack.methods().chatPostMessage(
                ChatPostMessageRequest.builder()
                        .token(message.token)
                        .channel(channel.getId())
                        .text(text)
                        .mrkdwn(true)
                        .asUser(false)
                        .username(ONBOARD)
                        .build());
    }
}
