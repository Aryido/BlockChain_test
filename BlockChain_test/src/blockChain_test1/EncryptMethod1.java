package blockChain_test1;

import java.security.MessageDigest;


/**
 * SHA（Secure Hash Algorithm）安全散列算法
 * 
 */
public class EncryptMethod1 {

	public static String applySha256(String input) {
		try {
			
			// JAVA有內建SHA-256加密，得到<摘要>digest
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); 
			
			for (byte digtal:hash) {
				
				//and位元運算的規則 只有兩者皆為 true(1) 的結果才為 1
				//0xff代表只取8位，因為Integer只有8bits
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