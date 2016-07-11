package Client;

import Server.RespCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginInterface {

    private static LoginInterface instance;

    private JLabel loginLabel    = new JLabel("  Login:");
    private JLabel passwordLabel = new JLabel("  Password:");

    private static JTextField     loginField    = new JTextField(10);
    private static JPasswordField passwordField = new JPasswordField(10);

    private JButton loginButton = new JButton("Enter");

    private JPanel jPanel = new JPanel();

    private static JFrame frame  = new JFrame("Login");

    private UserInterface userInterface;


    private LoginInterface(UserInterface userInterface){

        this.userInterface = userInterface;

        jPanel.setLayout(new GridLayout(3,2));

        jPanel.add(loginLabel);
        jPanel.add(loginField);
        jPanel.add(passwordLabel);
        jPanel.add(passwordField);
        jPanel.add(loginButton);

        loginField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                super.keyPressed(e);

                if( e.getKeyCode()==KeyEvent.VK_ENTER){
                    passwordField.grabFocus();
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                super.keyPressed(e);

                if( e.getKeyCode() == KeyEvent.VK_ENTER){
                    loginAction();
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });

        frame.add(jPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public static LoginInterface getLoginInterface(UserInterface userInterface){

        if( instance == null){
            instance = new LoginInterface( userInterface);
        }

        if( !frame.isVisible()){

            frame.setVisible( true);
            loginField.setText("");
            passwordField.setText("");
        }

        return instance;
    }

    private void loginAction(){

        String login = loginField.getText();
        String passw = new String( passwordField.getPassword());

        if( login.contains(" ") || passw.contains(" ")){
            JOptionPane.showMessageDialog(null, "Login/password should not contain spaces");
            return;
        }

        byte[] response = ClientMessageRunner.getFromServlet("http://localhost:8080/auth?login=" + login + "&password=" + passw);

        if( response[0]== RespCode.USER_ADDED){
            JOptionPane.showMessageDialog(null, "User " + login + " has been registered");
            userInterface.logout();
            userInterface.setCurrentUser(login);
            userInterface.startGetChatMessagesThread();
            frame.dispose();
        }

        if( response[0]== RespCode.AUTH_ERR){
            JOptionPane.showMessageDialog(null, "Incorrect Login/Password");
        }

        if( response[0] == RespCode.AUTH_OK){
            JOptionPane.showMessageDialog(null, login + " logged in");
            userInterface.logout();
            userInterface.setCurrentUser(login);
            userInterface.startGetChatMessagesThread();
            frame.dispose();
        }
    }
}
