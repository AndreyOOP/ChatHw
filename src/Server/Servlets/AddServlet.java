package Server.Servlets;

import Message.Message;
import Message.MessageList;
import Server.ServletStream;
import Server.RespCode;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends HttpServlet {

    private MessageList messageList = MessageList.getInstance();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {

        byte[] messageInBytes = ServletStream.readRequest(req);

        Message message = Message.fromXML( new String(messageInBytes));

        if (message != null)
            messageList.add(message);
        else
            resp.setStatus( RespCode.BAD_REQUEST);
    }
}
