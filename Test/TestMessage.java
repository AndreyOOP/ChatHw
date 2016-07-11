import Message.Message;
import Message.MessageList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class TestMessage {

    public static void main(String[] args) {

        MessageList mlist = MessageList.getInstance();
        Message m = new Message(new Date(), "from", "to", "text");
        mlist.add(m);
        mlist.add(m);

        System.out.println( mlist.toXML(0));

        try {

            RandomAccessFile rf = new RandomAccessFile("C:\\Users\\AS\\Google Диск\\Java Courses\\Dz\\ChatHw\\TestFolder\\x.xml", "rw");

//            rf.writeBytes( m.toXML());

            String s, res = "";
            while ( (s = rf.readLine()) != null){
                res += s;
            }

            System.out.println(res);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
