package org.lf.jssm.action.catalog;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.CYhzw;
import org.lf.jssm.service.catalog.CYhzwService;
import org.lf.jssm.service.model.UiautoCompleteBox;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/catalog/")
public class YhzwController implements BaseController {
	Logger logger = Logger.getLogger(YhzwController.class);
	
	@Autowired
	private CYhzwService yhzwService;
	/**
	 * 页面跳转
	 * @return
	 */	
	@RequestMapping("yhzwListUI.do")
	public String toYWZUList(){
		return "catalog/yhzwList";
	}
	

	@RequestMapping("yhzwBrowseUI.do")
	public String toYWZUBrowse(){
		return "catalog/yhzwBrowse";
	}
	
	@RequestMapping("yhzwAddInfo.do")
	public String toAddPage(String isNew,Model model){
		boolean flag=false;
		if(isNew.equals("yes")){
			flag=true;
		}
		model.addAttribute("isNew", flag);
		return "catalog/yhzwInfo";
	}
	
	/*
	 * 获取自动补全的数据
	 * 
	 */
	@RequestMapping("yhzwBrowseInfo.do")
	@ResponseBody
	public List<UiautoCompleteBox> findBrowseInfo(){
		return yhzwService.findBrowseInfo();
	}
	
	/**
	 * 获取表格数据
	 * @return
	 */
	@RequestMapping("yhzwList.do")
	@ResponseBody
	public EasyuiDatagrid<CYhzw> findAllYHZWInfo(int page,int rows){
		return yhzwService.findAllYHZWInfo(page,rows);
	}
	
	/*
	 * 表单验证
	 * 
	 */
	@RequestMapping("checkInfo.do")
	@ResponseBody
	public Map<String,Integer> checkId_Name(Integer checkid,String checkname){
		return yhzwService.checkId_Name(new DecimalFormat("000").format(checkid), checkname);
	}
	
	/**
	 * 增加信息
	 * @return
	 */
	@RequestMapping("addyhzwinfo.do")
	@ResponseBody
	public void addYZHWInfo(Integer dm,String mc,String tybz,String remark){
		yhzwService.addYZHWInfo(new DecimalFormat("000").format(dm), mc,tybz, remark);
	}
	
	/*
	 * 
	 * 更新信息
	 */
	@RequestMapping("updateyhzwinfo.do")
	@ResponseBody
	public void updeteYZHWInfo(String dm,String mc,String tybz,String remark){
		yhzwService.updeteYZHWInfo(dm, mc, tybz, remark);
	}
}
