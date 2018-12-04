package zebra.example.common.jms;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import zebra.util.ConfigUtil;

public class ZebraMessageQueueSender {
	@Autowired
	private JmsTemplate jmsQueueTemplate;

	public void send(String messageValue) {
		this.jmsQueueTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(messageValue);
				message.setJMSReplyTo(new ActiveMQQueue(ConfigUtil.getProperty("jms.queue.name")));
				return message;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void send(Map map) {
		jmsQueueTemplate.convertAndSend(map);
	}

	public void sendText(String messageValue) {
		jmsQueueTemplate.convertAndSend(messageValue);
	}

	public void send(Destination destination, String messageValue) {
		this.jmsQueueTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(messageValue);
				return message;
			}
		});
	}
}