package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

public class SignIn {
	private JFrame frame;
	private JLabel label, userlabel, passlabel;
	private JTextField usertext;
	private JPasswordField passtext;
	private JButton signinbut, registerbut;
	JCheckBox showpass;
	private String UserName = "tratm";
	private String PassWord = "123456789";
	
	public SignIn(){
		frame = new JFrame("Log In");
		frame.setBounds(370, 70, 550, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		
		URL frameicon = SignIn.class.getResource("busicon.png");
		Image img = Toolkit.getDefaultToolkit().createImage(frameicon);
		frame.setIconImage(img);
		
		label = new JLabel("WELCOME!");
		label.setBounds(200, 55, 300, 30);
		label.setFont(new Font("Serif", Font.BOLD, 25));
		label.setForeground(Color.DARK_GRAY);
		
		frame.add(label);
		
		userlabel = new JLabel("User Name:");
		userlabel.setBounds(90, 130, 130, 30);
		userlabel.setFont(new Font("Arial", Font.PLAIN, 20));
		frame.add(userlabel);
		
		usertext = new JTextField();
		usertext.setBounds(245, 130, 200, 30);
		usertext.setFont(new Font("Arial", Font.PLAIN, 20));
		frame.add(usertext);
		
		passlabel = new JLabel("Password:");
		passlabel.setBounds(90, 180, 130, 30);
		passlabel.setFont(new Font("Arial", Font.PLAIN, 20));
		frame.add(passlabel);
		
		passtext = new JPasswordField();
		passtext.setBounds(245, 180, 200, 30);
		frame.add(passtext);
		
		signinbut = new JButton("Log in");
		signinbut.setBounds(210, 260, 130, 30);
		signinbut.setFont(new Font("Georgia", Font.PLAIN, 20));
		signinbut.setBackground(Color.pink);
		
		signinbut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				authenticate();
				
			}
		});
		
		
		
//		URL signin = SignIn.class.getResource("login.png");
//		Image img_signin = Toolkit.getDefaultToolkit().createImage(signin);
//		signinbut.setIcon(new ImageIcon (img_signin));
		frame.add(signinbut);
		
		
		showpass = new JCheckBox("Show password");
		showpass.setBounds(340, 215, 150, 20);
		showpass.setFont(new Font("Georgia", Font.PLAIN, 12));
		
		showpass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showpass.isSelected()) {
                    passtext.setEchoChar((char) 0); // Hiển thị mật khẩu
                } else {
                    passtext.setEchoChar('*'); // Ẩn mật khẩu
                }
            }
        });
		
		frame.add(showpass);
		
		ImageIcon userIcon = new ImageIcon(getClass().getResource("userIcon.png"));
		JLabel userLabelIcon = new JLabel(userIcon);
		userLabelIcon.setFont(new Font("Serif", Font.BOLD, 35));
		userLabelIcon.setBounds(350, 180, 200, 200);
		
		
		frame.add(userLabelIcon);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	private void authenticate () {
		String username = usertext.getText();
        String password = new String(passtext.getPassword());

        if (UserName.equals(username) && PassWord.equals(password)) {
            // Mở cửa sổ quản lý nếu xác thực thành công
            new BusManager();
            
           frame.dispose(); // Đóng cửa sổ đăng nhập
        } else {
            JOptionPane.showMessageDialog(frame, "Login failed");
        }
	}
	
	
}



