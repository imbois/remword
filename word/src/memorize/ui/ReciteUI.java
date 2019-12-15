/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorize.ui;

import java.awt.*;
import java.util.Random;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.jws.WebParam;
import javax.swing.*;


import managers.ReciteManager;
import managers.ReciteManager.Modes;
import managers.WordManager;
import models.Word;
/**
 *
 * @author kailun
 */
public class ReciteUI extends JFrame implements KeyListener, ActionListener {
	private static final long serialVersionUID = -6413333438707390999L;
	
	/* 各种控件 */
	final private JPanel mainPanel;
	final private JLabel lblEnglish = new JLabel();
	final private JLabel lblPhonetic = new JLabel();
	final private JTextArea txtChinese = new JTextArea();
	final private JButton SEL1 = new JButton("选择模式"); // select learn mode
	final private JButton SEL2 = new JButton("输入模式");
	final private JButton CH1 = new JButton();
	final private JButton CH2 = new JButton();
	final private JButton CH4 = new JButton();
	final private JButton CH3 = new JButton();
	final private JButton Next = new JButton();
	final private JLabel lblStatus = new JLabel();

	final private JMenuBar menuBar = new JMenuBar();
	final private JMenu fileMenu = new JMenu("文件 (F)");
	final private JMenu CountMenu = new JMenu("统计 (C)");
	final private JMenuItem chooseThItem = new JMenuItem("选择词库 (T)");
	final private JMenuItem statItem = new JMenuItem("词汇统计 (S)");
	final private JMenuItem modeItem = new JMenuItem("复习 (R)");
	final private JMenuItem exitItem = new JMenuItem("退出 (X)");
	final private JMenu LearnMenu = new JMenu("模式 (M)");
	final private JMenuItem LearnItem = new JMenuItem("输入模式");
	/* 各种控件 */
	
	private String recordPath = "./record/recite.dat";	// 背诵记录文件路径
	private String strSpelling = "";
	// 标志位，忽略一次Type事件
	private boolean isCorrect = false;
	private boolean isFalse = false;
	private boolean chooseCorrect = false;
	private ReciteManager reciteManager;
	private WordManager wordManager;
	private JButton[] Bvector = {CH1,CH2,CH3,CH4};
	private JButton Answer;

