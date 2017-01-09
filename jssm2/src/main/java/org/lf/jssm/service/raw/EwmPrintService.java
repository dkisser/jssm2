package org.lf.jssm.service.raw;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lf.jssm.db.dao.JEnvMapper;
import org.lf.jssm.db.dao.TEwmMapper;
import org.lf.jssm.db.pojo.JEnv;
import org.lf.jssm.db.pojo.TEwm;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.ErrorCorrectionLevel;

/**
 * 原始档案服务层
 * 
 * @author sunwill
 *
 */
@Service("ewmPrintService")
public class EwmPrintService {
	/**
	 * 二维码行数
	 */
	private static final int EWM_ROW_NUM = 10;
	/**
	 * 二维码列数
	 */
	private static final int EWM_COL_NUM = 8;
	/**
	 * 当天二维码的最大序号
	 */
	public static final int MAX_EWN_NUMBER = 99999;
	@Autowired
	private JEnvMapper jEnvDao;
	@Autowired
	private TEwmMapper tEwmDao;

	/**
	 * 生成二维码
	 * 
	 * @param ewmType
	 *            类型
	 * @param pageNumbers
	 *            页数
	 * @return
	 */
	public synchronized List<List<String>> getEwms(String ewmType,
			String pageNumbers) {
		if (StringUtils.isEmpty(ewmType) || StringUtils.isEmpty(pageNumbers)) {
			return null;
		}
		Integer pageNum = Integer.parseInt(pageNumbers);// 页数
		if (!validPageNum(pageNum)) {
			return null;
		}
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date currDate = new Date();
		String currDateStr = sdf.format(currDate);
		JEnv jev = gerFydmInfo();
		// 查询当天是否存在生成记录
		TEwm tem = tEwmDao.selectByDate(currDate);
		Integer lastId;
		if (tem == null || tem.getLastId() == null) {
			lastId = -1;// 新序号比当前最大序号大一
		} else {
			lastId = tem.getLastId();
		}
		if (lastId >= MAX_EWN_NUMBER) {
			return null;// 序号最大到99999
		}
		if (jev != null && !StringUtils.isEmpty(jev.getEnvValue())) {
			// 二维码前缀，时间加法院代码
			String ewmPrefix = currDateStr + jev.getEnvValue();
			// 行集合
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (int i = 1; i <= EWM_ROW_NUM * pageNum; i++) {
				// 列集合
				List<String> colList = new ArrayList<String>();
				for (int n = 1; n <= EWM_COL_NUM; n++) {
					lastId++;// 编号加1
					String ewm = ewmPrefix
							+ StringUtils.lpad(lastId + "", '0', 5)
							+ ewmType.trim();
					colList.add(ewm);
					if (lastId >= MAX_EWN_NUMBER) {
						break;
					}
				}
				rowList.add(colList);
				if (lastId >= MAX_EWN_NUMBER) {
					break;
				}
			}
			if (tem == null) {
				// 当天存不在记录则插入新数据
				tem = new TEwm();
				tem.setId(StringUtils.getUUID());
				tem.setCreateDate(currDate);
				tem.setLastId(lastId);
				tEwmDao.insert(tem);
			} else {
				// 当天存在记录则更新
				tem.setCreateDate(currDate);
				tem.setLastId(lastId);
				tEwmDao.updateById(tem);
			}
			return rowList;
		}
		return null;
	}

