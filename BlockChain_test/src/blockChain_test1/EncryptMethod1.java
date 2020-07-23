package blockChain_test1;

import java.security.MessageDigest;


/**
 * SHA（Secure Hash Algorithm）安全散列算法
 * 
 */
public class EncryptMethod1 {

	public static String applySha256(String input) {
		try {
			
			// JAVA有內建SHA-256加密
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); 
			for (byte digtal:hash) {
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
}