	private String fontPath = "./font";	// 字体路径
	private String phoneticFontName = "TOPhonetic.ttf";
	// private String interpFontName = "STFANGSO.TTF";
	private Random random = new Random(System.currentTimeMillis());
	private String thesPath = "./thesaurus/TOFEL.txt";	// 默认词库路径
	public ReciteUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// 初始化菜单栏
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);

		LearnMenu.setMnemonic('M');
		menuBar.add(LearnMenu);

		CountMenu.setMnemonic('C');
		menuBar.add(CountMenu);

		LearnMenu.add(LearnItem);

		chooseThItem.setMnemonic('T');
		fileMenu.add(chooseThItem);
		
		statItem.setMnemonic('S');
		CountMenu.add(statItem);
		
		modeItem.setMnemonic('R');
		fileMenu.add(modeItem);
		
		exitItem.setMnemonic('X');
		fileMenu.add(exitItem);




		LearnItem.addActionListener(this);
		chooseThItem.addActionListener(this);
		statItem.addActionListener(this);
		modeItem.addActionListener(this);
		exitItem.addActionListener(this);
		SEL1.addActionListener(this);
		SEL2.addActionListener(this);
		Next.addActionListener(this);
		txtChinese.setFocusable(false);
		this.addKeyListener(this);
		lblEnglish.addKeyListener(this);
		this.setJMenuBar(menuBar);
		for(int i = 0; i < 4; i++){
			Bvector[i].addActionListener(this);
		}

		{
			SEL1.setHorizontalAlignment(JButton.CENTER);
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 5;
			c.weightx = 1;
			c.weighty = 1;
			mainPanel.add(SEL1, c);

			SEL2.setHorizontalAlignment(JButton.CENTER);
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 1;
			mainPanel.add(SEL2, c);
		}
		//SEL1.removeActionListener(this);
		//SEL2.removeActionListener(this);

		try {
			reciteManager = new ReciteManager(recordPath);
			reciteManager.setThesaurus(thesPath);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		}


		showWindow();
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
			if(reciteManager.getLearnMode() == ReciteManager.Learn_Modes.CHOOSE) {

				int index = random.nextInt(4);
                String interp = new String();;
				for (int i = 0; i < 4; i++) {
					if (i == index) {
						Bvector[index].setText(reciteManager.getWord().interp);
						Answer = Bvector[index];
						Answer.setForeground(Color.black);
					}
                    else{
                        boolean chooseAgain = true;
                        int j;
                        while(chooseAgain) {
                            interp = reciteManager.getWordManager().getRandomWord().interp;
                            if(interp == reciteManager.getWord().interp) // 随机与正确结果相同
                                continue;
                            for (j = 0; j < i; j++) {
                                if (Bvector[j].getText().equals(interp) ) // 与之前单词存在相同释义
                                {

                                    break;
                                }
                            }
                            if(i != j && j != 0) // j = 0 第一个单词不用重选
                                chooseAgain = true;
                            else
                                chooseAgain = false;
                        }
                        Bvector[i].setText(interp);

                        Bvector[i].setForeground(Color.black);
                    }
                    //System.out.println("index="+ index);
                    //System.out.println("interp:"+ interp);
                    //System.out.println("answer:" + reciteManager.getWord().interp);
                    //System.out.println("final:" + Bvector[i].getText());
				} // end of for
            }// end of if(reciteManager.getLearnMode() == ReciteManager.Learn_Modes.CHOOSE)
		} // end of try
		catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		}
		showWord(reciteManager.getWord());
	}
	
	// 将单词音标释义显示在界面上
	public void showWord(Word word) {
		strSpelling = "";
		if(reciteManager.getLearnMode() == ReciteManager.Learn_Modes.INPUT) // INPUT_MODE 不显示单词 需要输入
		{
			lblEnglish.setText(" ");
			lblPhonetic.setText(word.phonetic);
			txtChinese.setText(word.interp);
		}
		else if(reciteManager.getLearnMode() == ReciteManager.Learn_Modes.CHOOSE){
			lblEnglish.setText(word.name);
			lblEnglish.setForeground(Color.BLACK);
			lblPhonetic.setText(word.phonetic);
			txtChinese.setText(word.interp);
		}
		// lblStatus.setText(wordManager.thesName);
	}
	

	@Override
	public void keyTyped(KeyEvent event) {

		// 当拼写正确时，忽略一次键盘事件，并产生新词
		if (isCorrect) {
			isCorrect = false;
			showDetail();
			//nextWord();
			return;
		}
		if (isFalse) {
			isFalse = false;
			showDetail();
			//nextWord();
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

    public void showDetail(){
	    mainPanel.removeAll();
	    mainPanel.repaint();

		GridBagConstraints c = new GridBagConstraints();
		lblEnglish.setOpaque(true); // 设置不透明
		lblEnglish.setBackground(Color.yellow);
		// lblEnglish.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
		lblEnglish.setFont(new Font("Arial", Font.BOLD, 50));
		lblEnglish.setHorizontalAlignment(JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 1;
		//c.gridwidth = 1;
		//c.gridheight = 2;
		c.weightx = 1;

		mainPanel.add(lblEnglish, c);
		lblPhonetic.setOpaque(true);
		lblPhonetic.setBackground(Color.cyan);
		// 设置居中
		lblPhonetic.setHorizontalAlignment(JLabel.CENTER); /// ***************
		// 获取音标字体
		try {
			lblPhonetic.setFont(MyFont.getFont(
					fontPath, phoneticFontName, Font.PLAIN, 20));
		} catch (FontFormatException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(-1);
		}
		c.fill = GridBagConstraints.BOTH;
		//c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 2;

		c.weightx = 0;
		c.weighty = 0.5;
		mainPanel.add(lblPhonetic, c);


		txtChinese.setLineWrap(true);

		txtChinese.setFont(new Font("华文仿宋", Font.PLAIN, 28));

		//txtChinese.setBackground(Color.black);
		//txtChinese.setForeground(Color.white);

		txtChinese.setEditable(false);
		//txtChinese.setAlignmentX(JTextArea.CENTER_ALIGNMENT);  // 没用
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1;
		c.weighty = 2;
		mainPanel.add(txtChinese, c);

		Next.setText("Next");
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 1;
		c.gridwidth = 4;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add(Next, c);
		// 词库名称 wordManager.thesName
		// lblEnglish.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
		lblStatus.setFont(new Font("宋体", Font.PLAIN, 12));
		lblStatus.setText(reciteManager.getThesaurusName());
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 0.2;
		mainPanel.add(lblStatus, c);

		lblEnglish.setText(reciteManager.getWord().name);
		lblEnglish.setForeground(Color.BLACK);
		lblPhonetic.setText(reciteManager.getWord().phonetic);
		txtChinese.setText(reciteManager.getWord().interp);
		System.out.println(reciteManager.getWord().phonetic);
		System.out.println(reciteManager.getWord().interp);
		mainPanel.updateUI();
		//nextWord();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (chooseCorrect) {
			chooseCorrect = false;
			showDetail();
			return;
		}
		if (event.getSource() == chooseThItem) {
			chooseThesaurus();
		}
		else if (event.getSource() == LearnItem) {
			if (reciteManager.getLearnMode() == ReciteManager.Learn_Modes.INPUT){
				reciteManager.setLearnMode(ReciteManager.Learn_Modes.CHOOSE);
				LearnItem.setText("输入模式");
				updateUI();
				nextWord();
			}
			else if(reciteManager.getLearnMode() == ReciteManager.Learn_Modes.CHOOSE){
				reciteManager.setLearnMode(ReciteManager.Learn_Modes.INPUT);
				LearnItem.setText("选择模式");
				updateUI();
				nextWord();
			}
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
		else if(event.getSource() == SEL1){
			reciteManager.setLearnMode(ReciteManager.Learn_Modes.CHOOSE);
			LearnItem.setText("输入模式");
			updateUI();
			nextWord();
		}
		else if(event.getSource() == SEL2){
			reciteManager.setLearnMode(ReciteManager.Learn_Modes.INPUT);
			LearnItem.setText("选择模式");
			updateUI();
			nextWord();
		}
		else if (event.getSource() == exitItem){
			System.exit(0);
		}
		else if (event.getSource() == Next){

			updateUI();
			nextWord();
		}
		else if (event.getSource() == Answer){
			Answer.setForeground(Color.blue);
			chooseCorrect = true;
		}
		else if (event.getSource() != Answer){
			for(int i = 0; i < 4; i++){
				if(event.getSource() == Bvector[i]){
					Bvector[i].setForeground(Color.red);
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent event) { }

	@Override
	public void keyReleased(KeyEvent event) { }

	public void showWindow(){
		this.add(mainPanel);
		//
		this.setTitle("MWS");
		this.setSize(400, 400);
		this.setResizable(true);

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
	}
	public void updateUI() {
		mainPanel.removeAll();
		mainPanel.repaint();

		GridBagConstraints c = new GridBagConstraints();
		if(reciteManager.getLearnMode() == ReciteManager.Learn_Modes.INPUT) {
    		// 以下为输入英文背词界面
			// font
			lblEnglish.setOpaque(true); // 设置不透明
			lblEnglish.setBackground(Color.yellow);
			// lblEnglish.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
			lblEnglish.setFont(new Font("Arial", Font.BOLD, 50));
			lblEnglish.setHorizontalAlignment(JLabel.CENTER);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 1;
			//c.gridwidth = 1;
			//c.gridheight = 2;
			c.weightx = 1;

			mainPanel.add(lblEnglish, c);
			lblPhonetic.setOpaque(true);
			lblPhonetic.setBackground(Color.cyan);
			// 设置居中
			lblPhonetic.setHorizontalAlignment(JLabel.CENTER); /// ***************
			// 获取音标字体
			try {
				lblPhonetic.setFont(MyFont.getFont(
						fontPath, phoneticFontName, Font.PLAIN, 20));
			} catch (FontFormatException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				System.exit(-1);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				System.exit(-1);
			}
			c.fill = GridBagConstraints.BOTH;
			//c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 2;

			c.weightx = 0;
			c.weighty = 0.5;
			mainPanel.add(lblPhonetic, c);


			txtChinese.setLineWrap(true);

			txtChinese.setFont(new Font("华文仿宋", Font.PLAIN, 28));

			//txtChinese.setBackground(Color.black);
			//txtChinese.setForeground(Color.white);

			txtChinese.setEditable(false);
			//txtChinese.setAlignmentX(JTextArea.CENTER_ALIGNMENT);  // 没用
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 2;
			mainPanel.add(txtChinese, c);

			// 词库名称 wordManager.thesName
			// lblEnglish.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
			lblStatus.setFont(new Font("宋体", Font.PLAIN, 12));
			lblStatus.setText(reciteManager.getThesaurusName());
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 4;
			c.weighty = 0.2;
			mainPanel.add(lblStatus, c);

			// 需要重新获取焦点使keytype有效
			requestFocus(true);


			nextWord();
		}
		else if(reciteManager.getLearnMode() == ReciteManager.Learn_Modes.CHOOSE){
			lblEnglish.setOpaque(true); // 设置不透明
			lblEnglish.setBackground(Color.yellow);
			// lblEnglish.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
			lblEnglish.setFont(new Font("Arial", Font.BOLD, 50));
			lblEnglish.setHorizontalAlignment(JLabel.CENTER);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 2;
			//c.gridheight = 2;
			c.weightx = 1;
			mainPanel.add(lblEnglish, c);

			/*lblPhonetic.setOpaque(true);
			lblPhonetic.setBackground(Color.cyan);
			// 设置居中
			lblPhonetic.setHorizontalAlignment(JLabel.CENTER); /// ***************
			// 获取音标字体
			try {
				lblPhonetic.setFont(MyFont.getFont(
						fontPath, phoneticFontName, Font.PLAIN, 20));
			} catch (FontFormatException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				System.exit(-1);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				System.exit(-1);
			}
			c.fill = GridBagConstraints.BOTH;
			//c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 2;

			c.weightx = 0;
			c.weighty = 0.5;
			mainPanel.add(lblPhonetic, c);*/
			lblPhonetic.setOpaque(true);
			lblPhonetic.setBackground(Color.cyan);
			// 设置居中
			lblPhonetic.setHorizontalAlignment(JLabel.CENTER); /// ***************
			// 获取音标字体
			try {
				lblPhonetic.setFont(MyFont.getFont(
						fontPath, phoneticFontName, Font.PLAIN, 20));
			} catch (FontFormatException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				System.exit(-1);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				System.exit(-1);
			}
			c.fill = GridBagConstraints.BOTH;
			//c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 2;

			c.weightx = 0;
			c.weighty = 0.5;
			//mainPanel.add(lblPhonetic, c);


			txtChinese.setLineWrap(true);

			txtChinese.setFont(new Font("华文仿宋", Font.PLAIN, 28));

			//txtChinese.setBackground(Color.black);
			//txtChinese.setForeground(Color.white);

			txtChinese.setEditable(false);
			//txtChinese.setAlignmentX(JTextArea.CENTER_ALIGNMENT);  // 没用
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 2;
			//mainPanel.add(txtChinese, c);

			// 词库名称 wordManager.thesName
			// lblEnglish.setFont(new Font("Bradley Hand ITC", Font.BOLD, 50));
			lblStatus.setFont(new Font("宋体", Font.PLAIN, 12));
			lblStatus.setText(reciteManager.getThesaurusName());
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 4;
			c.weighty = 0.2;
			//mainPanel.add(lblStatus, c);

			for(int i = 0; i < 4; i++){
				Bvector[i].setHorizontalAlignment(JButton.CENTER);
				Bvector[i].setBackground(Color.white);
				//Bvector[i].setMargin(new java.awt.Insets(0,0,0,0));
				c.fill = GridBagConstraints.BOTH;
				c.anchor = GridBagConstraints.CENTER;
				c.gridx = 0;
				c.gridy = 2+i;
				c.gridheight = 1;
				c.weightx = 1;
				c.weighty = 1;
				mainPanel.add(Bvector[i], c);
				//System.out.println(Bvector[i].getText() == ""?"null":Bvector[i].getText());
			}
			requestFocus(true);
            //nextWord();

		}
	}
}

