package zebra.exception;

public class EncodingException extends Exception {
	private String encoding;

	public EncodingException(String encoding) {
		super(encoding);
		this.encoding = encoding;
	}

	public EncodingException(String msg, String encoding) {
		super(msg);
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}
}