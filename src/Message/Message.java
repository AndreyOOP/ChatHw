package Message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement
public class Message implements Serializable {

    private Date   date;
    private String from;
    private String to;
    private String text;

    public Message(){}

    public Message(Date date, String from, String to, String text) {

        this.date = date;
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public String toXML(){

        return XmlConverter.convertToXml(this);
    }

    public static Message fromXML(String input){

        return (Message) XmlConverter.readFromXml( Message.class, input);
    }

    @Override
    public String toString(){

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy:MM:dd HH:mm");

        return sdf.format(date) + "\n" + from + ": " + text;
    }

    public Date getDate() {
        return date;
    }

    @XmlElement
    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    @XmlElement
    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    @XmlElement
    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    @XmlElement
    public void setText(String text) {
        this.text = text;
    }
}
