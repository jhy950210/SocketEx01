package pack2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerEx01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 포트번호(각 프로그램 1개 이상 포트와 연관)
		// ServerSocket
		// Address already in use: JVM_Bind - 이미 포트에 서버가 bind 되어있는 상태라는 뜻.
		
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		BufferedWriter bw = null;
		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("서버가 준비되었습니다.");
			// 접속 대기
			socket = serverSocket.accept();
			System.out.println("클라이언트가 연결되었습니다.");
			
//			socket.getInputStream(); // 한글 입력이 안됨 (Buffered 필요)
//			socket.getOutputStream();
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")); // utf-8 : 한글 지원
			bw.write("안녕 클라이언트" + "\n");
			
			System.out.println("전송이 완료되었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(bw != null) try {bw.close();} catch(IOException e) {}
			if(socket != null) try {socket.close();} catch(IOException e) {}
			if(serverSocket != null) try {serverSocket.close();} catch(IOException e) {}
			
		}
	}

}
