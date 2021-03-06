package blockChain_test1;

import java.util.Date;
import java.util.List;

import blockChain_test2.EncryptMethod2;

/**
 * 	區塊鏈基礎架構
 */
public class Block {

	public String hashCode;// hash即為數字簽名

	public String previousHashCode;// 前一塊的hashCode

	private List<Transaction1> transactions;// 記錄各種交易

	private long timeStamp; // 以1/1/1970開始，到現今的毫秒數，為一個時間戳章
	
	private int nonce; // 挖礦所需亂數

	public Block() {

	}

	public Block(List<Transaction1> transactions, String previousHashCode) {
		this.transactions = transactions;
		this.previousHashCode = previousHashCode;
		this.timeStamp = new Date().getTime();
		this.hashCode = calculateHash();
	}

	/**
	 * 	計算hashCode: "前一塊hash"、"該塊交易紀錄"、"該塊時間戳章"，生成該塊hash
	 */
	public String calculateHash() {

		StringBuilder sb = new StringBuilder();
		for (int i=0; i<transactions.size(); i++) {
			String stringHashCode = Integer.toString(transactions.get(i).hashCode());
			sb.append(stringHashCode);
		}

		String total = previousHashCode + sb.toString() + Long.toString(timeStamp);
		String calculatedhash = EncryptMethod2.applySha256(total);

		return calculatedhash;
	}

	/**
	 * 	挖礦
	 * @param difficulty
	 */
	public void mineBlock(int difficulty) {
		
		String miningHash="";
		
		//建�?�難�?:??要幾?�零
		String target = new String(new char[difficulty]).replace('\0', '0');

		do {
			miningHash = calculateMiningHash();
			nonce++;
		}
		while (!miningHash.substring(0, difficulty).equals(target)); 
		
		System.out.println("Block Mined!!! : " + miningHash);
	}
	
	/**
	 * 	挖礦的PoW工作證明
	 */
	private String calculateMiningHash() {

		StringBuilder sb = new StringBuilder();
		for (int i=0; i<transactions.size(); i++) {
			String stringTransactionHashCode = Integer.toString(transactions.get(i).hashCode());
			sb.append(stringTransactionHashCode);
		}

		String total = previousHashCode + sb.toString() + Long.toString(timeStamp)+Integer.toString(nonce);
		String calculatedMiningHash = EncryptMethod2.applySha256(total);

		return calculatedMiningHash;
	}

}