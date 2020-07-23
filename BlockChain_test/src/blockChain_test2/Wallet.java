package blockChain_test2;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *	用java.security內的功能，創建公私鑰匙
 */
public class Wallet {

	/**
	 * 每個人而言必須保護好自己的私鑰 
	 * 私鑰的作用是為了對交易進行簽名
	 *私鑰用來簽名不想被篡改的數據
	 */
	private PrivateKey privateKey; // 私鑰

	/**
	 * 公鑰的作用就是地址，你可以分享你的公鑰給別人以此來獲取付款 
	 * 公鑰用來驗證我們的簽名是有效的而且沒有數據被篡改
	 */
	private PublicKey publicKey; // 公鑰

	
	public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();


	public PrivateKey getPrivateKey() {
		return privateKey;
	}



	public PublicKey getPublicKey() {
		return publicKey;
	}




	public Wallet() {
		generateKeyPair();
	}
 
	
	public void generateKeyPair() {
		try {
			
			//	使用java原生KeyPairGenerator，生成私有和公鑰
			//	ECDSA = ECC & DSA = Elliptic Curves Cryptography(橢圓曲線加密 )  	&	Digital Signature Algorithm(數位簽章演算法)
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			
			keyGen.initialize(ecSpec, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
			
		}
	}
	
	   public float getBalance() {
	        float total = 0;
	        for (Map.Entry<String, TransactionOutput> item: ShowBlockSystem_DealWithTransaction.UTXOs.entrySet()){
	            TransactionOutput UTXO = item.getValue();
	            if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
	                UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
	                total += UTXO.amount ;
	            }
	        }
	        return total;
	    }

	    public Transaction2 sendFunds(PublicKey _recipient,float value ) {
	        if(getBalance() < value) {
	            System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
	            return null;
	        }
	        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();

	        float total = 0;
	        for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
	            TransactionOutput UTXO = item.getValue();
	            total += UTXO.amount;
	            inputs.add(new TransactionInput(UTXO.id));
	            if(total > value) break;
	        }

	        Transaction2 newTransaction = new Transaction2(publicKey, _recipient , value, inputs);
	        newTransaction.generateSignature(privateKey);

	        for(TransactionInput input: inputs){
	            UTXOs.remove(input.transactionOutputId);
	        }

	        return newTransaction;
	    }


}
