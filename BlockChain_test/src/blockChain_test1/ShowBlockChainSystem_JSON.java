package blockChain_test1;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;


public class ShowBlockChainSystem_JSON {
	
	public static int difficulty = 5;
	public static ArrayList<Block> blockchain = new ArrayList<Block>();

	/**
	 * 	檢查區塊鏈的正確性
	 * @return
	 */
	public static Boolean verifyChain() {

		Block currentBlock;
		Block previousBlock;

		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			
			//	對照目前區塊所紀錄上一個區塊的hashCode，和上一個區塊的hashCode，這兩個是否相等
			if (!previousBlock.hashCode.equals(currentBlock.previousHashCode)) {
				System.out.println("目前區塊所紀錄上一個區塊的hashCode，和上一個區塊的hashCode不一樣!!");
				return false;
			}
			
			if (!currentBlock.hashCode.equals(currentBlock.calculateHash())) {
				
				System.out.println(currentBlock.hashCode);
				System.out.println(currentBlock.calculateHash());
				System.out.println("目前區塊的hashCode，和目前區塊算出來的hashCode不一樣!");
				return false;
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) {

		//1
		Transaction1 tx1_1 = new Transaction1("1_1", "addressSender1", "addressRecipient1", 10);
		ArrayList<Transaction1> list1 = new ArrayList<Transaction1>();
		list1.add(tx1_1);
		Block genesisBlock = new Block(list1, "init");//起始區塊，給值init!!
		System.out.println("Hash for block 1 : " + genesisBlock.hashCode);
		
		blockchain.add(genesisBlock);
		
		System.out.println("Trying to Mine block 1... ");
		blockchain.get(0).mineBlock(difficulty);
		System.out.println("");

		
		//2
		Transaction1 tx2_1 = new Transaction1("2_1", "addressSender2", "addressRecipient2", 50);
		Transaction1 tx2_2 = new Transaction1("2_2", "addressSender1", "addressRecipient5", 40);
		Transaction1 tx2_3 = new Transaction1("2_3", "addressSender1", "addressRecipient5", 1);
		ArrayList<Transaction1> list2 = new ArrayList<Transaction1>();
		list2.add(tx2_1);
		list2.add(tx2_2);
		list2.add(tx2_3);
		Block secondBlock = new Block(list2, genesisBlock.hashCode);
		System.out.println("Hash for block 2 : " + secondBlock.hashCode);
		blockchain.add(secondBlock);
		
		System.out.println("Trying to Mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);
		System.out.println("");
		
		//3
		Transaction1 tx3_1 = new Transaction1("3_1", "addressSender3", "addressRecipient3", 24);
		Transaction1 tx3_2 = new Transaction1("3_2", "addressSender3", "addressRecipient3", 6);
		ArrayList<Transaction1> list3 = new ArrayList<Transaction1>();
		list3.add(tx3_1);
		list3.add(tx3_2);
		Block thirdBlock = new Block(list3, secondBlock.hashCode);
		System.out.println("Hash for block 3 : " + thirdBlock.hashCode);
		blockchain.add(thirdBlock);
		
		System.out.println("Trying to Mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);   
		System.out.println("");
		
		
		System.out.println("\nBlockchain is Valid: " + verifyChain());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		
		System.out.println("\nThe block chain: ");
		
		System.out.println(blockchainJson);
		
		
		
	}

	

}
