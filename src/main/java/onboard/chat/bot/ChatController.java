package onboard.chat.bot;


import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Controller
public class ChatController {

    @Value("${slack.token}")
    private String slackToken;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> doChat(@RequestBody String body) {
        val message = new Gson().fromJson(body, IncomingMessage.class);
        message.token = slackToken;
        val headers = new HttpHeaders();
        headers.set("Authorized", "Bearer " + message.token);
        if (message.challenge != null) {
            log.info("Incoming message contains challenge returning same");
            val challenge = ChallengeHandler.builder();
            challenge.challengeResponse = message.challenge;
            val challengeHandler = new Gson().toJson(challenge);
            return ResponseEntity.ok().headers(headers).body(challengeHandler);
        }
        log.info("Here is the text: " + message.event.text);

        try {
            log.info("Handling incoming message, passed to ChatHandler");
            ChatHandler.handleIncomingMessage(message);
        } catch (Exception ex) {
            log.info("An exception occurred " + ex.getMessage());
        }
        return new ResponseEntity(OK);
    }

    @Builder
    static class ChallengeHandler {
        String challengeResponse;
    }

    static class ChatHandler {

        public static void handleIncomingMessage(IncomingMessage message) throws IOException, SlackApiException {
            val slack = Slack.getInstance();
            val token = ApiToken.of(message.token);

            val channelsResponse = slack.methods().channelsList(
                    ChannelsListRequest.builder().token(token.getValue()).build());
            val channel = channelsResponse.getChannels().stream()
                    .filter(c -> c.getId().equals(message.event.channel)).findFirst().get();
            log.info("Have channel to send response to, passing to the ResponseEngine to handle the response");

            try {
                ResponseEngine.handleReply(slack, message, channel);
            } catch (Exception ex) {
                log.info("An exception occurred " + ex.getMessage());
            }
        }
    }

    static class ResponseEngine {

        public static void handleReply(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException, InterruptedException {
            log.info("Handling reply to incoming message");
            val incomingText = message.event.text.toLowerCase();
            if (message.event.type.equals("app_mention")) {
                if (message.event.text.contains("hi") || message.event.text.contains("hello")) {
                    log.info("Message is a greeting, starting the conversation");
                    replyToGreeting(slack, message, channel);
                }
            }

            if (message.event.type.equals("message") && message.event.subtype == null) {
                if (incomingText.contains("how do i")) {
                    replyToQuestion(slack, message, channel);
                }
                if (incomingText.contains("help")) {
                    replyToHelp(slack, message, channel);
                }
                if(incomingText.contains("thanks") || incomingText.contains("thank you")) {
                    sendThankYouResponse(slack, message, channel);
                }
                if(incomingText.contains("who")) {
                    replyToPersonQuestion(slack, message, channel);
                }
                if(incomingText.contains("what") || incomingText.contains("why")) {
                    sendDefaultResponse(slack, message, channel);
                }
            }
        }

        private static void replyToPersonQuestion(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException {
            log.info("Incoming message cannot answer response passing back to buddy, message contents {}", message.event.text);
            slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .token(message.token)
                            .channel(channel.getId())
                            .text("I can't answer that question, I'm really sorry :anguished:\n" +
                                    "try asking your buddy first then your manager")
                            .asUser(false)
                            .username("@onboard")
                            .build());
        }

        private static void sendThankYouResponse(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException {
            log.info("Sending thank you response");
            slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .token(message.token)
                            .channel(channel.getId())
                            .text("It's my pleasure, anytime :smile:\n" +
                                    "you know where to find me bye for now :wave:")
                            .mrkdwn(true)
                            .asUser(false)
                            .username("@onboard")
                            .build());
        }

        private static void sendDefaultResponse(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException {
            log.info("Sending default message, response to incoming message {}", message.event.text);
            slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .token(message.token)
                            .channel(channel.getId())
                            .text("This, is so embarrassing but I don't understand what your asking,\n" +
                                    "why not try typing *help* to see what I can do!")
                            .mrkdwn(true)
                            .asUser(false)
                            .username("@onboard")
                            .build());
        }

        private static void replyToGreeting(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException {
            slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .token(message.token)
                            .channel(channel.getId())
                            .text("Hey <@" + message.event.user + "> what can I do for you?").mrkdwn(true)
                            .asUser(false)
                            .username("@onboard")
                            .build());
        }

        private static void replyToQuestion(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException, InterruptedException {
            log.info("Reply to greeting, contents of the incoming message are {}", message.event.text);
            ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .token(message.token)
                            .channel(channel.getId())
                            .text("_Let's see what I can find_...").mrkdwn(true)
                            .asUser(false)
                            .username("@onboard")
                            .build());
            assert postResponse.isOk();

            Thread.sleep(1000);

            slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .token(message.token)
                            .channel(channel.getId())
                            .text("Here's what I have\n" +
                                    "Try using the slash command for example */onboard* with the section name\n " +
                                    "_/onboard <section name>_").mrkdwn(true)
                            .asUser(false)
                            .username("@onboard")
                            .build());
        }

        private static void replyToHelp(Slack slack, IncomingMessage message, Channel channel) throws IOException, SlackApiException {
            log.info("Reply to request for help");
            slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder()
                            .token(message.token)
                            .channel(channel.getId())
                            .text("Hey <@" + message.event.user + ">, I feel your pain so here is how we can interact\n" +
                                    "you can say _@onboard hello_ or _hi_\n" +
                                    "you can ask how to onboard try _how do I onboard to <team>_\n" +
                                    "I'm sure my makers have plans to add much more")
                            .mrkdwn(true)
                            .asUser(false)
                            .username("@onboard")
                            .build());
        }
    }
}