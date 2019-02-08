package onboard.chat.bot;

public interface ChatResponses {

    String HELP_RESPONSE = "you can say _@onboard hello_ or _hi_ to start a conversation\n" +
            "you can ask how to onboard try _how do I onboard to <team>_\n" +
            "you can ask where to find important onboarding cv documents\n" +
            "You can ask about completing timesheets\n" +
            "Try how do I book travel\n" +
            "Just please use the usual question marker such as where, when, how etc";

    String THANK_YOU_RESPONSE = "It's my pleasure, anytime :smile:\n" +
            "you know where to find me bye for now :wave:";

    String HUMAN_RESPONSE = "I can neither confirm nor deny the presence of a thing called a soul\n" +
            "Aren't we all the stuff that dreams are made of";

    String UNABLE_TO_ANSWER = "I'm so sorry but I don't understand your question,\n" +
            "perhaps you could try again but phrase it differently :smile:";
}
