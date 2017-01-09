package org.lf.jssm.action.query;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.lf.jssm.service.model.JZQueryParam;
import org.lf.jssm.service.model.VJzModel;
import org.lf.jssm.service.query.JZQueryService;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 内部查询
 */
@Controller
@RequestMapping("/query/")
public class JZQueryController {
	@Autowired
	private JZQueryService jzQueryService;
	
	@RequestMapping("formSubmit.do")
	@ResponseBody
	public String formSub(JZQueryParam param, HttpSession session){
		session.removeAttribute("queryParam");
		session.setAttribute("queryParam", param);
		return "success";
	}
	
	@RequestMapping("query_Datagrid.do")
	@ResponseBody
	public EasyuiDatagrid<VJzModel> getData(int rows, int page, HttpSession session){
		JZQueryParam param = (JZQueryParam) session.getAttribute("queryParam");
		if(param == null){
			return new EasyuiDatagrid<VJzModel>();
		}
		EasyuiDatagrid<VJzModel> vjzModel = jzQueryService.getVjzList(rows, page,param);
		return vjzModel;
	}
	
	
	@RequestMapping("query_ahzInfo.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<Integer, String>> getahzList(){
		return jzQueryService.getAllAhzInfo();
	}
	
	@RequestMapping("query_jzlxInfo.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<Integer, String>> getjzlxList(){
		return jzQueryService.getJzlxInfo();
	}
	
	@RequestMapping("query_aqjbInfo.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<Integer, String>> getaqjbList(){
		return jzQueryService.getAqjbInfo();
	}
	
	@RequestMapping("query_bgqxInfo.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<Integer, String>> getbgqxList(){
		return jzQueryService.getBgqxInfo();
	}
	
	@RequestMapping("query_jzztInfo.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<Integer, String>> getjzztList(){
		return jzQueryService.getJzztInfo();
	}
}
