package jobs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

import play.jobs.Every;
import play.jobs.Job;

@Every("1mn")
public class consumerCloudAMQP extends Job {
	
	public void doJob() throws IOException, ShutdownSignalException, 
		ConsumerCancelledException, InterruptedException, KeyManagementException, 
		NoSuchAlgorithmException, URISyntaxException {
		
		String uri = System.getenv("CLOUDAMQP_URL");
        if (uri == null) uri = "amqp://ohnyasyb:lhae9F38Lqc08e6ySZYlvjgVP8xWDjgI@tiger.cloudamqp.com/ohnyasyb";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        
        //Recommended settings
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);
        
        com.rabbitmq.client.Connection connection = factory.newConnection();
        com.rabbitmq.client.Channel channel = connection.createChannel();
        
        String queue = "hello";
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queue, true, consumer);       

        while (true) {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());
          System.out.println(" [x] Received '" + message + "'");
        }
		
    }
	
}
