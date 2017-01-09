package org.lf.jssm.action.catalog;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.CAjuanYjfl;
import org.lf.jssm.service.catalog.CAJuanYjflService;
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
public class YjflController implements BaseController {
	@Autowired
	private CAJuanYjflService yjflService;

	private PageNavigator<CAjuanYjfl> nav;

	/**
	 * 欢迎页面到案卷一级分类的跳转
	 */
	@RequestMapping("yjflListUI.do")
	public String getyjflListUI() {
		return "catalog/yjflList";
	}

	@RequestMapping("yjflInfo.do")
	public String getyjflInfo(String action, Integer dm, String mc, String pym, String tybz, Integer flag, Model m) {

		if (action.equals(ADD)) {
			boolean isnew = true;
			m.addAttribute("isnew", isnew);
			m.addAttribute("chuyjfl", null);
			return "catalog/yjflInfo";
		} else if (action.equals(UPDATE)) {
			boolean isnew = false;
			m.addAttribute("isnew", isnew);
			CAjuanYjfl chuyjfl = (CAjuanYjfl) yjflService.findYjflByDm(new DecimalFormat("000").format(dm));
			m.addAttribute("chuyjfl", chuyjfl);
			return "catalog/yjflInfo";

		} else if (action.equals(SAVE)) {
			if (flag == 0) { // flag=0表示为新增数据
				if (mc != null) {
					if (tybz.equals("启用")) {
						tybz = "0";
					} else {
						tybz = "1";
					}
					yjflService.addYjflList(new DecimalFormat("000").format(dm), mc, pym, tybz); // 新增字段
				}
			} else {
				if (tybz.equals("启用")) {
					tybz = "0";
				} else {
					tybz = "1";
				}
				yjflService.updateYjflList(mc, pym, tybz, new DecimalFormat("000").format(dm)); // 更新字段
			}
		} else if (action.equals(DEL)) {
			if (dm != null) {
				yjflService.delYjflByDm(new DecimalFormat("000").format(dm));
			}
			return "catalog/yjflList";
		}
		return null;
	}

	/**
	 * 一级分类以JSON的格式显示在表格中
	 */
	@RequestMapping("yjflList.do")
	@ResponseBody
	public EasyuiDatagrid<CAjuanYjfl> getAllYjflList(int page, int rows) {
		List<CAjuanYjfl> yjflList = yjflService.findAllYjflList();
		if (nav == null) {
			nav = new PageNavigator<>(yjflList, rows);
		} else if (nav.getPageSize() != rows) {
			nav.resetPage(rows);
		}
		List<CAjuanYjfl> pageList = nav.getPage(page);
		EasyuiDatagrid<CAjuanYjfl> pagePojo = new EasyuiDatagrid<CAjuanYjfl>(pageList, yjflList.size());
		nav = null;
		return pagePojo;
	}

	/**
	 * 对案卷一级分类中的dm和mc的新建做唯一性检查
	 */
	@RequestMapping(value = "checkYjflList.do", method = RequestMethod.POST)
	public void checkYjflList(Integer dm, String mc, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			if (yjflService.findYjflByDm(new DecimalFormat("000").format(dm)) != null || yjflService.findYjflByMc(mc) != null) {
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
	 * 对案卷一级分类中的mc的更新做唯一性检查
	 */
	@RequestMapping(value = "checkYjflListUpdate.do", method = RequestMethod.POST)
	public void checkYjflListByUpdate(String mc, String newmc, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			if (mc.equals(newmc)) {
				printWriter.print("{\"success\":0 }");
			} else {
				if (yjflService.findYjflByMc(mc) != null) {
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
