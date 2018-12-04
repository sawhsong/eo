package zebra.example.common.jms;

import java.util.Enumeration;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZebraMessageTopicListener implements MessageListener {
	private Logger logger = LogManager.getLogger(getClass());

	@SuppressWarnings("rawtypes")
	public void onMessage(Message message) {
		try {
			if (message instanceof MapMessage) {
				ActiveMQMapMessage mapMessage = (ActiveMQMapMessage)message;

				for (Enumeration names = mapMessage.getMapNames(); names.hasMoreElements();) {
					String name = (String)names.nextElement();
					Object ob = ((MapMessage)mapMessage).getObject(name);
					logger.debug("MapMessage - [" + name + "] : " + ob);
				}
			} else if (message instanceof TextMessage) {
				ActiveMQTextMessage textMessage = (ActiveMQTextMessage)message;
				logger.debug("TextMessage : "+textMessage.toString());
			}
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
	}
}