package onboard.chat.bot.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "resources")
public class Resources {
    private Template[] template;

    public Template[] getTemplate ()
    {
        return template;
    }

    public void setTemplate (Template[] template)
    {
        this.template = template;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [template = "+template+"]";
    }
}
