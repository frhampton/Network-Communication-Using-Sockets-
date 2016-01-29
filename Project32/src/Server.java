import java.io.*;
import java.net.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Scanner;
import java.nio.file.StandardCopyOption;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.concurrent.Semaphore;





class ClientWorker implements Runnable 
{
   private Socket client;
   
   ClientWorker(Socket client) 
   {
      this.client = client;
   }

   public void run()
   {
      String line;
      String txtexe = null;
      BufferedReader in = null;
      PrintWriter out = null;
      try 
      {
	 in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	 out = new PrintWriter(client.getOutputStream(), true);
      } 
      catch (IOException e) 
      {
	 System.out.println("in or out failed");
	 System.exit(-1);
      }

      
   
      try 
      {
	 
	 line = in.readLine();// text from client
	 if(!line.equals("a")){
		 txtexe =in.readLine();
	 }
	 
	 char topchose = line.charAt(0);
	 switch(topchose){
	 case 'a':
		 final File folder = new File("C:/Project32");
		 read_files(folder);
		 break;
	 case 'b':
		 copy_file(txtexe);
	
		 break;
	 
	 case 'c':
		 del_file(txtexe);
		 break;
		 
	 case 'd':
		 add_file(txtexe);
		 System.out.println(txtexe +" file was added");
		 break;
	 default:
	 }
	 
	
      } 
      catch (IOException e) 
      {
	 System.out.println("Read failed");
	 System.exit(-1);
      }
      
      
      try 
      {
	 client.close();
      } 
      catch (IOException e) 
      {
	 System.out.println("Close failed");
	 System.exit(-1);
      }
   }


   private static void read_files(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            read_files(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
		System.out.println("\n\n");
	}

	private static void copy_file(String c) throws IOException {
		Path FROM = Paths.get("C://Project32//" + c);
		Path TO = Paths.get("C://TempDirectory5//" +c);
		
		//overwrite existing file, if exists
		CopyOption[] options = new CopyOption[]{
				 StandardCopyOption.REPLACE_EXISTING,
			     StandardCopyOption.COPY_ATTRIBUTES
		};
		
		Files.copy(FROM,TO,options);
		String pathname ="C://TempDirectory5//" + c;
		File f = new File(pathname);
		
		if(f.exists()){
			System.out.println("download of " +f +" complate");
		}else{
			System.out.println("downloading error");
		}
	}

	private static void del_file(String x) {
		String pathname ="C://Project32//" + x;
		File file = new File (pathname);
		
			if(file.delete()){
				//System.out.println(file.getAbsolutePath());
					file.delete();
					System.out.println(file.getName() + " is deleted!");
			} else{
				System.out.println("Delete operation failed");
			}
	
	}// end of del_file

	private static void add_file(String x) {
		
		try {
			PrintWriter writer1 = new PrintWriter( x,"UTF-8"); // add new file
			writer1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

}
	
	
}// end of worker class






public class Server {

	 static ServerSocket ss = null;
	
	public static void main(String[] args) {
		
		// The set up 
		new File ("C://TempDirectory5").mkdir();// makes a folder to hold the files in C://TempDirectory5
		System.out.println("TempDirectory5 fold made in C://TempDirectory5");
		simulated_file_list();
		
		//End of set up
		if (args.length != 1)
	      {
	         System.out.println("Usage: java Server port");
		 System.exit(1);
	      }

		Server ss = new Server();
		int port = Integer.valueOf(args[0]);
		Server.listenSocket(port);
	
	
	
	}// end of main class

	
	private static void listenSocket(int port) {
		try
	      {
		
		ss = new ServerSocket(port); 
		 System.out.println("Server running on port " + port + "," + " use ctrl-C to end");
	      } 
	      catch (IOException e) 
	      {
		 System.out.println("Error creating socket");
		 System.exit(-1);
	      }
	      while(true)
	      {
	         ClientWorker w;
	         try
	         {
	            w = new ClientWorker(ss.accept());
	            Thread t = new Thread(w);
	            t.start();
		 } 
		 catch (IOException e) 
		 {
		    System.out.println("Accept failed");
		    System.exit(-1);
	         }
	      }
		
	}// End of listenSocket
	
	protected void finalize()
	   {
	      try
	      {
	         ss.close();
	      } 
	      catch (IOException e) 
	      {
	         System.out.println("Could not close socket");
	         System.exit(-1);
	      }
	   }//finalize end of

	
	private static void simulated_file_list() {
		try {
			for(int i=0; i<5;i++){
				String text = Integer.toString(i);
			PrintWriter writer = new PrintWriter(text+".txt","UTF-8"); // makes text files
			PrintWriter writer1 = new PrintWriter( text+".jpg","UTF-8"); // make jpg files
			writer.close();
			writer1.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Simulated files are made");
	}



}// end of server class
