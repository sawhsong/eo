package zebra.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import zebra.mail.ExceptionMessageSender;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class ExceptionLoggingAdvice {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private ExceptionMessageSender exceptionMessageSender;

	public void executeLogging(JoinPoint pjp, Exception exception) throws Exception {
		String targetClassName = pjp.getTarget().getClass().getName();
		String targetMethodName = pjp.getSignature().getName();

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.advice.exception"), "Y")) {
			logger.error("ExceptionLog : [" + CommonUtil.getSysdate("yyyy-MM-dd HH:mm:ss") + "] " + targetClassName + "." + targetMethodName + "()");
			logger.error("ExceptionCause : " + exception);
			logger.error("ExceptionContents : ");
			exception.printStackTrace();
		}

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.exception.email"), "Y")) {
			exceptionMessageSender.sendMessage(exception);
		}
	}
}