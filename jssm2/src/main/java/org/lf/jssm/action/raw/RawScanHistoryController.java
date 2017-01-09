package org.lf.jssm.action.raw;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.lf.jssm.db.pojo.VRawfile;
import org.lf.jssm.service.raw.RawScanHistoryService;
import org.lf.utils.EasyuiTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 扫描参数配置控制层
 * 
 * @author sunwill
 *
 */
@Controller
@RequestMapping("/raw/")
public class RawScanHistoryController {

	@Autowired
	private RawScanHistoryService scanHistoryService;


	/**
	 * 扫描参数配置初始化
	 * 
	 * @return
	 */
	@RequestMapping("scanHistoryUI.do")
	public String scanHistoryUI() {
		return "raw/scanHistoryDate";
	}
	
	/**
	 * 查询是否存在扫描历史
	 * @param session
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("selectHistory.do")
	@ResponseBody
	public String selectHistory(HttpSession session,String startDate,String endDate) throws Exception {
		String hasFlag = scanHistoryService.hasHistory(session,startDate,endDate);
		return hasFlag;
	}
	/**
	 * 扫描历史预览初始化界面
	 * @param session
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showHistoryUI.do")
	public String showHistoryUI(HttpSession session,String startDate,String endDate,Model m) {
		m.addAttribute("historyStartDate", startDate);
		m.addAttribute("historyEndDate", endDate);
		return "raw/showHistory";
	}
	/**
	 * 初始化扫描历史树目录
	 * @param session
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("scanHistoryTree.do")
	@ResponseBody
	public List<EasyuiTree> scanHistoryTree(HttpSession session,String startDate,String endDate) throws Exception {
		return scanHistoryService.getScanHistoryTree(session,startDate,endDate);
	}
	/**
	 * 获得扫描历史图片信息
	 * @param session
	 * @param vrf
	 * @param m
	 * @return
	 */
	@RequestMapping("getHistoryImgs.do")
	public String getHistoryImgs(HttpSession session,VRawfile vrf,Model m) {
		List<VRawfile> imgEwmList = scanHistoryService.getImgsEwm(session,vrf);
		m.addAttribute("imgEwmList",imgEwmList);
		return "raw/historyImgs";
	}
	
}
