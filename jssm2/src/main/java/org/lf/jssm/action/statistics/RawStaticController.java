package org.lf.jssm.action.statistics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.lf.jssm.service.statistics.RawStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/statistics/")
public class RawStaticController {
	
	@Autowired
	private RawStaticService rawStaticService;
	
	/**
	 *跳转到扫描工作统计页面
	 * **/
	@RequestMapping("rawStaticUI.do")
	public String rawStaticUI() {
		return "statistics/rawStatic";
	}
	
	/**
	 * 导出扫描工作统计数据到xls文件，返回给客户端浏览器
	 * 
	 * @throws IOException
	 **/
	@RequestMapping("outputStaticDataToXls.do")
	@ResponseBody
	public void outputStaticData(HttpServletResponse response) throws IOException {
		OutputStream output = response.getOutputStream();
		response.reset();
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		String filename = String.valueOf(year - 4) + "-" + year;
		response.setHeader("Content-disposition", "attachment; filename=" + new String((filename + "扫描工作统计.xls").getBytes("gb2312"), "ISO8859-1"));
		response.setContentType("application/msexcel");
		HSSFWorkbook wb = rawStaticService.createHSSFWorkbook();
		wb.write(output);
		output.close();
	}

}
