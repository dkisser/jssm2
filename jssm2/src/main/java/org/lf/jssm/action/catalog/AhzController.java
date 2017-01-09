package org.lf.jssm.action.catalog;

import java.util.List;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.CAhz;
import org.lf.jssm.service.catalog.CAhzService;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/catalog/")
public class AhzController implements BaseController {
	@Autowired
	private CAhzService AhzService;

	@RequestMapping("ahzListUI.do")
	public String AhzListUI() {
		return "catalog/ahzList";
	}

	/**
	 * 从数据库中获取案号字列表，转换成easyuiDatagrid传给前台jsp页面显示
	 * 
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping("getAhzList.do")
	@ResponseBody
	public EasyuiDatagrid<CAhz> getAhzList(int rows, int page) {
		return AhzService.getAhzList(rows, page);
	}

	// 跳转到新建或修改页面
	@RequestMapping("AhzInfo.do")
	public String DealAhzInfo(String action, Model m) {
		if (action.equals("add")) {
			m.addAttribute("isnew", true);
		} else {
			m.addAttribute("isnew", false);
		}
		return "catalog/ahzInfo";
	}

	// 保存新建修改操作到数据库
	@RequestMapping("saveAhzInfo.do")
	@ResponseBody
	public String saveAhzInfo(String isnew, CAhz cu, String cbt) {
		return AhzService.saveAhzInfo(isnew, cu, cbt);
	}

	/**
	 * 返回用于案号字comboBox显示的数据
	 */
	@RequestMapping("findAhzList.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> findAhzList() {
		return AhzService.findOtherAhz();
	}

	// 返回所有一级分类名称
	@RequestMapping("findYJFLList.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> findYJFLList() {
		return AhzService.findAllYJFL();
	}

	// 返回所有承办庭名称
	@RequestMapping("findCBTSList.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> findCBTSList() {
		return AhzService.findAllCBTS();
	}
}
