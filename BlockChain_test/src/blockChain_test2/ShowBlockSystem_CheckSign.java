package blockChain_test2;

import java.security.Security;
import java.util.ArrayList;
import blockChain_test1.Block;

public class ShowBlockSystem_CheckSign {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	public static Wallet walletA;
	public static Wallet walletB;

	public static void main(String[] args) {
		//	下載Bouncy Castle ，這是一款輕量級的密碼包，包含的許多常用的密碼演算法，對 Java安全體系能夠起到很好的補充，同時其支援橢圓曲線密碼體系
	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
	   
	    walletA = new Wallet();
	    walletB = new Wallet();
	    
	   
	    System.out.println("Private and public keys:");
	    System.out.println(EncryptMethod2.getStringFromKey(walletA.getPrivateKey()));
	    System.out.println(EncryptMethod2.getStringFromKey(walletA.getPublicKey()));
	    
	    //	生成一個交易(A>>B)並使用walletA的私鑰對其進行簽名
	    Transaction2 transaction = new Transaction2(walletA.getPublicKey(), walletB.getPublicKey(), 5, null);
	    transaction.generateSignature(walletA.getPrivateKey());
	    
	    
	    System.out.println("Is signature verified");
	    System.out.println(transaction.verifiySignature());
	}

}