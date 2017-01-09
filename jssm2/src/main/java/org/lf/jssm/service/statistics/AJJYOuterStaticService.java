package org.lf.jssm.service.statistics;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Test;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.JWlryMapper;
import org.lf.jssm.db.pojo.JWlry;
import org.lf.jssm.service.model.AJJYOuterStaticModel;
import org.lf.jssm.service.model.DayInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;

@Service("ajjyOuterstaticService")
public class AJJYOuterStaticService {

	@Autowired
	private JWlryMapper jwlryDao;
	
	/**
	 * 功能： 得到j_wlry所有的记录
	 * @return
	 */
	public List<JWlry> getAllWlry () {
		return jwlryDao.getAllJWlry();
	}
	/**
	 * 功能:得到某一年的借阅情况数据
	 * @return
	 */
	public List<Integer[]> getPictureData(List<JWlry> wlryList) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		List<Integer[]> dataList = new ArrayList<Integer[]>();
		Integer[] permit = new Integer []{0,0,0,0,0,0,0,0,0,0,0,0};
		Integer[] unpermit = new Integer []{0,0,0,0,0,0,0,0,0,0,0,0};
		for (JWlry wlry : wlryList) {
			for (int i=0;i<permit.length;i++) {
				int month = Integer.parseInt(sdf.format(wlry.getKsrq()));
				if (month==i+1) {
					if (wlry.getPrint().equals("1")) {
						permit[i]++;
					} else {
						unpermit[i]++;
					}
				} 
			}
		}
		dataList.add(permit);
		dataList.add(unpermit);
		return dataList;
	}
	/**
	 * 功能:查在某一年内的所有借阅记录
	 * @param year
	 * @return
	 */
	public List<JWlry> getJWlryByYear (Integer year) {
		String beginYear = year + "-01-01 00:00:00";
		String endYear = year + "-12-31 23:59:59";
		return jwlryDao.getJWlryByYear(beginYear,endYear);
	}
	
	/**
	 * 功能:根据年份得到对应的借阅数据Map
	 */
	public Map<String,List<Integer[]>> getPictureDataByYear (Integer year) {
		Map<String,List<Integer[]>> dataMap = new HashMap<String,List<Integer[]>>();
		List<JWlry> wlryList = getJWlryByYear(year);
		List<Integer[]> countList = getPictureData(wlryList);
		dataMap.put("dataList", countList);
		return dataMap;
	} 
	
	/**
	 * 功能:得到5年查阅允许打印的案卷数量
	 * @return
	 */
	public Integer getSumPermit (Integer beginYear,Integer endYear) {
		String beginDate = beginYear + "-01-01 00:00:00";
		String endDate =  endYear + "-12-31 23:59:59";
		return jwlryDao.getSumPermitORUnPermit(beginDate, endDate, "1");
	} 
	/**
	 * 功能:得到5年查阅不允许打印的案卷数量
	 * @return
	 */
	public Integer getSumUnPermit (Integer beginYear,Integer endYear) {
		String beginDate = beginYear + "-01-01 00:00:00";
		String endDate =  endYear + "-12-31 23:59:59";
		return jwlryDao.getSumPermitORUnPermit(beginDate, endDate, "0");
	} 
	/**
	 * 功能:将一年的借阅数(包括允许的和不允许的都放到一个model中)
	 * @param year
	 * @return
	 */
	public AJJYOuterStaticModel getAllDataByYear (Integer year){
		AJJYOuterStaticModel ajOuter = new AJJYOuterStaticModel();
		List<JWlry> wlryList = getJWlryByYear(year);
		List<Integer[]> countList = getPictureData(wlryList);
		List<Integer> permitList = new ArrayList<Integer>();
		List<Integer> unpermitList = new ArrayList<Integer>();
		ajOuter.setYear(year);
		for (Integer j: countList.get(0)) {
			permitList.add(j);
		}
		ajOuter.setPermit(permitList);
		for (Integer j: countList.get(1)) {
			unpermitList.add(j);
		}
		ajOuter.setUnpermit(unpermitList);
		return ajOuter;
	}
	
	/**
	 * 功能:根据月份得到5年的借阅数量
	 * @param month
	 * @param startYear
	 * @param endYear
	 * @param status
	 * @return
	 */
	public Integer getAjjyOuterByMonth(Integer month, Integer startYear, Integer endYear, Integer print) {
		String Month = "";
		if (month>=1&&month<=9) {
			Month = "0"+month;
		} else {
			Month = month +"";
		}
		int count = 0;
		for (int i=startYear;i<=endYear;i++) {
			String beginDate = i + "-" + Month + "-01"+" 00:00:00";
			String Print = print+"";
			String endDate ="";
			if (i%4==0&&i%100!=0||i%400==0) {
				if (month == 2) {
					endDate = i + "-" + Month + "-29"+" 23:59:59";
				} else if (month==1||month==3||month==5||month==7||month==8||month==10||month==12){
					endDate  =  i + "-" + Month + "-31"+" 23:59:59";
				} else {
					endDate = i + "-" + Month + "-30"+" 23:59:59";
				}
			} else {
				if (month == 2) {
					endDate = i + "-" + Month + "-28"+" 23:59:59";
				} else if (month==1||month==3||month==5||month==8||month==10||month==12){
					endDate  =  i + "-" + Month + "-31"+" 23:59:59";
				} else {
					endDate = i + "-" + Month + "-30"+" 23:59:59";
				}
			}
			count += jwlryDao.getSumPermitORUnPermit(beginDate, endDate, Print);
		}
		
		return count;
	}
	/**
	 * 功能:导出Excel表
	 * @param response
	 * @throws IOException
	 */
	public void exportAjjyOuterXls(HttpServletResponse response) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
		HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
		sheet.setDefaultColumnWidth(10);
		sheet.setColumnWidth(0, 15 * 256);
		sheet.setColumnWidth(1, 15 * 256);

		workbook.setSheetName(0, "Sheet1");
		// 产生一行
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeight((short) 650);
		// 产生一个单元格
		HSSFCell cell0 = row0.createCell(0);
		cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell0.setCellValue(new HSSFRichTextString("卷 宗 对 外 借 阅 情 况 一 览"));
		// 指定合并区域
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
		cell0.setCellStyle(titleStyle(workbook));
		// 产生第二行
		HSSFRow row1 = sheet.createRow(1);
		// 产生第二行的单元格
		for (int i = 0; i < 14; i++) {
			row1.createCell(i);
		}
		// 产生第三行
		HSSFRow row2 = sheet.createRow(2);
		row2.setHeight((short) 450);
		// 产生第三行的单元格
		for (int i = 0; i < 14; i++) {
			HSSFCell cell = row2.createCell(i);
			cell.setCellStyle(firstStyle(workbook));
			if (i > 1) {
				cell.setCellValue(i - 1 + "月");
			}
		}
		// 取得当年的年份
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		Integer year = Integer.parseInt(sdf.format(date));
		// 迭代产生以下的行数，按照年份降序生成对应的数据
		for (int start = 0; start < 10; start += 2) {
			// 产生迭代行的第1行
			HSSFRow rowFirst = sheet.createRow(3 + start);
			rowFirst.setHeight((short) 450);
			for (int i = 0; i < 14; i++) {
				HSSFCell cell = rowFirst.createCell(i);
				cell.setCellStyle(firstStyle(workbook));
				if (i == 0) {
					cell.setCellValue(year - (start / 2) + "年");
				}
				if (i == 1) {
					cell.setCellValue("允许打印");
					cell.setCellStyle(sixStyle(workbook));
				}
				if (i > 1) {
					AJJYOuterStaticModel outer = getAllDataByYear(year - (start / 2));
					cell.setCellValue(outer.getPermit().get(i-2));
					cell.setCellStyle(secondStyle(workbook));
				}
			}

			// 产生迭代行的第2行
			HSSFRow rowFinally = sheet.createRow(4 + start);
			rowFinally.setHeight((short) 450);
			for (int i = 0; i < 14; i++) {
				HSSFCell cell = rowFinally.createCell(i);
				cell.setCellStyle(firstStyle(workbook));
				if (i == 1) {
					cell.setCellValue("不允许打印");
					cell.setCellStyle(sevenStyle(workbook));
				}
				if (i > 1) {
					cell.setCellStyle(thirdStyle(workbook));
					cell.setCellValue(getAllDataByYear(year - (start / 2)).getUnpermit().get(i - 2));
				}
			}
			// 合并单元格
			sheet.addMergedRegion(new CellRangeAddress(3 + start, 4 + start, 0, 0));
		}

		// 产生小计行第一行
		HSSFRow row3 = sheet.createRow(13);
		row3.setHeight((short) 450);
		for (int i = 0; i < 14; i++) {
			HSSFCell cell = row3.createCell(i);
			cell.setCellStyle(firstStyle(workbook));
			if (i == 0) {
				cell.setCellValue("小计");
			}
			if (i == 1) {
				cell.setCellValue("允许打印");
				cell.setCellStyle(sixStyle(workbook));
			}
			if (i > 1) {
				cell.setCellStyle(secondStyle(workbook));
				cell.setCellValue(getAjjyOuterByMonth(i - 1, year - 4, year, 1));//?
			}
		}

		// 产生小计行第二行
		HSSFRow row4 = sheet.createRow(14);
		row4.setHeight((short) 450);
		for (int i = 0; i < 14; i++) {
			HSSFCell cell = row4.createCell(i);
			cell.setCellStyle(firstStyle(workbook));
			if (i == 1) {
				cell.setCellValue("不允许打印");
				cell.setCellStyle(sevenStyle(workbook));
			}
			if (i > 1) {
				cell.setCellStyle(thirdStyle(workbook));
				cell.setCellValue(getAjjyOuterByMonth(i - 1, year - 4, year, 0));//?
			}
		}
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(13, 14, 0, 0));
		// 空行
		@SuppressWarnings("unused")
		HSSFRow row5 = sheet.createRow(15);
		// 合计行
		HSSFRow row6 = sheet.createRow(16);
		for (int i = 0; i < 3; i++) {
			HSSFCell cell = row6.createCell(i);
			cell.setCellStyle(forthStyle(workbook));
			if (i == 0) {
				cell.setCellValue("合计");
			}
			if (i == 1) {
				cell.setCellValue("允许打印");
			}
			if (i > 1) {
				cell.setCellStyle(fifthStyle(workbook));
				// 合计已归档
				cell.setCellValue(getSumPermit(year - 4, year));//?
			}
		}
		HSSFRow row7 = sheet.createRow(17);
		for (int i = 0; i < 3; i++) {
			HSSFCell cell = row7.createCell(i);
			cell.setCellStyle(forthStyle(workbook));
			if (i == 1) {
				cell.setCellValue("不允许打印");
			}
			if (i > 1) {
				cell.setCellStyle(fifthStyle(workbook));
				// 合计未归档
				cell.setCellValue(getSumUnPermit(year - 4, year));//?
			}
		}
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(16, 17, 0, 0));
		// 弹出下载框
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String fileName = year - 4 + "-" + year + "卷宗借阅情况统计"; // 下载的文件名字
		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));

		ServletOutputStream out = response.getOutputStream();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			workbook.close();
		}

	}
	public HSSFCellStyle titleStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 创建单元格样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中
		// 设置标题字体样式
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setBold(true);
		font.setFontHeight((short) 480);
		cellStyle.setFont(font);
		return cellStyle;
	}

	public HSSFCellStyle firstStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 创建单元格样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); // 背景色
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置第二种字体样式
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setFontName("宋体");
		font2.setBold(true);
		font2.setFontHeight((short) 240);
		cellStyle.setFont(font2);
		return cellStyle;
	}

	public HSSFCellStyle secondStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 创建单元格样式
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中
		// 边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置第三种字体样式
		HSSFFont font2 = workbook.createFont();
		font2.setFontName("宋体");
		font2.setFontHeight((short) 240);
		cellStyle.setFont(font2);
		return cellStyle;
	}

	public HSSFCellStyle thirdStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 创建单元格样式
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex()); // 背景色
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置第三种字体样式
		HSSFFont font2 = workbook.createFont();
		font2.setFontName("宋体");
		font2.setFontHeight((short) 240);
		cellStyle.setFont(font2);
		return cellStyle;
	}

	public HSSFCellStyle forthStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 创建单元格样式
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		// 设置第二种字体样式
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setFontName("宋体");
		font2.setBold(true);
		font2.setFontHeight((short) 240);
		cellStyle.setFont(font2);
		return cellStyle;
	}

	public HSSFCellStyle fifthStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 创建单元格样式
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中
		// 设置第三种字体样式
		HSSFFont font2 = workbook.createFont();
		font2.setFontName("宋体");
		font2.setFontHeight((short) 240);
		cellStyle.setFont(font2);
		return cellStyle;
	}

	public HSSFCellStyle sixStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 创建单元格样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置第二种字体样式
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setFontName("宋体");
		font2.setBold(true);
		font2.setFontHeight((short) 240);
		cellStyle.setFont(font2);
		return cellStyle;
	}

	public HSSFCellStyle sevenStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 创建单元格样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中

		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex()); // 背景色
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置第二种字体样式
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setFontName("宋体");
		font2.setBold(true);
		font2.setFontHeight((short) 240);
		cellStyle.setFont(font2);
		return cellStyle;
	}
	
}
