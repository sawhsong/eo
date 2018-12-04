package zebra.data;

import java.util.HashMap;
import java.util.Iterator;

import zebra.util.CommonUtil;

public class ParamMap {
	@SuppressWarnings("rawtypes")
	private HashMap object;

	public Object getObject(String key) {
		if (object == null || CommonUtil.isBlank(key)) {return null;}
		if (!object.containsKey(key)) {return null;}

		return object.get(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setObject(String key, Object obj) {
		if (CommonUtil.isBlank(key) || obj == null) {return;}
		if (object == null) {object = new HashMap();}

		object.put(key, obj);
	}

	public void resetObject() {
		object.clear();
	}

	public void removeObject(String key) {
		if (object == null || CommonUtil.isBlank(key)) {return;}
		if (!object.containsKey(key)) {return;}

		object.remove(key);
	}

	@SuppressWarnings("unchecked")
	public void renameObject(String from, String to) {
		if (object == null || CommonUtil.isBlank(CommonUtil.nvl(from)) || CommonUtil.isBlank(CommonUtil.nvl(to))) {return;}
		if (!object.containsKey(from)) {return;}

		object.put(to, object.get(from));
		object.remove(from);
	}

	/**
	 * toString
	 */
	@SuppressWarnings("rawtypes")
	public String toString() {
		String str = "";

		if (!(object == null || object.isEmpty())) {
			for (Iterator keys = object.keySet().iterator(); keys.hasNext();) {
				String key = (String)keys.next();
				str += "ParamMap object - [" + key + "] : " + object.get(key) + "\n";
			}
		}

		return  str;
	}
}