package onboard.chat.bot;


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

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Controller
public class ChatController {

    @Value("${slack.token}")
    private String slackToken;

    @Builder
    static class ChallengeHandler {
        String challengeResponse;
    }

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
}