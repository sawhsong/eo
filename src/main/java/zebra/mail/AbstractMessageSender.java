package zebra.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.javamail.JavaMailSender;

public abstract class AbstractMessageSender {
	protected Logger logger = LogManager.getLogger(this.getClass());
	protected JavaMailSender javaMailSender;
	protected MessageSourceAccessor messageSourceAccessor;

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}
}