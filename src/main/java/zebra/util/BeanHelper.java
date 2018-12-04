package zebra.util;

import zebra.data.DataSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class BeanHelper {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(BeanHelper.class);

	public static Object getBean(String beanName) {
		ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
		return appContext.getBean(beanName);
	}

	public static boolean containsBean(String beanName) {
		ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
		return appContext.containsBean(beanName);
	}

	public static DataSet getBeanNameDataSet() throws Exception {
		ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
		DataSet ds = new DataSet(new String[] {"beanName", "beanString"});

		for (String beanName : appContext.getBeanDefinitionNames()) {
			ds.addRow();
			try {
				ds.setValue(ds.getRowCnt()-1, "beanName", beanName);
				ds.setValue(ds.getRowCnt()-1, "beanString", appContext.getBean(beanName).toString());
			} catch (Exception e) {
				ds.setValue(ds.getRowCnt()-1, "beanName", beanName);
				ds.setValue(ds.getRowCnt()-1, "beanString", "");
			}
		}
		return ds;
	}

	public static DataSet getBeanNameDataSet(String type) throws Exception {
		ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
		DataSet ds = new DataSet(new String[] {"beanName", "beanString"});

		for (String beanName : appContext.getBeanDefinitionNames()) {
			try {
				String str = appContext.getBean(beanName).toString();

				if (CommonUtil.equalsIgnoreCase(type, "action") && (CommonUtil.containsIgnoreCase(str, ".app.") || CommonUtil.containsIgnoreCase(str, ".common.")) && CommonUtil.containsIgnoreCase(str, "Action")) {
					ds.addRow();
					ds.setValue(ds.getRowCnt()-1, "beanName", beanName);
					ds.setValue(ds.getRowCnt()-1, "beanString", str);
				} else if (CommonUtil.equalsIgnoreCase(type, "biz") && (CommonUtil.containsIgnoreCase(str, ".app.") || CommonUtil.containsIgnoreCase(str, ".common.")) && CommonUtil.containsIgnoreCase(str, "Biz")) {
					ds.addRow();
					ds.setValue(ds.getRowCnt()-1, "beanName", beanName);
					ds.setValue(ds.getRowCnt()-1, "beanString", str);
				} else if (CommonUtil.equalsIgnoreCase(type, "dao") && (CommonUtil.containsIgnoreCase(str, ".app.") || CommonUtil.containsIgnoreCase(str, ".common.")) && CommonUtil.containsIgnoreCase(str, "Dao")) {
					ds.addRow();
					ds.setValue(ds.getRowCnt()-1, "beanName", beanName);
					ds.setValue(ds.getRowCnt()-1, "beanString", str);
				} else if (CommonUtil.equalsIgnoreCase(type, "mapper") && CommonUtil.containsIgnoreCase(str, ".ibatis.") && CommonUtil.containsIgnoreCase(str, "Mapper")) {
					ds.addRow();
					ds.setValue(ds.getRowCnt()-1, "beanName", beanName);
					ds.setValue(ds.getRowCnt()-1, "beanString", str);
				} else if (CommonUtil.equalsIgnoreCase(type, "bizmodule") && CommonUtil.containsIgnoreCase(str, ".bizmodule.") && (CommonUtil.containsIgnoreCase(beanName, "MBiz") || CommonUtil.containsIgnoreCase(beanName, "BizM"))) {
					ds.addRow();
					ds.setValue(ds.getRowCnt()-1, "beanName", beanName);
					ds.setValue(ds.getRowCnt()-1, "beanString", str);
				}
			} catch (Exception e) {
			}
		}
		return ds;
	}

	public static String toStringBeanNames() {
		ApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();
		String str = "";

		for (String beanName : appContext.getBeanDefinitionNames()) {
			try {
				str += beanName+" : "+appContext.getBean(beanName).toString()+"\n";
			} catch (Exception e) {
				str += beanName+"\n";
			}
		}
		return str;
	}
}