package onboard.chat.bot;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import onboard.chat.bot.model.IncomingMessage;
import onboard.chat.bot.response.ResponseEngine;

import java.io.IOException;

/**
 * Class ChatHandler
 *
 * This class handles the incoming message processed by the controller function
 * the incoming message is not processed in this class, the function takes the incoming message as a string
 * and passes that along with a slack object and a channel object to the ResponseEngine object
 */
@Slf4j
public class ChatHandler {

    /**
     * Function handleIncomingMessage
     *
     * Function is a handler, this means that it does the minimum amount of work needed to be done before passing onto the
     * next function in the chain.
     *
     * @param message The incoming message object
     *
     * @throws IOException
     * @throws SlackApiException
     */
    public void handleIncomingMessage(IncomingMessage message) throws IOException, SlackApiException {
        val slack = Slack.getInstance();
        val token = ApiToken.of(message.token);

        val channelsResponse = slack.methods().channelsList(
                ChannelsListRequest.builder().token(token.getValue()).build());
        val channel = channelsResponse.getChannels().stream()
                .filter(c -> c.getId().equals(message.event.channel)).findFirst().get();
        log.info("Have channel to send response to, passing to the ResponseEngine to handle the response");

        try {
            new ResponseEngine().handleReply(slack, message, channel);
        } catch (Exception ex) {
            log.info("An exception occurred " + ex.getMessage());
        }
    }
}