	/**
	 * 生成二维码pdf文件并以流的形式输出
	 * 
	 * @param ewmType
	 * @param pageNumbers
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public synchronized String getEwmPdf(String ewmType, String pageNumbers,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (StringUtils.isEmpty(ewmType) || StringUtils.isEmpty(pageNumbers)) {
			return null;
		}
		Integer pageNum = Integer.parseInt(pageNumbers);// 页数
		if (!validPageNum(pageNum)) {
			return null;
		}
		JEnv jev = gerFydmInfo();// 查询法院代码
		if (jev == null || StringUtils.isEmpty(jev.getEnvValue())) {
			return null;
		}
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date currDate = new Date();
		String currDateStr = sdf.format(currDate);
		// 查询当天是否存在生成记录
		TEwm tem = tEwmDao.selectByDate(currDate);
		Integer lastId;
		if (tem == null || tem.getLastId() == null) {
			lastId = -1;// 新序号比当前最大序号大一
		} else {
			lastId = tem.getLastId();
		}
		if (lastId >= MAX_EWN_NUMBER) {
			return null;// 序号最大到99999
		}
		String ewmPrefix = currDateStr + jev.getEnvValue();// 二维码前缀，时间加法院代码
		int ewmTotalNum = getEwmTotalNum(pageNum);// 获得二维码
		// String serverDir =
		// request.getSession().getServletContext().getRealPath("/");
		return createPdfFile(tem, currDate, lastId, ewmPrefix, ewmTotalNum,
				ewmType, request, response);
	}

	/**
	 * 生成pdf文件
	 * 
	 * @param tem
	 * 
	 * @param currDate
	 * 
	 * @param lastId
	 * @param ewmPrefix
	 * @param ewmTotalNum
	 * @param ewmType
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String createPdfFile(TEwm tem, Date currDate, Integer lastId,
			String ewmPrefix, int ewmTotalNum, String ewmType,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Document doc = new Document();// 创建文档对象
		// 页边空白
		doc.setMargins(0, 0, 0, 0);
		// pdf文件临时存放目录
		String tmpPdfDir = request.getServletContext().getRealPath("/")
				+ "ewmTempPdf.pdf";
		FileOutputStream fos = new FileOutputStream(tmpPdfDir);
		try {
			// 定义输出文件的位置
			PdfWriter.getInstance(doc, fos);
			// 开启文档
			doc.open();
			// 设置二维码字符集和容错级别
			Map<com.itextpdf.text.pdf.qrcode.EncodeHintType, Object> hints = new HashMap<com.itextpdf.text.pdf.qrcode.EncodeHintType, Object>();
			hints.put(
					com.itextpdf.text.pdf.qrcode.EncodeHintType.CHARACTER_SET,
					"UTF-8");
			hints.put(
					com.itextpdf.text.pdf.qrcode.EncodeHintType.ERROR_CORRECTION,
					ErrorCorrectionLevel.H);
			// 最外层table，8列
			PdfPTable table = new PdfPTable(8);
			table.setSpacingBefore(0);
			table.setSpacingAfter(0);
			// 无边框
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			table.setWidthPercentage(100);
			PdfPCell cell = null;
			ewmType = ewmType.trim();
			int beginId = lastId + 1;
			for (int i = 0; i < ewmTotalNum; i++) {
				lastId++;
				String ewmInfo = ewmPrefix
						+ StringUtils.lpad(lastId + "", '0', 5) + ewmType;
				// 单元格内层嵌套table，1列
				PdfPTable nestedTab = new PdfPTable(1);
				nestedTab.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
				// 生成二维码
				BarcodeQRCode qrcode = new BarcodeQRCode(ewmInfo.trim(), 73,
						73, hints);
				// 转换为图片对象
				Image qrcodeImage = qrcode.getImage();
				qrcodeImage.setAlignment(Image.MIDDLE | Image.TEXTWRAP);
				cell = new PdfPCell(qrcodeImage);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				nestedTab.addCell(cell);// 添加图片到单元格
				// 添加二维码值到单元格
				Phrase ewmVal = new Phrase(ewmInfo, new Font(
						Font.FontFamily.TIMES_ROMAN, 6));// 设置字体和大小
				cell = new PdfPCell(ewmVal);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.NO_BORDER);
				nestedTab.addCell(cell);
				// 将内层嵌套table加入到外层table的单元格中
				cell = new PdfPCell(nestedTab);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 水平居中
				cell.setVerticalAlignment(Element.ALIGN_CENTER);// 垂直居中
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
				if (lastId >= MAX_EWN_NUMBER) {
					break;
				}
			}
			doc.add(table);
			doc.close();// 完成pdf生成
			if (tem == null) {
				// 当天存不在记录则插入新数据
				tem = new TEwm();
				tem.setId(StringUtils.getUUID());
				tem.setCreateDate(currDate);
				tem.setLastId(lastId);
				tEwmDao.insert(tem);
			} else {
				// 当天存在记录则更新
				tem.setCreateDate(currDate);
				tem.setLastId(lastId);
				tEwmDao.updateById(tem);
			}
			DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String currDateStr = sdf.format(currDate);
			// 下载pdf文件
			String fileName = "QRCode" + currDateStr + "(" + beginId + "--"
					+ lastId + ")";
			downLoadPdfFile(fileName, tmpPdfDir, response);
		} finally {
			if (doc != null) {
				doc.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return null;
	}

	/**
	 * 下载指定文件
	 * 
	 * @param fileName
	 * @param filePath
	 * @param response
	 * @throws Exception
	 */
	public void downLoadPdfFile(String fileName, String filePath,
			HttpServletResponse response) throws Exception {
		BufferedInputStream br = null;
		OutputStream out = null;
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				response.sendError(404, "File not found!");
				return;
			}
			br = new BufferedInputStream(new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.reset(); // 非常重要
			// 纯下载方式
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName + ".pdf");
			out = response.getOutputStream();
			while ((len = br.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
		} finally {
			if (br != null)
				br.close();
			if (out != null)
				out.close();
		}
	}

