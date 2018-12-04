package zebra.log4jdbc;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Slf4jSpyLogDelegator implements SpyLogDelegator {
	public Slf4jSpyLogDelegator() {
	}

	private final Logger jdbcLogger = LogManager.getLogger("jdbc.audit");

	private final Logger resultSetLogger = LogManager.getLogger("jdbc.resultset");

	private final Logger sqlOnlyLogger = LogManager.getLogger("jdbc.sqlonly");

	private final Logger sqlTimingLogger = LogManager.getLogger("jdbc.sqltiming");

	private final Logger connectionLogger = LogManager.getLogger("jdbc.connection");

	private final Logger debugLogger = LogManager.getLogger("log4jdbc.debug");

	public boolean isJdbcLoggingEnabled() {
		return jdbcLogger.isErrorEnabled() || resultSetLogger.isErrorEnabled() || sqlOnlyLogger.isErrorEnabled() || sqlTimingLogger.isErrorEnabled() || connectionLogger.isErrorEnabled();
	}

	public void exceptionOccured(Spy spy, String methodCall, Exception e, String sql, long execTime) {
		String classType = spy.getClassType();
		Integer spyNo = spy.getConnectionNumber();
		String header = spyNo + ". " + classType + "." + methodCall;
		if (sql == null) {
			jdbcLogger.error(header, e);
			sqlOnlyLogger.error(header, e);
			sqlTimingLogger.error(header, e);
		} else {
			sql = processSql(sql);
			jdbcLogger.error(header + " " + sql, e);

			// if at debug level, display debug info to error log
			if (sqlOnlyLogger.isDebugEnabled()) {
				sqlOnlyLogger.error(getDebugInfo() + nl + spyNo + ". " + sql, e);
			} else {
				sqlOnlyLogger.error(header + " " + sql, e);
			}

			// if at debug level, display debug info to error log
			if (sqlTimingLogger.isDebugEnabled()) {
				sqlTimingLogger.error(getDebugInfo() + nl + spyNo + ". " + sql + " {FAILED after " + execTime + " msec}", e);
			} else {
				sqlTimingLogger.error(header + " FAILED! " + sql + " {FAILED after " + execTime + " msec}", e);
			}
		}
	}

	public void methodReturned(Spy spy, String methodCall, String returnMsg) {
		String classType = spy.getClassType();
		Logger logger = ResultSetSpy.classTypeDescription.equals(classType) ? resultSetLogger : jdbcLogger;
		if (logger.isInfoEnabled()) {
			String header = spy.getConnectionNumber() + ". " + classType + "." + methodCall + " returned " + returnMsg;
			if (logger.isDebugEnabled()) {
				logger.debug(header + " " + getDebugInfo());
			} else {
				logger.info(header);
			}
		}
	}

	public void constructorReturned(Spy spy, String constructionInfo) {
	}

	private static String nl = System.getProperty("line.separator");

	private boolean shouldSqlBeLogged(String sql) {
		if (sql == null) {
			return false;
		}
		sql = sql.trim();

		if (sql.length() < 6) {
			return false;
		}
		sql = sql.substring(0, 6).toLowerCase();
		return (DriverSpy.DumpSqlSelect && "select".equals(sql)) || (DriverSpy.DumpSqlInsert && "insert".equals(sql)) || (DriverSpy.DumpSqlUpdate && "update".equals(sql))
				|| (DriverSpy.DumpSqlDelete && "delete".equals(sql)) || (DriverSpy.DumpSqlCreate && "create".equals(sql));
	}

	public void sqlOccured(Spy spy, String methodCall, String sql) {
		if (!DriverSpy.DumpSqlFilteringOn || shouldSqlBeLogged(sql)) {
			if (sqlOnlyLogger.isDebugEnabled()) {
				sqlOnlyLogger.debug(getDebugInfo() + nl + spy.getConnectionNumber() + ". " + processSql(sql));
			} else if (sqlOnlyLogger.isInfoEnabled()) {
				sqlOnlyLogger.info(processSql(sql));
			}
		}
	}

	private String processSql(String sql) {
		if (sql == null) {
			return null;
		}

		if (DriverSpy.TrimSql) {
			sql = sql.trim();
		}

		StringBuilder output = new StringBuilder();

		if (DriverSpy.DumpSqlMaxLineLength <= 0) {
			output.append(sql);
		} else {
			// insert line breaks into sql to make it more readable
			StringTokenizer st = new StringTokenizer(sql);
			String token;
			int linelength = 0;

			while (st.hasMoreElements()) {
				token = (String)st.nextElement();

				output.append(token);
				linelength += token.length();
				output.append(" ");
				linelength++;
				if (linelength > DriverSpy.DumpSqlMaxLineLength) {
					output.append("\n");
					linelength = 0;
				}
			}
		}

		if (DriverSpy.DumpSqlAddSemicolon) {
			output.append(";");
		}

		String stringOutput = output.toString();

		if (DriverSpy.TrimExtraBlankLinesInSql) {
			LineNumberReader lineReader = new LineNumberReader(new StringReader(stringOutput));

			output = new StringBuilder();

			int contiguousBlankLines = 0;
			try {
				while (true) {
					String line = lineReader.readLine();
					if (line == null) {
						break;
					}

					// is this line blank?
					if (line.trim().length() == 0) {
						contiguousBlankLines++;
						// skip contiguous blank lines
						if (contiguousBlankLines > 1) {
							continue;
						}
					} else {
						contiguousBlankLines = 0;
						output.append(line);
					}
					output.append("\n");
				}
			} catch (IOException e) {
				// since we are reading from a buffer, this isn't likely to happen,
				// but if it does we just ignore it and treat it like its the end of the
				// stream
			}
			stringOutput = output.toString();
		}

		// trim whitespace that is the same from the front of each line in the SQL
		if (DriverSpy.TrimSqlLines) {
			// the algorithm below is not the most efficient possible, but it
			// represents a reasonable trade off between performance and
			// maintainability as well as time to develop in the first place.

			// There are a lot of approaches that could be taken to make it run
			// faster if the need arises, but the size of the strings involved
			// shouldn't require that, at least at this time.

			// root line to use for comparison purposes.
			String rootLine = null;

			LineNumberReader lineReader = new LineNumberReader(new StringReader(stringOutput));

			// first make one pass to gather the lines into a List
			List<String> linesList = new ArrayList<String>();
			try {
				while (true) {
					String line = lineReader.readLine();
					if (line == null) {
						break;
					}

					line = Utilities.rtrim(line);
					if (rootLine == null && line.length() > 0) {
						rootLine = line;
					}

					// any lines that are all whitespace get collapsed here to an empty
					// string so we will know that we can
					// skip those completely in the next stage
					linesList.add(line);
				}
			} catch (IOException e) {
				// since we are reading from a buffer, this isn't likely to happen,
				// but if it does we just ignore it and treat it like its the end of the
				// stream
			}

			// early termination... only one line, or no output!
			if (rootLine == null || rootLine.length() == 0 || linesList.size() <= 1) {
				return stringOutput.trim();
			}

			// now make multiple passes comparing whitespace from each line until
			// a deviation occurs
			// then we know how much whitespace to consume from each line

			int whiteSpaceIndex = -1;

			outer: while (true) {
				whiteSpaceIndex++;
				// walk each string until we find non whitespace or divergent types
				// of whitespace

				// we are safe from terminating this loop off the end of the string
				// because the previous step collapsed all white space strings to
				// an empty string and we skip empty strings here, so one of the strings
				// must necessarily terminate the loop via a non-whitespace char
				// (or earlier via diverging types of whitespace , like a space vs. a
				// tab)
				for (String line : linesList) {
					// completely blank lines are exempt from this check...
					if (line.equals("")) {
						continue;
					}

					Character ch = line.charAt(whiteSpaceIndex);

					// whitespace must match identically to be considered for collapsing
					if (!Character.isWhitespace(ch) || ch != rootLine.charAt(whiteSpaceIndex)) {
						break outer;
					}
				}
			}

			// now that we know how much we can trim from each string, do the trim
			if (whiteSpaceIndex > 0) {
				output = new StringBuilder();
				for (String line : linesList) {
					if (!line.equals("")) {
						output.append(line.substring(whiteSpaceIndex));
					}
					output.append(nl);
				}
				stringOutput = output.toString();
			}

		}

		return stringOutput;
	}

	public void sqlTimingOccured(Spy spy, long execTime, String methodCall, String sql) {
		if (sqlTimingLogger.isErrorEnabled() && (!DriverSpy.DumpSqlFilteringOn || shouldSqlBeLogged(sql))) {
			if (DriverSpy.SqlTimingErrorThresholdEnabled && execTime >= DriverSpy.SqlTimingErrorThresholdMsec) {
				sqlTimingLogger.error(buildSqlTimingDump(spy, execTime, methodCall, sql, sqlTimingLogger.isDebugEnabled()));
			} else if (sqlTimingLogger.isWarnEnabled()) {
				if (DriverSpy.SqlTimingWarnThresholdEnabled && execTime >= DriverSpy.SqlTimingWarnThresholdMsec) {
					sqlTimingLogger.warn(buildSqlTimingDump(spy, execTime, methodCall, sql, sqlTimingLogger.isDebugEnabled()));
				} else if (sqlTimingLogger.isDebugEnabled()) {
					sqlTimingLogger.debug(buildSqlTimingDump(spy, execTime, methodCall, sql, true));
				} else if (sqlTimingLogger.isInfoEnabled()) {
					sqlTimingLogger.info(buildSqlTimingDump(spy, execTime, methodCall, sql, false));
				}
			}
		}
	}

	private String buildSqlTimingDump(Spy spy, long execTime, String methodCall, String sql, boolean debugInfo) {
		StringBuffer out = new StringBuffer();

		if (debugInfo) {
			out.append(getDebugInfo());
			out.append(nl);
			out.append(spy.getConnectionNumber());
			out.append(". ");
		}

		// NOTE: if both sql dump and sql timing dump are on, the processSql
		// algorithm will run TWICE once at the beginning and once at the end
		// this is not very efficient but usually
		// only one or the other dump should be on and not both.

		sql = processSql(sql);

		out.append(sql);
		out.append(" {executed in ");
		out.append(execTime);
		out.append(" msec}");

		return out.toString();
	}

	private static String getDebugInfo() {
		Throwable t = new Throwable();
		t.fillInStackTrace();

		StackTraceElement[] stackTrace = t.getStackTrace();

		if (stackTrace != null) {
			String className;

			StringBuffer dump = new StringBuffer();

			/**
			 * The DumpFullDebugStackTrace option is useful in some situations when we want to see the full stack trace in the debug info- watch out though as this will make the logs HUGE!
			 */
			if (DriverSpy.DumpFullDebugStackTrace) {
				boolean first = true;
				for (int i = 0; i < stackTrace.length; i++) {
					className = stackTrace[i].getClassName();
					if (!className.startsWith("zebra.log4jdbc")) {
						if (first) {
							first = false;
						} else {
							dump.append("  ");
						}
						dump.append("at ");
						dump.append(stackTrace[i]);
						dump.append(nl);
					}
				}
			} else {
				dump.append(" ");
				int firstLog4jdbcCall = 0;
				int lastApplicationCall = 0;

				for (int i = 0; i < stackTrace.length; i++) {
					className = stackTrace[i].getClassName();
					if (className.startsWith("zebra.log4jdbc")) {
						firstLog4jdbcCall = i;
					} else if (DriverSpy.TraceFromApplication && className.startsWith(DriverSpy.DebugStackPrefix)) {
						lastApplicationCall = i;
						break;
					}
				}
				int j = lastApplicationCall;

				if (j == 0) // if app not found, then use whoever was the last guy that
							// called a log4jdbc class.
				{
					j = 1 + firstLog4jdbcCall;
				}

				dump.append(stackTrace[j].getClassName()).append(".").append(stackTrace[j].getMethodName()).append("(").append(stackTrace[j].getFileName()).append(":")
						.append(stackTrace[j].getLineNumber()).append(")");
			}

			return dump.toString();
		} else {
			return null;
		}
	}

	public void debug(String msg) {
		debugLogger.debug(msg);
	}

	public void connectionOpened(Spy spy) {
		if (connectionLogger.isDebugEnabled()) {
			connectionLogger.info(spy.getConnectionNumber() + ". Connection opened " + getDebugInfo());
			connectionLogger.debug(ConnectionSpy.getOpenConnectionsDump());
		} else {
			connectionLogger.info(spy.getConnectionNumber() + ". Connection opened");
		}
	}

	public void connectionClosed(Spy spy) {
		if (connectionLogger.isDebugEnabled()) {
			connectionLogger.info(spy.getConnectionNumber() + ". Connection closed " + getDebugInfo());
			connectionLogger.debug(ConnectionSpy.getOpenConnectionsDump());
		} else {
			connectionLogger.info(spy.getConnectionNumber() + ". Connection closed");
		}
	}
}