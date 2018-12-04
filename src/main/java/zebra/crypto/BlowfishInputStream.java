package zebra.crypto;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlowfishInputStream extends InputStream {
	PushbackInputStream is;
	BlowfishCBC bfc;
	byte[] buf;
	int bufPos;
	int bufCount;

	void init(byte[] key, int ofs, int len, InputStream is) throws IOException {
		this.bufPos = (this.bufCount = 0);

		this.is = new PushbackInputStream(new BufferedInputStream(is));

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException nse) {
			throw new UnsupportedOperationException();
		}

		md.update(key, ofs, len);

		byte[] ckey = md.digest();
		md.reset();

		this.bfc = new BlowfishCBC(ckey, 0, ckey.length, 0L);

		this.buf = new byte[8];

		int i = 0;
		for (int c = this.buf.length; i < c; i++) {
			int val = this.is.read();
			if (-1 == val) {
				throw new IOException("truncated stream, IV is missing");
			}
			this.buf[i] = ((byte) val);
		}

		this.bfc.setCBCIV(this.buf, 0);
	}

	void fillBuffer() throws IOException {
		int i = 0;
		for (int c = this.buf.length; i < c; i++) {
			int val;
			if (-1 == (val = this.is.read())) {
				throw new IOException("truncated stream, unexpected end");
			}
			this.buf[i] = ((byte) val);
		}

		this.bfc.decrypt(this.buf, 0, this.buf, 0, this.buf.length);

		int val = this.is.read();
		if (-1 == val) {
			int c = this.buf[(this.buf.length - 1)];

			if ((c > this.buf.length) || (c < 0)) {
				throw new IOException("unknown padding value detected");
			}

			this.bufCount = (this.buf.length - c);

			for (i = this.bufCount; i < this.buf.length; i++) {
				if (this.buf[i] != (byte) c) {
					throw new IOException("invalid padding data detected");
				}
			}

			this.bfc.cleanUp();
			this.bfc = null;
		} else {
			this.is.unread(val);
			this.bufCount = this.buf.length;
		}

		this.bufPos = 0;
	}

	public BlowfishInputStream(byte[] key, int ofs, int len, InputStream is) throws IOException {
		init(key, ofs, len, is);
	}

	public int read() throws IOException {
		while (this.bufCount <= this.bufPos) {
			if (this.bfc == null) {
				return -1;
			}

			fillBuffer();
		}

		return this.buf[(this.bufPos++)] & 0xFF;
	}

	public void close() throws IOException {
		if (this.is != null) {
			this.is.close();
			this.is = null;
		}
	}
}