package zebra.log4jdbc;

public interface SpyLogDelegator {
	public boolean isJdbcLoggingEnabled();
	public void exceptionOccured(Spy spy, String methodCall, Exception e, String sql, long execTime);
	public void methodReturned(Spy spy, String methodCall, String returnMsg);
	public void constructorReturned(Spy spy, String constructionInfo);
	public void sqlOccured(Spy spy, String methodCall, String sql);
	public void sqlTimingOccured(Spy spy, long execTime, String methodCall, String sql);
	public void connectionOpened(Spy spy);
	public void connectionClosed(Spy spy);
	public void debug(String msg);
}