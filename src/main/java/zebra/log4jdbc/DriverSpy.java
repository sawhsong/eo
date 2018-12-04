package zebra.log4jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DriverSpy implements Driver {
	private Driver lastUnderlyingDriverRequested;
	private static Map rdbmsSpecifics;
	static final SpyLogDelegator log = SpyLogFactory.getSpyLogDelegator();
	static String DebugStackPrefix;
	static boolean TraceFromApplication;
	static boolean SqlTimingWarnThresholdEnabled;
	static long SqlTimingWarnThresholdMsec;
	static boolean SqlTimingErrorThresholdEnabled;
	static long SqlTimingErrorThresholdMsec;
	static boolean DumpBooleanAsTrueFalse;
	static int DumpSqlMaxLineLength;
	static boolean StatementUsageWarn;
	static boolean DumpSqlSelect;
	static boolean DumpSqlInsert;
	static boolean DumpSqlUpdate;
	static boolean DumpSqlDelete;
	static boolean DumpSqlCreate;
	static boolean DumpSqlFilteringOn;
	static boolean DumpSqlAddSemicolon;
	static boolean DumpFullDebugStackTrace;
	static boolean AutoLoadPopularDrivers;
	static boolean TrimSql;
	static boolean TrimSqlLines;
	static boolean TrimExtraBlankLinesInSql;
	static boolean SuppressGetGeneratedKeysException;

	private static Long getLongOption(Properties props, String propName) {
		String propValue = props.getProperty(propName);
		Long longPropValue = null;
		if (propValue == null) {
			log.debug("x " + propName + " is not defined");
		} else {
			try {
				longPropValue = Long.valueOf(Long.parseLong(propValue));
				log.debug("  " + propName + " = " + longPropValue);
			} catch (NumberFormatException n) {
				log.debug("x " + propName + " \"" + propValue + "\" is not a valid number");
			}
		}
		return longPropValue;
	}

	private static Long getLongOption(Properties props, String propName, long defaultValue) {
		String propValue = props.getProperty(propName);
		Long longPropValue;
		if (propValue == null) {
			log.debug("x " + propName + " is not defined (using default of " + defaultValue + ")");
			longPropValue = Long.valueOf(defaultValue);
		} else {
			try {
				longPropValue = Long.valueOf(Long.parseLong(propValue));
				log.debug("  " + propName + " = " + longPropValue);
			} catch (NumberFormatException n) {
				log.debug("x " + propName + " \"" + propValue + "\" is not a valid number (using default of " + defaultValue + ")");
				longPropValue = Long.valueOf(defaultValue);
			}
		}
		return longPropValue;
	}

	private static String getStringOption(Properties props, String propName) {
		String propValue = props.getProperty(propName);
		if (propValue == null || propValue.length() == 0) {
			log.debug("x " + propName + " is not defined");
			propValue = null; // force to null, even if empty String
		} else {
			log.debug("  " + propName + " = " + propValue);
		}
		return propValue;
	}

	private static boolean getBooleanOption(Properties props, String propName, boolean defaultValue) {
		String propValue = props.getProperty(propName);
		boolean val;
		if (propValue == null) {
			log.debug("x " + propName + " is not defined (using default value " + defaultValue + ")");
			return defaultValue;
		} else {
			propValue = propValue.trim().toLowerCase();
			if (propValue.length() == 0) {
				val = defaultValue;
			} else {
				val = "true".equals(propValue) || "yes".equals(propValue) || "on".equals(propValue);
			}
		}
		log.debug("  " + propName + " = " + val);
		return val;
	}

	static {
		log.debug("... log4jdbc initializing ...");

		InputStream propStream = DriverSpy.class.getResourceAsStream("/log4jdbc.properties");

		Properties props = new Properties(System.getProperties());
		if (propStream != null) {
			try {
				props.load(propStream);
			} catch (IOException e) {
				log.debug("ERROR!  io exception loading " + "log4jdbc.properties from classpath: " + e.getMessage());
			} finally {
				try {
					propStream.close();
				} catch (IOException e) {
					log.debug("ERROR!  io exception closing property file stream: " + e.getMessage());
				}
			}
			log.debug("  log4jdbc.properties loaded from classpath");
		} else {
			log.debug("  log4jdbc.properties not found on classpath");
		}

		// look for additional driver specified in properties
		DebugStackPrefix = getStringOption(props, "log4jdbc.debug.stack.prefix");
		TraceFromApplication = DebugStackPrefix != null;

		Long thresh = getLongOption(props, "log4jdbc.sqltiming.warn.threshold");
		SqlTimingWarnThresholdEnabled = (thresh != null);
		if (SqlTimingWarnThresholdEnabled) {
			SqlTimingWarnThresholdMsec = thresh.longValue();
		}

		thresh = getLongOption(props, "log4jdbc.sqltiming.error.threshold");
		SqlTimingErrorThresholdEnabled = (thresh != null);
		if (SqlTimingErrorThresholdEnabled) {
			SqlTimingErrorThresholdMsec = thresh.longValue();
		}

		DumpBooleanAsTrueFalse = getBooleanOption(props, "log4jdbc.dump.booleanastruefalse", false);

		DumpSqlMaxLineLength = getLongOption(props, "log4jdbc.dump.sql.maxlinelength", 90L).intValue();

		DumpFullDebugStackTrace = getBooleanOption(props, "log4jdbc.dump.fulldebugstacktrace", false);

		StatementUsageWarn = getBooleanOption(props, "log4jdbc.statement.warn", false);

		DumpSqlSelect = getBooleanOption(props, "log4jdbc.dump.sql.select", true);
		DumpSqlInsert = getBooleanOption(props, "log4jdbc.dump.sql.insert", true);
		DumpSqlUpdate = getBooleanOption(props, "log4jdbc.dump.sql.update", true);
		DumpSqlDelete = getBooleanOption(props, "log4jdbc.dump.sql.delete", true);
		DumpSqlCreate = getBooleanOption(props, "log4jdbc.dump.sql.create", true);

		DumpSqlFilteringOn = !(DumpSqlSelect && DumpSqlInsert && DumpSqlUpdate && DumpSqlDelete && DumpSqlCreate);

		DumpSqlAddSemicolon = getBooleanOption(props, "log4jdbc.dump.sql.addsemicolon", false);

		AutoLoadPopularDrivers = getBooleanOption(props, "log4jdbc.auto.load.popular.drivers", true);

		TrimSql = getBooleanOption(props, "log4jdbc.trim.sql", true);
		TrimSqlLines = getBooleanOption(props, "log4jdbc.trim.sql.lines", false);
		if (TrimSqlLines && TrimSql) {
			log.debug("NOTE, log4jdbc.trim.sql setting ignored because " + "log4jdbc.trim.sql.lines is enabled.");
		}

		TrimExtraBlankLinesInSql = getBooleanOption(props, "log4jdbc.trim.sql.extrablanklines", true);

		SuppressGetGeneratedKeysException = getBooleanOption(props, "log4jdbc.suppress.generated.keys.exception", false);

		// The Set of drivers that the log4jdbc driver will preload at instantiation
		// time. The driver can spy on any driver type, it's just a little bit
		// easier to configure log4jdbc if it's one of these types!

		Set subDrivers = new TreeSet();

		if (AutoLoadPopularDrivers) {
			subDrivers.add("oracle.jdbc.driver.OracleDriver");
			subDrivers.add("oracle.jdbc.OracleDriver");
			subDrivers.add("com.sybase.jdbc2.jdbc.SybDriver");
			subDrivers.add("net.sourceforge.jtds.jdbc.Driver");

			// MS driver for Sql Server 2000
			subDrivers.add("com.microsoft.jdbc.sqlserver.SQLServerDriver");

			// MS driver for Sql Server 2005
			subDrivers.add("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			subDrivers.add("weblogic.jdbc.sqlserver.SQLServerDriver");
			subDrivers.add("com.informix.jdbc.IfxDriver");
			subDrivers.add("org.apache.derby.jdbc.ClientDriver");
			subDrivers.add("org.apache.derby.jdbc.EmbeddedDriver");
			subDrivers.add("com.mysql.jdbc.Driver");
			subDrivers.add("org.postgresql.Driver");
			subDrivers.add("org.hsqldb.jdbcDriver");
			subDrivers.add("org.h2.Driver");
		}

		// look for additional driver specified in properties
		String moreDrivers = getStringOption(props, "log4jdbc.drivers");

		if (moreDrivers != null) {
			String[] moreDriversArr = moreDrivers.split(",");

			for (int i = 0; i < moreDriversArr.length; i++) {
				subDrivers.add(moreDriversArr[i]);
				log.debug("    will look for specific driver " + moreDriversArr[i]);
			}
		}

		try {
			DriverManager.registerDriver(new DriverSpy());
		} catch (SQLException s) {
			// this exception should never be thrown, JDBC just defines it
			// for completeness
			throw (RuntimeException)new RuntimeException("could not register log4jdbc driver!").initCause(s);
		}

		// instantiate all the supported drivers and remove
		// those not found
		String driverClass;
		for (Iterator i = subDrivers.iterator(); i.hasNext();) {
			driverClass = (String)i.next();
			try {
				Class.forName(driverClass);
				log.debug("  FOUND DRIVER " + driverClass);
			} catch (Throwable c) {
				i.remove();
			}
		}

		if (subDrivers.size() == 0) {
			log.debug("WARNING!  " + "log4jdbc couldn't find any underlying jdbc drivers.");
		}

		SqlServerRdbmsSpecifics sqlServer = new SqlServerRdbmsSpecifics();
		OracleRdbmsSpecifics oracle = new OracleRdbmsSpecifics();
		MySqlRdbmsSpecifics mySql = new MySqlRdbmsSpecifics();

		/** create lookup Map for specific rdbms formatters */
		rdbmsSpecifics = new HashMap();
		rdbmsSpecifics.put("oracle.jdbc.driver.OracleDriver", oracle);
		rdbmsSpecifics.put("oracle.jdbc.OracleDriver", oracle);
		rdbmsSpecifics.put("net.sourceforge.jtds.jdbc.Driver", sqlServer);
		rdbmsSpecifics.put("com.microsoft.jdbc.sqlserver.SQLServerDriver", sqlServer);
		rdbmsSpecifics.put("weblogic.jdbc.sqlserver.SQLServerDriver", sqlServer);
		rdbmsSpecifics.put("com.mysql.jdbc.Driver", mySql);

		log.debug("... log4jdbc initialized! ...");
	}

	static RdbmsSpecifics defaultRdbmsSpecifics = new RdbmsSpecifics();

	static RdbmsSpecifics getRdbmsSpecifics(Connection conn) {
		String driverName = "";
		try {
			DatabaseMetaData dbm = conn.getMetaData();
			driverName = dbm.getDriverName();
		} catch (SQLException s) {
			// silently fail
		}

		log.debug("driver name is " + driverName);

		RdbmsSpecifics r = (RdbmsSpecifics)rdbmsSpecifics.get(driverName);

		if (r == null) {
			return defaultRdbmsSpecifics;
		} else {
			return r;
		}
	}

	public DriverSpy() {
	}

	public int getMajorVersion() {
		if (lastUnderlyingDriverRequested == null) {
			return 1;
		} else {
			return lastUnderlyingDriverRequested.getMajorVersion();
		}
	}

	public int getMinorVersion() {
		if (lastUnderlyingDriverRequested == null) {
			return 0;
		} else {
			return lastUnderlyingDriverRequested.getMinorVersion();
		}
	}

	public boolean jdbcCompliant() {
		return lastUnderlyingDriverRequested != null && lastUnderlyingDriverRequested.jdbcCompliant();
	}

	public boolean acceptsURL(String url) throws SQLException {
		Driver d = getUnderlyingDriver(url);
		if (d != null) {
			lastUnderlyingDriverRequested = d;
			return true;
		} else {
			return false;
		}
	}

	private Driver getUnderlyingDriver(String url) throws SQLException {
		if (url.startsWith("jdbc:log4")) {
			url = url.substring(9);

			Enumeration e = DriverManager.getDrivers();

			Driver d;
			while (e.hasMoreElements()) {
				d = (Driver)e.nextElement();

				if (d.acceptsURL(url)) {
					return d;
				}
			}
		}
		return null;
	}

	public Connection connect(String url, Properties info) throws SQLException {
		Driver d = getUnderlyingDriver(url);
		if (d == null) {
			return null;
		}

		// get actual URL that the real driver expects
		// (strip off "jdbc:log4" from url)
		url = url.substring(9);

		lastUnderlyingDriverRequested = d;
		Connection c = d.connect(url, info);

		if (c == null) {
			throw new SQLException("invalid or unknown driver url: " + url);
		}
		if (log.isJdbcLoggingEnabled()) {
			ConnectionSpy cspy = new ConnectionSpy(c);
			RdbmsSpecifics r = null;
			String dclass = d.getClass().getName();
			if (dclass != null && dclass.length() > 0) {
				r = (RdbmsSpecifics)rdbmsSpecifics.get(dclass);
			}

			if (r == null) {
				r = defaultRdbmsSpecifics;
			}
			cspy.setRdbmsSpecifics(r);
			return cspy;
		} else {
			return c;
		}
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		Driver d = getUnderlyingDriver(url);
		if (d == null) {
			return new DriverPropertyInfo[0];
		}

		lastUnderlyingDriverRequested = d;
		return d.getPropertyInfo(url, info);
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}