package zebra.util;

import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import zebra.data.DataSet;

public class JsonUtil {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(JsonUtil.class);

	@SuppressWarnings({ "unchecked" })
	public static DataSet getDataSetFromJsonArray(JSONArray jsonArray) throws Exception {
		DataSet ds = new DataSet();

		if (jsonArray != null) {
			for (Iterator<String> iter = jsonArray.iterator(); iter.hasNext();) {
				JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(iter.next());
				for (Object keys : jsonObject.keySet()) {
					String key = (String)keys;
					ds.addName(key);
				}
				break;
			}

			for (Iterator<String> iter = jsonArray.iterator(); iter.hasNext();) {
				ds.addRow();
				JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(iter.next());
				for (Object keys : jsonObject.keySet()) {
					String key = (String)keys;
					Object value = jsonObject.get(key);
					ds.setValue(ds.getRowCnt()-1, key, value);
				}
			}
		}
		return ds;
	}

	public static DataSet getDataSetFromJsonArrayString(String jsonArrayString) throws Exception {
		DataSet ds = new DataSet();
		JSONArray jsonArray = (JSONArray)JSONSerializer.toJSON(jsonArrayString);
		ds = getDataSetFromJsonArray(jsonArray);
		return ds;
	}
}