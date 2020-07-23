package blockChain_test2;

/**
 *	創建並驗證輸入和輸出，並把交易保存到區塊鏈中去。
 *	交易的輸入指向前一個交易的輸出。
 *	錢包餘額是所有未使用的交易輸出的總和。
 */
public class TransactionInput {

	public String transactionOutputId; // 引用 transactionId
	public TransactionOutput UTXO; // 未使用的交易

	public TransactionInput(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}
}
