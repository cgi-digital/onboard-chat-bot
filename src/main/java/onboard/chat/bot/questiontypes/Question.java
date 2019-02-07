package onboard.chat.bot.questiontypes;

public class Question {
    private String[] text;

    public String[] getText ()
    {
        return text;
    }

    public void setText (String[] text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+"]";
    }
}
