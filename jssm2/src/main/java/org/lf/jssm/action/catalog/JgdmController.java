package org.lf.jssm.action.catalog;


import java.util.List;

import org.lf.jssm.db.pojo.CJgdm;
import org.lf.jssm.service.catalog.CJgdmService;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/catalog/")
public class JgdmController {
	
	@Autowired
	private CJgdmService jgdmSerVice;
	
	/*
	 * 页面跳转catalog/jgdmListUI.do
	 * */
	@RequestMapping("jgdmListUI.do")
	public String jgdmListUI(){
		return "catalog/jgdmList";
	}
	/*
	 * 查找所有信息
	 * */
	@RequestMapping("jgdmListInfo.do")
	@ResponseBody
	public EasyuiDatagrid<CJgdm> findAllJgdmListInfo(int page,int rows){
		return jgdmSerVice.findJgdmListInfo(page, rows);
	}
	
	@RequestMapping("jgdmInfo.do")
	public String jgdmInfo(String isNew,Model model){
		if(isNew.equals("yes")){
			model.addAttribute("isNew", true);
		}else{
			model.addAttribute("isNew", false);
		}
		return "/catalog/jgdmInfo";
	}
	/*
	 * 查找上级名称
	 * */
	@RequestMapping("jgdmSjmc.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> findSjmc(){
		return jgdmSerVice.findSjmc();
	}
	/*
	 * 机构名称唯一性检查
	 * */
	@RequestMapping("checkMc.do")
	@ResponseBody
	public int checkMc(String mc){
		 int num=jgdmSerVice.checkMc(mc);			 
		 return num;
		}
	/*
	 * 新增机构
	 * */
	@RequestMapping("addjgdm.do")
	@ResponseBody
	public int Addjgdm(String mc,char tybz,String remark,String sjdm){
		return jgdmSerVice.Addjgdm(mc, tybz, remark, sjdm);
	}
	/*
	 * 修改机构
	 * */
	@RequestMapping("updagejgdm.do")
	@ResponseBody
	public int updatejgdm(String dm,String mc,char tybz,String remark,String sjdm){
		return jgdmSerVice.updatejgdm(dm, mc, tybz, remark, sjdm);
	}
}
