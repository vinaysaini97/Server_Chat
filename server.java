import java.io.*;
import java.util.*;
import java.net.*;
//starting class 
class Server{
	public static ServerSocket ss;
	public static Socket s;
	public static Check check;
	public static HashMap<String,ArrayList<String>> friends;
	public static HashMap<String,String> nameandpass;
    public static void main(String args[])throws Exception
	{
         
         //intitalize HashMap
            friends=new HashMap<String,ArrayList<String>>();
            nameandpass=new HashMap<String,String>();

		try{ss=new ServerSocket(9090);}
		catch(Exception e){System.out.println("Port Already Used");}
		while(true)
		{
			try{s=ss.accept();}
			catch(Exception e){System.out.println("Socket is Not Accepting");}
			System.out.println("New Connection Established");
            check=new Check(s);
            check.valid(); 
		}

	}
}

//end class







class Check{
	public static PrintStream pw;
	public static BufferedReader br;
	public Socket s;
	public static Server serve;



	public Check(Socket s){
		this.s=s;
		try{
			serve=new Server();
			pw=new PrintStream(s.getOutputStream());//Output Stream
		    br=new BufferedReader(new InputStreamReader(s.getInputStream()));//Input Stream
           }
		catch(Exception e){System.out.println("PrintStream is Not Working");}
	}



//Validation of User and Check if user already exist or not 
	public void valid()throws Exception
	{ 
		String login=br.readLine();
		boolean alertlogin=true;
		while(alertlogin)
		{
			if(login.equalsIgnoreCase("register"))//if Client select register
			{
			String name=br.readLine();
            
           if(serve.nameandpass.containsKey(name))
		   {
		   	//Push Notification
		   	System.out.println("Already Exist Try Another One");
		   }	
		   else
		   {
		   	//Successfull Added
		   	String pass=br.readLine();
		   	serve.nameandpass.put(name,pass);
		   	
		   // Push Notification 
		   	System.out.println("Succesfull Register");
		   }
			}


			else if(login.equalsIgnoreCase("LogIn"))//if client select old user
			{
			boolean validuser=true;
			boolean validpass=true;
			//Old User Chat Page
			while(validuser)
			{
			    String name=br.readLine();
			    String pass=br.readLine();

				if(serve.nameandpass.containsKey(name))
					{   
						validuser=false;
                        while(validpass){
										if(serve.nameandpass.get(name).equals(pass))
											{
												validpass=false;
                                                alertlogin=false;
												System.out.println("Welcome Again");
												Message msg=new Message(s);
												msg.run();


												//Class Third Initate
												//Give A new Thread 
											}
										else
											{
												System.out.println("Enter Password Again");
											}
						                 }
			        }
			     else
			     {
			     	System.out.println("Not a valid username or not found in database");
			     }
		   }
			}

        }

	}
}



class Message extends Thread{
	public Socket sock;
	public String send;
	public String receive;
	public PrintStream ps;
	public BufferedReader br;
	public Server serve;
	public Message(Socket sock){
		this.sock=sock;
		ps=new PrintStream(sock.getOutputStream());//Output Stream
		br=new BufferedReader(new InputStreamReader(sock.getInputStream()));//Input Stream
		serve=new Server();
	}
	public void start(){



	}

}