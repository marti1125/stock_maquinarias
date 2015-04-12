package jobs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import models.Principal;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

import controllers.Application;

import play.db.jpa.JPA;
import play.jobs.Every;
import play.jobs.Job;

@Every("35s")
public class ConsumerCloudAMQP extends Job {
	
	public void doJob() throws IOException, ShutdownSignalException, 
		ConsumerCancelledException, InterruptedException, KeyManagementException, 
		NoSuchAlgorithmException, URISyntaxException {
		
		 	String uri = System.getenv("CLOUDAMQP_URL");
	        if (uri == null) uri = "{url from rabbitmq}";

	        ConnectionFactory factory = new ConnectionFactory();
	        factory.setUri(uri);
	        
	        //Recommended settings
	        factory.setRequestedHeartbeat(30);
	        factory.setConnectionTimeout(30000);
	        
	        com.rabbitmq.client.Connection connection = factory.newConnection();
	        com.rabbitmq.client.Channel channel = connection.createChannel();
	        
	        String queue = "maquinaria";
	        
	        QueueingConsumer consumer = new QueueingConsumer(channel);
	        channel.basicConsume(queue, true, consumer);       
	        
	        while (true) {
	        
	          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	          String message = new String(delivery.getBody());
	          
	          System.out.println("message:  " + message);
	          
	          /* TRANSACTIONAL BLOCK BEGINS */
	          //JPA.em().getTransaction().begin();
	          // solucion en: http://stackoverflow.com/questions/8488565/play-framework-immediate-save
	          
	          try{
	              // do some stuff
	              // fetching, updating, deleting, whatever
	        	  List<String> datos = new ArrayList<String>(Arrays.asList(message.split("\\|")));
	        	  
	        	  Principal p = new Principal();
		          p.dni = datos.get(0).toString();
		          p.ciudadano = datos.get(1).toString();
		          p.direccionCiudadano = datos.get(2).toString();
		          p.telefono = datos.get(3).toString();
		          p.ruc = datos.get(4).toString();	
		      	  p.empresa = datos.get(5).toString();	  	
		      	  p.direccionEmpresa = datos.get(6).toString();   	
		      	  p.fechaInicio = datos.get(7).toString(); 
		      	  p.fechaFin = datos.get(8).toString();	
		      	  p.stockMunicipalidad = datos.get(9).toString();	      	
		      	  p.costoTotal = datos.get(10).toString();
		      	  p.municipalidad = datos.get(11).toString();
		      	  p.fechaCreacion = new Date();
		          p.save();

	              JPA.em().getTransaction().commit();
	          }
	          catch (Exception e)
	          {
	              // if an error occurs, rollback the transaction
	              JPA.em().getTransaction().rollback();
	          }
	          
	          
	          
	          
	        }
        
		
    }
	
}
