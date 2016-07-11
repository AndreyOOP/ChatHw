package Server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServletStream {

    public static void sendResponse(HttpServletResponse response, String message){

        try(OutputStream os = response.getOutputStream()) {

            os.write( message.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendResponse(HttpServletResponse response, byte message){

        try(OutputStream os = response.getOutputStream()) {

            os.write( message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readRequest(HttpServletRequest request){

        try{

            InputStream is = request.getInputStream();

            byte[] buf = new byte[ request.getContentLength()];

            is.read(buf);

            return buf;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
