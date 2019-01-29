package onboard.chat.bot;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.IOException;

@Slf4j
public class ChatHandler {

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
