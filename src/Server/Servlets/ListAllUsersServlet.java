package Server.Servlets;

import Server.ServletStream;
import Server.User;
import Server.UserList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListAllUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();

        UserList userList = UserList.getInstance();

        for(User user: userList.getUsers()){

            if( user.getOnline())
                sb.append( user.getName() ).append(" : ").append( user.getPassword() ).append(" - OnLINE\n");
            else
                sb.append( user.getName() ).append(" : ").append( user.getPassword() ).append(" - offline\n");
        }

        ServletStream.sendResponse(resp, sb.toString());
    }
}
