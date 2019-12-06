/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorize.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


import managers.ReciteManager;
import managers.ReciteManager.Modes;
import models.Word;
/**
 *
 * @author kailun
 */
public class ReciteUI extends JFrame implements KeyListener, ActionListener {
	private static final long serialVersionUID = -6413333438707390999L;
	
	/* 各种控件 */
	final private JPanel mainPanel;
	final private JLabel lblEnglish;
	final private JLabel lblPhonetic;
	final private JTextArea txtChinese;
	final private JLabel lblStatus;

	final private JMenuBar menuBar = new JMenuBar();
	final private JMenu fileMenu = new JMenu("文件 (F)");
	final private JMenuItem chooseThItem = new JMenuItem("选择词库 (T)");
	final private JMenuItem statItem = new JMenuItem("词汇统计 (S)");
	final private JMenuItem modeItem = new JMenuItem("复习 (R)");
	final private JMenuItem exitItem = new JMenuItem("退出 (X)");

	/* 各种控件 */
	
	private String recordPath = "./record/recite.dat";	// 背诵记录文件路径
	private String strSpelling = "";
	// 标志位，忽略一次Type事件
	private boolean isCorrect = false;
	private boolean isFalse = false;
	private ReciteManager reciteManager;
	

	private String fontPath = "./font";	// 字体路径
	private String phoneticFontName = "TOPhonetic.ttf";
	// private String interpFontName = "STFANGSO.TTF";
	
	private String thesPath = "./thesaurus/TOFEL.txt";	// 默认词库路径
	
