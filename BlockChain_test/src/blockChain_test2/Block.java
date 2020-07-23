package blockChain_test2;

import java.util.ArrayList;
import java.util.Date;


public class Block {
    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction2> transactions = new ArrayList<Transaction2>(); //our data will be a simple message.
    public long timeStamp; //as number of milliseconds since 1/1/1970.
    public int nonce;

    //Block Constructor.
    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); 
    }

    public String calculateHash() {
        String calculatedhash = EncryptMethod2.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        merkleRoot
        );
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        merkleRoot = EncryptMethod2.getMerkleRoot(transactions);
        String target = EncryptMethod2.getDificultyString(difficulty); 
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    
    public boolean addTransaction(Transaction2 transaction) {
        if (transaction == null) return false;
        if ((previousHash != "0")) {
            if ((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
