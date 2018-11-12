package cn.kgc.frame;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.kgc.service.Cal;

public class MainFrame {
	
	/**
	 * 初始化计算机界面
	 */
	public void initFrame() {
		
		JFrame mainFrame = new JFrame("计算器");
		mainFrame.setSize(400, 350);
		mainFrame.setLayout(null);
		mainFrame.setLocationRelativeTo(null);
		addWidget2MainFrame(mainFrame);
		
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * 添加控件到mainFrame
	 */
	public void addWidget2MainFrame(JFrame mainFrame) {
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight());
		mainPanel.setLayout(null);
		
		JTextField displayWindowTextField = new JTextField("0");
		displayWindowTextField.setFont(new Font("宋体", Font.BOLD, 25));
		displayWindowTextField.setBounds(10, 10, mainPanel.getWidth()-40, 50);
		displayWindowTextField.setHorizontalAlignment(JTextField.RIGHT);
		
		// 0 ~ 9 的按钮
		JButton[] button = new JButton[10];
		for(int i=0; i<10; i++) {
			button[i] = new JButton(i + "");
		}
		int x = 10;
		int y =70;
		for(int j=1; j<=9; j++) {
			button[j].setBounds(x, y, 60, 50);
			x+=65;
			if(j%3==0) {
				x=10;
				y+=55;
			}
			button[j].setFont(new Font("宋体", Font.BOLD, 25));
			setNoButtonAction(button[j], displayWindowTextField);
		}
		button[0].setBounds(10, 235, 125, 50);
		button[0].setFont(new Font("宋体", Font.BOLD, 25));
		setNoButtonAction(button[0], displayWindowTextField);
		for(int i=0;i<=9;i++)
			mainPanel.add(button[i]);
		JButton pointButton = new JButton(".");
		pointButton.setBounds(140, 235, 60, 50);
		pointButton.setFont(new Font("宋体", Font.BOLD, 25));
		setNoButtonAction(pointButton, displayWindowTextField);
		mainPanel.add(pointButton);
		
		//+-*/按钮
		JButton addButton = new JButton("+");
		addButton.setBounds(205, 70, 60, 50);
		addButton.setFont(new Font("宋体", Font.BOLD, 25));
		setNoButtonAction(addButton, displayWindowTextField);
		
		mainPanel.add(addButton);
		JButton subtractButton = new JButton("-");
		subtractButton.setFont(new Font("宋体", Font.BOLD, 25));
		subtractButton.setBounds(205, 125, 60, 50);
		setNoButtonAction(subtractButton, displayWindowTextField);
		mainPanel.add(subtractButton);
		
		JButton multipleButton = new JButton("*");
		multipleButton.setFont(new Font("宋体", Font.BOLD, 25));
		multipleButton.setBounds(205, 180, 60, 50);
		setNoButtonAction(multipleButton, displayWindowTextField);
		mainPanel.add(multipleButton);
		
		JButton divideButton = new JButton("/");
		divideButton.setFont(new Font("宋体", Font.BOLD, 25));
		divideButton.setBounds(205, 235, 60, 50);
		setNoButtonAction(divideButton, displayWindowTextField);
		mainPanel.add(divideButton);
		
		//归零按钮、等号按钮
		JButton cButton = new JButton("C");
		cButton.setFont(new Font("宋体", Font.BOLD, 25));
		cButton.setBounds(270, 70, 100, 75);
		cButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayWindowTextField.setText("0");
				
			}
		});
		mainPanel.add(cButton);
		JButton equalButton = new JButton("=");
		equalButton.setBounds(270, 150, 100, 80);
		equalButton.setFont(new Font("宋体", Font.BOLD, 25));
		equalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 按等号计算。
				String str = "0+0" + displayWindowTextField.getText() + "=";
				Cal cal = new Cal();
				System.out.println("in...." + str);
				cal.dealwithStr(str);
				displayWindowTextField.setText(cal.number.getLast().toString());
			}
		});
		mainPanel.add(equalButton);
		
		//左右括号
		JButton leftBracket = new JButton("(");
		JButton rightBracket = new JButton(")");
		leftBracket.setBounds(270, 235, 45, 50);
		leftBracket.setFont(new Font("宋体", Font.BOLD, 20));
		leftBracket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = displayWindowTextField.getText();
				if("0".equals(str)) 
					displayWindowTextField.setText("(");
				else
					displayWindowTextField.setText(str + "(");
			}
		});
		rightBracket.setBounds(325, 235, 45, 50);
		rightBracket.setFont(new Font("宋体", Font.BOLD, 20));
		rightBracket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = displayWindowTextField.getText();
				if("0".equals(str)) 
					displayWindowTextField.setText(")");
				else
					displayWindowTextField.setText(str + ")");
			}
		});
		mainPanel.add(leftBracket);
		mainPanel.add(rightBracket);
		
		mainPanel.add(displayWindowTextField);
		mainFrame.add(mainPanel);
	}
	
	/**
	 * 添加按钮事件
	 * @param noButton
	 * @param displayWindowTextField
	 */
	public void setNoButtonAction(JButton noButton,JTextField displayWindowTextField) {
		noButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = displayWindowTextField.getText();
				char noButtonChar = noButton.getText().charAt(0);
				if("0".equals(str) && (noButtonChar>='0' && noButtonChar<='9')) 
					displayWindowTextField.setText(noButton.getText());
				else
					displayWindowTextField.setText(str + noButton.getText());
				
			}
		});
	}
	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.initFrame();
		String str = "0+9+2";
		System.out.println(str.charAt(str.length()-1));
	}
	
}
