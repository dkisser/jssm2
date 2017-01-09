package org.lf.jssm.action.raw;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.lf.jssm.service.model.ScanParameterModel;
import org.lf.jssm.service.raw.RawScanParameterService;
import org.lf.jssm.service.raw.RawScanService;
import org.lf.jssm.service.sys.JEnvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 扫描参数配置控制层
 * 
 * @author sunwill
 *
 */
@Controller
@RequestMapping("/raw/")
public class RawScanParameterController {
	private static final Logger logger = LoggerFactory
			.getLogger(RawScanParameterController.class);

	@Autowired
	private RawScanService rawScanervice;

	@Autowired
	private JEnvService jEnvService;
	
	@Autowired
	private RawScanParameterService jScanEnvService;
	/**
	 * 扫描模式选择初始化界面
	 * @return
	 */
	@RequestMapping("scanModelUI.do")
	public String scanModel(HttpServletRequest request,Model m) {
		Map<String, String> map = jScanEnvService.selectByScaner(request);
		m.addAttribute("jScanEnv", JSON.toJSONString(map));
		return "raw/scanModel";
	}
	
	/**
	 * 自动扫描参数配置初始化
	 * @param request
	 * @param m
	 * @param scanModel
	 * @return
	 */
	@RequestMapping("autoScanParamUI.do")
	public String autoScanParamUI(HttpServletRequest request,Model m,String scanningModel) {
		Map<String, String> map = jScanEnvService.selectByScaner(request);
		m.addAttribute("jScanEnv", JSON.toJSONString(map));
		return "raw/autoScanParam";
	}

	/**
	 * 手动扫描参数配置初始化
	 * @param request
	 * @param m
	 * @param scanModel
	 * @return
	 */
	@RequestMapping("manualScanParamUI.do")
	public String manualScanParamUI(HttpServletRequest request,Model m,String scanningModel) {
		Map<String, String> map = jScanEnvService.selectByScaner(request);
		map.put(RawScanParameterService.SCANNING_MODEL, scanningModel);
		m.addAttribute("jScanEnv", JSON.toJSONString(map));
		return "raw/manualScanParam";
	}
	/**
	 * 保存扫描配置参数
	 * @param request
	 * @param scanPaprm
	 */
	@RequestMapping("scanParamSave.do")
	@ResponseBody
	public void scanParamSave(HttpServletRequest request,ScanParameterModel scanPaprm) {
		jScanEnvService.updateScanPaprm(request,scanPaprm);
	}
	/**
	 * 重置扫描参数
	 * @param request
	 * @param scanPaprm
	 */
	@RequestMapping("scanParamReset.do")
	@ResponseBody
	public String scanParamReset(HttpServletRequest request,String scanningModel) {
		try {
			return jScanEnvService.resetScanPaprm(request,scanningModel);
		} catch (Exception e) {
			logger.error("重置扫描参数出错", e);
			return "error";
		}
	}
	
}
