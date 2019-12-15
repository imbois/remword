/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.io.FileNotFoundException;
import java.io.IOException;

import models.ReciteRecord;
import models.Word;

/**
 *
 * @author kailun
 */
public class ReciteManager {
    public enum Modes {NEW, REVIEW};
	public enum Learn_Modes {INPUT, CHOOSE};
	private Modes reciteMode = Modes.NEW;
	private Learn_Modes LearnMode = Learn_Modes.INPUT;
	private Word currentWord;
	private int strange = 0;
	
	private String thesName;
	private WordManager wordManager;
	private ReciteRecordManager reciteRecordManager;
	
	public ReciteManager(String recordPath) throws IOException {
		reciteRecordManager = new ReciteRecordManager(recordPath);
	}

	// 陌生度++
	public int increaseStrange() {
		return ++strange;
	}
	
	// 陌生度清零
	public void clearStrange() {
		strange = 0;
	}

	public WordManager getWordManager() {return wordManager; }
	// 返回词库名
	public String getThesaurusName() {
		return thesName;
	}
	
	// 返回记忆模式
	public Modes getReciteMode() {
		return reciteMode;
	}

	public Learn_Modes getLearnMode() { return LearnMode; }
	// 设置记忆模式
	public void setReciteMode(Modes mode) {
		reciteMode = mode;
	}

	public void setLearnMode(Learn_Modes mode){
		LearnMode = mode;
	}
	// 设置词库
	public void setThesaurus(String thesPath) throws IOException {
		wordManager = new WordManager(thesPath);
		thesName = wordManager.getThesaurusName();
	}
	
	public Word getWord() {
		//System.out.println("reciteManager.getWord():" + currentWord.interp);
		return currentWord;
	}
	
	public Word nextWord() throws IOException {

		switch (reciteMode) {
		case NEW:
			do {
				currentWord = wordManager.getRandomWord();
				//System.out.println("ReciteManager.nextWord:" + currentWord.interp);
			}
			while (currentWord == null || reciteRecordManager.getReciteRecords().contains(currentWord)); // until find a new word not in reciterecords
			break;
		case REVIEW:
			do {
				currentWord = reciteRecordManager.getRandomRecord();
			}
			while (reciteRecordManager.getReciteRecords().contains(currentWord));
			break;
		}
		
		strange = 0; // 陌生度置0
		return currentWord;
	}
	
	public void saveReciteRecord() throws FileNotFoundException, IOException {
		switch (reciteMode) {
		case REVIEW :
			for (ReciteRecord r : reciteRecordManager.getReciteRecords()) {
				if (currentWord.name.equals(r.word)) {
					ReciteRecord reciteRecord = new ReciteRecord(
							r.word,
							r.startTime, 
							System.currentTimeMillis(),
							r.stage + 1, // 复习次数
							r.strange + strange);
					 reciteRecordManager.saveReciteRecord(reciteRecord);
					 break;
				}
			}
			break;
		case NEW:
			ReciteRecord reciteRecord = new ReciteRecord(
					currentWord.name, 
					System.currentTimeMillis(), 	// 首次记忆时间
					System.currentTimeMillis(), 	// 上次记忆时间
					0, 								// 阶段
					strange							// 陌生度
					);
			reciteRecordManager.saveReciteRecord(reciteRecord);
			break;
		}
	} 
}