	/**
	 * 判断页码是否有效
	 * 
	 * @param pageNum
	 * @return 有效返回true
	 */
	private boolean validPageNum(int pageNum) {
		if (pageNum < 1 || getEwmTotalNum(pageNum) > (MAX_EWN_NUMBER + 1)) {
			return false;
		}
		return true;
	}

	/**
	 * 根据页数，计算要生成的二维码总数
	 * 
	 * @param pageNum
	 *            页数
	 * @return
	 */
	private int getEwmTotalNum(int pageNum) {
		return pageNum * EWM_ROW_NUM * EWM_COL_NUM;
	}

	/**
	 * 查询法院代码
	 * 
	 * @return
	 */
	public JEnv gerFydmInfo() {
		JEnv jev = new JEnv();
		jev.setEnvKey("SYS_FYDM");
		// 查询法院代码
		return jEnvDao.selectEnv(jev);
	}

	/**
	 * 根据文件名称，生成单个二维码
	 * 
	 * @param pureFileName
	 *            文件名称（不含后缀）
	 * @param jzewm
	 *            卷宗封面二维码
	 * @param currId
	 *            当前二维码序号
	 * @param currDateStr
	 *            当前时间
	 * @param fydm
	 *            法院代码
	 * @return
	 */
	public String generateEwm(String pureFileName, String jzewm,
			Integer currId, String currDateStr, String fydm) {
		if (StringUtils.isEmpty(pureFileName) || currDateStr == null
				|| currId == null || StringUtils.isEmpty(fydm)) {
			return null;
		}
		if (!StringUtils.isEmpty(jzewm) && pureFileName.toUpperCase().endsWith(EwmDecode.EWM_TYPE_FM)) {
			return jzewm;// 封面图片直接返回封面二维码
		}
		// 查询当天是否存在生成记录
		String ewmType = null;
		if (pureFileName.toUpperCase().endsWith(EwmDecode.EWM_TYPE_ML)) {
			ewmType = EwmDecode.EWM_TYPE_ML;
		} else {
			ewmType = EwmDecode.EWM_TYPE_NR;
		}
		return currDateStr + fydm + StringUtils.lpad(currId + "", '0', 5)
				+ ewmType.trim();
	}

	/**
	 * 获得当天最后的二维码
	 * 
	 * @param currDate
	 * @return
	 * 
	 *         public Integer getLastId(Date currDate) { TEwm tem =
	 *         tEwmDao.selectByDate(currDate); Integer lastId; if (tem == null
	 *         || tem.getLastId() == null) { lastId = -1;// 新序号比当前最大序号大一 } else
	 *         { lastId = tem.getLastId(); } if (lastId >= MAX_EWN_NUMBER) {
	 *         return null;// 序号最大到99999 } return lastId; }
	 */

}
