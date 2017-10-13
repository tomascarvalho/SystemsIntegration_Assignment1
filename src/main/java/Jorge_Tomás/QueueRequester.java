package Jorge_Tomás;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.InputStreamReader;
import java.util.Scanner;



public class QueueRequester {
    private Destination d;
    private ConnectionFactory cf;


    public QueueRequester() throws NamingException {
        this.d = InitialContext.doLookup("jms/queue/QueueExample");
        this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
    }

    public void send(String request) {

        try (JMSContext cntxt = this.cf.createContext("queue", "queue")) {
            //set the client ID
            cntxt.setClientID("carsRequester");
            //create message producer
            JMSProducer sendToRequestQueue = cntxt.createProducer();
            TextMessage textMessage = cntxt.createTextMessage();
            textMessage.setText(request);
            Destination tempDest = cntxt.createTemporaryQueue();
            JMSConsumer responseConsumer = cntxt.createConsumer(tempDest);

            textMessage.setJMSReplyTo(tempDest);
            sendToRequestQueue.send(d,textMessage);
            String response = responseConsumer.receive().getBody(String.class);
            System.out.println(response);
        } catch (JMSException e) {
        //Handle the exception appropriately
        }
    }

    public static void main(String[] args) {
        try {
            QueueRequester queueRequester = new QueueRequester();
            while (true) {
                System.out.println("Insert query or insert 'quit' to quit: ");
                System.out.println("Ex: brand = bmw or price < 15000");
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();

                if (!(input.indexOf("quit") < 0)) {
                    break;
                } else {
                    queueRequester.send(input.toLowerCase());
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

}