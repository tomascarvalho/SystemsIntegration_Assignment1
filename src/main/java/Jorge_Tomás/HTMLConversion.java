package Jorge_Tomás;

import javax.ejb.Stateless;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.io.*;

import static java.awt.SystemColor.text;

/**
 * Created by jorgearaujo on 12/10/2017.
 */
public class HTMLConversion {

    private ConnectionFactory cf;
    private Destination d;
    private Topic tpc;


    public HTMLConversion() throws NamingException {
        this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
        //lookup for the topic
        this.tpc = InitialContext.doLookup("jms/topic/TopicExample");
    }

    public String receive() {

        try (JMSContext cntxt = this.cf.createContext("joao", "br1o+sa*")) {

            //set the client ID
            cntxt.setClientID("htmlSummaryCreator");

            //create durable consumer
            JMSConsumer htmlSummaryConsumer = cntxt.createDurableConsumer(this.tpc,"htmlSummaryCreator");
            //receive the xml
            return htmlSummaryConsumer.receiveBody(String.class);
        }
    }


    //function to tranform the xml fie into HTML
    public static File transformXML(String xmlFilename) throws TransformerException, FileNotFoundException {

        //XSL template to do the conversion
        Source xslFile  =  new StreamSource("stylesheet.xsl");
        //xml file to be converted
        Source xmlFile =  new StreamSource(xmlFilename);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        //output file
        OutputStream htmlFile = new FileOutputStream("htmlSummary.html");
        //apply the transformation
        Transformer transform = transformerFactory.newTransformer(xslFile);
        transform.transform(xmlFile, new StreamResult(htmlFile));
        //return the html transformed file
        File transformed = new File("htmlSummary.html");
        return transformed;
    }

    public static void main(String[] args) throws TransformerException, IOException, NamingException {
        while(true) {

            //receive the XML String
            HTMLConversion receiver = new HTMLConversion();
            String xmlString = receiver.receive();
            //write it to a file
            File file = new File ("file.xml");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(xmlString);
            out.close();

            //transfor to html
            File f = transformXML("file.xml");
            //open html with browser
            Desktop.getDesktop().browse(f.toURI());

        }

    }
}
