package pack6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ChatServer {
	// 채팅룸
	// 아이디, 출력스트림(인원)
   private HashMap<String, OutputStream> clients;
   
   
   public static void main(String[] args) {
      
      new ChatServer().start();
      
   }
   public ChatServer() {
      // TODO 자동 생성된 생성자 스텁
	   // 인스턴스화
      clients = new HashMap<String, OutputStream>();
      
   }
   
   public void start() {
      // TODO 자동 생성된 메소드 스텁
      ServerSocket serverSocket = null;
      Socket socket = null;
      
      try {
         serverSocket = new ServerSocket(7777);
         System.out.println("서버가 시작되었습니다.");
         
         while(true) {
            socket = serverSocket.accept();
            System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "]" + "에서 접속하였습니다" );
            
            ServerReceiver thread = new ServerReceiver(socket);
            thread.start();
         }
      } catch (IOException e) {
         // TODO 자동 생성된 catch 블록
         e.printStackTrace();
      }
      
      
   }
   
   // 브로드캐스팅
   public void sendToAll(String msg) {
      // TODO 자동 생성된 메소드 스텁
      Iterator<String> it = clients.keySet().iterator();
      
      while(it.hasNext()) {
         
         try {
            DataOutputStream out = (DataOutputStream)clients.get(it.next());
            out.writeUTF(msg);
         } catch (IOException e) {
            // TODO 자동 생성된 catch 블록
            e.printStackTrace();
         }
      }
      
   }
   
   class ServerReceiver extends Thread {
      private Socket socket;
      private DataInputStream in;
      private DataOutputStream out;
      
      public ServerReceiver(Socket socket) {
         // TODO 자동 생성된 생성자 스텁
         this.socket = socket;
         
         try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
         } catch (IOException e) {
            // TODO 자동 생성된 catch 블록
            e.printStackTrace();
         }
         
      }
      
      public void run() {
         String name = "";
         
         try {
            name = in.readUTF();
            sendToAll("#" + name + "님이 들어오셨습니다.");
            // 채팅룸 등록
            clients.put(name, out);
            
            System.out.println("현재 서버접속자 수는 " + clients.size() + "입니다.");
            
            // 채팅
            while(in != null) {
            	String msg = in.readUTF();
            	if(msg.endsWith("exit")) {
            		break;
            	} else {
            		sendToAll(msg);
            	}
               
            }
         } catch (IOException e) {
            // TODO 자동 생성된 catch 블록
            e.printStackTrace();
         } finally {
            sendToAll("#" + name + "님이 나가셨습니다.");
            clients.remove(name);
            System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "]" + "에서 접속을 종료하셨습니다." );
            System.out.println("현재 서버접속자 수는 " + clients.size() + "입니다.");
         }
      }
   }
}