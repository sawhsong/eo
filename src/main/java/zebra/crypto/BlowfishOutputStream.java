package zebra.crypto;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class BlowfishOutputStream extends OutputStream {
	OutputStream os;
	BlowfishCBC bfc;
	byte[] bufIn;
	byte[] bufOut;
	int bytesInBuf;

	void initialize(byte[] key, int ofs, int len, OutputStream os) throws IOException {
		this.os = os;

		this.bytesInBuf = 0;

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException nse) {
			throw new UnsupportedOperationException();
		}
		md.update(key, ofs, len);

		byte[] ckey = md.digest();
		md.reset();

		this.bfc = new BlowfishCBC(ckey, 0, ckey.length);

		Arrays.fill(ckey, 0, ckey.length, (byte) 0);

		this.bufIn = new byte[8];
		this.bufOut = new byte[8];

		SecureRandom srnd = new SecureRandom();
		srnd.nextBytes(this.bufIn);

		this.os.write(this.bufIn, 0, this.bufIn.length);
		this.bfc.setCBCIV(this.bufIn, 0);
	}

	public BlowfishOutputStream(byte[] key, int ofs, int len, OutputStream os) throws IOException {
		initialize(key, ofs, len, os);
	}

	public void write(int val) throws IOException {
		this.bytesInBuf += 1;
		if (this.bytesInBuf < this.bufIn.length) {
			this.bufIn[(this.bytesInBuf - 1)] = ((byte) val);
			return;
		}

		this.bufIn[(this.bytesInBuf - 1)] = ((byte) val);
		this.bytesInBuf = 0;

		this.bfc.encrypt(this.bufIn, 0, this.bufOut, 0, this.bufIn.length);

		this.os.write(this.bufOut, 0, this.bufOut.length);
	}

	public void close() throws IOException {
		if (this.os == null) {
			return;
		}

		byte padVal = (byte) (this.bufIn.length - this.bytesInBuf);

		while (this.bytesInBuf < this.bufIn.length) {
			this.bufIn[this.bytesInBuf] = padVal;
			this.bytesInBuf += 1;
		}

		this.bfc.encrypt(this.bufIn, 0, this.bufOut, 0, this.bufIn.length);

		this.os.write(this.bufOut, 0, this.bufOut.length);

		this.os.close();
		this.os = null;

		this.bfc.cleanUp();
	}

	public void flush() throws IOException {
		this.os.flush();
	}
}