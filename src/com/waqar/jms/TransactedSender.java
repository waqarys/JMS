package com.waqar.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.NamingException;

public class TransactedSender {

	public static void main(String[] args) throws JMSException, NamingException {
		Connection connection = null;
		try{
			System.out.println("Create JNDI Context");
			Context context = ContextUtil.getInitialContext();
			
			System.out.println("Get connection factory");
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
			
			System.out.println("Create connection");
			connection = connectionFactory.createConnection();
			
			System.out.println("Create Session");
			Session session = connection.createSession(false, QueueSession.DUPS_OK_ACKNOWLEDGE);
			
			System.out.println("Lookup Queue");
			Queue queue = (Queue) context.lookup("/queue/HelloWorldQueue");
			
			
			System.out.println("Start connection");
			connection.start();
			
		}finally{
			if (connection != null) {
                System.out.println("close the connection");
                connection.close();
			}
		}
		
	}
}
