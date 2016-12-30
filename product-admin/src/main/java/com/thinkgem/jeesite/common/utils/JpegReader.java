package com.thinkgem.jeesite.common.utils;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.jpeg.JpegImageParser;
import org.apache.sanselan.formats.jpeg.segments.UnknownSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CMYK TO RGB
 * 
 * @author feng.jing.wei
 *
 */
public class JpegReader {
	
	private final static Logger logger = LoggerFactory.getLogger(JpegReader.class);

	public static final int COLOR_TYPE_RGB = 1;
	public static final int COLOR_TYPE_CMYK = 2;
	public static final int COLOR_TYPE_YCCK = 3;

	private int colorType = COLOR_TYPE_CMYK;
	private boolean hasAdobeMarker = false;

	/**
	 * 获取成功转化成RGB模式的图片对象，如果原图本身不是cmyk模式图片，则返回null
	 * 
	 * @param imagePath
	 * @return
	 * @throws IOException
	 * @throws ImageReadException
	 */
	public BufferedImage readImage(String imagePath) throws IOException, ImageReadException {
		hasAdobeMarker = false;

		File file = new File(imagePath);
		ImageInputStream stream = ImageIO.createImageInputStream(file);
		if (stream == null) {
			logger.error("read picture file fail!, imagePath={}", imagePath);
			return null;
		}

		Iterator<ImageReader> iter = ImageIO.getImageReaders(stream);
		if (iter.hasNext()) {
			ImageReader reader = iter.next();
			reader.setInput(stream);

			BufferedImage image;
			if(isCmykMode(reader)){
				colorType = COLOR_TYPE_CMYK;
				checkAdobeMarker(file);
				WritableRaster raster = (WritableRaster) reader.readRaster(0, null);
				if (colorType == COLOR_TYPE_YCCK)
					convertYcckToCmyk(raster);
				if (hasAdobeMarker)
					convertInvertedColors(raster);
				ICC_Profile profile = Sanselan.getICCProfile(file);
				image = convertCmykToRgb(raster, profile);
				return image;
			}
		}
		return null;
	}
	
	private boolean isCmykMode(ImageReader reader) throws IOException{
		try {
			reader.read(0);
			return false;
		} catch (IIOException e) {//如果抛出此异常，表示是cmyk模式的图片
			return true;
		}
	}

	private void checkAdobeMarker(File file) throws IOException, ImageReadException {
		JpegImageParser parser = new JpegImageParser();
		ByteSource byteSource = new ByteSourceFile(file);
		@SuppressWarnings("rawtypes")
		ArrayList segments = parser.readSegments(byteSource, new int[] { 0xffee }, true);
		if (segments != null && segments.size() >= 1) {
			UnknownSegment app14Segment = (UnknownSegment) segments.get(0);
			byte[] data = app14Segment.bytes;
			if (data.length >= 12 && data[0] == 'A' && data[1] == 'd' && data[2] == 'o' && data[3] == 'b' && data[4] == 'e') {
				hasAdobeMarker = true;
				int transform = app14Segment.bytes[11] & 0xff;
				if (transform == 2)
					colorType = COLOR_TYPE_YCCK;
			}
		}
	}

	private static void convertYcckToCmyk(WritableRaster raster) {
		int height = raster.getHeight();
		int width = raster.getWidth();
		int stride = width * 4;
		int[] pixelRow = new int[stride];
		for (int h = 0; h < height; h++) {
			raster.getPixels(0, h, width, 1, pixelRow);

			for (int x = 0; x < stride; x += 4) {
				int y = pixelRow[x];
				int cb = pixelRow[x + 1];
				int cr = pixelRow[x + 2];

				int c = (int) (y + 1.402 * cr - 178.956);
				int m = (int) (y - 0.34414 * cb - 0.71414 * cr + 135.95984);
				y = (int) (y + 1.772 * cb - 226.316);

				if (c < 0)
					c = 0;
				else if (c > 255)
					c = 255;
				if (m < 0)
					m = 0;
				else if (m > 255)
					m = 255;
				if (y < 0)
					y = 0;
				else if (y > 255)
					y = 255;

				pixelRow[x] = 255 - c;
				pixelRow[x + 1] = 255 - m;
				pixelRow[x + 2] = 255 - y;
			}

			raster.setPixels(0, h, width, 1, pixelRow);
		}
	}

	private static void convertInvertedColors(WritableRaster raster) {
		int height = raster.getHeight();
		int width = raster.getWidth();
		int stride = width * 4;
		int[] pixelRow = new int[stride];
		for (int h = 0; h < height; h++) {
			raster.getPixels(0, h, width, 1, pixelRow);
			for (int x = 0; x < stride; x++)
				pixelRow[x] = 255 - pixelRow[x];
			raster.setPixels(0, h, width, 1, pixelRow);
		}
	}

	private static BufferedImage convertCmykToRgb(Raster cmykRaster, ICC_Profile cmykProfile) throws IOException {
		if (cmykProfile == null)
			cmykProfile = ICC_Profile.getInstance(Object.class.getResourceAsStream("/iso/ISOcoated_v2_300_eci.icc"));
		if (cmykProfile.getProfileClass() != ICC_Profile.CLASS_DISPLAY) {
			byte[] profileData = cmykProfile.getData();
			if (profileData[ICC_Profile.icHdrRenderingIntent] == ICC_Profile.icPerceptual) {
				intToBigEndian(ICC_Profile.icSigDisplayClass, profileData, ICC_Profile.icHdrDeviceClass); // Header is first
				cmykProfile = ICC_Profile.getInstance(profileData);
			}
		}

		ICC_ColorSpace cmykCS = new ICC_ColorSpace(cmykProfile);
		BufferedImage rgbImage = new BufferedImage(cmykRaster.getWidth(), cmykRaster.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster rgbRaster = rgbImage.getRaster();
		ColorSpace rgbCS = rgbImage.getColorModel().getColorSpace();
		ColorConvertOp cmykToRgb = new ColorConvertOp(cmykCS, rgbCS, null);
		cmykToRgb.filter(cmykRaster, rgbRaster);
		return rgbImage;
	}

	private static void intToBigEndian(int value, byte[] array, int index) {
		array[index] = (byte) (value >> 24);
		array[index + 1] = (byte) (value >> 16);
		array[index + 2] = (byte) (value >> 8);
		array[index + 3] = (byte) (value);
	}
	
	public static void main(String[] args) {
		try {
			BufferedImage rgbImage = new JpegReader().readImage("F:/q_images/56394.jpg");
			ImageIO.write(rgbImage, "JPEG", new File("F:/target/" + System.currentTimeMillis() + ".jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
