package org.lf.jssm.action.statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.lf.jssm.service.statistics.AJJYOuterStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("statistics")
public class AJJYOuterStaticController {

	@Autowired
	private AJJYOuterStaticService ajjyOuterstaticService;
	
	@RequestMapping("getPictureDataByYear.do")
	@ResponseBody
	public Map<String,List<Integer[]>> getPictureDataByYear (Integer year) {
		return ajjyOuterstaticService.getPictureDataByYear(year);
	}
	
	@RequestMapping("ajjyOuterStaticUI.do")
	public String ajjyOuterStaticUI () {
		return "statistics/ajjyOuterStatic";
	}

	@RequestMapping("exportAjjyOuterXls.do")
	public void exportAjjyOuterXls (HttpServletResponse response) {
		try {
			ajjyOuterstaticService.exportAjjyOuterXls(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
