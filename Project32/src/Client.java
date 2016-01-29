import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	   Socket socket = null;
	   PrintWriter out = null;
	   BufferedReader in = null;
	   String txtexe = "";
	   static String cclient="";
	   
	   public static void main(String[] args) {
		 
		
		if (args.length != 2){
	     System.out.println("Worng client hostname port");
		 System.exit(1);
	      }
		
		do{
			Client cs = new Client();
			String host = args[0];
			int port = Integer.valueOf(args[1]);
		    cs.listenSocket(host, port);
		    cs.communicate();
		    
		    if(cclient.equals("close"))
		    		{
		    	System.out.println("closeing client");
		    	System.exit(1);
		    }
		    Scanner sc = new Scanner(System.in);
		    System.out.println("Would you like to keep going: Y or N");
			String YoN = sc.nextLine();
		    
			if(YoN.equalsIgnoreCase("N")){
				System.out.println("closeing client");
				System.exit(1);
			}
		}while(true);
	
	}// end of main

	 public void listenSocket(String host, int port)
	   {
	      //Create socket connection
	      try
	      {
		 socket = new Socket(host, port);
		 out = new PrintWriter(socket.getOutputStream(), true);
		 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	      } 
	      catch (UnknownHostException e) 
	      {
		 System.out.println("Unknown host");
		 System.exit(1);
	      } 
	      catch (IOException e) 
	      {
		 System.out.println("No I/O");
		 System.exit(1);
	      }
	   }

	 
	 public void communicate()
	   {
	     
	     //do{
		 char op= client_gui();
	      char opp=outcomeSwitch(op);
	      String option = String.valueOf(opp);
	      
	      if(option.equals("a")){
	      out.println(option);// sends option to server
	      }else{
	    	  out.println(option);  
	    	  out.println(txtexe);
	    	 
	      }
	
	    
	   }// end of communicate


	private char outcomeSwitch(char op) {
		
		switch(op){
		case 'a':
		case 'A':	
			op='a';
				System.out.println("List of files\n");
				
			break;
		
		case 'b':
		case 'B':	
			op='b';
			Scanner sc = new Scanner(System.in);
			System.out.println("Name of file to download: ex. 0.txt or 1.jpg");
			txtexe = sc.nextLine();
			break;
		
		case 'c':
		case 'C':
			Scanner sc0 =new Scanner(System.in);
			System.out.println("Name of file to remove: ex. 0.txt or 1.jpg ");
			txtexe = sc0.nextLine();
			break;
			
		case 'd':
		case 'D':
			Scanner sc1 =new Scanner(System.in);
			System.out.println("Name of file to add: (make sure to inluce file extension .txt or .jpg)");
			txtexe = sc1.nextLine();
			break;
		
		case 'e':
		case 'E':
				System.out.println("Exiting client ");
				out.println("e"); 
				cclient ="close";
			break;
		
		default: 
			System.out.println("Not an option");
			
		}
	
		return op;
	}

	private static char client_gui() {
			char k = ' ';
			System.out.println("\nClient Menu\n a. Display the names of all files.\n b. Get a particular file.\n"
					+ " c. Remove a file from the list\n d. Add a new file to the server\n e. Exit\n" +
					"Enter in your chouse: ");
				Scanner sc= new Scanner(System.in);
				String L=sc.nextLine();
				 k =L.charAt(0);
			return  k;
	}

}// end of client class
