package pack3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClientEx01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 아이피, 포트
		Socket socket = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			System.out.println("서버와 연결 중입니다.");
			socket = new Socket("localhost", 7777); // 서버의 아이피 -192.168.0.52 / 포트 - 7777
			System.out.println("서버와 연결되었습니다.");
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")); // utf-8 : 한글 지원
			
			bw.write("안녕 서버" + System.lineSeparator());
			bw.flush();
			System.out.println("전송 완료");
			
			String msg = br.readLine();
			
			System.out.println("메세지 : " + msg);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(br != null) try {br.close();} catch(IOException e) {}
			if(bw != null) try {bw.close();} catch(IOException e) {}
			if(socket != null) try {socket.close();} catch(IOException e) {}
		}
	}

}
