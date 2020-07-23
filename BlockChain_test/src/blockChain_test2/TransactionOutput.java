package blockChain_test2;

import java.security.PublicKey;

/**
 *	創建並驗證輸入和輸出，並把交易保存到區塊鏈中去。
 *	交易輸出類將顯示從交易中發送給每一方的最終金額。
 *	這些作爲新交易中的輸入參考，作爲證明你可以發送的金額數量。
 */
public class TransactionOutput {

	public String id;
	public PublicKey reciepient; //	硬幣的新所有者。
	public float amount; //	擁有的硬幣數量
	public String parentTransactionId; //	建立此輸出的交易的ID

	
	public TransactionOutput(PublicKey reciepient, float amount, String parentTransactionId) {
	    this.reciepient = reciepient;
	    this.amount = amount;
	    this.parentTransactionId = parentTransactionId;
	    this.id = EncryptMethod2.applySha256(EncryptMethod2.getStringFromKey(reciepient)+Float.toString(amount)+parentTransactionId);
	}

	//	檢查幣的所有權是否是自己。
	public boolean isMine(PublicKey publicKey) {
	    return (publicKey == reciepient);
	}
}
