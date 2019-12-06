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
public class Word {
    public String name;
	public String phonetic;
	public String interp;
	
	public Word() {
		
	}
	
	public Word(String name, String phonetic, String interp) {
		this.name = name;
		this.phonetic = phonetic;
		this.interp = interp;
	}
}
