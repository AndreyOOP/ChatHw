package Client;

import Message.Message;
import Message.XmlConverter;
import Message.TransferList;

public class GetThread extends Thread {

    private int n = 0;
    private UserInterface userInterface;

    public GetThread(UserInterface userInterface){
        this.userInterface = userInterface;
    }

    @Override
    public void run() {

        while ( !isInterrupted()) {

            byte[] response = ClientMessageRunner.getFromServlet("http://localhost:8080/get?from=" + n);

            if ( response.length != 0){

                TransferList messageList = (TransferList)XmlConverter.readFromXml( TransferList.class, new String (response));

                StringBuilder sb = new StringBuilder();

                for (Message message : messageList.getRes()) {

                    if( message.getTo().contentEquals(""))
                        sb.append(message);

                    if( message.getTo().contentEquals( UserInterface.getUser()))
                        sb.append("private: " + message);

                    if( message.getFrom().contentEquals( UserInterface.getUser()) && !message.getTo().contentEquals(""))
                        sb.append("private: " + message);

                    n++;
                }

                userInterface.appendTextToChatArea( sb.toString());
            }

            threadSleep(500);
        }
    }

    private void threadSleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
