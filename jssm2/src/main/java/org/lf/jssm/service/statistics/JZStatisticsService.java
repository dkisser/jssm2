package org.lf.jssm.service.statistics;

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
import java.util.Set;
import java.util.TreeMap;
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
import org.lf.jssm.db.dao.JJzMapper;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.service.model.DayInfo;
import org.lf.jssm.service.model.JzStatistics;
import org.lf.jssm.service.model.JzStatisticsMonths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 统计分析服务层
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("deprecation")
@Service("jZStatisticsService")
public class JZStatisticsService {
	@Autowired
	private JJzMapper jjzDao;
	@Autowired
	private JRawfileMapper jRawfileMapper;

	public JzStatistics findAlldata(Integer year) {
		JzStatistics data = new JzStatistics();
		JzStatisticsMonths months = null;
		List<JzStatisticsMonths> yearDatas = jjzDao.findAllygd(year);
		if (yearDatas != null) {
			for (int i = 0; i < yearDatas.size(); i++) {
				months = yearDatas.get(i);
				if (months.getStatus() == 1) {
					data.setYgd(months.getData());
				} else {
					data.setWgd(months.getData());
				}
			}
		}
		data.setYear(year);
		if (data.getWgd() == null) {
			data.setWgd(newStatusList()); // 初始化当年为空的未归档案卷
		}
		if (data.getYgd() == null) {
			data.setYgd(newStatusList()); // 初始化当年为空的已归档案卷
		}
		return data;
	}

	/**
	 * 初始化状态为空的当年每月的数据
	 */
	public ArrayList<Integer> newStatusList() {
		List<Integer> newStatusList = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			newStatusList.add(0);
		}
		return (ArrayList<Integer>) newStatusList;
	}

	/**
	 * 返回近5年已归档的总卷数
	 */
	public Integer getSumHasGd(Integer startYear, Integer endYear) {
		return jjzDao.sumHasGdJzStatic(startYear, endYear);
	}

	/**
	 * 返回近5年未归档的总卷数
	 */
	public Integer getSumHasNotGd(Integer startYear, Integer endYear) {
		return jjzDao.sumHasNotGdJzStatic(startYear, endYear);
	}

	/**
	 * 通过月份统计近5年按照是否归档的卷宗数目
	 * 
	 * @param month
	 * @param startYear
	 * @param endYear
	 * @param status
	 * @return
	 */
	public Integer getJZStatusByMonth(Integer month, Integer startYear, Integer endYear, Integer status) {
		return jjzDao.countJZStatusByMonth(month, startYear, endYear, status);
	}

	/**
	 * 导出xls文件
	 */
	public void exportJzStaticXls(HttpServletResponse response) throws IOException {
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
		cell0.setCellValue(new HSSFRichTextString("卷 宗 整 理 情 况 一 览"));
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
					cell.setCellValue("已归档");
					cell.setCellStyle(sixStyle(workbook));
				}
				if (i > 1) {
					cell.setCellValue(findAlldata(year - (start / 2)).getYgd().get(i - 2));
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
					cell.setCellValue("未归档");
					cell.setCellStyle(sevenStyle(workbook));
				}
				if (i > 1) {
					cell.setCellStyle(thirdStyle(workbook));
					cell.setCellValue(findAlldata(year - (start / 2)).getWgd().get(i - 2));
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
				cell.setCellValue("已归档");
				cell.setCellStyle(sixStyle(workbook));
			}
			if (i > 1) {
				cell.setCellStyle(secondStyle(workbook));
				cell.setCellValue(getJZStatusByMonth(i - 1, year - 4, year, 1));
			}
		}

		// 产生小计行第二行
		HSSFRow row4 = sheet.createRow(14);
		row4.setHeight((short) 450);
		for (int i = 0; i < 14; i++) {
			HSSFCell cell = row4.createCell(i);
			cell.setCellStyle(firstStyle(workbook));
			if (i == 1) {
				cell.setCellValue("未归档");
				cell.setCellStyle(sevenStyle(workbook));
			}
			if (i > 1) {
				cell.setCellStyle(thirdStyle(workbook));
				cell.setCellValue(getJZStatusByMonth(i - 1, year - 4, year, 0));
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
				cell.setCellValue("已归档");
			}
			if (i > 1) {
				cell.setCellStyle(fifthStyle(workbook));
				// 合计已归档
				cell.setCellValue(getSumHasGd(year - 4, year));
			}
		}
		HSSFRow row7 = sheet.createRow(17);
		for (int i = 0; i < 3; i++) {
			HSSFCell cell = row7.createCell(i);
			cell.setCellStyle(forthStyle(workbook));
			if (i == 1) {
				cell.setCellValue("未归档");
			}
			if (i > 1) {
				cell.setCellStyle(fifthStyle(workbook));
				// 合计未归档
				cell.setCellValue(getSumHasNotGd(year - 4, year));
			}
		}
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(16, 17, 0, 0));
		// 弹出下载框
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String fileName = year - 4 + "-" + year + "卷宗整理情况统计"; // 下载的文件名字
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

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	private Date date = new Date();

	/**
	 * 功能:查j_rawfile中的所有记录
	 * 
	 * @return
	 */
	public List<JRawfile> getAllRawFile() {
		System.out.println(jRawfileMapper.getAllRawFile());
		return jRawfileMapper.getAllRawFile();
	}

	/**
	 * 功能:将所有的数据中的scan_month和scan_year放入一个map<Integer,List<Integer>>
	 * 前面是scan_year,后面是scan_month List中的下标代表月份（从0开始）
	 * 
	 * @return
	 */
	public Map<Integer, List<Integer>> getDataMap() {
		Map<Integer, List<Integer>> dataMap = new TreeMap<Integer, List<Integer>>();
		for (int i = Integer.parseInt(sdf.format(date)); i > Integer.parseInt(sdf.format(date)) - 5; i--) {
			List<Integer> number = new ArrayList<Integer>();
			for (int j = 1; j <= 12; j++) {
				Integer a = 0;
				if (j <= 9) {
					a = jRawfileMapper.getPictureNumberByYearAndMonth(String.valueOf(i), String.valueOf("0" + j));
				} else {
					a = jRawfileMapper.getPictureNumberByYearAndMonth(String.valueOf(i), String.valueOf(j));
				}
				number.add(a);
			}
			dataMap.put(i, number);
		}
		return dataMap;
	}

	/**
	 * 功能:将上面的map内容放入一个List
	 */
	public List<List<Integer>> getDataList() {
		Map<Integer, List<Integer>> map = this.getDataMap();
		List<List<Integer>> dataList = new ArrayList<List<Integer>>();
		Set<Integer> yearSet = map.keySet();
		for (Integer i : yearSet) {
			dataList.add(map.get(i));
		}
		return dataList;
	}

}
