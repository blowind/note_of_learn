package com.zxf.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 *  @ClassName: QRCodeUtil
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/29 15:12
 *  @Version: 1.0
 **/
public class QRCodeUtil {
	/*默认用白底黑字*/
	private static final int QR_BLACK = 0xFF000000;
	private static final int BG_WHITE = 0xFFFFFFFF;

	/*测试logo长宽各占二维码图片16%时，可以识别，再多可能导致掩盖太多信息识别不了二维码*/
	private static final float LOGO_PERCENTAGE_Numerator = 16;
	private static final int FONT_HEIGHT = 25;

	/************************  生成编码相关的底层操作函数  ************************/

	private static BufferedImage encode(String text, BarcodeFormat codeFormat, int imgWidth, int imgHeight) throws Exception {
		/*解决中文乱码方法一：使用ISO-8859-1编码解决中文乱码问题，然后将text替换为formattedText*/
		// String formattedText = new String(text.getBytes("UTF-8"), "ISO-8859-1");

		/*解决中文乱码方法二：指定使用UTF-8编码生成二进制*/
		Map<EncodeHintType, Object> hintType = new HashMap<>();
		hintType.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hintType.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hintType.put(EncodeHintType.MARGIN, 0);

		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, codeFormat, imgWidth, imgHeight, hintType);
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < imgWidth; i++) {
			for(int j = 0; j < imgHeight; j++) {
				image.setRGB(i, j, bitMatrix.get(i, j) ? QR_BLACK : BG_WHITE);
			}
		}
		return image;
	}

	/**
	 * 根据指定比例调整logo图片与二维码长宽比
	 */
	private static int resize(int origin) {
		return new Float(origin * LOGO_PERCENTAGE_Numerator / 100).intValue();
	}

	/**
	 * @param qrCode 生成的二维码图片
	 * @param logo  logo图片
	 * @param remark  文件说明
	 */
	private static BufferedImage addLogoToQRCode(BufferedImage qrCode, BufferedImage logo, String remark) {
		BufferedImage outputBI = qrCode;
		Graphics2D graphics = outputBI.createGraphics();

		if(logo != null) {
			/*设置二维码内logo图片大小，按不超过长宽各10%设置*/
			int logoWidth = logo.getWidth() > resize(outputBI.getWidth()) ?
					resize(outputBI.getWidth()) : logo.getWidth();
			int logoHeight = logo.getHeight() > resize(outputBI.getHeight()) ?
					resize(outputBI.getHeight()) : logo.getHeight();

			/*logo放在中心*/
			int logoTopLeftX = (outputBI.getWidth() - logoWidth) / 2;
			int logoTopLeftY = (outputBI.getHeight() - logoHeight) / 2;
			/*logo放在右下角*/
			/*int logoTopLeftX = (outputBI.getWidth() - logoWidth);
			int logoTopLeftY = (outputBI.getHeight() - logoHeight);*/

			/* 绘制图片，最终结果存放到qrCode指向的BufferedImage */
			graphics.drawImage(logo, logoTopLeftX, logoTopLeftY, logoWidth, logoHeight, null);
			graphics.dispose();
			logo.flush();
		}

		/*在二维码下面加载文字说明*/
		if(remark != null && !remark.equals("")) {
			//画文字到新的面板
			graphics.setColor(Color.BLACK);
			//字体、字型、字号
			graphics.setFont(new Font("微软雅黑", Font.BOLD, FONT_HEIGHT));
			int remarkWidth = graphics.getFontMetrics().stringWidth(remark);
			int qrCodeWidth = outputBI.getWidth();
			int lineNumber = new Double(Math.ceil(remarkWidth*1.0/qrCodeWidth)).intValue();
			int stringLengthPerLine = remark.length() / lineNumber;

			System.out.println(lineNumber * FONT_HEIGHT);

			BufferedImage bufferedImage = new BufferedImage(outputBI.getWidth(), outputBI.getHeight() + lineNumber * FONT_HEIGHT + 10, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphicsWithText = bufferedImage.createGraphics();

			//把带Logo的二维码放到新的面板
			graphicsWithText.drawImage(outputBI, 0, 0, outputBI.getWidth(), outputBI.getHeight(), null);

			//画文字到新的面板
			graphicsWithText.setColor(Color.BLACK);
			//字体、字型、字号
			graphicsWithText.setFont(new Font("微软雅黑", Font.BOLD, FONT_HEIGHT));

			for(int i = 0; i < lineNumber; i++) {
				String lineStr;
				if(remarkWidth > (i+1)*qrCodeWidth) {
					lineStr = remark.substring(i*stringLengthPerLine, (i+1)*stringLengthPerLine);
				}else{
					lineStr = remark.substring(i*stringLengthPerLine);
				}
				int lineWidth = graphicsWithText.getFontMetrics().stringWidth(lineStr);

				graphicsWithText.drawString(lineStr,
						(qrCodeWidth - lineWidth)/2,
						outputBI.getHeight() + (i + 1)*FONT_HEIGHT);
			}

			graphicsWithText.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			// 释放对象
			graphicsWithText.dispose();
			bufferedImage.flush();
			outputBI = bufferedImage;
		}

		outputBI.flush();
		return outputBI;
	}

	private static void toLocalPicture(BufferedImage image, String filePath, String imageType) throws Exception {
		File output = new File(filePath);
		if(!output.exists()) {
			output.createNewFile();
		}
		ImageIO.write(image, imageType, output);
	}

	/************************  生成二维码  ************************/

	/**
	 * 生成二维码(QRCODE)码流
	 */
	private static BufferedImage encodeToQRCode(String text, int imgWidth, int imgHeight) throws Exception {
		return encode(text, BarcodeFormat.QR_CODE, imgWidth, imgHeight);
	}

	/**
	 * 生成二维码图片
	 */
	public static void toLocalQRCodePicture(String text, String filePath, String imageType, int imgWidth, int imgHeight) throws Exception {
		BufferedImage image = encodeToQRCode(text, imgWidth, imgHeight);
		toLocalPicture(image, filePath, imageType);
	}

	/**
	 * 生成带logo和文字说明的二维码图片
	 */
	public static void toLocalQRCodePictureWithLogo(String text, String outputPath, String imageType, int imgWidth, int imgHeight, String logoPath) throws Exception {
		BufferedImage image = encodeToQRCode(text, imgWidth, imgHeight);
		image = addLogoToQRCode(image, ImageIO.read(new File(logoPath)), "二维码备注说明");
		toLocalPicture(image, outputPath, imageType);
	}

	/**
	 * 生成前端可直接使用的二维码字符串
	 * 前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>  其中${imageBase64QRCode}对应二维码的imageBase64字符串
	 */
	public static String imageBase64QRCode(String text, String outputPath, String imageType, int imgWidth, int imgHeight, String logoPath) throws Exception {
		BufferedImage image = encodeToQRCode(text, imgWidth, imgHeight);
		image = addLogoToQRCode(image, ImageIO.read(new File(logoPath)), "二维码备注说明");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.flush();
		ImageIO.write(image, "png", baos);

		return Base64.encodeBase64URLSafeString(baos.toByteArray());
	}

	/************************  生成条形码  ************************/

	/**
	 * 生成条形码码流
	 */
	private static BufferedImage encodeToBarCode(String text, int imgWidth, int imgHeight) throws Exception {
		return encode(text, BarcodeFormat.CODE_128, imgWidth, imgHeight);
	}

	/**
	 * 生成条形码图片
	 */
	public static void toLocalBarCodePicture(String text, String filePath, String imageType, int imgWidth, int imgHeight) throws Exception {
		BufferedImage image = encodeToBarCode(text, imgWidth, imgHeight);
		toLocalPicture(image, filePath, imageType);
	}

	/************************  解析二维码  ************************/

	public static String decode(File file) throws Exception {
		BufferedImage image = ImageIO.read(file);
		if(image == null) {
			System.out.println("Could not decode image");
			return null;
		}

		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		Hashtable hints = new Hashtable();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
		Result result = new MultiFormatReader().decode(bitmap, hints);
		return result.getText();
	}

	public static void main(String[] args) throws Exception{
//		QRCodeUtil.toLocalQRCodePicture("就看看中文", "e:\\text.png", "png", 240, 240);
//		QRCodeUtil.toLocalQRCodePicture("jpg格式的中文二维码", "e:\\text.jpg", "jpg", 360, 360);
////		QRCodeUtil.toLocalFile("https://cn.bing.com", "e:\\text.png", "png", 360, 360);
//
//		QRCodeUtil.toLocalBarCodePicture("aaaaaaaaaa", "e:\\bar.png", "png", 240, 50);
//		QRCodeUtil.toLocalBarCodePicture("bbbbbbbbbbbb", "e:\\bar.jpg", "jpg", 360, 50);
//
//		System.out.println(decode(new File("e:\\text.png")));
//		System.out.println(decode(new File("e:\\bar.png")));

		String logoPath = "e:\\th.jpg";
		String outputPath = "e:\\text.png";

		QRCodeUtil.toLocalQRCodePictureWithLogo("查看有logo的二维码", outputPath, "png", 360, 360, logoPath);
		System.out.println(imageBase64QRCode("查看有logo的二维码", outputPath, "png", 360, 360, logoPath));

	}

}
