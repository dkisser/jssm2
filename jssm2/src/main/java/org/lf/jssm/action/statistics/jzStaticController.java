package org.lf.jssm.action.statistics;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.lf.jssm.service.statistics.JZStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 历年卷宗统计分析
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/statistics/")
public class jzStaticController {

	@Autowired
	private JZStatisticsService jZStatisticsService;

	@RequestMapping("jzStaticUI.do")
	public String jzStatic(Model m) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		Integer year = Integer.parseInt(sdf.format(date));
		// 查询出当前年份近5年的数据
		for (int i = 0; i < 5; i++) {
			m.addAttribute("jzStatistics" + i, jZStatisticsService.findAlldata(year - i));
		}
		return "statistics/jzStatic";
	}

	@RequestMapping("getPictureData.do")
	@ResponseBody
	public List<List<Integer>> getAllRawFile() {
		return jZStatisticsService.getDataList();
	}

	/**
	 * 导出xls文件
	 * 
	 * @throws IOException
	 */
	@RequestMapping("exportJzStaticXls.do")
	public void exportJzStaticXls(HttpServletResponse response) throws IOException {
		jZStatisticsService.exportJzStaticXls(response);
	}

}
