package zebra.aop;

import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class LoggingAdvice {
	private Logger logger = LogManager.getLogger(this.getClass());

	public Object executeLogging(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch();
		String targetClassName = pjp.getTarget().getClass().getName();
		String targetMethodName = pjp.getSignature().getName();
		Object[] args = pjp.getArgs();

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.advice.logging"), "Y")) {
			sw.start();
			logger.debug("LoggingAdvice : " + targetClassName + "." + targetMethodName + "() is starting.");
		}

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.advice.logging"), "Y")) {
			for (int i=0; i<args.length; i++) {
				logger.debug("LoggingAdvice : Arguments[" + i + "] = " + args[i]);
			}
		}

		Object returnObject = pjp.proceed();

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.advice.logging"), "Y")) {
			sw.stop();
			logger.debug("LoggingAdvice : " + targetClassName + "." + targetMethodName + "() has processed for " + sw.getTotalTimeMillis() + " ms.");
		}

		return returnObject;
	}
}