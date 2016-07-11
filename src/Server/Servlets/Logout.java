package Server.Servlets;

import Server.User;
import Server.UserList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");

        UserList userList = UserList.getInstance();

        User user = userList.getByName(login);
        user.setOnline(false);
    }
}
