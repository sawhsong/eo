package zebra.log4jdbc;

public class Utilities {
	public static String rightJustify(int fieldSize, String field) {
		if (field == null) {
			field = "";
		}
		StringBuffer output = new StringBuffer();
		for (int i = 0, j = fieldSize - field.length(); i < j; i++) {
			output.append(' ');
		}
		output.append(field);
		return output.toString();
	}

	public static String rtrim(String s) {
		if (s == null) {
			return null;
		}
		int i = s.length() - 1;
		while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
			i--;
		}
		return s.substring(0, i + 1);
	}
}