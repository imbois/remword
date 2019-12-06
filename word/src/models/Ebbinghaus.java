/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author kailun
 */
public class Ebbinghaus {
    private int forgettingCurve[] = {
			5,			// 5分钟 
			30,			// 30分钟 
			12*60,		// 12小时 
			1*24*60,	// 1天
			2*24*60,	// 2天 
			4*24*60,	// 4天 
			7*24*60,	// 7天 
			15*24*60	// 15天 
			};
	
	// 根据记忆曲线，判断单词是否需要复习
	public boolean needRecite(ReciteRecord record) {
		// 记忆曲线完成 
		// record.stage == 8
		// forgettingCurve.length == 8
		if (record.stage >= forgettingCurve.length) {
			return false;
		}
		long currentTime = System.currentTimeMillis();
		int timeDiff = (int)((currentTime - record.lastTime) / (1000 * 60)); // ms -> minute
		
		if (timeDiff > forgettingCurve[record.stage]) { // 记忆经过时间大于艾宾浩斯的对应阶段时间，需要重新记忆
			return true;
		}
		else {
			return false;
		}
		
	}
}
