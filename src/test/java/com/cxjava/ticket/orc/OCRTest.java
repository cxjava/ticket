/**************************************************
 * Filename: GUIObserver.java
 * Version: v1.0
 * CreatedDate: 2011-12-29
 * Copyright (C) 2011 By cafebabe.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * If you would like to negotiate alternate licensing terms, you may do
 * so by contacting the author: talentyao@foxmail.com
 ***************************************************/
package com.cxjava.ticket.orc;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxjava.ticket.ocr.BinaryTest;
import com.cxjava.ticket.ocr.OCR;

/**
 * 功能描述
 * 
 * @author YAOWENHAO
 * @since 2011-11-28
 * @version 1.0
 */
public class OCRTest {
	private static final Logger LOG = LoggerFactory.getLogger(OCRTest.class);

	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("验证码");

		final JLabel label = new JLabel(new ImageIcon(), JLabel.CENTER);
		File file = new File("D:\\05_Document\\Downloads\\passCodeAction.jpg");
		BinaryTest.binaryImage(new URL("http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand").openStream(), "jpg", file);
		String randCodeByRob = OCR.read(file);
		label.setIcon(new ImageIcon(FileUtils.readFileToByteArray(file)));
		label.setText(randCodeByRob);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					File file = new File("D:\\05_Document\\Downloads\\passCodeAction.jpg");
					BinaryTest.binaryImage(new URL("http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand").openStream(), "jpg",
							file);
					String randCodeByRob = OCR.read(file);
					label.setIcon(new ImageIcon(FileUtils.readFileToByteArray(file)));
					label.setText(randCodeByRob);
					LOG.debug("randCodeByRob : {}.", randCodeByRob);
				} catch (Exception e1) {
					LOG.error("e1 : {}.", e1);
				}
			}
		});
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}
