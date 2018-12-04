package zebra.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class BlowfishEasy {
	BlowfishCBC bfc;
	SecureRandom srnd = new SecureRandom();

	public BlowfishEasy(char[] passw) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException nse) {
			throw new UnsupportedOperationException();
		}

		int i = 0;
		for (int c = passw.length; i < c; i++) {
			char pc = passw[i];
			md.update((byte) (pc >>> '\b'));
			md.update((byte) (pc & 0xFF));
		}

		byte[] hash = md.digest();

		this.bfc = new BlowfishCBC(hash, 0, hash.length, 0L);
	}

	public String encryptString(String plainText) {
		return encStr(plainText, this.srnd.nextLong());
	}

	public String encryptString(String plaintext, Random rndgen) {
		return encStr(plaintext, rndgen.nextLong());
	}

	private String encStr(String plainText, long newCBCIV) {
		int strlen = plainText.length();
		byte[] buf = new byte[(strlen << 1 & 0xFFFFFFF8) + 8];

		int pos = 0;
		for (int i = 0; i < strlen; i++) {
			char achar = plainText.charAt(i);
			buf[(pos++)] = ((byte) (achar >> '\b' & 0xFF));
			buf[(pos++)] = ((byte) (achar & 0xFF));
		}

		byte padval = (byte) (buf.length - (strlen << 1));
		while (pos < buf.length) {
			buf[(pos++)] = padval;
		}

		this.bfc.setCBCIV(newCBCIV);
		this.bfc.encrypt(buf, 0, buf, 0, buf.length);

		byte[] newIV = new byte[8];

		BinConverter.longToByteArray(newCBCIV, newIV, 0);

		return BinConverter.bytesToHexStr(newIV, 0, 8) + BinConverter.bytesToHexStr(buf, 0, buf.length);
	}

	public String decryptString(String cipherText) {
		int len = cipherText.length() >> 1 & 0xFFFFFFF8;

		if (8 > len) {
			return null;
		}

		byte[] cbciv = new byte[8];

		int numOfBytes = BinConverter.hexStrToBytes(cipherText, cbciv, 0, 0, 8);

		if (numOfBytes < 8) {
			return null;
		}

		this.bfc.setCBCIV(cbciv, 0);

		len -= 8;
		if (len == 0) {
			return "";
		}

		byte[] buf = new byte[len];

		numOfBytes = BinConverter.hexStrToBytes(cipherText, buf, 16, 0, len);

		if (numOfBytes < len) {
			return null;
		}

		this.bfc.decrypt(buf, 0, buf, 0, buf.length);

		int padbyte = buf[(buf.length - 1)] & 0xFF;

		if (8 < padbyte) {
			padbyte = 0;
		}

		numOfBytes -= padbyte;

		if (numOfBytes < 0) {
			return "";
		}

		return BinConverter.byteArrayToStr(buf, 0, numOfBytes);
	}

	public void destroy() {
		this.bfc.cleanUp();
	}
}