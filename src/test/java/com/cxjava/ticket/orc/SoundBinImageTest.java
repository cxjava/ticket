package com.cxjava.ticket.orc;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.cxjava.ticket.ocr.SoundBinImage;

/**
 * @author Maty Chen
 * @date 2013-1-22上午10:44:21
 */
public class SoundBinImageTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			File file = new File("D:\\05_Document\\Downloads\\123061\\" + i + ".jpg");
			SoundBinImage.releaseSound(new URL("http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand").openStream(), file, 80+i,
					"jpg");
		}
	}

}
