package pack4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClientEx01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 아이피, 포트
		Socket socket = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			// System.in
			// 보낼 내용 > 문자열
			// 1.
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("보낼 메세지 입력 : ");
			String msg = br.readLine();
			
			// 2.
//			Scanner scan = new Scanner(System.in);
//			System.out.print("단 입력 : ");
//			String msg = scan.nextLine();
//			
			System.out.println("서버와 연결 중입니다.");
			socket = new Socket("localhost", 7777); // 서버의 아이피 -192.168.0.52 / 포트 - 7777
			System.out.println("서버와 연결되었습니다.");
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")); // utf-8 : 한글 지원
			
			bw.write(msg + System.lineSeparator());
			bw.flush();
			System.out.println("전송 완료");
			
			String returnMsg = null;
			while((returnMsg=br.readLine()) != null) {
				System.out.println("메세지 : " +returnMsg);
			}
			
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
