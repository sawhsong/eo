package zebra.crypto;

import java.io.ByteArrayOutputStream;

public class URL64 {
	@SuppressWarnings("unused")
	private static final char _fldif = 'ÿ';
	@SuppressWarnings("unused")
	private static final char a = '@';
	@SuppressWarnings("unused")
	private static final char _fldint = 'ÿ';
	static final char[] _fldfor = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', '-', '.', '0' };

	static final char[] _flddo = { 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ',
			'ÿ', 'ÿ', '>', '?', 'ÿ', '@', '4', '5', '6', '7', '8', '9', ':', ';', '<', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ', '\000', '\001', '\002', '\003', '\004', '\005', '\006', '\007', '\b', '\t', '\n', '\013', '\f', '\r', '\016', '\017', '\020',
			'\021', '\022', '\023', '\024', '\025', '\026', '\027', '\030', '\031', 'ÿ', 'ÿ', 'ÿ', 'ÿ', '=', 'ÿ', '\032', '\033', '\034', '\035', '\036', '\037', ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0',
			'1', '2', '3', 'ÿ', 'ÿ', 'ÿ', 'ÿ', 'ÿ' };

	public static byte[] decode(String s) {
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		int[] ai = new int[4];
		if (s == null)
			return null;
		int i = 0;
		for (int j = s.length(); i < j;) {
			int k = 0;
			do {
				char c = s.charAt(i++);
				if (c < '') {
					char c1 = _flddo[c];
					if (c1 != 'ÿ') {
						ai[(k++)] = c1;
						if (i != j)
							continue;
						if (k == 4)
							break;
					}
				}
				return null;
			} while (k < 4);
			if ((ai[0] == 64) || (ai[1] == 64))
				return null;
			bytearrayoutputstream.write(ai[0] << 2 | (ai[1] & 0x30) >>> 4);
			if (ai[2] == 64)
				break;
			bytearrayoutputstream.write((ai[1] & 0xF) << 4 | (ai[2] & 0x3C) >>> 2);
			if (ai[3] == 64)
				break;
			bytearrayoutputstream.write((ai[2] & 0x3) << 6 | ai[3]);
		}

		return bytearrayoutputstream.toByteArray();
	}

	public static String encode(byte[] abyte0) {
		return encode(abyte0, abyte0.length);
	}

	public static String encode(byte[] abyte0, int i) {
		StringBuffer stringbuffer = new StringBuffer();
		int j = 0;
		int j2 = 0;
		for (int k2 = i / 3; j2 < k2;) {
			int k = abyte0[j] & 0xFF;
			int j1 = abyte0[(j + 1)] & 0xFF;
			int i2 = abyte0[(j + 2)] & 0xFF;
			stringbuffer.append(_fldfor[(k >>> 2)]);
			stringbuffer.append(_fldfor[((k & 0x3) << 4 | (j1 & 0xF0) >>> 4)]);
			stringbuffer.append(_fldfor[((j1 & 0xF) << 2 | (i2 & 0xC0) >>> 6)]);
			stringbuffer.append(_fldfor[(i2 & 0x3F)]);
			j2++;
			j += 3;
		}

		switch (i % 3) {
		case 1:
			int l = abyte0[j] & 0xFF;
			int k1 = 0;
			stringbuffer.append(_fldfor[(l >>> 2)]);
			stringbuffer.append(_fldfor[((l & 0x3) << 4 | (k1 & 0xF0) >>> 4)]);
			stringbuffer.append(_fldfor[64]);
			stringbuffer.append(_fldfor[64]);
			break;
		case 2:
			int i1 = abyte0[j] & 0xFF;
			int l1 = abyte0[(j + 1)] & 0xFF;
			stringbuffer.append(_fldfor[(i1 >>> 2)]);
			stringbuffer.append(_fldfor[((i1 & 0x3) << 4 | (l1 & 0xF0) >>> 4)]);
			stringbuffer.append(_fldfor[((l1 & 0xF) << 2)]);
			stringbuffer.append(_fldfor[64]);
		}

		return stringbuffer.toString();
	}

	public static char[] getBasis_64() {
		return _fldfor;
	}

	public static char[] getIndex_64() {
		return _flddo;
	}

	public static char getInvalid() {
		return 'ÿ';
	}
}