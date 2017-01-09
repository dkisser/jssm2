package org.lf.jssm.action.sys;

import org.apache.log4j.Logger;
import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.JEnv;
import org.lf.jssm.service.sys.JEnvService;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys/")
public class EnvController implements BaseController {
	Logger logger = Logger.getLogger(EnvController.class);
	@Autowired
	private JEnvService jenvService;
	/**
	 * 返回环境参数设置列表jsp页面
	 */
	@RequestMapping("envListUI.do")
	public String envList(){
		return "sys/envList";
	}
	
	/**
	 * 获取数据库中所有的环境参数记录，传给前台的datagrid进行显示
	 */
	@RequestMapping("envList.do")
	@ResponseBody
	public EasyuiDatagrid<JEnv> getEnvList(int page,int rows){
		return jenvService.getEnvList(page, rows);
	}
	
	/**
	 * 显示编辑信息页面
	 */
	@RequestMapping("showUpdatePage.do")
	public String showUpdatePage(Model m,String envId){
		JEnv env=jenvService.getRecordById(envId);
		m.addAttribute("editJEnv", env);
		return "sys/envInfo";
	}
	
	/**
	 * 修改环境参数表的环境值字段
	 */
	@RequestMapping("updateEnvInfo.do")
	@ResponseBody
	public int updateEnvInfo(String envValue,String envId){
		return jenvService.updateEnvValue(envValue, envId);
	}
	
}
