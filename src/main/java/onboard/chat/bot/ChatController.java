package onboard.chat.bot;


import com.google.gson.Gson;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import onboard.chat.bot.model.IncomingMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.HttpStatus.OK;

/**
 * Class ChatController
 *
 * Controller class to handle the incoming post requests returning chat responses to the user
 */
@Slf4j
@Controller
public class ChatController {

    @Value("${slack.token}")
    private String slackToken;

    /**
     * Class ChallengeHandler
     *
     * This class has one responsibility which is to handle the challenge token, sent by the slack server to verify the
     * endpoint url, if the challenge is returned then the endpoint is verified. If the challenge is not returned then
     * the endpoint is not verified. This is not a security issue just a simple endpoint verification process.
     */
    @Builder
    static class ChallengeHandler {
        String challengeResponse;
    }

    /**
     * Function doChat
     *
     * This function handles the incoming chat message which can be either an app_mention or a message.
     * or the chanllenge. If the challenge is issued by slack then the challenge is returned to the slack server unchanged
     * otherwise the body is processed to get the incoming message from the body and return a ResponseEntity object
     * containing the text response to what is considered to be a question.
     *
     * @param body The incoming string from the request
     * @return ResponseEntity the returned response answering the questions.
     */
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
            new ChatHandler().handleIncomingMessage(message);
        } catch (Exception ex) {
            log.info("An exception occurred " + ex.getMessage());
        }
        return new ResponseEntity(OK);
    }
}