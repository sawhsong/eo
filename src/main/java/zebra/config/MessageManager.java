package zebra.config;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zebra.data.DataSet;
import zebra.util.ConfigUtil;

public class MessageManager {
	private static Logger logger = LogManager.getLogger(MessageManager.class);

	public static void loadMessageBundles() throws Exception {
		String realPath = (String)MemoryBean.get("applicationRealPath")+"WEB-INF/classes/";
		File rootDir;

		rootDir = new File(realPath+ConfigUtil.getProperty("path.dir.messageFwk"));
		iterateScanDirectory(rootDir);
		rootDir = new File(realPath+ConfigUtil.getProperty("path.dir.messageProject"));
		iterateScanDirectory(rootDir);
	}

	public static void reload() throws Exception {
		loadMessageBundles();
	}

	public static DataSet getMessageDataSet(String langCode) throws Exception {
		DataSet ds = (DataSet)MemoryBean.get("messageBundle_"+langCode);
		if (ds == null) {ds = (DataSet)MemoryBean.get("messageBundle_default");}
		return ds;
	}

	@SuppressWarnings("rawtypes")
	private static void iterateScanDirectory(File directory) throws Exception {
		File[] files = directory.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					iterateScanDirectory(file);
				} else {
					if (file.getName().endsWith(".properties")) {
						String fileName = file.getAbsolutePath().replaceAll("\\\\", "/").replaceAll(".properties$", "");
						String langCode = "";
						Properties prop = new Properties();
						DataSet messageBundle;

						langCode = (fileName.lastIndexOf("_") == -1) ? "_default" : fileName.substring(fileName.lastIndexOf("_"));

						prop.load(new FileReader(file));

						messageBundle = (DataSet)MemoryBean.get("messageBundle"+langCode);
						if (messageBundle == null) {
							messageBundle = new DataSet();
						}

						for (Iterator iter = prop.entrySet().iterator(); iter.hasNext();) {
							Entry entry = (Entry)iter.next();
							messageBundle.addColumn((String)entry.getKey(), (String)entry.getValue());
						}

						MemoryBean.set("messageBundle"+langCode, messageBundle);

						fileName = (fileName.substring(fileName.indexOf("/WEB-INF/classes/"), fileName.length())).replaceAll("/WEB-INF/classes/", "");
						logger.debug("Message bundle loaded to MemoryBean : " + fileName);
					}
				}
			}
		}
	}
}