package com.waqar.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;

public class TopicExample implements MessageListener{

	@Override
	public void onMessage(Message message) {
		TextMessage text = (TextMessage) message;
		String sMessage=null;
		try{
			sMessage = text.getText();
		}catch(JMSException e){
			e.printStackTrace();
		}
		System.out.println("Message received: "+ sMessage);
	}
	
	public void example() throws Exception {
		String destinationName = "topic/topicA";
		Context ic = null;
		ConnectionFactory cf = null;
		Connection connection = null;
		
		try{
			ic = ContextUtil.getInitialContext();
			
			cf = (ConnectionFactory) ic.lookup("ConnectionFactory");
			Topic topic = (Topic) ic.lookup(destinationName);
			
			connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = session.createProducer(topic);
			MessageConsumer subscriber = session.createConsumer(topic);
			
			subscriber.setMessageListener(this);
			connection.start();
			
			TextMessage message = session.createTextMessage("Hello!");
			publisher.send(message);
			
			Scanner keyIn = new Scanner(System.in); 
	           
	        System.out.print("JMS Server listening. Type a Key + CR to exit\n");
	        keyIn.next();
			
		} finally {
			if(ic != null)
	         {
	            try
	            {
	               ic.close();
	            }
	            catch(Exception e)
	            {
	               throw e;
	            }
	         }
	          
	         // ALWAYS close your connection in a finally block to avoid leaks.
	         // Closing connection also takes care of closing its related objects e.g. sessions.
	         closeConnection(connection);
		}
	}
	
	private void closeConnection(Connection con){      
	      try{
	         if (con != null){
	            con.close();
	         }         
	      }
	      catch(JMSException jmse){
	          System.out.println("Could not close connection " + con +" exception was " + jmse);
	      }
	}
	
	public static void main(String[] args) throws Exception {
		new TopicExample().example();	
	}

}
