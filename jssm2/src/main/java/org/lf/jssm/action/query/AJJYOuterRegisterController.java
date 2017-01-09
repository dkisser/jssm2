package org.lf.jssm.action.query;

import java.util.Date;
import java.util.List;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.JWlry;
import org.lf.jssm.service.model.JZQueryParam;
import org.lf.jssm.service.query.AJJYOuterRegisterService;
import org.lf.utils.EasyuiComboBoxItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/query/")
public class AJJYOuterRegisterController implements BaseController {
	@Autowired
	private AJJYOuterRegisterService ajjyOuterService;
	
	@RequestMapping("ajjyOuterRegisterUI.do")
	public String ajjyOuterRegister(){
		return "query/ajjyOuterRegister";
	}
	
	@RequestMapping("ajjyOuter_IsOver.do")
	@ResponseBody
	public String getAjjyOuterQuery(JZQueryParam param){
		if(ajjyOuterService.getQueryList(param).size()>10){
			return "error";
		}
		return "success";
	}
	
	/**
	 * 外来人员登记
	 * @param wlry
	 * @param jzewm
	 * @return
	 */
	@RequestMapping("ajjyOuter_jzewm.do")
	@ResponseBody
	public String register(JWlry wlry, String jzewm){
		wlry.setKsrq(new Date());
		if(ajjyOuterService.insertWlry(wlry, jzewm)==1){
			return "success";
		}
		return "error";
	}
	
	/**
	 * 检查当前电脑是否被占用
	 * @param djdn
	 * @return
	 */
	@RequestMapping("ajjyOuter_checkDjdn.do")
	@ResponseBody
	public String check(String djdn){
		if(ajjyOuterService.getDjdnStatus(djdn)==null){
			return "yes";
		}
		return "no";
	}
	
	@RequestMapping("ajjyOuter_rylx.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getRylx(){
		return ajjyOuterService.getRylx(); 
	}
	
	@RequestMapping("ajjyOuter_zllx.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getZllx(){
		return ajjyOuterService.getZjlx();
	}
	
	@RequestMapping("ajjyOuter_yldn.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getYldn(){
		return ajjyOuterService.getYldn();
	}
}
