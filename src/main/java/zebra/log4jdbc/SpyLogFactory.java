package zebra.log4jdbc;

public class SpyLogFactory {

	private SpyLogFactory() {
	}

	private static final SpyLogDelegator logger = new Slf4jSpyLogDelegator();

	public static SpyLogDelegator getSpyLogDelegator() {
		return logger;
	}
}