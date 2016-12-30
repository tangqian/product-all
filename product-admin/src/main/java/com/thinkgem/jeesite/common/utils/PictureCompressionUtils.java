package com.thinkgem.jeesite.common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片压缩处理工具类
 * 
 * @author feng.jing.wei
 *
 */
public class PictureCompressionUtils {

	private final static Logger logger = LoggerFactory.getLogger(PictureCompressionUtils.class);

	private final static Set<String> formats = new HashSet<String>(Arrays.asList("BMP", "GIF", "EPS", "DCS", "JPEG", "JPG",
			"JPE", "PCX", "PDF", "RAW", "PICT", "PXR", "PNG", "SCT", "TIFF", "TARGA"));

	/**
	 * 按照比例进行缩放，宽与高的比例保持不变
	 * 
	 * @param sourceImgPath
	 *            源图片路径
	 * @param scale
	 *            比例
	 * @param targetImgPath
	 *            目标图片路径(被压缩后的图片路径)
	 */
	public static void equalRatioScale(String sourceImgPath, String targetImgPath, double scale) {
		Builder<File> builder = getGeneralBuilder(sourceImgPath).scale(scale);
		doCompression(sourceImgPath, builder, targetImgPath);
	}
	
	/**
	 * 根据指定宽高进行等比例缩放,宽与高的比例保持不变
	 * 
	 * @param sourceImgPath
	 *            源图片路径
	 * @param targetImgPath
	 *            目标图片路径(被压缩后的图片路径)
	 * @param width
	 *            压缩后的图片期望高度
	 * @param height
	 *            压缩后的图片期望宽度
	 */
	public static void equalRatioSize(String sourceImgPath, String targetImgPath, int width, int height) {
		Builder<File> builder = getGeneralBuilder(sourceImgPath).size(width, height);
		doCompression(sourceImgPath, builder, targetImgPath);
	}

	/**
	 * 按照指定宽高进行缩放
	 * 
	 * @param sourceImgPath
	 *            源图片路径
	 * @param targetImgPath
	 *            目标图片路径(被压缩后的图片路径)
	 * @param width
	 *            压缩后的图片高度
	 * @param height
	 *            压缩后的图片宽度
	 */
	public static void unEqualRatioSize(String sourceImgPath, String targetImgPath, int width, int height) {
		Builder<File> builder = getGeneralBuilder(sourceImgPath).size(width, height).keepAspectRatio(false);
		doCompression(sourceImgPath, builder, targetImgPath);
	}
	
	/**
	 * 转化图像格式
	 * 
	 * @param sourceImgPath
	 *            源图片路径
	 * @param targetImgPath
	 *            目标图片路径(被转化后的图片路径)
	 * @param width
	 *            转化后的图片宽度
	 * @param height
	 *            转化后的图片高度
	 * @param format
	 *            要转化的格式
	 */
	public static void equalRatioFormat(String sourceImgPath, String targetImgPath, int width, int height, String format) {
		if (StringUtils.isNotBlank(format) && formats.contains(format.toUpperCase())) {
			Builder<File> builder = getGeneralBuilder(sourceImgPath).size(width, height).outputFormat(format);
			doCompression(sourceImgPath, builder, targetImgPath);
		}
	}
	
	/**
	 * 获取常用的图片压缩构建器
	 * @param imagePath
	 * @return
	 */
	private static Builder<File> getGeneralBuilder(String imagePath){
		return Thumbnails.of(imagePath).outputQuality(1.0f);
	}

	/**
	 * 实际压缩方法
	 * @param sourceImgPath 
	 * @param builders 由sourceImgPath参数指定路径创建的图片压缩构建器
	 * @param targetImgPath
	 * @return
	 */
	private static boolean doCompression(String sourceImgPath, Builder<File> builder, String targetImgPath) {
		boolean result = false;
		try {
			File file = new File(sourceImgPath);
			if (!file.exists()) {
				logger.error("源图片路径出错，srcPath={}", sourceImgPath);
			} else {
				builder.toFile(targetImgPath);
				result = true;
			}
		} catch (IOException e) {
			logger.error("系统异常,图片压缩失败,srcPath={}", sourceImgPath);
			logger.error("异常信息:", e);
		}
		return result;
	}

	/**
	 * 缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param height
	 *            缩放后的高度
	 * @param width
	 *            缩放后的宽度
	 * @param bb
	 *            比例不对时是否需要补白:true为补白,false为不补白
	 */
	public static void scale(String sourceImgPath, String targetImgPath, int height, int width, boolean bb) {
		try {
			File file = new File(sourceImgPath);
			if (!file.exists()) {
				logger.error("上传图片路径出错." + sourceImgPath);
				throw new FileNotFoundException("上传图片路径出错." + sourceImgPath);
			}
			double ratio = 0.0; // 缩放比例
			BufferedImage bi = ImageIO.read(file);
			Image itemp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
							itemp.getHeight(null), Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "JPEG", new File(targetImgPath));
			logger.info("图片压缩成功." + targetImgPath);
		} catch (IOException e) {
			logger.error("系统异常,图片压缩失败.");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		String sourceImgPath = "F:/source/35326.jpg";
		String targetImgPath = "F:/target/" + System.currentTimeMillis() + ".gif";
		PictureCompressionUtils.equalRatioScale(sourceImgPath, targetImgPath, 0.25f);
		PictureCompressionUtils.equalRatioSize(sourceImgPath, targetImgPath, 160, 160);
	}

}
