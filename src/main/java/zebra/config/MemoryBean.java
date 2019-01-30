package zebra.config;

import java.util.HashMap;
import java.util.Map;

public class MemoryBean {
	@SuppressWarnings("rawtypes")
	private Map map;
	private static MemoryBean instance;

	static {
		instance = new MemoryBean();
	}

	@SuppressWarnings("rawtypes")
	private MemoryBean() {
		map = new HashMap();
	}

	private static MemoryBean getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public static void set(String key, Object object) {
		MemoryBean.getInstance().map.put(key, object);
	}

	public static Object get(String key) {
		return MemoryBean.getInstance().map.get(key);
	}

	public static void remove(String key) {
		MemoryBean.getInstance().map.remove(key);
	}

	@SuppressWarnings("rawtypes")
	public static Map getObject() {
		return MemoryBean.getInstance().map;
	}
}