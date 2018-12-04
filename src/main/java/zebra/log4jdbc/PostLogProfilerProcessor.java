package zebra.log4jdbc;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

public class PostLogProfilerProcessor {
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("usage: java PostLogProfilerProcessor <log-file>");
			System.exit(1);
		}
		new PostLogProfilerProcessor(args[0], System.out);
	}

	private long totalSql = 0L;
	private long lineNo = 0L;
	private long totalMsec = 0L;
	private long maxMsec = 0L;
	private long flaggedSqlTotalMsec = 0L;
	private long threshold = 100L;
	private long topOffenderCount = 1000L;
	@SuppressWarnings("rawtypes")
	private List flaggedSql = new LinkedList();

	public PostLogProfilerProcessor(String filename, PrintStream out) throws Exception {
		FileReader f = new FileReader(filename);
		LineNumberReader l = new LineNumberReader(f);

		String line;
		boolean blankLine;

		StringBuffer sql = new StringBuffer();

		do {
			line = l.readLine();

			if (line != null) {
				blankLine = line.length() == 0;
				lineNo++;
				/*
				 * if (lineNo%100000L==0L) { out.println("" + lineNo + " lines..."); }
				 */
				if (blankLine) {
					processSql(sql);
					sql = new StringBuffer();
				} else {
					sql.append(line);
				}

			}
		} while (line != null);

		out.println("processed " + lineNo + " lines.");

		f.close();

		// display report to stdout

		out.println("Number of sql statements:  " + totalSql);
		out.println("Total number of msec    :  " + totalMsec);
		if (totalMsec > 0) {
			out.println("Average msec/statement  :  " + totalSql / totalMsec);
		}

		int flaggedSqlStmts = flaggedSql.size();

		if (flaggedSqlStmts > 0) {
			out.println("Sql statements that took more than " + threshold + " msec were flagged.");
			out.println("Flagged sql statements              :  " + flaggedSqlStmts);
			out.println("Flagged sql Total number of msec    :  " + flaggedSqlTotalMsec);
			out.println("Flagged sql Average msec/statement  :  " + flaggedSqlTotalMsec / flaggedSqlStmts);

			out.println("sorting...");

			Object[] flaggedSqlArray = flaggedSql.toArray();
			Arrays.sort(flaggedSqlArray);

			int execTimeSize = ("" + maxMsec).length();

			if (topOffenderCount > flaggedSqlArray.length) {
				topOffenderCount = flaggedSqlArray.length;
			}

			out.println("top " + topOffenderCount + " offender" + (topOffenderCount == 1 ? "" : "s") + ":");

			ProfiledSql p;

			for (int i = 0; i < topOffenderCount; i++) {
				p = (ProfiledSql)flaggedSqlArray[i];
				out.println(Utilities.rightJustify(execTimeSize, "" + p.getExecTime()) + " " + p.getSql());
			}
		}
	}

	private void processSql(StringBuffer sql) {
		if (sql.length() > 0) {
			totalSql++;
			String sqlStr = sql.toString();
			if (sqlStr.endsWith("msec}")) {
				int executedIn = sqlStr.indexOf("{executed in ");
				if (executedIn == -1) {
					System.err.println("WARNING:  sql w/o timing info found at line " + lineNo);
					return;
				}

				// todo: proper error handling for parse
				String msecStr = sqlStr.substring(executedIn + 13, sqlStr.length() - 6);
				long msec = Long.parseLong(msecStr);
				totalMsec += msec;
				if (msec > maxMsec) {
					maxMsec = msec;
				}

				if (msec > threshold) {
					flagSql(msec, sqlStr);
					flaggedSqlTotalMsec += msec;
				}
			} else {
				System.err.println("WARNING:  sql w/o timing info found at line " + lineNo);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void flagSql(long msec, String sql) {
		flaggedSql.add(new ProfiledSql(msec, sql));
	}

	@SuppressWarnings("rawtypes")
	private class ProfiledSql implements Comparable {
		private Long execTime;
		private String sql;

		public ProfiledSql(long msec, String sql) {
			this.execTime = Long.valueOf(msec);
			this.sql = sql;
		}

		public int compareTo(Object o) {
			return ((ProfiledSql)o).execTime.compareTo(execTime);
		}

		public Long getExecTime() {
			return execTime;
		}

		public String getSql() {
			return sql;
		}

		public String toString() {
			return this.execTime + " msec:  " + this.sql;
		}
	}
}