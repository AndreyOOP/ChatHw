package Message;

import java.util.ArrayList;
import java.util.List;


public class MessageList {

    private static final MessageList msgList = new MessageList();

    private final List<Message> list = new ArrayList<Message>();

    public static MessageList getInstance() {
        return msgList;
    }

    private MessageList() {}

    public synchronized void add(Message m) {
        list.add(m);
    }

    public synchronized String toXML(int n) {

        List<Message> res = new ArrayList<Message>();

        for (int i = n; i < list.size(); i++)
            res.add(list.get(i));

        if (res.size() > 0) {

            TransferList transferList = new TransferList();
            transferList.setRes(res);

            return XmlConverter.convertToXml( transferList);
        }

        return null;
    }
}
