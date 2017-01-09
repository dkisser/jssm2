package org.lf.jssm.action.query;

import javax.servlet.http.HttpSession;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.service.model.AjjyOuterHistoryModel;
import org.lf.jssm.service.model.AjjyOuterHistoryParam;
import org.lf.jssm.service.query.AJJYOuterHistoryService;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/query/")
public class AJJYOuterHistoryController implements BaseController {
	@Autowired
	private AJJYOuterHistoryService ajjyOuterHistoryService;
	
	@RequestMapping("ajjyOuterHistoryUI.do")
	public String ajjyOuterHistory(){
		return "query/ajjyOuterHistory";
	}
	
	@RequestMapping("ajjyOuterHistoryData.do")
	@ResponseBody 
	public String  getListModel(AjjyOuterHistoryParam param, HttpSession session){
		session.removeAttribute("historyParam");
		session.setAttribute("historyParam", param);
		return "success";
	}
	
	@RequestMapping("ajjyOuterHistoryDatagridData.do")
	@ResponseBody
	public EasyuiDatagrid<AjjyOuterHistoryModel> getData(int rows, int page, HttpSession session){
		AjjyOuterHistoryParam param = (AjjyOuterHistoryParam) session.getAttribute("historyParam");
		return ajjyOuterHistoryService.getAjjyOutHistory(rows, page, param);
	}
}
