package onboard.chat.bot;

public interface ChatResponses {

    String HOW_DO_I_QUESTION_RESPONSE = "Here's what I have\n" +
            "Join a channel and if there is an onboarding process for that channel\n" +
            "I'll notice that you've joined and display the information for you";

    String TIMESHEET_RESPONSE = "Timesheets are best to go through with your buddy so that they\n" +
            "can make sure your doing things correctly until your used to it\n" +
            "You can find those through the CGI portal, click *MY CGI* and then click the _timesheets_ link\n" +
            "sign in with your usual sign in details and you'll be redirected to the page\n" +
            "Just remember to get your timesheet submitted by midday *Friday* or directors burst into tears! :grinning:";

    String HELP_RESPONSE = "you can say _@onboard hello_ or _hi_\n" +
            "you can ask how to onboard try _how do I onboard to <team>_\n" +
            "you can ask where to find important onboarding cv documents\n" +
            "You can ask about completing timesheets\n" +
            "I'm sure my makers have plans to add much more interactions";

    String CV_RESPONSE = "First go to the CGI portal\n" +
            "Click on Tools and then the templates link\n" +
            "Click the Cv's and biography link and download them\n" +
            "When that's completed upload to your EPMS profile";

    String DEFAULT_RESPONSE = "This, is so embarrassing but I don't understand what your asking,\n" +
            "why not try typing *help* to see what I can do!";

    String THANK_YOU_RESPONSE = "It's my pleasure, anytime :smile:\n" +
            "you know where to find me bye for now :wave:";

    String UNABLE_TO_ANSWER = "I can't answer that question, I'm really sorry :anguished:\n" +
            "try asking your buddy first then your manager";
}
