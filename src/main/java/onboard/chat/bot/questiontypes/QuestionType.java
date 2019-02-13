package onboard.chat.bot.questiontypes;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import onboard.chat.bot.model.IncomingMessage;
import onboard.chat.bot.model.Resources;
import onboard.chat.bot.model.Template;
import onboard.chat.bot.response.ResponseHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;

/**
 * Class QuestionType
 *
 * This class handles the different types of question dropping all words from the question but picking up on keywords
 * if the keyword is present from the XML template there should be an associated response and the response is returned
 * to the caller.
 */
@Slf4j
public class QuestionType {

    /**
     * Function filterAndRespond
     *
     * This function filters the incoming message object for the text representing the question. The function then reads the
     * XML resources file and takes the text as either a or one of a list of keywords to check for. If the keyword(s) are in the
     * list then the function gets the response for that keyword and sends that with the other parameters to the ResponseHandler
     * class to handle the response to the users request.
     *
     * @param slack The slack object
     * @param message The incoming message object
     * @param channel The channel to send the response to
     * @param incomingText The incoming text which contains the keywords to be searched for
     * @return ChatPostEphemeralResponse the chat response object returned to the caller
     */
    public ChatPostMessageResponse filterAndRespond(Slack slack, IncomingMessage message, Channel channel, String incomingText) {
        val resource = new File("./resources/resources.xml").getAbsoluteFile();

        ClassLoader classLoader = getClass().getClassLoader();
        val resourcesHandler =
                (resource.canRead())
                        ? resource : new File(classLoader.getResource("resources.xml").getFile());
        try {

            val jaxbContext = JAXBContext.newInstance(Resources.class);
            val unmarshaller = jaxbContext.createUnmarshaller();
            val resources = (Resources) unmarshaller.unmarshal(resourcesHandler);
            val templates = asList(resources.getTemplate());
            for (Template t : templates) {
                val textList = asList(t.getQuestion().getText());
                for (String s : textList) {
                    if (incomingText.toLowerCase().contains(s)) {
                        log.info("The response to print is {}", t.getResponse());
                        return ResponseHandler.sendReply(slack, message, channel, t.getResponse());
                    }
                }
            }

        }catch (IOException ex) {
                log.info("Exception: {}", ex.getMessage());
        }catch (SlackApiException ex) {
            log.info("Exception: {}", ex.getMessage());
        } catch (JAXBException ex) {
            log.info("Exception: {}", ex.getMessage());
        }
        return new ChatPostMessageResponse();
    }
}
