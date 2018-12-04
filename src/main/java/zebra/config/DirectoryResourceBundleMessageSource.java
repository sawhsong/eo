package zebra.config;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import zebra.util.CommonUtil;

public class DirectoryResourceBundleMessageSource extends ResourceBundleMessageSource {
	private Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private ServletContext servletContext;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setDirectoryNames(ArrayList directoryPath) {
		String classPath = "";
		File rootDir;
		ArrayList baseNames = new ArrayList();

		for (int i=0; i<directoryPath.size(); i++) {
			classPath = CommonUtil.replace((String)directoryPath.get(i), "classpath:", "/WEB-INF/classes/");
			rootDir = new File(servletContext.getRealPath(classPath));
			addMessageBaseNames(baseNames, rootDir);
		}
		super.setBasenames((String[])baseNames.toArray(new String[baseNames.size()]));

		/*!
		 * No need to load query files
		 */
//		HibernateQueryManager hibernateQueryManager = new HibernateQueryManager();
//		hibernateQueryManager.loadQueryFiles(servletContext, directoryPath);
	}

	private void addMessageBaseNames(ArrayList<String> baseNames, File directory) {
		File[] files = directory.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				addMessageBaseNames(baseNames, file);
			} else {
				if (file.getName().endsWith(".properties")) {
					String fileName = file.getAbsolutePath().replaceAll("\\\\", "/").replaceAll(".properties$", "");
					fileName = (fileName.substring(fileName.indexOf("/WEB-INF/classes/"), fileName.length())).replaceAll("/WEB-INF/classes/", "");
					baseNames.add(fileName);
					logger.debug("Registered to Spring messageSource : " + fileName);
				}
			}
		}
	}
}