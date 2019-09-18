import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class BlockChain {
	private ArrayList<Block> myNewBlockChain;
	
	public BlockChain() {
		myNewBlockChain = new ArrayList<Block>();
		
	}
	
	public static BlockChain fromFile(String fileName) throws NumberFormatException, IOException {
		BlockChain myBlockChain = new BlockChain();
		File myFile = new File(fileName);
		BufferedReader myBufferedReader = new BufferedReader(new FileReader(myFile));
			Block myBlock;
			Transaction myTransaction;
			 int newInstance =0;
			java.sql.Timestamp timestamp = null;
		
			 String from ="placeholder" , to = "placeholder";
			 int bitcoinExchanged =0;
			 String nonce ="placeholder",hash ="placeholder",previousHash = "00000";
			 
			 String line;
			 while( (line = myBufferedReader.readLine()) != null) {
					newInstance= Integer.parseInt(line);	// index	
					timestamp= 	new Timestamp(Long.parseLong(myBufferedReader.readLine()));
					from= myBufferedReader.readLine(); // sender
					to= myBufferedReader.readLine(); // receiver
					bitcoinExchanged=Integer.parseInt(myBufferedReader.readLine()); // amount
					nonce= myBufferedReader.readLine();
					if( newInstance> 0){
						 previousHash = myBlockChain.myNewBlockChain.get((newInstance)-1).createHash();
						 
					 }
					hash= myBufferedReader.readLine();
					
		
		myTransaction = new Transaction(bitcoinExchanged, from, to); // amount from to
		myBlock = new Block(myTransaction, hash,newInstance,nonce,timestamp,previousHash);
		myBlockChain.add(myBlock);
		}
		
		return myBlockChain;
		
	
	}
	
	public void toFile(String fileName) throws IOException {
		
		
		PrintWriter myWriter = new PrintWriter(fileName, "UTF-8");
		for(int x = 0; x< myNewBlockChain.size(); x++) {
		      myWriter.println(myNewBlockChain.get(x).getIndex());
		      myWriter.println(myNewBlockChain.get(x).getTimestamp());
		      myWriter.println(myNewBlockChain.get(x).getTransaction().getSender());
		      myWriter.println(myNewBlockChain.get(x).getTransaction().getReceiver());
		      myWriter.println(myNewBlockChain.get(x).getTransaction().getAmount());
		      myWriter.println(myNewBlockChain.get(x).getNonce());
		      myWriter.println(myNewBlockChain.get(x).getHash());
		      
		      }
		myWriter.close();
		
	}
	
	public boolean validateBlockChain() throws UnsupportedEncodingException {
		    	 
    	 boolean flag = true;
    	
    	 for( int i = 0; i< myNewBlockChain.size(); i++) {
    		 
    		 Block aNewBlock = myNewBlockChain.get(i);
    		 if(!aNewBlock.createHash().startsWith("00000")) {
            	 flag = false;
             }
    		 //Previous 
             if(i !=0 && !aNewBlock.getPreviousHash().equals(myNewBlockChain.get(i-1).getHash())) {
            	 flag = false;
     
             }
             else if (!aNewBlock.getPreviousHash().startsWith("00000")) {
            	 flag = false;
             }
             
             if(getBalance(aNewBlock.getTransaction().getSender()) < 0 && !aNewBlock.getTransaction().getSender().equals("bitcoin")){ // i !=0) { // if bitcoin isnt allowed to do mutliple transactions then change what comes after the and to whats commented out
            	 flag = false;
             }
    	 }		 
    	     	
    	 return flag;
		
	}
	
	public int getBalance(String username) {

		Transaction iteratingTransaction;
		Block iteratingBlock;
		int leftOverAmount = 0; //balance
		int counter = 0; 
		
		while (counter < myNewBlockChain.size()) {
			iteratingBlock = myNewBlockChain.get(counter); // setting each instance of the iterating block to its corresponding Array list value
			iteratingTransaction = iteratingBlock.getTransaction(); // calling the transaction getter method
			if (iteratingTransaction.getReceiver().equals(username)) { // adds to the leftOverAmount  the balance
				leftOverAmount = leftOverAmount + iteratingTransaction.getAmount();
			}
			else if (iteratingTransaction.getSender().equals(username)) { //subtracts from the leftOverAmount( the balance)
				leftOverAmount = leftOverAmount - iteratingTransaction.getAmount();
			}
			counter++; // goes to the next value of the newBlockChain ArrayList
		}
		
		
		return leftOverAmount;
	}
	 
	public void add(Block block) {
		 myNewBlockChain.add(block); 
	 }
	
	
	public static void main(String[] args)  {
		
		try {
			
			 
			Scanner myScanner = new Scanner(System.in);
			System.out.println("please input the name of your file( the one you are reading from)  in quotations along with the extension .");
			System.out.println("An example is -> \"blockchain_sbaru060.txt\" ");
			String filename = myScanner.nextLine();
			BlockChain myBlockChain = null;
			try {
				myBlockChain = fromFile(filename);
			}catch(FileNotFoundException e) {
				System.out.println("The file was not found.");
			}
			
	     
	         boolean flag1 = false, flag2 = true;  // flags to validate the chain
	        String to ="placeholder",from ="placeholder",consoleInput = " placeholder";
	        int bitcoinTransferred = 0;
	        
	        System.out.println("Do you want to conduct a transaction y/n ");
	        if(myScanner.nextLine().equals("y"))
	        	flag2 = false;
	        	
	        while(!flag2) {
	        	while(!flag1) { 
	            	System.out.println("please enter the name of the sender ( case sensitive)");
	                from = myScanner.nextLine();
	                System.out.println("please enter the name of the reciever ( case sensitive)");
	                to = myScanner.nextLine();
	                System.out.println("please enter the value of the bitcoin being transferred ( integer data type only)");
	              
	                	bitcoinTransferred = myScanner.nextInt();
	                
	                
	                if(myBlockChain.getBalance(from) >= bitcoinTransferred) {
	                	flag1 = true;
	                	break;
	                } else {
	                	System.out.println(from + " does not have enough bitcoins.");
	                }
	           
	                myScanner.nextLine();
	            }
	            Transaction newTransaction = new Transaction(bitcoinTransferred, from , to);
	           
	            Block newBlock = new Block( newTransaction, myBlockChain.myNewBlockChain.get(myBlockChain.myNewBlockChain.size()-1).getHash(),myBlockChain.myNewBlockChain.size());
	            myBlockChain.add(newBlock);
	            System.out.println("Do you want another transaction y/n ");
	            myScanner.nextLine();
	            consoleInput = myScanner.nextLine();
	            flag1 = false;
	            if(consoleInput.equals("n")) {
	            	flag2 = true;
	            }
	        }
	        System.out.println("please input the name of the file you want to store to.");
			System.out.println("An example is -> \"blockchain_sbaru060.txt\" ");
			String outfilename = myScanner.nextLine();
	        myBlockChain.toFile(outfilename);
	        System.out.println("The blockchain validations results were: " + myBlockChain.validateBlockChain());
	        System.out.println("Thank you for usin our services. please come back later");
	    
		
			
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
