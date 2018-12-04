package zebra.example.common.module.menu;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import zebra.config.MemoryBean;
import zebra.util.ConfigUtil;

public class ZebraMenuManager {
	private static Logger logger = LogManager.getLogger(ZebraMenuManager.class);

	public static void loadMenu() throws Exception {
		String menuJson = IOUtils.toString(new BufferedInputStream(new FileInputStream((String)MemoryBean.get("applicationRealPath") + ConfigUtil.getProperty("path.file.fwkMenu"))), "utf-8");
		MemoryBean.set("zebraMenuAll", (JSONObject)JSONSerializer.toJSON(menuJson));
		logger.debug("[MemoryBean] - Framework Menu has been loaded.");
	}

	public static void reloadMenu() throws Exception {
		loadMenu();
	}

	public static JSONObject getGlobalMenu() {
		JSONObject json = (JSONObject)MemoryBean.get("zebraMenuAll");
		return (JSONObject)json.get("globalMenu");
	}

	public static JSONObject getMainMenu() {
		JSONObject json = (JSONObject)MemoryBean.get("zebraMenuAll");
		return (JSONObject)json.get("mainMenu");
	}

	public static JSONObject getLeftMenu(String mainMenuId) {
		JSONObject json = (JSONObject)getMainMenu();
		return (JSONObject)json.get(mainMenuId);
	}
}