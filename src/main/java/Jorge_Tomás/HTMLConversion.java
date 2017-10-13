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
public class HTMLConversion implements MessageListener{

    private ConnectionFactory cf;
    private Destination d;
    private Topic tpc;


    public void HTMLConversion() throws NamingException {
        this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.d = InitialContext.doLookup("jms/queue/QueueExample");
        //lookup for the topic
        this.tpc = InitialContext.doLookup("jms/topic/TopicExample");
    }

    public String receive() throws NamingException {

        //doing all the lookups
        this.HTMLConversion();

        try (JMSContext cntxt = this.cf.createContext("joao", "br1o+sa*")) {

            //set the client ID
            cntxt.setClientID("htmlSummaryCreator");
            //create topic consumer
            JMSConsumer receiveRequestQueue = cntxt.createConsumer(d);
            //create durable consumer
            JMSConsumer carsKeeperConsumer = cntxt.createDurableConsumer(this.tpc,"carsKeeper");
            //set mesasge listener
            receiveRequestQueue.setMessageListener(this);
            //receive the xml
            return carsKeeperConsumer.receiveBody(String.class);
        }
    }

    @Override
    public void onMessage(Message msg) {
        System.out.println("Received from request queue.");
        //TODO Process request
    }

    //function to tranform the xml fie into HTML
    public File transformXML(String xmlFilename) throws TransformerException, FileNotFoundException {

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
            HTMLConversion Receiver = new HTMLConversion();
            String xmlString = Receiver.receive();
            //write it to a file
            File file = new File ("file.xml");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(xmlString);
            out.close();

            //transfor to html
            File f = Receiver.transformXML("file.xml");
            //open html with browser
            Desktop.getDesktop().browse(f.toURI());

        }

    }
}
