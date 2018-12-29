package com.zxf.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private static BufferedImage encode(String text, BarcodeFormat codeFormat, int imgWidth, int imgHeight) throws Exception {
		/*解决中文乱码方法一：使用ISO-8859-1编码解决中文乱码问题，然后将text替换为formattedText*/
		// String formattedText = new String(text.getBytes("UTF-8"), "ISO-8859-1");

		/*解决中文乱码方法二：指定使用UTF-8编码生成二进制*/
		Map<EncodeHintType, String> hintType = new HashMap<>();
		hintType.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, codeFormat, imgWidth, imgHeight, hintType);
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < imgWidth; i++) {
			for(int j = 0; j < imgHeight; j++) {
				image.setRGB(i, j, bitMatrix.get(i, j) ? BLACK : WHITE);
			}
		}
		return image;
	}

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

	/**
	 * 生成二维码(QRCODE)码流
	 */
	private static BufferedImage encodeToQRCode(String text, int imgWidth, int imgHeight) throws Exception {
		return encode(text, BarcodeFormat.QR_CODE, imgWidth, imgHeight);
	}

	/**
	 * 生成条形码码流
	 */
	private static BufferedImage encodeToBarCode(String text, int imgWidth, int imgHeight) throws Exception {
		return encode(text, BarcodeFormat.CODE_128, imgWidth, imgHeight);
	}

	private static void toLocalPicture(BufferedImage image, String filePath, String imageType) throws Exception {
		File output = new File(filePath);
		if(!output.exists()) {
			output.createNewFile();
		}
		ImageIO.write(image, imageType, output);
	}

	public static void toLocalQRCodePicture(String text, String filePath, String imageType, int imgWidth, int imgHeight) throws Exception {
		BufferedImage image = encodeToQRCode(text, imgWidth, imgHeight);
		toLocalPicture(image, filePath, imageType);
	}

	public static void toLocalBarCodePicture(String text, String filePath, String imageType, int imgWidth, int imgHeight) throws Exception {
		BufferedImage image = encodeToBarCode(text, imgWidth, imgHeight);
		toLocalPicture(image, filePath, imageType);
	}

	public static void main(String[] args) throws Exception{
		QRCodeUtil.toLocalQRCodePicture("就看看中文", "e:\\text.png", "png", 240, 240);
		QRCodeUtil.toLocalQRCodePicture("jpg格式的中文二维码", "e:\\text.jpg", "jpg", 360, 360);
//		QRCodeUtil.toLocalFile("https://cn.bing.com", "e:\\text.png", "png", 360, 360);

		QRCodeUtil.toLocalBarCodePicture("aaaaaaaaaa", "e:\\bar.png", "png", 240, 50);
		QRCodeUtil.toLocalBarCodePicture("bbbbbbbbbbbb", "e:\\bar.jpg", "jpg", 360, 50);

		System.out.println(decode(new File("e:\\text.png")));
		System.out.println(decode(new File("e:\\bar.png")));
	}

}
