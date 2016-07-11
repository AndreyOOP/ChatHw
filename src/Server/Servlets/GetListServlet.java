package Server.Servlets;

import Message.MessageList;
import Server.ServletStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetListServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {

        String fromStr = req.getParameter("from");

        int from = Integer.parseInt(fromStr);

        String msgListInXml = msgList.toXML(from);

        if( msgListInXml != null)
            ServletStream.sendResponse( resp, msgListInXml);
    }
}
