package zebra.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * number format : "#,###.##"
 * date format : "yyyy-MM-dd HH:mm:ss"
 */
public class CommonUtil extends StringUtils {
	private static Logger logger = LogManager.getLogger(CommonUtil.class);

	public static boolean isEmpty(Object[] array) {
		if (array == null || array.length == 0) {return true;}
		else {return false;}
	}

	public static boolean isNotEmpty(Object[] array) {
		boolean result = false;
		for (int i=0; i<array.length && !result; i++) {
			if (array[i] != null) {
				result = true;
			}
		}
		return result;
	}

	public static String nvl(String value) {
		return nvl(value, "");
	}

	public static String nvl(String value, String replaceValue) {
		return StringUtils.defaultIfBlank(value, replaceValue);
	}

	public static String[] splitWithTrim(String value, String separator) {
		String str[] = StringUtils.splitByWholeSeparator(value, separator);
		if (isEmpty(str)) {
			return null;
		} else {
			for (int i=0; i<str.length; i++) {
				str[i] = StringUtils.trimToEmpty(str[i]);
			}
			return str;
		}
	}

	public static String uid() {
		return toString(System.nanoTime(), "####");
	}

	public static boolean isUpperCase(String value) {
		if (StringUtils.isBlank(value)) {return false;}

		for (char ch : value.toCharArray()) {
			if (!Character.isUpperCase(ch)) {return false;}
		}
		return true;
	}

	public static boolean isUpperCaseWithNumeric(String value) {
		if (StringUtils.isBlank(value)) {return false;}

		for (char ch : value.toCharArray()) {
			if (Character.isDigit(ch)) {continue;}

			if (!Character.isUpperCase(ch)) {return false;}
		}
		return true;
	}

	public static String toCamelCase(String value) {
		return toCamelCaseStartUpperCase(value);
	}

