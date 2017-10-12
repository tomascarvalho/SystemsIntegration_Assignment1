package Jorge_Tomás;

/**
 * Created by jorgearaujo on 26/09/2017.
 */
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
            cntxt.setClientID("client1");
            //create topic consumer
            JMSConsumer cons = cntxt.createConsumer(d);
            //create durable consumer
            JMSConsumer consumer2 = cntxt.createDurableConsumer(this.tpc,"client1");
            //set mesasge listener
            consumer2.setMessageListener(this);

            return cons.receiveBody(String.class);
        }
    }

        public static void main(String[] args) throws NamingException {
        System.out.println(new Receiver().receive());
    }


    @Override
    public void onMessage(Message message) {

        //receive message from topic
        System.out.println(message);
    }
}

