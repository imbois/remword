/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Vector;

import models.Ebbinghaus;
import models.ReciteRecord;
import models.Word;
/**
 *
 * @author kailun
 */
public class ReciteRecordManager {
    private Vector<ReciteRecord> reciteRecords = new Vector<ReciteRecord>();
	private String recordPath;
	
	public ReciteRecordManager(String recordPath) throws IOException {
		this.recordPath = recordPath;
		loadReciteRecords();
	}
	
	public void saveAllReciteRecords() throws FileNotFoundException, IOException {
		ObjectOutputStream outputStream = 
			new ObjectOutputStream(
					new FileOutputStream(recordPath));

		for (ReciteRecord r : reciteRecords) {
			outputStream.writeUTF(r.word);
			outputStream.writeLong(r.startTime);
			outputStream.writeLong(r.lastTime);
			outputStream.writeInt(r.stage);
			outputStream.writeInt(r.strange);	
		}
		outputStream.close();
	}
	
	private void loadReciteRecords() throws IOException {
		try {
			ObjectInputStream inputStream = 
					new ObjectInputStream(
							new FileInputStream(recordPath));
			while (true) {
				ReciteRecord temp = new ReciteRecord();
				try {
					temp.word = inputStream.readUTF();
					temp.startTime = inputStream.readLong();
					temp.lastTime = inputStream.readLong();
					temp.stage = inputStream.readInt();
					temp.strange = inputStream.readInt();	
				}
				catch (EOFException e) {
					break;
				}
				this.reciteRecords.addElement(temp);
			}
			
			inputStream.close();
		}
		catch (FileNotFoundException e){
			return;
		}
	}
	
	public Vector<ReciteRecord> getReciteRecords() {
		return reciteRecords;
	}
	
	// 保存单条记录
	public boolean saveReciteRecord(ReciteRecord record) throws FileNotFoundException, IOException {
		// 如果单词已存在于背诵记录中，则更新
		int index = -1;
		for (ReciteRecord r : reciteRecords) {
			if (record.word.equals(r.word)) {
				index = reciteRecords.indexOf(r);
				break;
			}
		}
		if (index != -1) {
			reciteRecords.remove(index);
			reciteRecords.add(record);
		}
		else {
			reciteRecords.add(record);
		}
		
		saveAllReciteRecords();
		
		/* 追加时存在 BUG
		// 如果单词不存在于背诵记录中，追加写入
		ObjectOutputStream outputStream = 
				new ObjectOutputStream(
						new FileOutputStream(recordPath, true));
		
		outputStream.writeUTF(record.word);
		outputStream.writeLong(record.startDate);
		outputStream.writeLong(record.lastDate);
		outputStream.writeInt(record.stage);
		outputStream.writeInt(record.strange);
		
		outputStream.close();
		*/
		return true;
	}
	
	public Word getRandomRecord() throws FileNotFoundException, IOException {
		Ebbinghaus ebbinghaus = new Ebbinghaus();
		Vector<ReciteRecord> needReciteRecords = new Vector<ReciteRecord>();
		for (ReciteRecord r : reciteRecords) {
			if (ebbinghaus.needRecite(r)) { // 已记忆时间达到艾宾浩斯阶段时间
				needReciteRecords.add(r);
			}
		}
		
		if (needReciteRecords.size() != 0) {
			Random random = new Random(System.currentTimeMillis()); // random seed
			int index = random.nextInt(needReciteRecords.size());
			// 由单词名获取单词对象
			String name = needReciteRecords.get(index).word;
			Word word = WordManager.getWordByName(name);
			return word;
		}
		else {
			return null;
		}
	}
}
