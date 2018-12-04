package zebra.crypto;

public class CryptoException extends Exception {
	private Exception ex;

	public CryptoException(String msg) {
		super(msg);
	}

	public CryptoException(String msg, Exception exception) {
		super(msg);
		this.ex = exception;
	}

	public String toString() {
		return (this.ex != null ? this.ex.toString() + "\n" : "") + super.toString();
	}
}