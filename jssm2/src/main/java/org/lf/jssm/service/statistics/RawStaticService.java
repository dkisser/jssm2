package org.lf.jssm.service.statistics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.service.model.DayInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rawStaticService")
public class RawStaticService {

	@Autowired
	private JRawfileMapper jRawfileMapper;
	
	public HSSFWorkbook createHSSFWorkbook() {
		HSSFWorkbook wb = new HSSFWorkbook(); // 创建HSSFWorkbook对象（Excel文档对象）
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		for (int i = year; i >= year - 4; i--) {
			HSSFSheet sheet = wb.createSheet(String.valueOf(i)); // 创建新的sheet对象（excel的表单）
			sheet.setDefaultRowHeightInPoints(18); // 设置缺省列高
			sheet.setDefaultColumnWidth(10); // 设置缺省列宽
			HSSFRow row1 = sheet.createRow(0); // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
			HSSFCell cell = row1.createCell(0); // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
			cell.setCellValue(i + "年扫描工作统计表"); // 设置单元格的值

			// 创建字体样式1
			HSSFFont fontStyle1 = wb.createFont(); // 创建字体样式对象
			fontStyle1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 设置粗体
			// 创建字体样式2
			HSSFFont fontStyle2 = wb.createFont(); // 创建字体样式对象
			fontStyle2.setBoldweight(HSSFFont.DEFAULT_CHARSET); // 设置默认字体
			// 创建样式1，25度灰前景，黑色边框，粗体字
			HSSFCellStyle cellStyle1 = createCellStyle(wb, IndexedColors.GREY_25_PERCENT.getIndex(), fontStyle1);
			// 创建样式2，亮蓝前景，黑色边框，默认字体
			HSSFCellStyle cellStyle2 = createCellStyle(wb, IndexedColors.LIGHT_TURQUOISE.getIndex(), fontStyle2);
			// 创建样式3，白色前景，黑色边框，默认字体
			HSSFCellStyle cellStyle3 = createCellStyle(wb, IndexedColors.WHITE.getIndex(), fontStyle2);

			cell.setCellStyle(createCellStyle(wb, IndexedColors.WHITE.getIndex(), fontStyle1));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12)); // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列，
																		// 截至列

			HSSFRow row2 = sheet.createRow(1); // 在sheet里创建第二行
			// 创建单元格并设置单元格内容
			HSSFCell cell2 = row2.createCell(0);
			cell2.setCellValue("");
			cell2.setCellStyle(cellStyle1);
			for (int j = 1; j <= 12; j++) {
				cell2 = row2.createCell(j);
				cell2.setCellValue(j + "月");
				cell2.setCellStyle(cellStyle1);
			}

			Map<Integer, List<Integer>> map = getSMDataByYear(i); // 获取数据库中扫描统计的数据
			for (int k = 1; k <= 31; k++) { // 在sheet中创建1~31行
				HSSFRow row = sheet.createRow(k + 1);
				HSSFCell cell1 = row.createCell(0);
				cell1.setCellValue(k);
				cell1.setCellStyle(cellStyle1);
				for (int m = 1; m <= 12; m++) { // 在第k行中创建1~12个单元格并赋值
					cell1 = row.createCell(m);
					cell1.setCellValue(map.get(m).get(k - 1));
					if (k % 2 == 0) {
						cell1.setCellStyle(cellStyle2);
					} else {
						cell1.setCellStyle(cellStyle3);
					}
				}
			}

			// 设置小计行
			HSSFRow row4 = sheet.createRow(33);
			HSSFCell cell4 = row4.createCell(0);
			cell4.setCellValue("小计");
			cell4.setCellStyle(cellStyle1);
			for (int n = 1; n <= 12; n++) {
				int sum = 0;
				for (int x : map.get(n)) {
					sum += x;
				}
				cell4 = row4.createCell(n);
				cell4.setCellValue(sum);
				cell4.setCellStyle(cellStyle2);
			}
			// 设置合计行
			HSSFRow row5 = sheet.createRow(35);
			HSSFCell cell5 = row5.createCell(0);
			cell5.setCellValue("合计");
			cell5.setCellStyle(cellStyle1);
			int sum = 0;
			for (int n = 1; n <= 12; n++) {
				for (int x : map.get(n)) {
					sum += x;
				}
			}
			cell5 = row5.createCell(1);
			cell5.setCellValue(sum);
			cell5.setCellStyle(cellStyle3);
		}
		return wb;
	}
	
	/**
	 * 获取Year年份1~12月1~31号的扫描次数
	 */
	public Map<Integer, List<Integer>> getSMDataByYear(Integer year) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 1; i <= 12; i++) {
			List<Integer> list = new ArrayList<>();
			// 初始化list,使它有31个元素，值为0
			int j = 0;
			while (j < 31) {
				list.add(0);
				j++;
			}
			// 查出i月份1~31天的扫描次数
			List<DayInfo> list1 = jRawfileMapper.SelectByMonth(year, i < 10 ? Integer.parseInt("0" + i) : i);//
			for (DayInfo info : list1) {
				list.remove(info.getDay() - 1);
				list.add(info.getDay() - 1, info.getCount());
			}
			map.put(i, list);
		}
		return map;
	}

	/**
	 *  用于生成单元格样式
	 * @param wb
	 * @param color
	 * @param fontStyle
	 * @return
	 */
	public HSSFCellStyle createCellStyle(HSSFWorkbook wb, short color, HSSFFont fontStyle) {
		// 创建单元格样式对象
		HSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格的横向和纵向对齐方式，具体参数就不列了，参考HSSFCellStyle
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		cellStyle.setFillForegroundColor(color); // 设置前景颜色
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 设置填充方式(填充图案)
		cellStyle.setFont(fontStyle); // 将字体对象赋予单元格样式对象

		return cellStyle;
	}
}
