package com.waqar.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.NamingException;

public class HelloWorldProducer {

	public static void main(String[] args) throws NamingException, JMSException {
		Connection connection = null;
		try{
			System.out.println("Create JNDI Context");
			Context context = ContextUtil.getInitialContext();
			
			System.out.println("Get connection factory");
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
			
			System.out.println("Create connection");
			connection = connectionFactory.createConnection();
			
			System.out.println("Create Session");
			Session session = connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			
			System.out.println("Lookup Queue");
			Queue queue = (Queue) context.lookup("/queue/HelloWorldQueue");
			
			System.out.println("Start connection");
			connection.start();
			
			System.out.println("Create Producer");
			MessageProducer producer = session.createProducer(queue); 
			
			System.out.println("Create hello world message");
			Message hellowWorldText = session.createTextMessage("Hello World!");
			
			System.out.println("Send hello world message");
			producer.send(hellowWorldText);
			
		} finally {
			if (connection != null) {
                System.out.println("close the connection");
                connection.close();
			}
		}
	}
}
