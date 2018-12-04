package zebra.mvc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StopWatch;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class TimerInterceptor implements Interceptor {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String className = invocation.getAction().getClass().getName();
		StopWatch sw = new StopWatch();

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.interceptor.timer"), "Y")) {
			sw.start();
			logger.debug("TimerInterceptor : " + className + " is starting.");
		}

		String result = invocation.invoke();

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.interceptor.timer"), "Y")) {
			sw.stop();
			logger.debug("TimerInterceptor : " + className + " has processed for " + sw.getTotalTimeMillis() + " ms.");
		}

		return result;
	}
}