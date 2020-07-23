package blockChain_test2;

import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

public class Transaction2 {

	private String transactionId; // 交易ID

	private PublicKey sender; // 出貨人的地址，即是出貨人的公鑰.

	private PublicKey reciepient; // 收貨人地址，即是收貨人的公鑰

	private float amount; // 交易金額

	// 	一個加密簽名，證明該交易是由地址的發送者是發送的，
	// 	簽名用來阻止其他人試圖篡改提交的交易。
	private byte[] signature;

	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

	private static int sequence = 0; //	共有幾筆交易

	public Transaction2() {

	}

	public Transaction2(PublicKey from, PublicKey to, float amount, ArrayList<TransactionInput> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.amount = amount;
		this.inputs = inputs;
		this.transactionId = calulateHash();
	}

	//	交易ID是由:出貨人的公鑰、收貨人的公鑰、交易金額、交易數量，以上加密而成
	private String calulateHash() {
		sequence++;
		return EncryptMethod2.applySha256(EncryptMethod2.getStringFromKey(sender)
				+ EncryptMethod2.getStringFromKey(reciepient) + Float.toString(amount) + sequence);
	}

	/**
	 * 對數據，使用簽名
	 * 
	 * @param privateKey
	 */
	public void generateSignature(PrivateKey privateKey) {
		String data = EncryptMethod2.getStringFromKey(sender) + EncryptMethod2.getStringFromKey(reciepient)
				+ Float.toString(amount);
		signature = EncryptMethod2.applyECDSASig(privateKey, data);
	}

	/**
	 * 驗證簽名的數據未被篡改
	 * 
	 * @return
	 */
	public boolean verifiySignature() {
		String data = EncryptMethod2.getStringFromKey(sender) + EncryptMethod2.getStringFromKey(reciepient)
				+ Float.toString(amount);
		return EncryptMethod2.verifyECDSASig(sender, data, signature);
	}
	/**
	 *	若能建立交易，則 返回Returns
	 *	全部放在一起用來處理交易
	 * @return
	 */
	public boolean processTransaction() {

	    if(verifiySignature() == false) {
	        System.out.println("#Transaction Signature failed to verify");
	        return false;
	    }

	    //gather transaction inputs (Make sure they are unspent):
	    for(TransactionInput i : inputs) {
	        i.UTXO = ShowBlockSystem_DealWithTransaction.UTXOs.get(i.transactionOutputId);
	    }

	    //check if transaction is valid:
	    if(getInputsValue() < ShowBlockSystem_DealWithTransaction.minimumTransaction) {
	        System.out.println("#Transaction Inputs to small: " + getInputsValue());
	        return false;
	    }

	    //generate transaction outputs:
	    float leftOver = getInputsValue() - amount; //get value of inputs then the left over change:
	    transactionId = calulateHash();
	    outputs.add(new TransactionOutput( this.reciepient, amount,transactionId)); //send value to recipient
	    outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender        

	    //add outputs to Unspent list
	    for(TransactionOutput o : outputs) {
	    	ShowBlockSystem_DealWithTransaction.UTXOs.put(o.id , o);
	    }

	    //remove transaction inputs from UTXO lists as spent:
	    for(TransactionInput i : inputs) {
	        if(i.UTXO == null) continue; //if Transaction can't be found skip it 
	        ShowBlockSystem_DealWithTransaction.UTXOs.remove(i.UTXO.id);
	    }

	    return true;
	}
	//returns sum of inputs(UTXOs) values
	public float getInputsValue() {
	float total = 0;
	for(TransactionInput i : inputs) {
	if(i.UTXO == null) continue; //if Transaction can’t be found skip it
	total += i.UTXO.amount;
	}
	return total;
	}

	//returns sum of outputs:
	public float getOutputsValue() {
	float total = 0;
	for(TransactionOutput o : outputs) {
	total += o.amount;
	}
	return total;
	}

	public String getTransactionId() {
		return transactionId;
	}

	

	public PublicKey getSender() {
		return sender;
	}

	

	public PublicKey getReciepient() {
		return reciepient;
	}

	
	public float getAmount() {
		return amount;
	}

	

	public byte[] getSignature() {
		return signature;
	}

	

	public static int getSequence() {
		return sequence;
	}

	

	
	
}
