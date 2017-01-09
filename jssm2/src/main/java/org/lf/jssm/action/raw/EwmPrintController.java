package org.lf.jssm.action.raw;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lf.jssm.service.raw.EwmPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 二维码打印控制层
 * 
 * @author sunwill
 *
 */
@Controller
@RequestMapping("/raw/")
public class EwmPrintController {
	@Autowired
	private EwmPrintService ewmPrintService;

	/**
	 * 进入类型和页数设置页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("ewmPrintUI.do")
	public String ewmPrintUI(HttpServletRequest request) {
		return "raw/ewmPrint";
	}

	/**
	 * 生成二维码
	 * 
	 * @param ewmType
	 * @param pageNumbers
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("ewmGrid.do")
	public String ewmGrid(String ewmType, String pageNumbers,
			String printModel, Model m, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer pageNo = Integer.parseInt(pageNumbers);
		if (pageNo == 0 || pageNo > 9999) {
			response.sendError(500, "页数无效!");
			return null;
		}
		if ("1".equals(printModel)) {
			// 输出pdf文件
			return ewmPrintService.getEwmPdf(ewmType, pageNumbers, request,
					response);
		} else if ("2".equals(printModel)) {
			// 打开预览页面
			List<List<String>> list = ewmPrintService.getEwms(ewmType,
					pageNumbers);
			m.addAttribute("allEwmInfo", list);
			return "raw/ewmGrid";
		} else {
			return null;
		}
	}

}
