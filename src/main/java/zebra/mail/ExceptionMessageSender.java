package zebra.mail;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.MimeMessageHelper;

import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

@SuppressWarnings("rawtypes")
public class ExceptionMessageSender extends AbstractMessageSender implements ApplicationListener {
	public void sendMessage(Throwable e) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, ConfigUtil.getProperty("mail.default.encoding"));

			mimeMessageHelper.setTo(new InternetAddress(ConfigUtil.getProperty("mail.exception.to"), ConfigUtil.getProperty("mail.exception.toName"), ConfigUtil.getProperty("mail.default.encoding")));
			mimeMessageHelper.setFrom(ConfigUtil.getProperty("mail.exception.from"));
			mimeMessageHelper.setSubject(e.toString());

			StringBuffer sb = new StringBuffer();
			sb.append("<html><head></head><body>");
			sb.append("<h1>");
			sb.append((e.getMessage() == null) ? e.toString() : e.getMessage());
			sb.append("</h1><br/>");
			sb.append(CommonUtil.stackTraceToString(e));
			sb.append("</body></html>");

			mimeMessageHelper.setText(sb.toString(), sb.toString());
//			mimeMessageHelper.setText(sb.toString());

			javaMailSender.send(mimeMessage);
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
	}
}