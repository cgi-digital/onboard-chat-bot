package onboard.chat.bot.model;

public class Template {
    private Question question;

    private String response;

    public Question getQuestion ()
    {
        return question;
    }

    public void setQuestion (Question question)
    {
        this.question = question;
    }

    public String getResponse ()
    {
        return response;
    }

    public void setResponse (String response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [question = "+question+", response = "+response+"]";
    }
}
