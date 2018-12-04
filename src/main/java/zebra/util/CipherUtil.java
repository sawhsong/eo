package zebra.util;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CipherUtil {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(CipherUtil.class);

	// some random salt
	private static final byte[] SALT = { (byte) 0x21, (byte) 0x21, (byte) 0xF0, (byte) 0x55, (byte) 0xC3, (byte) 0x9F, (byte) 0x5A, (byte) 0x75 };
	private static final int ITERATION_COUNT = 31;

	public static String encode(String value) throws Exception {
		if (CommonUtil.isBlank(value)) {
			return "";
		}

		String res = "";
		KeySpec keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT);
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		Cipher ecipher = Cipher.getInstance(key.getAlgorithm());

		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		byte[] enc = ecipher.doFinal(value.getBytes());
		res = new String(Base64.encodeBase64(enc));
		res = res.replace('+', '-').replace('/', '_').replace("%", "%25").replace("\n", "%0A");

		return res;
	}

	public static String decode(String value) throws Exception {
		if (CommonUtil.isBlank(value)) {
			return "";
		}

		String input = value.replace("%0A", "\n").replace("%25", "%").replace('_', '/').replace('-', '+');

		byte[] dec = Base64.decodeBase64(input.getBytes());
		KeySpec keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT);
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
		Cipher dcipher = Cipher.getInstance(key.getAlgorithm());

		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		byte[] decoded = dcipher.doFinal(dec);
		String result = new String(decoded);

		return result;
	}
}