	public ReciteUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// 初始化菜单栏
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);	
		
		chooseThItem.setMnemonic('T');
		fileMenu.add(chooseThItem);
		
		statItem.setMnemonic('S');
		fileMenu.add(statItem);
		
		modeItem.setMnemonic('R');
		fileMenu.add(modeItem);
		
		exitItem.setMnemonic('X');
		fileMenu.add(exitItem);
		


		
		chooseThItem.addActionListener(this);
		statItem.addActionListener(this);
		modeItem.addActionListener(this);
		exitItem.addActionListener(this);

		this.setJMenuBar(menuBar);


		lblEnglish = new JLabel();
		lblEnglish.setBackground(Color.WHITE);
		// lblEnglish.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
		lblEnglish.setFont(new Font("Arial", Font.BOLD, 50));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		mainPanel.add(lblEnglish, c);

		lblPhonetic = new JLabel();
		lblPhonetic.setBackground(Color.WHITE);

		// 获取音标字体
		try {
			lblPhonetic.setFont(MyFont.getFont(
					fontPath, phoneticFontName, Font.PLAIN,	20));
		} catch (FontFormatException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0.1;
		mainPanel.add(lblPhonetic, c);

		txtChinese = new JTextArea();
		txtChinese.setLineWrap(true);
		
		txtChinese.setFont(new Font("华文仿宋", Font.PLAIN, 28));

		//txtChinese.setBackground(Color.black);
		//txtChinese.setForeground(Color.white);

		txtChinese.setEditable(false);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 2;
		mainPanel.add(txtChinese, c);

		lblStatus = new JLabel();		// 词库名称 wordManager.thesName
		// lblEnglish.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
		lblStatus.setFont(new Font("宋体", Font.PLAIN, 12));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 0.2;
		mainPanel.add(lblStatus, c);


		txtChinese.setFocusable(false);
		this.addKeyListener(this);
		lblEnglish.addKeyListener(this);
		lblPhonetic.addKeyListener(this);
		txtChinese.addKeyListener(this);

		this.add(mainPanel);
		// 
		this.setTitle("MWS");
		this.setSize(400, 400);
		this.setResizable(false);
		
		ImageIcon icon = new ImageIcon("./res/love.png");
		this.setIconImage(icon.getImage());

		// 使窗口居中
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高
		int height = this.getHeight();
		int width = this.getWidth();
		setLocation(screenWidth - width / 2, screenHeight - height / 2);

		this.setVisible(true);
		
		try {
			reciteManager = new ReciteManager(recordPath);
			reciteManager.setThesaurus(thesPath);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		}
		lblStatus.setText(reciteManager.getThesaurusName());
		nextWord();
	}
	
	public void nextWord() {
		try {
			if ((reciteManager.getReciteMode() == Modes.REVIEW) &&
					(reciteManager.nextWord() == null)
					) {
				// 所有单词已经复习完
				JOptionPane.showMessageDialog(this, "已经没有需要复习的单词了~");
				reciteManager.setReciteMode(Modes.NEW);
				modeItem.setText("复习 (R)");
				modeItem.setMnemonic('R');
			}
			reciteManager.nextWord();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		}
		showWord(reciteManager.getWord());
	}
	
	// 将单词显示在界面上
	public void showWord(Word word) {
		strSpelling = "";
		lblEnglish.setText(" ");
		lblPhonetic.setText(word.phonetic);
		txtChinese.setText(word.interp);
		// lblStatus.setText(wordManager.thesName);
	}
	

	@Override
	public void keyTyped(KeyEvent event) {

		// 当拼写正确时，忽略一次键盘事件，并产生新词
		if (isCorrect) {
			isCorrect = false;
			nextWord();
			return;
		}
		if (isFalse) {
			isFalse = false;
			nextWord();
			return;
		}
		char ch = event.getKeyChar();
		String strWord = reciteManager.getWord().name;
		// 单词长度限制
		if (strSpelling.length() < strWord.length()) {
			// 字母限制
			if (ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch == '-'
				|| ch == '(' || ch == ')') {	//  || ch == ' '
				lblEnglish.setForeground(Color.BLACK);
				strSpelling += ch;
				lblEnglish.setText(strSpelling);
			}
		}

		// 退格键
		if (strSpelling.length() > 0) {
			if (ch == '\b') { // backspace
				strSpelling = strSpelling
				.substring(0, strSpelling.length() - 1);
				if (strSpelling.length() > 0) {
					lblEnglish.setText(strSpelling);
				} else {
					lblEnglish.setText(" "); // set null
				}
			}
		}

		// 回车键
		if (ch == '\n' || ch == ' ') { // space or enter

			if (strSpelling.equals(strWord)) {
				// 当拼写正确时，以蓝色字体显示，并设置isCorrect标志
				strSpelling = ""; //  清空输入
				lblEnglish.setForeground(Color.blue);
				lblEnglish.setText(strWord);
				isCorrect = true;
				
				
				// 生成该单词背诵数据，并写入文件
				try {
					reciteManager.saveReciteRecord();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
					System.exit(-1);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
					System.exit(-1);
				}
				
			} else {
				if (lblEnglish.getForeground() != Color.red) {
					// 陌生度++
					reciteManager.increaseStrange();
				}
				// 当拼写不正确时，以红色字体显示
				strSpelling = "";
				lblEnglish.setForeground(Color.red);
				lblEnglish.setText(strWord);
				isFalse = true;
				//nextWord(); //in showWrong

			}
		}

		// ` 键，跳过该单词
		if (ch == '`') {
			nextWord();
		}
	}
	/*public void showWrong() {
		strSpelling = "";
		lblEnglish.setForeground(Color.red);
		lblEnglish.setText(strWord);
	}*/
	public void chooseThesaurus() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("./thesaurus"));
		chooser.setDialogTitle("选择词库");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			thesPath = chooser.getSelectedFile().getPath();
			// thesName = chooser.getSelectedFile().getName();
			try {
				reciteManager.setThesaurus(thesPath);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				System.exit(-1);
			}
			lblStatus.setText(reciteManager.getThesaurusName());
			
			reciteManager.setReciteMode(ReciteManager.Modes.NEW);
			modeItem.setText("复习 (R)");
			modeItem.setMnemonic('R');
			nextWord();
		} else {
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == chooseThItem) {
			chooseThesaurus();
		} 
		else if (event.getSource() == statItem) {
			new ReciteStatUI(recordPath);
		}
		else if (event.getSource() == modeItem) {
			if (reciteManager.getReciteMode() == ReciteManager.Modes.REVIEW) {
				reciteManager.setReciteMode(ReciteManager.Modes.NEW);
				modeItem.setText("复习 (R)");
				modeItem.setMnemonic('R');
				nextWord();
			}
			else if (reciteManager.getReciteMode() == ReciteManager.Modes.NEW) {
				reciteManager.setReciteMode(ReciteManager.Modes.REVIEW);
				modeItem.setText("学习 (N)");
				modeItem.setMnemonic('N');
				nextWord();
			}
		}
		else if (event.getSource() == exitItem){
			System.exit(0);
		}

	}

	@Override
	public void keyPressed(KeyEvent event) { }

	@Override
	public void keyReleased(KeyEvent event) { }
}
