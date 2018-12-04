package zebra.crypto;

public class CryptoRun {
	public static String EncRun(String key, String plaintext) {
		StringBuffer temp = new StringBuffer();
		temp.append("^");
		temp.append(plaintext);
		temp.append("$");
		BlowfishEasy bfe = new BlowfishEasy(key.toCharArray());

		return bfe.encryptString(temp.toString());
	}

	public static String DecRun(String key, String cipherText) {
		BlowfishEasy bfe = new BlowfishEasy(key.toCharArray());
		StringBuffer temp = new StringBuffer();
		StringBuffer returnvalue = new StringBuffer();
		temp.append(bfe.decryptString(cipherText));

		if (temp.toString().equals("")) {
			returnvalue.append("");
		} else if ((temp.substring(0, 1).equals("^")) && (temp.substring(temp.length() - 1, temp.length()).equals("$"))) {
			temp = temp.replace(0, 1, "");
			temp = temp.replace(temp.length() - 1, temp.length(), "");
			returnvalue.append(temp.toString());
		} else {
			returnvalue.append("");
		}

		return returnvalue.toString();
	}
}