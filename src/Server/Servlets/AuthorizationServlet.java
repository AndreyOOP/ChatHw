package Server.Servlets;

import Server.ServletStream;
import Server.RespCode;
import Server.User;
import Server.UserList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationServlet extends HttpServlet{

    public void doGet(HttpServletRequest req, HttpServletResponse resp){

        UserList userList = UserList.getInstance();

        String login    = req.getParameter("login");
        String password = req.getParameter("password");

        if( userList.isInList(login)){

            User user = userList.getByName(login);

            if( user.getPassword().contentEquals(password)){
                user.setOnline(true);
                ServletStream.sendResponse(resp, RespCode.AUTH_OK);
            }else{
                ServletStream.sendResponse(resp, RespCode.AUTH_ERR);
            }
        }else{

            userList.add( new User(login, password, true));
            ServletStream.sendResponse(resp, RespCode.USER_ADDED);
        }
    }
}
