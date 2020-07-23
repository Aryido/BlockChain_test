package blockChain;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;


public class Transaction {

	private String id;

	private String sender; // 交易傳送方錢包地址

	private String recipient;// 交易接收方錢包地址

	private int amount;

	public Transaction() {
	}

	/**
	 * @param id
	 * @param sender
	 * @param recipient
	 * @param amount
	 */
	public Transaction(String id, String sender, String recipient, int amount) {
		this.id = id;
		this.sender = sender;
		this.recipient = recipient;
		this.amount = amount;
	}

	

}
