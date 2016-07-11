package Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class XmlConverter {

    public static String convertToXml(Object toXmlObject){

        try {

            Writer      writer      = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance( toXmlObject.getClass());
            Marshaller  marshaller  = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal( toXmlObject, writer);

            return writer.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object readFromXml( Class objectClass, String input){

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance( objectClass);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Reader reader = new StringReader( input);
            return unmarshaller.unmarshal( reader);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}
