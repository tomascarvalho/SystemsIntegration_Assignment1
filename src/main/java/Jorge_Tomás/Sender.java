package Jorge_Tomás;

import org.wildfly.extension.messaging.activemq.jms.JMSTopicAdd;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 * Created by jorgearaujo on 26/09/2017.
 */


public class Sender {
    private ConnectionFactory cf;
    private Destination d;
    private Topic tpc;


    public Sender() throws NamingException {
        this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");

        //lookup for queue
        this.d = InitialContext.doLookup("jms/queue/QueueExample");

        //lookup for Topic
        this.tpc = (Topic) InitialContext.doLookup("jms/topic/TopicExample");

    }

    public void send(String msg) throws NamingException {

        try (JMSContext cntx = this.cf.createContext("joao", "br1o+sa*")) {

            //topic producer
            JMSProducer prod = cntx.createProducer();
            //send message to topic
            prod.send(tpc,msg);
        }
    }

}
