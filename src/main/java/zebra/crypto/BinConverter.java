package zebra.crypto;

public class BinConverter {
	static final char[] HEXTAB = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static final int byteArrayToInt(byte[] buf, int ofs) {
		return buf[ofs] << 24 | (buf[(ofs + 1)] & 0xFF) << 16 | (buf[(ofs + 2)] & 0xFF) << 8 | buf[(ofs + 3)] & 0xFF;
	}

	public static final void intToByteArray(int value, byte[] buf, int ofs) {
		buf[ofs] = ((byte) (value >>> 24 & 0xFF));
		buf[(ofs + 1)] = ((byte) (value >>> 16 & 0xFF));
		buf[(ofs + 2)] = ((byte) (value >>> 8 & 0xFF));
		buf[(ofs + 3)] = ((byte) value);
	}

	public static final long byteArrayToLong(byte[] buf, int ofs) {
		return (buf[ofs] << 24 | (buf[(ofs + 1)] & 0xFF) << 16 | (buf[(ofs + 2)] & 0xFF) << 8 | buf[(ofs + 3)] & 0xFF) << 32 | (buf[(ofs + 4)] << 24 | (buf[(ofs + 5)] & 0xFF) << 16 | (buf[(ofs + 6)] & 0xFF) << 8 | buf[(ofs + 7)] & 0xFF) & 0xFFFFFFFF;
	}

	public static final void longToByteArray(long value, byte[] buf, int ofs) {
		int tmp = (int) (value >>> 32);

		buf[ofs] = ((byte) (tmp >>> 24));
		buf[(ofs + 1)] = ((byte) (tmp >>> 16 & 0xFF));
		buf[(ofs + 2)] = ((byte) (tmp >>> 8 & 0xFF));
		buf[(ofs + 3)] = ((byte) tmp);

		tmp = (int) value;

		buf[(ofs + 4)] = ((byte) (tmp >>> 24));
		buf[(ofs + 5)] = ((byte) (tmp >>> 16 & 0xFF));
		buf[(ofs + 6)] = ((byte) (tmp >>> 8 & 0xFF));
		buf[(ofs + 7)] = ((byte) tmp);
	}

	public static final long intArrayToLong(int[] buf, int ofs) {
		return buf[ofs] << 32 | buf[(ofs + 1)] & 0xFFFFFFFF;
	}

	public static final void longToIntArray(long value, int[] buf, int ofs) {
		buf[ofs] = ((int) (value >>> 32));
		buf[(ofs + 1)] = ((int) value);
	}

	public static final long makeLong(int lo, int hi) {
		return hi << 32 | lo & 0xFFFFFFFF;
	}

	public static final int longLo32(long val) {
		return (int) val;
	}

	public static final int longHi32(long val) {
		return (int) (val >>> 32);
	}

	public static final String bytesToHexStr(byte[] data) {
		return bytesToHexStr(data, 0, data.length);
	}

	public static final String bytesToHexStr(byte[] data, int ofs, int len) {
		StringBuffer sbuf = new StringBuffer();
		sbuf.setLength(len << 1);

		int pos = 0;
		int c = ofs + len;

		while (ofs < c) {
			sbuf.setCharAt(pos++, HEXTAB[(data[ofs] >> 4 & 0xF)]);
			sbuf.setCharAt(pos++, HEXTAB[(data[(ofs++)] & 0xF)]);
		}
		return sbuf.toString();
	}

	public static final int hexStrToBytes(String hex, byte[] data, int srcofs, int dstofs, int len) {
		int strlen = hex.length();

		int availBytes = strlen - srcofs >> 1;
		if (availBytes < len) {
			len = availBytes;
		}

		int outputCapacity = data.length - dstofs;
		if (len > outputCapacity) {
			len = outputCapacity;
		}

		int dstofsBak = dstofs;

		for (int i = 0; i < len; i++) {
			byte abyte = 0;
			boolean convertOK = true;

			for (int j = 0; j < 2; j++) {
				abyte = (byte) (abyte << 4);
				char cActChar = hex.charAt(srcofs++);

				if ((cActChar >= 'a') && (cActChar <= 'f')) {
					abyte = (byte) (abyte | (byte) (cActChar - 'a') + 10);
				} else if ((cActChar >= '0') && (cActChar <= '9')) {
					abyte = (byte) (abyte | (byte) (cActChar - '0'));
				} else {
					convertOK = false;
				}
			}

			if (convertOK) {
				data[(dstofs++)] = abyte;
			}
		}

		return dstofs - dstofsBak;
	}

	public static final String byteArrayToStr(byte[] data, int ofs, int len) {
		len &= -2;

		int availCapacity = data.length - ofs;

		if (availCapacity < len) {
			len = availCapacity;
		}

		StringBuffer sbuf = new StringBuffer();
		sbuf.setLength(len >> 1);

		int sbufPos = 0;

		while (len > 0) {
			sbuf.setCharAt(sbufPos++, (char) (data[ofs] << 8 | data[(ofs + 1)] & 0xFF));
			ofs += 2;
			len -= 2;
		}

		return sbuf.toString();
	}
}