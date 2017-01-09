package org.lf.jssm.action.query;

import javax.servlet.http.HttpServletRequest;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.query.AJJYOuterQueryService;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/query/")
public class AJJYOuterQueryController implements BaseController {
	@Autowired
	private AJJYOuterQueryService ajjyOuterQueryService;

	/**
	 * 跳转到卷宗对外借阅页面
	 * @param request
	 * @return
	 */
	@RequestMapping("ajjyOuterQueryUI.do")
	public String AJJYOuterQueryUI(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		request.setAttribute("print", ajjyOuterQueryService.getPrintInfo(ip));
		return "query/ajjyOuterQuery";
	}

	/**
	 * 查询所有数据
	 * @param rows
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("findAllDatas.do")
	@ResponseBody
	public EasyuiDatagrid<VJz> findAllDatas(int rows, int page, HttpServletRequest request) {
		return ajjyOuterQueryService.findAllDatas(rows, page, request.getRemoteAddr());
	}
	

	/**
	 * 关闭后修改Status 和 jsrq 的值
	 * @param request
	 * @return
	 */
	@RequestMapping("updateStatus.do")
	@ResponseBody
	public Integer updateStatus(HttpServletRequest request) {
		return ajjyOuterQueryService.updateStatus(request.getRemoteAddr());
	}
	
	/**
	 * 检查是否登记
	 * @param ip
	 * @return : 1 表示已经登记  0 表示没有登记
	 */
	@RequestMapping("checkIsRegister.do")
	@ResponseBody
	public int CheckIsRegister(HttpServletRequest request){
		return ajjyOuterQueryService.CheckIsRegister(request.getRemoteAddr());
		
	}
}
