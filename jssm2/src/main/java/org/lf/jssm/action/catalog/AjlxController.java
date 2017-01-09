package org.lf.jssm.action.catalog;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.CAjuanLx;
import org.lf.jssm.service.catalog.CAJuanLxService;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/catalog/")
public class AjlxController implements BaseController {
	@Autowired
	private CAJuanLxService ajlxService;

	private PageNavigator<CAjuanLx> nav;

	/**
	 * 欢迎页面到案卷类型的跳转
	 */
	@RequestMapping("ajlxListUI.do")
	public String getajlxListUI() {
		return "catalog/ajlxList";
	}

	@RequestMapping("ajlxInfo.do")
	public String getajlxInfo(String action, Integer dm, String mc, String pym, String tybz, Integer flag, Model m) {
		if (action.equals(ADD)) {
			boolean isnew = true;
			m.addAttribute("isnew", isnew);
			return "catalog/ajlxInfo";
		} else if (action.equals(UPDATE)) {
			boolean isnew = false;
			m.addAttribute("isnew", isnew);
			CAjuanLx chuajlx = ajlxService.findAjlxByDm(new DecimalFormat("000").format(dm));
			m.addAttribute("chuajlx", chuajlx);
			return "catalog/ajlxInfo";
		} else if (action.equals(DEL)) {
			if (dm != null) {
				ajlxService.delAjlxList(new DecimalFormat("000").format(dm));
			}
			return "catalog/ajlxList";
		} else if (action.equals(SAVE)) {
			if (flag == 0) {
				if (dm != null) {
					if (tybz.equals("启用")) {
						tybz = "0";
					} else {
						tybz = "1";
					}
					CAjuanLx chu = new CAjuanLx();
					chu.setDm(new DecimalFormat("000").format(dm));
					chu.setMc(mc);
					chu.setPym(pym);
					chu.setTybz(tybz);
					ajlxService.addAjlxList(chu);
				}
			} else {
				if (dm != null) {
					if (tybz.equals("启用")) {
						tybz = "0";
					} else {
						tybz = "1";
					}
					ajlxService.updateAjlxList(new DecimalFormat("000").format(dm), mc, pym, tybz);
				}
			}
		}
		return "catalog/ajlxInfo";
	}

	/**
	 * 案卷类型以JSON的格式显示在表格中
	 */
	@RequestMapping("ajlxList.do")
	@ResponseBody
	public EasyuiDatagrid<CAjuanLx> getAllAjlxList(int page, int rows) {
		List<CAjuanLx> ajlxList = ajlxService.findAllAjlxList();
		if (nav == null) {
			nav = new PageNavigator<>(ajlxList, rows);
		} else if (nav.getPageSize() != rows) {
			nav.resetPage(rows);
		}
		List<CAjuanLx> pageList = nav.getPage(page);
		EasyuiDatagrid<CAjuanLx> pagePojo = new EasyuiDatagrid<CAjuanLx>(pageList, ajlxList.size());
		nav = null;
		return pagePojo;
	}

	/**
	 * 显示下拉框里面的案卷类型内容
	 */
	@RequestMapping("findAjlxCombox.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> findAjlxComboBoxList() {
		List<CAjuanLx> ajlxList = ajlxService.findAllAjlxList();
		List<EasyuiComboBoxItem<String, String>> list = new ArrayList<EasyuiComboBoxItem<String, String>>();
		Iterator<CAjuanLx> it = ajlxList.iterator();
		while (it.hasNext()) {
			CAjuanLx chuajlx = it.next();
			list.add(new EasyuiComboBoxItem<String, String>(chuajlx.getMc(), chuajlx.getMc()));
		}
		return list;
	}

	/**
	 * 对案卷类型新建的分类代码和分类名称做唯一性检查
	 */
	@RequestMapping(value = "checkajlxList.do", method = RequestMethod.POST)
	public void checkAjlxList(Integer dm, String mc, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			if (ajlxService.findAjlxByDm(new DecimalFormat("000").format(dm)) != null || ajlxService.findAjlxByMc(mc) != null) {
				printWriter.print("{\"success\":1 }");
			} else {
				printWriter.print("{\"success\":0 }");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}

	/**
	 * 对案卷类型编辑的分类代码和分类名称做唯一性检查
	 */

	@RequestMapping(value = "checkajlxListUpdate.do", method = RequestMethod.POST)
	public void checkAjlxListUpdate(String mc, String newmc, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			if (mc.equals(newmc)) {
				printWriter.print("{\"success\":0 }");
			} else {
				if (ajlxService.findAjlxByMc(mc) != null) {
					printWriter.print("{\"success\":1 }");
				} else {
					printWriter.print("{\"success\":0 }");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}
}
