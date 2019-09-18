
public class Transaction {
	private String sender; //username of person giving money
	private String receiver;//username of person receiving money
	private int amount; //number of bitcoins involved in the transaction
	// where to put timeStamp of the transaction???
	
	public Transaction(int bitcoinExchanged, String from, String to) {// instantiating the transaction
	
		amount = bitcoinExchanged;
		sender = from;
		receiver= to;
		
	}
	//getter methods for variables
	public int getAmount() {
		return amount;
	}
	public String getSender() {
		return sender;
		
	}
	public String getReceiver() {
		return receiver;
		
	}
	public String toString() {
		return sender + ":" + receiver + "=" + amount;
	}
	

}
