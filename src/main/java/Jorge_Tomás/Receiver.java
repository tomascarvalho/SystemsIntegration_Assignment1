package Jorge_Tomás;

/**
 * Created by jorgearaujo on 26/09/2017.
 */
import com.sun.xml.internal.bind.v2.TODO;
import org.xml.sax.SAXException;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Receiver implements MessageListener{
    private ConnectionFactory cf;
    private Destination d;
    private Topic tpc;

    public Receiver() throws NamingException {
        this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.d = InitialContext.doLookup("jms/queue/QueueExample");
        //lookup for the topic
        this.tpc = InitialContext.doLookup("jms/topic/TopicExample");
    }

    public String receive() {
        try (JMSContext cntxt = this.cf.createContext("joao", "br1o+sa*")) {
            //set the client ID
            cntxt.setClientID("carsKeeper");
            //create topic consumer
            JMSConsumer receiveRequestQueue = cntxt.createConsumer(d);
            //create durable consumer
            JMSConsumer carsKeeperConsumer = cntxt.createDurableConsumer(this.tpc,"carsKeeper");
            //set mesasge listener
            receiveRequestQueue.setMessageListener(this);

            return carsKeeperConsumer.receiveBody(String.class);
        }
    }

        public static void main(String[] args) throws NamingException {
            while (true) {
                Receiver receiver = new Receiver();
                String xmlString = receiver.receive();
                System.out.println(xmlString);
                Advertisements advertisements = new Advertisements();
                if (isValidXML(xmlString, "skeleton.xsd")) {
                    advertisements = unmarshalXML(xmlString);
                }
            }
    }


    @Override
    public void onMessage(Message msg) {
        System.out.println("Received from request queue.");
        //TODO Process request
    }

    public static boolean isValidXML(String xml, String xsd)
    {
        File schemaFile = new File(xsd);
        Source xmlFile = new StreamSource(new StringReader(xml));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println("XML is valid");
            return true;
        } catch (SAXException e) {
            System.out.println("XML is NOT valid reason:" + e);
        } catch (IOException e) {
            System.err.println(e);
        }
        return false;
    }

    public static Advertisements unmarshalXML(String xmlString) {
        Advertisements advertisements = new Advertisements();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Advertisements.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlString);
            advertisements = (Advertisements) unmarshaller.unmarshal(reader);
        } catch(JAXBException je) {
            System.err.println(je);
        }
        return advertisements;
    }
}

