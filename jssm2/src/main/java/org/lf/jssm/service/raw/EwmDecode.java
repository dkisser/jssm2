package org.lf.jssm.service.raw;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lf.utils.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;

/**
 * 解析图片中的二维码
 * 
 * @author sunwill
 *
 */
public class EwmDecode {
	/**
	 * 日期格式yyyyMMdd
	 */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMdd");
	/**
	 * 封面二维码类型
	 */
	public static final String EWM_TYPE_FM = "F";
	/**
	 * 目录二维码类型
	 */
	public static final String EWM_TYPE_ML = "M";
	/**
	 * 内容二维码类型
	 */
	public static final String EWM_TYPE_NR = "Y";

	private static final Map<DecodeHintType, Object> HINTS;
	private static final Map<DecodeHintType, Object> HINTS_PURE;
	static {
		Collection<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
		formats.add(BarcodeFormat.QR_CODE);// 设置解析
		HINTS = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
		HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		// HINTS.put(DecodeHintType.POSSIBLE_FORMATS,
		// EnumSet.allOf(BarcodeFormat.class));
		HINTS.put(DecodeHintType.POSSIBLE_FORMATS, formats);
		HINTS.put(DecodeHintType.CHARACTER_SET, "UTF-8");// 设置字符集

		HINTS_PURE = new EnumMap<DecodeHintType, Object>(HINTS);
		HINTS_PURE.put(DecodeHintType.POSSIBLE_FORMATS, formats);
		HINTS_PURE.put(DecodeHintType.CHARACTER_SET, "UTF-8");// 设置字符集
		HINTS_PURE.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
	}

	/**
	 * 解析二维码
	 */
	public Collection<Result> readQRCode(InputStream inputStream)
			throws ReaderException, IOException {
		BufferedImage bfImage = ImageIO.read(inputStream);
		if (bfImage == null) {
			return null;
		}
		LuminanceSource source = new BufferedImageLuminanceSource(bfImage);
		BinaryBitmap binaryBitmap = new BinaryBitmap(
				new HybridBinarizer(source));
		Collection<Result> results = new ArrayList<Result>(1);
		ReaderException savedException = null;
		Reader reader = new MultiFormatReader();
		try {
			// 寻找多个条码
			MultipleBarcodeReader multiReader = new GenericMultipleBarcodeReader(
					reader);
			Result[] theResults = multiReader.decodeMultiple(binaryBitmap,
					HINTS);
			if (theResults != null) {
				results.addAll(Arrays.asList(theResults));
			}
		} catch (ReaderException re) {
			savedException = re;
		}
		if (results.isEmpty()) {
			try {
				// 寻找纯条码
				Result theResult = reader.decode(binaryBitmap, HINTS_PURE);
				if (theResult != null) {
					results.add(theResult);
				}
			} catch (ReaderException re) {
				savedException = re;
			}
		}

		if (results.isEmpty()) {
			try {
				// 寻找图片中的正常条码
				Result theResult = reader.decode(binaryBitmap, HINTS);
				if (theResult != null) {
					results.add(theResult);
				}
			} catch (ReaderException re) {
				savedException = re;
			}
		}

		if (results.isEmpty()) {
			try {
				// 再次尝试其他特殊处理
				BinaryBitmap hybridBitmap = new BinaryBitmap(
						new HybridBinarizer(source));
				Result theResult = reader.decode(hybridBitmap, HINTS);
				if (theResult != null) {
					results.add(theResult);
				}
			} catch (ReaderException re) {
				savedException = re;
			}
		}
		if (inputStream != null) {
			inputStream.close();
		}
		if (results.isEmpty()) {
			throw savedException;
		} else {
			return results;
		}
	}

	/**
	 * 从图片中解析二维码，并获得有效二维码的值
	 * 
	 * @param inputStream
	 * @return
	 * @throws ReaderException
	 * @throws IOException
	 */
	public Result readQRCodeResult(InputStream inputStream)
			throws ReaderException, IOException {
		Collection<Result> results = readQRCode(inputStream);
		if (results != null && !results.isEmpty()) {
			// 寻找结果集中非空的结果
			for (Result result : results) {
				if (result != null && isValidEwm(result.getText(), null)) {
					return result;
				}
			}
		}
		throw NotFoundException.getNotFoundInstance();
	}

	/**
	 * 判断二维码是否有效（长度为18位；前8位是日期字符串；最后一位为类型）
	 * 
	 * @param ewm
	 * @param type
	 *            (不为空时，判断二维码是否以type结尾； 为空时，判断二维码最后一位是否是（F,M,Y）其中的一个)
	 * @return
	 */
	public boolean isValidEwm(String ewm, String type) {
		if (ewm == null || ewm.length() != 18
				|| !isValidDate(ewm.substring(0, 8)) || !isValidType(ewm, type)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断指定字符串是否为标准日期格式（yyyyMMdd）
	 * 
	 * @param str
	 * @return
	 */
	private boolean isValidDate(String str) {
		boolean convertSuccess = true;
		try {
			DATE_FORMAT.setLenient(false);
			DATE_FORMAT.parse(str);
		} catch (Exception e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 判断指定字符串是否为有效类型字符串
	 * 
	 * @param ewm
	 * @param type
	 *            （不为空时，判断二维码是否以type结尾）
	 * @return
	 */
	private boolean isValidType(String ewm, String type) {
		if (!StringUtils.isEmpty(type)) {
			if (ewm.endsWith(type)) {
				return true;
			} else {
				return false;
			}
		} else {
			String suffix = ewm.substring(ewm.length() - 1);
			if (EWM_TYPE_FM.equals(suffix) || EWM_TYPE_ML.equals(suffix)
					|| EWM_TYPE_NR.equals(suffix)) {
				return true;
			}
			return false;
		}
	}

}
