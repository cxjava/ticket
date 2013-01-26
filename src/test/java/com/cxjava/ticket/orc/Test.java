package com.cxjava.ticket.orc;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Maty Chen
 * @date 2013-1-25下午1:27:03
 */
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cxjava.ticket.orc.BinaryTest;


/**
 * 功能描述
 * @author YAOWENHAO
 * @since 2011-11-28 
 * @version 1.0
 */
public class Test {
	private static int i=0;
	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("验证码");
		
		final JLabel label = new JLabel(new ImageIcon(),
				JLabel.CENTER);
		BufferedImage bufferedImage = ImageIO
				.read(new URL(
						"http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand")
						.openStream());
//		BufferedImage output=BinaryTest.get(bufferedImage,0);
//		String randCodeByRob = OCR.read(output.);			
		label.setIcon(new ImageIcon(bufferedImage));
		label.setText("aaa");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					BufferedImage bufferedImage = ImageIO
							.read(new URL(
									"http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand")
									.openStream());
					File output= new File("f:\\Downloads\\12306\\"+i+".png");
					BinaryTest.binaryImage(new URL(
							"http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand")
							.openStream(),"png",output);
					label.setIcon(new ImageIcon(ImageIO
							.read(new URL(
									"http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand")
									.openStream())));
					label.setText("aaa");
					i=i+1;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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

