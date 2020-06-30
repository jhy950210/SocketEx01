package pack6;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ChatGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField1;
	private JTextArea textArea;
	private JTextField textField2;
	private JButton btn3;
	private DataOutputStream dos = null;
	private Socket socket;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatGUI frame = new ChatGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChatGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField1 = new JTextField();
		textField1.setText("아이디 입력");
		textField1.setBounds(12, 10, 337, 21);
		contentPane.add(textField1);
		textField1.setColumns(10);
		
		JButton btn1 = new JButton("연결");
		btn1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				try {
					socket = new Socket("localhost", 7777);
					dos = new DataOutputStream( socket.getOutputStream() );
					
					Thread receiver = new Thread( new ClientReceiver( socket ) );
					receiver.start();
					
					dos.writeUTF(textField1.getText());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		btn1.setBounds(361, 9, 97, 23);
		contentPane.add(btn1);
		
		JButton btn2 = new JButton("전송");
		btn2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					dos.writeUTF( "[" + textField1.getText() + "]" + textField2.getText() );
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn2.setBounds(361, 42, 97, 23);
		contentPane.add(btn2);
		
		textArea = new JTextArea();
		textArea.setBounds(12, 75, 446, 371);
		contentPane.add(textArea);
		
		textField2 = new JTextField();
		textField2.setColumns(10);
		textField2.setBounds(12, 41, 337, 21);
		contentPane.add(textField2);
		
		btn3 = new JButton("종료");
		btn3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChatGUI.this.dispose();
			}
		});
		btn3.setBounds(361, 456, 97, 23);
		contentPane.add(btn3);
	}
	
	class ClientReceiver extends Thread {
		private Socket socket;
		private DataInputStream dos;
		
		public ClientReceiver(Socket socket) {
			this.socket = socket ;
			try {
				dos = new DataInputStream( socket.getInputStream() );
			} catch(IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
			while(dos != null) {
				try {
					ChatGUI.this.textArea.append( dos.readUTF() + "\n");
				} catch(IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
