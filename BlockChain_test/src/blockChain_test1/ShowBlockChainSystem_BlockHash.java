package blockChain_test1;

import java.util.ArrayList;


/**
 * 測試??塊�?��?�hash
 */
public class ShowBlockChainSystem_BlockHash {
	public static void main(String[] args) {
		
		Transaction1 tx1 = new Transaction1("1","addressSender1","addressRecipient1",10);
		ArrayList<Transaction1> list1 = new ArrayList<Transaction1>();
		list1.add(tx1);
		Block genesisBlock = new Block(list1, "init");//起始區塊，給值init!!
		System.out.println("Hash for block 1 : " + genesisBlock.hashCode);
		
		
		
		Transaction1 tx2 = new Transaction1("2","addressSender2","addressRecipient2",50);
		ArrayList<Transaction1> list2 = new ArrayList<Transaction1>();
		list1.add(tx2);
		Block secondBlock = new Block(list2, genesisBlock.hashCode);
		System.out.println("Hash for block 2 : " + secondBlock.hashCode);
		
		
		

		Transaction1 tx3 = new Transaction1("3","addressSender3","addressRecipient3",24);
		ArrayList<Transaction1> list3 = new ArrayList<Transaction1>();
		list1.add(tx3);
		Block thirdBlock = new Block(list3, secondBlock.hashCode);
		System.out.println("Hash for block 3 : " + thirdBlock.hashCode);
	}
}