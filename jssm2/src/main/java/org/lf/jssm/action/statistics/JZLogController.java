package org.lf.jssm.action.statistics;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.service.model.LJzModel;
import org.lf.jssm.service.model.LJzParam;
import org.lf.jssm.service.statistics.JZLogService;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/statistics/")
public class JZLogController implements BaseController {
	
	@Autowired
	private JZLogService jZLogService;
	
	@RequestMapping("jzLogUI.do")
	public String JZLogUI(){
		return "statistics/jzLog";
	}
	
	@RequestMapping("formSubmit.do")
	@ResponseBody
	public String formSub(LJzParam param, HttpSession session){
		session.removeAttribute("queryParam");
		session.setAttribute("queryParam", param);
		return "success";
	}
	
	/**
	 * 查找表格数据
	 * */
	@RequestMapping("findJzLogInfo.do")
	@ResponseBody
	public EasyuiDatagrid<LJzModel> findjzLogInfo(int rows,int page,HttpSession session){
		LJzParam param = (LJzParam)session.getAttribute("queryParam");
		return jZLogService.findjzLogInfo(rows,page,param);
	}
	
	/**
	 * 查出所有的操作者
	 * **/
	@RequestMapping("findOperators.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<Integer,String>> findOperators(){
		return jZLogService.findOperators();
	}
	
	
}
