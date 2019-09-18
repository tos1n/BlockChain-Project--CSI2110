import java.io.UnsupportedEncodingException;
import java.sql.Timestamp; // imported for the timestamp
import java.util.Random;

public class Block {
	
	private int index; // the index of the block in the list
	 private java.sql.Timestamp timestamp; // time at which transaction has been processed
	 private Transaction transaction; // the transaction object
	 private String nonce; // random string (for proof of work)
	 private String previousHash; // previous hash (in first block, set to string of zeroes of size of complexity "00000")
	 private String hash; // hash of the block (hash of string obtained from previous variables via toString() method) 
	 
	 public Block(Transaction newTransaction, String concatenateHash,int newInstance ) throws UnsupportedEncodingException { // instantiate new block to be added to the blockchain
		 this.index = newInstance; // this value gets incremented every time a new transaction takes place
		 this.transaction = newTransaction; 
		 this.timestamp = new Timestamp(System.currentTimeMillis()); //exact time the transaction takes place
		 this.previousHash = concatenateHash; 
		 this.myNonce();
		 this.hash=createHash();
	 }
	 
	 public Block(Transaction newTransaction, String myHash,int newInstance, String myNonce,Timestamp timestamp, String concatenateHash ) { // overloading
		 this.index = newInstance; // this value gets incremented every time a new transaction takes place
		 this.hash = myHash; 
		 this.nonce = myNonce;
		 this.transaction = newTransaction; 
		 this.timestamp =  timestamp; //exact time the transaction takes place
		 this.previousHash = concatenateHash; 
	 }
	 public Transaction getTransaction() { // getter method for transaction
		
		 return transaction;
		 
	 }
	 public int getIndex() {// getter method for index
		
		 return index;
	 
	 }
	 
	 public void myNonce() throws UnsupportedEncodingException {
		 Random myRandom = new Random();
		 
		 do {
				this.nonce = "";
				for (int i = 0; i < myRandom.nextInt(18)+3; i++) {
					int myRandom1 = myRandom.nextInt(94)+33;
					this.nonce = nonce+ ((char)myRandom1);
					}
				
			} while(!Sha1.hash(toString()).startsWith("00000"));
	 }
	  
	 
	 public String getPreviousHash() {
	    	return previousHash;
	    }
	 public String getNonce() {
	    	return nonce;
	    }
	 public String getHash() {
	    	return hash;
	    }
	public String createHash() throws UnsupportedEncodingException {
		hash = Sha1.hash(toString());	
		return hash;
	}
	public long  getTimestamp() {
		return timestamp.getTime();
	}
	 
	 public String toString() {
		 
	 return timestamp.toString() + ":" + transaction.toString()	 + "." + nonce+ previousHash;
	 
	 }

}
