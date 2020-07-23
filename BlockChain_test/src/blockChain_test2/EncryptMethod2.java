package blockChain_test2;

import java.awt.List;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;

/**
 * SHA（Secure Hash Algorithm）安全散列算法
 * 
 */
public class EncryptMethod2 {

	public static String applySha256(String input) {
		try {

			// JAVA有內建SHA-256加密
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			for (byte digtal : hash) {
				String hex = Integer.toHexString(0xff & digtal);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();

		} catch (Exception e) {

			throw new RuntimeException(e);

		}
	}

	/**
	 * 產生簽名的方法 使用橢圓曲線密碼
	 * 
	 * @param privateKey
	 * @param input
	 * @return
	 */
	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		byte[] output = new byte[0];
		try {
			Signature dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);

			byte[] strByte = input.getBytes();
			dsa.update(strByte);

			byte[] realSig = dsa.sign();
			output = realSig;

		} catch (Exception e) {

			throw new RuntimeException(e);

		}
		return output;
	}

	/**
	 * 驗證簽名是否是有效
	 * 
	 * @param publicKey
	 * @param data
	 * @param signature
	 * @return
	 */
	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回key的編碼字符串
	 * 
	 * @param key
	 * @return
	 */
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public static String getMerkleRoot(ArrayList<Transaction2> transactions) {
		int count = transactions.size();

		ArrayList<String> previousTreeLayer = new ArrayList<String>();
		for (Transaction2 transaction : transactions) {
			previousTreeLayer.add(transaction.getTransactionId());
		}
		ArrayList<String> treeLayer = previousTreeLayer;

		while (count > 1) {
			treeLayer = new ArrayList<String>();
			for (int i = 1; i < previousTreeLayer.size(); i += 2) {
				treeLayer.add(applySha256(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
			}
			count = treeLayer.size();
			previousTreeLayer = treeLayer;
		}

		String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
		return merkleRoot;
	}

	public static String getDificultyString(int difficulty) {
		return new String(new char[difficulty]).replace('\0', '0');
	}

}
