/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorize.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 *
 * @author kailun
 */
public class MyFont {
    public static Font getFont(String filePath, String fileName, int style, float size)
			throws FontFormatException, IOException {

		File file = new File(filePath + '/' + fileName);
		FileInputStream fi;
		BufferedInputStream fb;
		Font font = null;
		fi = new FileInputStream(file);
		fb = new BufferedInputStream(fi);
		font = Font.createFont(Font.TRUETYPE_FONT, fb);
		font = font.deriveFont(style, size);

		return font;
	}
}
