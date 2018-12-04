package zebra.example.common.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class ZebraMessageQueueReceiver {
	@Autowired
	private JmsTemplate jmsQueueTemplate;

	public String receive() {
//		return (String)jmsQueueTemplate.receiveAndConvert(ConfigUtil.getProperty("jms.queue.name"));
		return (String)jmsQueueTemplate.receiveAndConvert("EmbeddedDestination");
	}
}