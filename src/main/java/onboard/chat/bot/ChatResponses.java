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

    String HELP_RESPONSE = "you can say _@onboard hello_ or _hi_ to start a conversation\n" +
            "you can ask how to onboard try _how do I onboard to <team>_\n" +
            "you can ask where to find important onboarding cv documents\n" +
            "You can ask about completing timesheets\n" +
            "Try how do I book travel\n" +
            "Just please use the usual question marker such as where, when, how etc";

    String CV_RESPONSE = "First go to the CGI portal\n" +
            "Click on Tools and then the templates link\n" +
            "Click the Cv's and biography link and download them\n" +
            "When that's completed upload to your EPMS profile";

    String DEFAULT_RESPONSE = "This, is so embarrassing but I don't understand what your asking,\n" +
            "why not try typing *help* to see what I can do!";

    String THANK_YOU_RESPONSE = "It's my pleasure, anytime :smile:\n" +
            "you know where to find me bye for now :wave:";

    String UNABLE_TO_ANSWER = "I can't answer that question, I'm really sorry :anguished:\n" +
            "You might require security clearance or granted access rights\n" +
            "try asking your buddy first then your manager";

    String COURSES_RESPONSE = "For accessing courses and providers like skillsport\n" +
            "Again open your web browser and navigate to the CGI portal\n" +
            "Click the Tools tab then click the Learning tools link\n" +
            "This will redirect you to the learning landing page";

    String PLANNED_QUESTION_RESPONSE = "Thank you for asking that question\n " +
            "At the moment I'm not able to answer that but if you want to \n " +
            "You can raise an issue with my builders to get a response to that question";

    String CGI_PROFILE_RESPONSE = "To find your CGI profile jsut do the following\n" +
            "Navigate to the CGI portal page and go through the login steps\n" +
            "Under the Banner heading you'll see a few tabs, click the profile tab.";

    String HR_PORTAL_RESPONSE = "You'll need to go to the HR service desk\n" +
            "Navigate to the CGI portal and mouse over the tools link\n" +
            "Select the HR service center under the Member support link\n" +
            "This will open a new tab where you can deal with HR and payroll and expenses issues";

    String TRAVEL_RESPONSE = "Navigate to the CGI portal and select Tools\n" +
            "Go to Local Tools and then select the CGI Travel Portal link\n" +
            "From there you can book travel and see any restrictions that you have to meet before booking";

    String MEETING_ROOM_BOOKING = "This is different depending on where you are located\n" +
            "The best advice I can give you here is to use the CGI portal, in the top right\n" +
            "You'll find the search box try searching there for your location followed by room booking";

    String EXPENSES_RESPONSE = "This is in the same place as the timesheets\n" +
            "Use the CGI portal, Tools then Local Tools and then Timesheets and expenses.";

    String IT_DESK_RESPONSE = "You can raise an IT issue through their service desk\n" +
            "Navigate to the CGI portal and then select Tools Memeber Support IT Service Portal.";

    String GLOBAL_PAY_RESPONSE = "Use the CGI portal and follow these steps\n" +
            "Select Tools Local tools GlobalView Pay\n" +
            "This will take you directly to the myView page, you'll find a view mypay button\n" +
            "Click that to see your pay breakdown and access your pay statement";

    String BENEFITS_RESPONSE = "Access this information through the CGI portal\n" +
            "Select Tools local tools and then Member Flexible Benefits\n" +
            "This page given you information and links to the Member flexible benefits pages\n" +
            "Where you can choose the benefits that best suit your life for the next year";

    String APPLICATIIONS_HUB_RESPONSE = "Access the applications hub through the CGI portal\n" +
            "Select Tools Local Tools and click on Applications Hub";

    String SEARCH_RESPONSE = "That's a very broad subject\n" +
            "For that information I'd recommend you access the CGI portal\n" +
            "And use the search feature in the top right hand section of the page.";

    String HUMAN_RESPONSE = "I can neither confirm nor deny the presence of a thing called a soul\n" +
            "Aren't we all the stuff that dreams are made of";
}
