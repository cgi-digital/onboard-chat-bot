package onboard.chat.bot.questiontypes;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import onboard.chat.bot.IncomingMessage;
import onboard.chat.bot.response.ResponseHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;

@Slf4j
public class QuestionType {

    public void filterAndRespond(Slack slack, IncomingMessage message, Channel channel, String incomingText) {
        val resource = new File("./resources/resources.xml").getAbsoluteFile();

        ClassLoader classLoader = getClass().getClassLoader();
        val resourcesHandler =
                (resource.canRead())
                        ? resource : new File(classLoader.getResource("resources.xml").getFile());
        try {

            val jaxbContext = JAXBContext.newInstance(Resources.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            val resources = (Resources) unmarshaller.unmarshal(resourcesHandler);
            val templates = asList(resources.getTemplate());
            for (Template t : templates) {
                val textList = asList(t.getQuestion().getText());
                for (String s : textList) {
                    if (incomingText.toLowerCase().contains(s)) {
                        log.info("The response to print is {}", t.getResponse());
                        ResponseHandler.sendReply(slack, message, channel, t.getResponse());
                        break;
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

    }
}
