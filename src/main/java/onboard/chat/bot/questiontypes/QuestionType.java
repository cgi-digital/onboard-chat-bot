package onboard.chat.bot.questiontypes;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.model.Channel;
import lombok.extern.slf4j.Slf4j;
import onboard.chat.bot.ChatResponses;
import onboard.chat.bot.IncomingMessage;
import onboard.chat.bot.ResponseHandler;

import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
public class QuestionType {

    private List<String> tokenizeInputString(String text) {
        return asList(text.split(" "));
    }

    public void filterAndRespond(Slack slack, IncomingMessage message, Channel channel, String incomingText) {
        List<String> tokens = this.tokenizeInputString(incomingText);

        try {

            if (tokens.contains("cv")) {
                if (tokens.contains("templates")) {
                    ResponseHandler.sendStandardThinkingResponse(slack, message, channel, ChatResponses.CV_RESPONSE);
                } else {
                    ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.SEARCH_RESPONSE);
                }
            }
            if (tokens.contains("timesheet")) {
                ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.TIMESHEET_RESPONSE);
            }
            if (tokens.contains("course")) {
                ResponseHandler.sendStandardThinkingResponse(slack, message, channel, ChatResponses.COURSES_RESPONSE);
            }
            if(tokens.contains("cgi") && tokens.contains("profile")) {
                ResponseHandler.sendStandardThinkingResponse(slack, message, channel, ChatResponses.CGI_PROFILE_RESPONSE);
            }
            if(tokens.contains("hr")) {
                if(tokens.contains("portal") || tokens.contains("information") || tokens.contains("service")) {
                    ResponseHandler.sendStandardThinkingResponse(slack, message, channel, ChatResponses.HR_PORTAL_RESPONSE);
                }
            }
            if(tokens.contains("expenses")) {
                ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.EXPENSES_RESPONSE);
            }
            if(tokens.contains("it")) {
                if(tokens.contains("issue") || tokens.contains("issues") ||tokens.contains("service desk")) {
                    ResponseHandler.sendStandardThinkingResponse(slack, message, channel, ChatResponses.IT_DESK_RESPONSE);
                } else {
                    ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.DEFAULT_RESPONSE);
                }
            }
            if(tokens.contains("pay")) {
                ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.GLOBAL_PAY_RESPONSE);
            }
            if(tokens.contains("benefits")) {
                ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.BENEFITS_RESPONSE);
            }
            if(tokens.contains("applications")) {
                ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.APPLICATIIONS_HUB_RESPONSE);
            }
            if(tokens.contains("jira")) {
                ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.UNABLE_TO_ANSWER);
            }
            if(tokens.contains("onboard") || tokens.contains("onboarding")) {
                ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.HOW_DO_I_QUESTION_RESPONSE);
            }
            if(tokens.contains("timesheet")) {
                ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.TIMESHEET_RESPONSE);
            }
            if(tokens.contains("book")) {
                if(tokens.contains("room")) {
                    ResponseHandler.sendStandardThinkingResponse(slack, message, channel, ChatResponses.MEETING_ROOM_BOOKING);
                }
                if(tokens.contains("travel")){
                    ResponseHandler.sendStandardThinkingResponse(slack, message, channel, ChatResponses.TRAVEL_RESPONSE);
                }
            }
            if(tokens.contains("emergency") || tokens.contains("fire") || tokens.contains("aid") || tokens.contains("aider")) {
                ResponseHandler.replyToQuestion(slack, message, channel, ChatResponses.UNABLE_TO_ANSWER);
            }

        } catch (Exception ex) {
            log.info("An exception handling a where question type {}", ex.getMessage());
        }
    }
}
