package zebra.wssupport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import zebra.data.DataSet;
import zebra.util.ConfigUtil;

public class SoapServiceSupport {
	private static final String PROVIDER_URL = ConfigUtil.getProperty("webService.provider.url");

	/*!
	 * Server support
	 */
	public static DataHandler getFileDataHandler(String fileFullPath) throws Exception {
		DataHandler dataHandler = null;
		DataSource dataSource;

		dataSource = new FileDataSource(new File(fileFullPath));
		dataHandler = new DataHandler(dataSource);

		return dataHandler;
	}

	public static DataHandler getFileDataHandler(File file) throws Exception {
		DataHandler dataHandler = null;
		DataSource dataSource;

		dataSource = new FileDataSource(file);
		dataHandler = new DataHandler(dataSource);

		return dataHandler;
	}

	/*!
	 * Client support
	 */
	@SuppressWarnings("rawtypes")
	public static JaxWsProxyFactoryBean getProxyFactory(String serviceUrl, Class serviceClass) throws Exception {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		Map<String, Object> prop = new HashMap<String, Object>();

		prop.put("mtom-enabled", Boolean.TRUE);
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		factory.getInFaultInterceptors().add(new LoggingInInterceptor());
//		factory.getOutFaultInterceptors().add(new LoggingOutInterceptor());
		factory.setProperties(prop);
		factory.setAddress(PROVIDER_URL+"/"+serviceUrl);
		factory.setServiceClass(serviceClass);

		return factory;
	}

	public static List<DataHandler> getFileDataHandler(DataSet fileDataSet) throws Exception {
		List<DataHandler> dataHandlerList = new ArrayList<DataHandler>();
		DataHandler dataHandler;
		DataSource dataSource;

		for (int i=0; i<fileDataSet.getRowCnt(); i++) {
			dataSource = new FileDataSource(new File(fileDataSet.getValue(i, "TEMP_PATH")+"/"+fileDataSet.getValue(i, "NEW_NAME")));
			dataHandler = new DataHandler(dataSource);
			dataHandlerList.add(dataHandler);
		}

		return dataHandlerList;
	}
}