package Client;

import Message.Message;
import Server.RespCode;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.stream.Stream;

public class ClientMessageRunner {

    public static byte[] getFromServlet(String urlStr){

        InputStream is = null;

        try {

            URL url = new URL( urlStr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            int respCode = http.getResponseCode();

            if( respCode != RespCode.OK_RESPONSE){
                JOptionPane.showMessageDialog(null, "Error, response code " + respCode);
                return null;
            }

            is = http.getInputStream();

            byte[] buf = new byte[ is.available()];

            is.read(buf);

            return buf;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeInputStream(is);
        }

        return null;
    }

    public static int postMessage(String urlStr, Message message){

        OutputStream outputStream = null;

        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            outputStream = connection.getOutputStream();
            outputStream.write( message.toXML().getBytes());

            return connection.getResponseCode();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeOutputStream( outputStream);
        }

        return -1;
    }

    private static void closeInputStream(InputStream stream){

        try {
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Server Input Stream is null");
        }
    }

    private static void closeOutputStream(OutputStream stream){

        try {
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Server Output Stream is null");
        }
    }

}