	public static String toCamelCaseStartLowerCase(String value) {
		StringBuffer sb = new StringBuffer();
		String[] str = value.split("_");
		boolean firstTime = true;
		for (String temp : str) {
			if (firstTime) {
				sb.append(temp.toLowerCase());
				firstTime = false;
			} else {
				sb.append(Character.toUpperCase(temp.charAt(0)));
				sb.append(temp.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	public static String toCamelCaseStartUpperCase(String value) {
		StringBuffer sb = new StringBuffer();
		String[] str = value.split("_");
		for (String temp : str) {
			sb.append(Character.toUpperCase(temp.charAt(0)));
			sb.append(temp.substring(1).toLowerCase());
		}
		return sb.toString();
	}

	public static String toWordStartUpperCase(String value) {
		String strRtn = "";
		String[] str = value.split("_");
		for (String temp : str) {
			if (isBlank(strRtn)) {
				strRtn += Character.toUpperCase(temp.charAt(0));
				strRtn += temp.substring(1).toLowerCase();
			} else {
				strRtn += " ";
				strRtn += Character.toUpperCase(temp.charAt(0));
				strRtn += temp.substring(1).toLowerCase();
			}
		}
		return strRtn;
	}

	public static int toInt(String value) {
		return NumberUtils.toInt(value.trim().replaceAll(",", ""));
	}

	public static long toLong(String value) {
		return NumberUtils.toLong(value.trim().replaceAll(",", ""));
	}

	public static double toDouble(String value) {
		return NumberUtils.toDouble(value.trim().replaceAll(",", ""));
	}

	public static double ceil(double doubleValue, int scale) throws Exception {
		if (isEmpty(toString(doubleValue)) || doubleValue == 0) {return 0;}
		else {return (new BigDecimal(doubleValue)).setScale(scale, RoundingMode.CEILING).doubleValue();}
	}

	public static double round(double doubleValue, int scale) throws Exception {
		if (isEmpty(toString(doubleValue)) || doubleValue == 0) {return 0;}
		else {return (new BigDecimal(doubleValue)).setScale(scale, RoundingMode.HALF_UP).doubleValue();}
	}

	public static double down(double doubleValue, int scale) throws Exception {
		if (isEmpty(toString(doubleValue)) || doubleValue == 0) {return 0;}
		else {return (new BigDecimal(doubleValue)).setScale(scale, RoundingMode.DOWN).doubleValue();}
	}

	public static double abs(double doubleValue) throws Exception {
		if (isEmpty(toString(doubleValue))|| doubleValue == 0) {return 0;}
		else {return (doubleValue < 0) ? doubleValue*(-1) : doubleValue*1;}
	}

	public static String toString(double val) {
		return Double.valueOf(val).toString();
	}

	public static String toString(double val, String format) {
		DecimalFormat form = new DecimalFormat(format);
		return form.format(Double.valueOf(val));
	}

	public static String toString(long val) {
		return Long.valueOf(val).toString();
	}

	public static String toString(long val, String format) {
		DecimalFormat form = new DecimalFormat(format);
		return form.format(Long.valueOf(val));
	}

	public static String toString(int val) {
		return Integer.valueOf(val).toString();
	}

	public static String toString(int val, String format) {
		DecimalFormat form = new DecimalFormat(format);
		return form.format(Integer.valueOf(val));
	}

	public static String toString(String arr[], String format) {
		String sTemp = "";

		if (isEmpty(arr)) {return sTemp;}
		else {
			for (int i=0; i<arr.length; i++) {
				sTemp += (i == 0) ? arr[i] : format+arr[i];
			}
		}
		return sTemp;
	}

	public static String toString(Date date) throws Exception {
		if (date == null) {return "";}
		return new SimpleDateFormat(ConfigUtil.getProperty("format.default.dateTime")).format(date);
	}

	public static String toString(Date date, String formatTo) throws Exception {
		if (date == null) {return "";}
		return new SimpleDateFormat(formatTo).format(date);
	}

	public static String toViewDateString(Date date) throws Exception {
		if (date == null) {return "";}
		return new SimpleDateFormat(ConfigUtil.getProperty("format.date.java")).format(date);
	}

	public static String toViewDateString(String dateString) throws Exception {
		if (isBlank(dateString)) {return "";}
		return new SimpleDateFormat(ConfigUtil.getProperty("format.date.java")).format(toDate(dateString));
	}

	public static String toViewDateTimeString(Date date) throws Exception {
		if (date == null) {return "";}
		return new SimpleDateFormat(ConfigUtil.getProperty("format.dateTime.java")).format(date);
	}

	public static Date toDate(String value) throws Exception {
		if (isBlank(value)) {return null;}
		return new SimpleDateFormat(ConfigUtil.getProperty("format.default.dateTime")).parse(value);
	}

	public static Date toDate(String value, String format) throws Exception {
		if (isBlank(value)) {return null;}
		return new SimpleDateFormat(format).parse(value);
	}

	public static String toDateTimeString(long value) throws Exception {
		return toDateTimeString(value, ConfigUtil.getProperty("format.dateTime.java"));
	}

	public static String toDateTimeString(long value, String format) throws Exception {
		return new SimpleDateFormat(format).format(value);
	}

	public static boolean toBoolean(String value) {
		return (equalsIgnoreCase(value, "true") || equalsIgnoreCase(value, "yes") || equalsIgnoreCase(value, "y"));
	}

	public static boolean toBoolean(String value, boolean defaultIfBlank) {
		if (isBlank(value)) {return defaultIfBlank;}
		return toBoolean(value);
	}

	public static boolean isIn(String value, String... values) {
		if (isBlank(value)) {return false;}
		for (String s : values) {
			if (equals(value, s)) {return true;}
		}
		return false;
	}

	public static String removeString(String value, String... values) {
		if (isBlank(value)) {return value;}
		String val = value;
		for (String s : values) {
			val = remove(val, s);
		}
		return val;
	}

	public static String getNumberMask(String value) {
		return getNumberMask(toDouble(value), ConfigUtil.getProperty("format.default.integer"));
	}

	public static String getNumberMask(String value, String format) {
		return getNumberMask(toDouble(value), format);
	}

	public static String getNumberMask(double value, String format) {
		return new DecimalFormat(format).format(Double.valueOf(value));
	}

	public static String getNumberMask(double value) {
		return new DecimalFormat(ConfigUtil.getProperty("format.default.decimal")).format(Double.valueOf(value));
	}

	public static String getDateTimeMask(String value, String format) throws Exception {
		if (isEmpty(value)) {return "";}
		else {
			Date date = new SimpleDateFormat(ConfigUtil.getProperty("format.default.dateTime")).parse(value);
			return new SimpleDateFormat(format).format(date);
		}
	}

	public static String getDateMask(String value, String format) throws Exception {
		if (isEmpty(value)) {return "";}
		else {
			Date date = new SimpleDateFormat(ConfigUtil.getProperty("format.default.date")).parse(value);
			return new SimpleDateFormat(format).format(date);
		}
	}

	public static boolean isValidDateString(String value) {
		boolean rtn = false;
		String dateString = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat();
		try {
			if (isBlank(value)) {
				rtn = false;
			} else  if (value.length() == 8) {
				format = new SimpleDateFormat("yyyyMMdd");
			} else if (value.length() == 10) {
				format = new SimpleDateFormat("yyyyMMddHH");
			} else if (value.length() == 12) {
				format = new SimpleDateFormat("yyyyMMddHHmm");
			} else if (value.length() == 14) {
				format = new SimpleDateFormat("yyyyMMddHHmmss");
			} else if (value.length() == 17) {
				format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			} else {
				rtn = false;
			}

			calendar.setTime(format.parse(value));
			dateString = toString(calendar.getTimeInMillis());

			if (isBlank(dateString)) {
				rtn = false;
			} else {
				rtn = true;
			}
		} catch(Exception e) {
			rtn = false;
		}
		return rtn;
	}

	public static boolean isValidDateString(String value, String format) {
		boolean rtn = false;
		try {
			if (isBlank(value)) {rtn = false;}
			else {
				Date date = new SimpleDateFormat(format).parse(value);
				new SimpleDateFormat(format).format(date);
				rtn = true;
			}
		} catch(Exception e) {
			rtn = false;
		}
		return rtn;
	}
	/**
	 * getCalcDate("D", "20121201", 1) => "20121202"
	 */
	public static String getCalcDate(String flag, String val, int cal) throws Exception {
		Calendar calendar = getCalendarFromString(val);

		if (flag.equals("Y")) {calendar.add(Calendar.YEAR, cal);}
		else if (flag.equals("M")) {calendar.add(Calendar.MONTH, cal);}
		else if (flag.equals("D")) {calendar.add(Calendar.DATE, cal);}

		return getStringFromCalendar(calendar);
	}

	public static Calendar getCalendarFromString(String value) throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		cal.setTime(format.parse(value));
		return cal;
	}

	public static String getStringFromCalendar(Calendar cal) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(cal.getTime());
	}
	/**
	 * getDateGap("20120101", "20121203") => 2
	 */
	public static double getDateGap(String startDate, String endDate) throws Exception {
		double dStartDate = toDouble(getLongDate(startDate));
		double dEndDate = toDouble(getLongDate(endDate));

		return (dEndDate - dStartDate) / (24*(60*(60*1000)));
	}
	/**
	 * week name
	 */
	public static String getDayOfWeek(String inputDate)throws Exception {
		if (inputDate == null || inputDate.length() < 8) {return "";}

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat ddf = new SimpleDateFormat("EEE");

		return ddf.format(df.parse(inputDate));
	}
	/**
	 * change to long date
	 */
	public static String getLongDate(String sDate) throws Exception {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat();

		if (sDate.length() == 8) {
			format = new SimpleDateFormat("yyyyMMdd");
		} else if (sDate.length() == 10) {
			format = new SimpleDateFormat("yyyyMMddHH");
		} else if (sDate.length() == 12) {
			format = new SimpleDateFormat("yyyyMMddHHmm");
		} else if (sDate.length() == 14) {
			format = new SimpleDateFormat("yyyyMMddHHmmss");
		} else if (sDate.length() == 17) {
			format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		} else {
			return "";
		}
		calendar.setTime(format.parse(sDate));

		return toString(calendar.getTimeInMillis());
	}

	public static String getDateFromLong(long date, String format) throws Exception {
		if (isEmpty(format)) {format = ConfigUtil.getProperty("format.default.dateTime");}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new Date(date));
	}
	/**
	 * getSysdate("yyyy-MM-dd HH:mm:ss")
	 */
	public static String getSysdate() {
		return getSysdate("");
	}

	public static String getSysdate(String format) {
		if (isEmpty(format)) {
			format = ConfigUtil.getProperty("format.default.dateTime");
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new Date());
	}
	/**
	 * getFormatString("1234567890", "???-??-?????") => "123-45-67890"
	 */
	public static String getFormatString(String source, String format) {
		String rtn = "";
		int iStart = 0, iLen = 0, iTCount, iFCount;

		iTCount = source.length();
		iFCount = format.length();

		if (isEmpty(source)) {return rtn;}
		if (isEmpty(format)) {return source;}
		if ("&nbsp;".equals(source)) {return "&nbsp;";}

		for (int i=0; i<iTCount; i++) {
			if (!(source.substring(i, i+1).equals("-"))) {rtn = rtn + source.substring(i, i+1);}
		}

		source = rtn;
		iTCount = source.length();

		for (int i=0; i<iFCount; i++) {
			if (format.substring(i, i+1).equals("?")) {iLen++;}
		}

		if (iTCount < iLen) {
			for (int i=0; i<(iLen - iTCount); i++) {source = "0" + source;}
			iTCount = iLen;
		}

		rtn = "";
		for (int i=0; i<iTCount; i++) {
			for (int j=iStart; j<iFCount; j++) {
				if (format.substring(j, j+1).equals("?")) {
					rtn = rtn + source.substring(i, i+1);
					iStart = iStart + 1;
					break;
				} else {
					rtn = rtn + format.substring(j, j+1);
					iStart = iStart + 1;
				}
			}
		}
		return rtn + format.substring(iStart);
	}

	public static String getRandomAlphanumeric(int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}

	public static String getIconNameByFileType(String fileType) {
		if (containsIgnoreCase(fileType, "pdf")) {
			return ConfigUtil.getProperty("path.image.icon")+"/"+"icnPdf.png";
		} else if (containsIgnoreCase(fileType, "ms-excel") || containsIgnoreCase(fileType, "officedocument.spreadsheetml.sheet")) {
			return ConfigUtil.getProperty("path.image.icon")+"/"+"icnExcel.png";
		} else if (containsIgnoreCase(fileType, "msword") || containsIgnoreCase(fileType, "officedocument.wordprocessingml.document")) {
			return ConfigUtil.getProperty("path.image.icon")+"/"+"icnWord.png";
		} else if (containsIgnoreCase(fileType, "ms-powerpoint") || containsIgnoreCase(fileType, "officedocument.presentationml.presentation")) {
			return ConfigUtil.getProperty("path.image.icon")+"/"+"icnPowerpoint.png";
		} else if (containsIgnoreCase(fileType, "image/")) {
			return ConfigUtil.getProperty("path.image.icon")+"/"+"icnImage.png";
		} else if (containsIgnoreCase(fileType, "text/html")) {
			return ConfigUtil.getProperty("path.image.icon")+"/"+"icnHtml.png";
		} else {
			return ConfigUtil.getProperty("path.image.icon")+"/"+"icnDocument.png";
		}
	}

	public static String stackTraceToString(Throwable ex) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(bos);
		String stackTrace = "";

		try {
			ex.printStackTrace(p);
			p.close();
			stackTrace = bos.toString();
			bos.close();
		} catch (IOException e) {
			logger.error(e);
		}

		return stackTrace;
	}
}