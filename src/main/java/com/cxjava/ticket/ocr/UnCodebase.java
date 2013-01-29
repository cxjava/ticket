package com.cxjava.ticket.ocr;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UnCodebase {
	public BufferedImage _bi;

	public int w;

	public int h;

	public UnCodebase(BufferedImage bi) {
		_bi = bi;
		w = bi.getWidth();
		h = bi.getHeight();
	}

	// / <summary>
	// / 得到灰度图像前景背景的临界值 最大类间方差法，yuanbao,2007.08
	// / </summary>
	// / <returns>前景背景的临界值</returns>
	public int GetDgGrayValue(BufferedImage bmpobj) {
		ColorModel cm = ColorModel.getRGBdefault();
		int[] pixelNum = new int[256]; // 图象直方图，共256个点
		int n, n1, n2;
		int total; // total为总和，累计值
		double m1, m2, sum, csum, fmax, sb; // sb为类间方差，fmax存储最大方差值
		int k, t, q;
		int threshValue = 1; // 阈值
		int step = 1;
		// 生成直方图
		for (int i = 0; i < bmpobj.getWidth(); i++) {
			for (int j = 0; j < bmpobj.getHeight(); j++) {
				// 返回各个点的颜色，以RGB表示
				pixelNum[cm.getRed(bmpobj.getRGB(i, j))]++; // 相应的直方图加1
			}
		}
		// 直方图平滑化
		for (k = 0; k <= 255; k++) {
			total = 0;
			for (t = -2; t <= 2; t++) // 与附近2个灰度做平滑化，t值应取较小的值
			{
				q = k + t;
				if (q < 0) // 越界处理
					q = 0;
				if (q > 255)
					q = 255;
				total = total + pixelNum[q]; // total为总和，累计值
			}
			pixelNum[k] = (int) ((float) total / 5.0 + 0.5); // 平滑化，左边2个+中间1个+右边2个灰度，共5个，所以总和除以5，后面加0.5是用修正值
		}
		// 求阈值
		sum = csum = 0.0;
		n = 0;
		// 计算总的图象的点数和质量矩，为后面的计算做准备
		for (k = 0; k <= 255; k++) {
			sum += (double) k * (double) pixelNum[k]; // x*f(x)质量矩，也就是每个灰度的值乘以其点数（归一化后为概率），sum为其总和
			n += pixelNum[k]; // n为图象总的点数，归一化后就是累积概率
		}

		fmax = -1.0; // 类间方差sb不可能为负，所以fmax初始值为-1不影响计算的进行
		n1 = 0;
		for (k = 0; k < 256; k++) // 对每个灰度（从0到255）计算一次分割后的类间方差sb
		{
			n1 += pixelNum[k]; // n1为在当前阈值遍前景图象的点数
			if (n1 == 0) {
				continue;
			} // 没有分出前景后景
			n2 = n - n1; // n2为背景图象的点数
			if (n2 == 0) {
				break;
			} // n2为0表示全部都是后景图象，与n1=0情况类似，之后的遍历不可能使前景点数增加，所以此时可以退出循环
			csum += (double) k * pixelNum[k]; // 前景的“灰度的值*其点数”的总和
			m1 = csum / n1; // m1为前景的平均灰度
			m2 = (sum - csum) / n2; // m2为背景的平均灰度
			sb = (double) n1 * (double) n2 * (m1 - m2) * (m1 - m2); // sb为类间方差
			if (sb > fmax) // 如果算出的类间方差大于前一次算出的类间方差
			{
				fmax = sb; // fmax始终为最大类间方差（otsu）
				threshValue = k; // 取最大类间方差时对应的灰度的k就是最佳阈值
			}
		}
		return threshValue;
	}

	// <summary>
	// 灰度转换,计算灰度值
	// </summary>
	public int getGrayNumColor(int pixel) {
		int gray;
		gray = (int) (((pixel >> 16) & 0xff) * 0.8);
		gray += (int) (((pixel >> 8) & 0xff) * 0.1);
		gray += (int) (((pixel) & 0xff) * 0.1);
		pixel = (255 << 24) | (gray << 16) | (gray << 8) | gray;
		return pixel;
	}

	// <summary>
	// 灰度转换,逐点方式
	// </summary>
	public void grayByPixels() {
		// 定义一数组，用来存储图片的象素
		int[] pixels = new int[w * h];
		PixelGrabber pg = new PixelGrabber(_bi, 0, 0, w, h, pixels, 0, w);
		try {
			// 读取像素值
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("处理被异常中断！请重试！");
		}
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int tmpValue = getGrayNumColor(pixels[i * w + j]);
				_bi.setRGB(j, i, tmpValue);
			}
		}
	}

	// <summary>
	// 去图形边框
	// </summary>
	// <param name="borderWidth"></param>
	public void ClearPicBorder(int borderWidth) {
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (i < borderWidth || j < borderWidth
						|| j > w - 1 - borderWidth || i > h - 1 - borderWidth)
					_bi.setRGB(i, j, 0);
			}
		}
	}

	// <summary>
	// 得到有效图形并调整为可平均分割的大小
	// </summary>
	// <param name="dgGrayValue">灰度背景分界值</param>
	// <param name="CharsCount">有效字符数</param>
	// <returns></returns>
	public void GetPicValidByValue(int dgGrayValue, int CharsCount) {
		int posx1 = w;
		int posy1 = h;

		int posx2 = 0;
		int posy2 = 0;
		for (int i = 0; i < h; i++) // 找有效区
		{
			for (int j = 0; j < w; j++) {
				int pixelValue = _bi.getRGB(j, i);
				if (pixelValue < dgGrayValue) // 根据灰度值
				{
					if (posx1 > j)
						posx1 = j;
					if (posy1 > i)
						posy1 = i;

					if (posx2 < j)
						posx2 = j;
					if (posy2 < i)
						posy2 = i;
				}
				;
			}
			;
		}
		;
		// 确保能整除
		int Span = CharsCount - (posx2 - posx1 + 1) % CharsCount; // 可整除的差额数
		if (Span < CharsCount) {
			int leftSpan = Span / 2; // 分配到左边的空列 ，如span为单数,则右边比左边大1
			if (posx1 > leftSpan)
				posx1 = posx1 - leftSpan;
			if (posx2 + Span - leftSpan < w)
				posx2 = posx2 + Span - leftSpan;
		}
		// 复制新图
		_bi = _bi.getSubimage(posx1, posy1, posx2 - posx1 + 1, posy2 - posy1
				+ 1);
	}

	public void saveImage(Image image, String filename, int w, int h) {
		Graphics2D g2 = (Graphics2D) _bi.getGraphics();
		g2.drawImage(image, 0, 0, null);
		try {
			ImageIO.write(_bi, "JPEG", new java.io.File(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// / <summary>
	// / 返回灰度图片的点阵描述字串，1表示灰点，0表示背景
	// / </summary>
	// / <param name="singlepic">灰度图</param>
	// / <param name="dgGrayValue">背前景灰色界限</param>
	// / <returns></returns>
	public void GetSingleBmpCode(int dgGrayValue) {
		ColorModel cm = ColorModel.getRGBdefault();
		int win = 0;
		int bin = 0;
		for (int posy = 0; posy < h; posy++) {
			for (int posx = 0; posx < w; posx++) {
				int temValue = cm.getRed(_bi.getRGB(posx, posy));
				// System.out.println(temValue);
				if (temValue < dgGrayValue) {
					_bi.setRGB(posx, posy, 0);
					win++;
				} else {
					_bi.setRGB(posx, posy, 0xFFFFFF);
					bin++;
				}
			}
		}
//		System.out.println(bin + "\n" + win);
		return;
	}

	public static void main(String[] args) throws Exception {
		//http://flights.ctrip.com/Domestic/RandomValidateImage.aspx
		
//		File imgs = new File("D:\\work\\CtripTest\\imgs");
//		File data = new File("D:\\work\\CtripTest\\data");
//		Util.delete(imgs);
//		Util.delete(data);
//		imgs.mkdir();
//		data.mkdir();
//		
//		Srmjava srm = new Srmjava();
//
//		
//		
//		File f = new File("D:\\dev\\cygwin\\home\\Scott.Lee\\ctrip_img");
//		File []all = f.listFiles();
//		for (int i = 0; i < all.length; i++) {
//			String oriImg = all[i].getAbsolutePath();
//			String processedImg=(new File(imgs, all[i].getName())).getAbsolutePath();
//			int q=200;
//			srm.convert(oriImg, processedImg, q);
//			split(processedImg,i);	
//		}
//		
////		split("D:\\dev\\cygwin\\home\\Scott.Lee\\ctrip_img\\10.jpg",1);
////		split("D:\\11.jpg",1);
////		split("D:\\2.jpg",1);
////		split("D:\\elong1.jpg",1);
//		
//		
////		for(int i=0; i<6; i++){
////			ImageData _imageSub = imageData.getSub(i, 6);
////			
////			printData(_imageSub.data);
////			System.out.println(_imageSub.data);
////			_imageSub.show();
////		}
//		
		// ImageIO.write(sbi, "JPEG", new java.io.File("E:\\uc1.jpg"));
	}
//
//	public static ImageData[] split(String fileName, int index) throws IOException, FileNotFoundException {
////		String fileName="D:\\dev\\cygwin\\home\\Scott.Lee\\ctrip_img";
//		File f = new File(fileName);
//		BufferedImage bi = ImageIO.read(f);
//		UnCodebase uc = new UnCodebase(bi);
//		uc.grayByPixels();
//		int grayValue=uc.GetDgGrayValue(bi);
////		System.out.println(grayValue);
//		uc.GetSingleBmpCode(grayValue);
//		BufferedImage sbi = uc._bi.getSubimage(0, 0, uc.w, uc.h);
//		ImageData imageData = new ImageData(sbi, new WhiteFilter());
////		imageData.modify();
//		//尝试多次修正的差异
//		// imageData.modify();
//		// imageData.modify();
////		ImageData _image = imageData.getSub(0, 6);
////		 imageData.show();
////		_image.show();
////		ImageData _image1 = imageData.getSub(1, 6);
////		_image1.show();
////		ImageData _image2 = imageData.getSub(2, 6);
////		_image2.show();
//		
//		 imageData.cal();
//		 
//		 imageData.writeImg(new File("tmp/ctrip_"+f.getName()));
//		 
//		 ImageData[] _imageSubs = imageData.split(6, 4);
//		 for(int i=0;i<_imageSubs.length;i++){
////			 _imageSubs[i].show();
//			 _imageSubs[i].writeImg(new File("imgs/ctrip_"+f.getName().replace(".jpg", "")+"_"+index+"_"+i+".jpg"));
//			 _imageSubs[i].writeData(new File("data/ctrip_"+f.getName().replace(".jpg", "")+"_"+index+"_"+i+".txt"));
//			 
//		 }
//		 return _imageSubs;
//	}
	
	public static void printData(int[][] data) {
		System.out.println();
		for (int i = 1; i < data.length; i++) {
			for (int j = 1; j < data[i].length; j++) {
				System.out.print((data[i][j]));
			}
			System.out.println();
		}
		System.out.println();
	}
}
