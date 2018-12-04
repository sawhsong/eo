package zebra.example.common.jms;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.support.JmsGatewaySupport;

import zebra.util.ConfigUtil;

public class ZebraMessageTopicSender extends JmsGatewaySupport {
	@Autowired
	private JmsTemplate jmsTopicTemplate;

	public void send(String messageValue) {
		this.jmsTopicTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(messageValue);
				message.setJMSReplyTo(new ActiveMQTopic(ConfigUtil.getProperty("jms.topic.name")));
				return message;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void send(Map map) {
		jmsTopicTemplate.convertAndSend(map);
	}

	public void sendText(String messageValue) {
		jmsTopicTemplate.convertAndSend(messageValue);
	}

	public void send(Destination destination, String messageValue) {
		this.jmsTopicTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(messageValue);
				return message;
			}
		});
	}
}