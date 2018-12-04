package zebra.config;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import zebra.util.CommonUtil;

public class HibernateQueryManager {
	private Logger logger = LogManager.getLogger(HibernateQueryManager.class);

	@SuppressWarnings("rawtypes")
	public void loadQueryFiles(ServletContext servletContext, ArrayList directoryPath) {
		String classPath = "";
		File rootDir;

		for (int i=0; i<directoryPath.size(); i++) {
			classPath = CommonUtil.replace((String)directoryPath.get(i), "classpath:", "/WEB-INF/classes/");
			rootDir = new File(servletContext.getRealPath(classPath));
			loadFiles(rootDir);
		}
	}

	public static String getNamedNativeQuery(String queryName) throws Exception {
		return (String)MemoryBean.get(queryName);
	}

	private void loadFiles(File directory) {
		File[] files = directory.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				loadFiles(file);
			} else {
				try {
					if (file.getName().endsWith(".hbm.xml")) {
						String fileName = file.getAbsolutePath().replaceAll("\\\\", "/").replaceAll(".hbm.xml$", "");
						DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
						docBuilderFactory.setValidating(false);
						docBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
						DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
						Document document = docBuilder.parse(file);
						NodeList nodeList = document.getElementsByTagName("sql-query");

						for (int i=0; i<nodeList.getLength(); i++) {
							Node node = nodeList.item(i);
							NamedNodeMap attrs = node.getAttributes();

							MemoryBean.set(attrs.getNamedItem("name").getNodeValue(), node.getTextContent());
						}

						fileName = (fileName.substring(fileName.indexOf("/WEB-INF/classes/"), fileName.length())).replaceAll("/WEB-INF/classes/", "");
						logger.debug("Registered to HibernateQueryManager : " + fileName);
					}
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}
	}
}