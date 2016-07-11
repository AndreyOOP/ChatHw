package Client;

import Message.Message;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.event.*;
import java.util.Date;

public class UserInterface {

    //<editor-fold desc="Fields">
    private UserInterface userInterface = this;

    private GetThread getChatMessages = new GetThread(userInterface);

    private JTextArea   chatArea          = new JTextArea  (16, 58);
    private JScrollPane scroll            = new JScrollPane(chatArea);
    private JTextField  inputMessageField = new JTextField ();
    private JPanel      jPanel            = new JPanel     ();

    private JMenuItem   login             = new JMenuItem  ("Login");
    private JMenuItem   listAllUsers      = new JMenuItem  ("List All Users");
    private JMenuItem   createChatRoom    = new JMenuItem  ("Create Chat Room");
    private JMenuItem   exit              = new JMenuItem  ("Exit");
    private JMenuItem   help              = new JMenuItem  ("Help");
    private JMenu       menu              = new JMenu      ("Menu");
    private JMenu       helpMenu          = new JMenu      ("Help");
    private JMenuBar    menuBar           = new JMenuBar   ();

    private JFrame      frame             = new JFrame     ();

    private static LoginInterface loginInterface;

    private static String user; //current logged in user of chat window
    //</editor-fold>

    public UserInterface(String windowName){

        DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        chatArea.setEditable(false);

        scroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        inputMessageField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                super.keyPressed(e);

                if( e.getKeyCode() == KeyEvent.VK_ENTER){

                    sendTextMessage();
                }
            }
        });

        jPanel.setLayout( new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setBorder( new TitledBorder( new EtchedBorder(), "Chat Area" ) );
        jPanel.add ( scroll );
        jPanel.add ( inputMessageField );

        menu.add(login);
        menu.add(listAllUsers);
        menu.add(createChatRoom);
        menu.add(exit);
        helpMenu.add(help);

        menuBar.add( menu);
        menuBar.add( helpMenu);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginInterface = LoginInterface.getLoginInterface(userInterface);
            }
        });
        listAllUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                byte[] response = ClientMessageRunner.getFromServlet("http://localhost:8080/listall");
                chatArea.append( new String(response));
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
                System.exit(0);
            }
        });
        createChatRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Chat room will be implemented into version 2");
            }
        });
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String helpMessage = "private message p\\nick Your Text Message\n" +
                                     "chat room will be implemented into version 2";

                JOptionPane.showMessageDialog(null, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setJMenuBar( menuBar);
        frame.add( jPanel);
        frame.pack();
        frame.setLocationRelativeTo (null);
        frame.setResizable(false);
        frame.setTitle(windowName);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                logout();
            }
        });
    }

    public void setCurrentUser(String user) {

        UserInterface.user = user;
        frame.setTitle(user);
    }

    public static String getUser() {
        return user;
    }

    private void sendTextMessage(){

        if( user == null){
            JOptionPane.showMessageDialog(null, "Please login");
            return;
        }

        String messageText = inputMessageField.getText();
        String to = "";

        if(messageText.startsWith("p\\")){

            try {

                String[] parts = messageText.split(" ");
                to = parts[0].substring(2);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Incorrect command syntax");
                e.printStackTrace();
            }
        }
        Message message = new Message(new Date(), user, to, "\t" + messageText + "\n");
        inputMessageField.setText("");

        ClientMessageRunner.postMessage("http://localhost:8080/add", message);
    }

    public void appendTextToChatArea(String text){
        chatArea.append(text);
    }

    public void startGetChatMessagesThread(){

        try {
            getChatMessages.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(){
        if(UserInterface.user != null)
            ClientMessageRunner.getFromServlet("http://localhost:8080/logout?login=" + UserInterface.user);
    }
}
