package pack6;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		 if(args.length != 1) {
//			 System.out.println("USAGE: java ChatClient 대화명");
//			 System.exit(0);
//		 }
		
//		BufferedReader br = null;
		 
		 try {
			 Socket socket = new Socket("localhost", 7777);
			
			 Scanner scan = new Scanner(System.in);
			 System.out.print("대화명 > ");
			
			 String name = scan.nextLine();
			 System.out.println("서버에 연결되었습니다."); 
			 
			 // 보내는 스레드
			 Thread sender = new Thread(new ClientSender(socket, name));     
			 // 받는 스레드
			 Thread receiver = new Thread(new ClientReceiver(socket)); 

			 sender.start();           
			 receiver.start();        
			 } catch(ConnectException e) {       
        	 // TODO Auto-generated catch block         
        	 e.printStackTrace();     
        	 } catch(IOException e) {          
        		 // TODO Auto-generated catch block    
        		 e.printStackTrace();             
       }   

	}
	
	 static class ClientSender extends Thread {          
		 private Socket socket;
		 private DataOutputStream out;
		 private String name;
		 

		 public ClientSender(Socket socket, String name) {                
			 this.socket = socket;                
			 try {                     
				 out = new DataOutputStream(socket.getOutputStream());                     
				 this.name = name;
			 } catch(IOException e) {                     
				 // TODO Auto-generated catch block                     
				 e.printStackTrace();                             
			 } 
		 }	
	
	 

		 public void run() {                
			 BufferedReader br = null;                
			 try {                     
				 br = new BufferedReader(new InputStreamReader(System.in));                     
				 if(out != null) {                          
					 out.writeUTF(name);                     
			 }
			 
			 while(out != null) {
				 out.writeUTF( "[" + name + "]" + br.readLine());                     
			 }                
			 } catch(IOException e) {                     
				 // TODO Auto-generated catch block                     
				 e.printStackTrace();                
			 } finally {                     
				 if (br != null) try { br.close(); } catch(IOException e) {}                
			 }           
		 }
}
	 
	 static class ClientReceiver extends Thread {           
		 private Socket socket;           
		 private DataInputStream in; 
	 
	 
		 public ClientReceiver(Socket socket) {                
			 this.socket = socket ;                
			 try {                     
				 in = new DataInputStream(socket.getInputStream()); 

			 } catch(IOException e) {                     
				 // TODO Auto-generated catch block                     
				 e.printStackTrace();                             
			 }           
		 }
		 public void run() {                
			 while(in != null) {                     
				 try {                          
					 System.out.println(in .readUTF());
				 } catch(IOException e) {                          
					 // TODO Auto-generated catch block     
					 e.printStackTrace();                                      
				}                
			}   
		}
	}
}
		 
			 
