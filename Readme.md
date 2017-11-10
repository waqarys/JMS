JBOSS:

Got to -> server/default/deploy/messaging/destinations-service.xml
Add Queue and TOPIC

Queue 
<mbean code="org.jboss.jms.server.destination.QueueService"
   name="jboss.messaging.destination:service=Queue,name=HelloWorldQueue"
   xmbean-dd="xmdesc/Queue-xmbean.xml">
   <depends optional-attribute-name="ServerPeer">jboss.messaging:service=ServerPeer</depends>
   <depends>jboss.messaging:service=PostOffice</depends>
</mbean>

TOPIC :
<mbean code="org.jboss.jms.server.destination.TopicService"
    name="jboss.messaging.destination:service=Topic,name=topicA"
    xmbean-dd="xmdesc/Topic-xmbean.xml">
   <depends optional-attribute-name="ServerPeer">jboss.messaging:service=ServerPeer</depends>
   <depends>jboss.messaging:service=PostOffice</depends>
</mbean>
