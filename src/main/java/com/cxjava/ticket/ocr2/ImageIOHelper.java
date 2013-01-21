package com.cxjava.ticket.ocr2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.media.imageio.plugins.tiff.TIFFImageWriteParam;

public class ImageIOHelper {
	private static final Logger LOG = LoggerFactory.getLogger(ImageIOHelper.class);

	/**
	 * 图片文件转换为tif格式
	 * 
	 * @param imageFile
	 *            文件路径
	 * @param imageFormat
	 *            文件扩展名
	 * @return
	 */
	public static File createImage(File imageFile, String imageFormat) {
		File tempFile = null;
		ImageOutputStream ios = null;
		ImageWriter writer = null;
		ImageReader reader = null;
		try {
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageFormat);
			reader = readers.next();

			ImageInputStream iis = ImageIO.createImageInputStream(imageFile);
			reader.setInput(iis);
			// Read the stream metadata
			IIOMetadata streamMetadata = reader.getStreamMetadata();

			// Set up the writeParam
			TIFFImageWriteParam tiffWriteParam = new TIFFImageWriteParam(Locale.ENGLISH);
			tiffWriteParam.setCompressionMode(ImageWriteParam.MODE_DISABLED);

			// Get tif writer and set output to file
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");
			writer = writers.next();

			BufferedImage bi = reader.read(0);
			IIOImage image = new IIOImage(bi, null, reader.getImageMetadata(0));
			tempFile = tempImageFile(imageFile);
			ios = ImageIO.createImageOutputStream(tempFile);
			writer.setOutput(ios);
			writer.write(streamMetadata, image, tiffWriteParam);

		} catch (IOException e) {
			LOG.error("IOException : {}.", e);
		} finally {
			try {
				ios.close();
			} catch (IOException e) {
				LOG.error("e : {}.", e);
			}
			writer.dispose();
			reader.dispose();
		}
		return tempFile;
	}

	private static File tempImageFile(File imageFile) {
		String path = imageFile.getPath();
		StringBuffer strB = new StringBuffer(path);
		strB.insert(path.lastIndexOf('.'), 0);
		return new File(strB.toString().replaceFirst("(?<=\\.)(\\w+)$", "tif"));
	}

}