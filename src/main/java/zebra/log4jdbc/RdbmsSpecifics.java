package zebra.log4jdbc;

import java.util.Date;
import java.text.SimpleDateFormat;

public class RdbmsSpecifics {

	RdbmsSpecifics() {
	}

	protected static final String dateFormat = "MM/dd/yyyy HH:mm:ss.SSS";

	String formatParameterObject(Object object) {
		if (object == null) {
			return "NULL";
		} else {
			if (object instanceof String) {
				return "'" + escapeString((String)object) + "'";
			} else if (object instanceof Date) {
				return "'" + new SimpleDateFormat(dateFormat).format(object) + "'";
			} else if (object instanceof Boolean) {
				return DriverSpy.DumpBooleanAsTrueFalse ? ((Boolean)object).booleanValue() ? "true" : "false" : ((Boolean)object).booleanValue() ? "1" : "0";
			} else {
				return object.toString();
			}
		}
	}

	String escapeString(String in) {
		StringBuilder out = new StringBuilder();
		for (int i = 0, j = in.length(); i < j; i++) {
			char c = in.charAt(i);
			if (c == '\'') {
				out.append(c);
			}
			out.append(c);
		}
		return out.toString();
	}

}