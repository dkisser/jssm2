package org.lf.jssm.action.ajuan;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.AJuanxx;
import org.lf.jssm.service.ajuan.AJuanxxService;
import org.lf.jssm.service.ajuan.AjuanInfoService;
import org.lf.jssm.service.ajuan.JzWizardService;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.servlet.CaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 案件信息 
 */
@Controller
@RequestMapping("/ajuan/")
public class AJXXController implements BaseController {
	@Autowired	
	private AjuanInfoService aJuanInfoService;
	@Autowired
	private JzWizardService jzWizardService;
	@Autowired
	private AJuanxxService ajxxService;
	
	@RequestMapping("findAjxxInfo.do")
	@ResponseBody
	public void getFindAjxxInfo(HttpSession session,Model model, String dsr, 
			String ah, String sfja, 
			String larq1, String larq2,
			String jarq1, String jarq2) throws Exception {
		
		session.setAttribute("caseInfoMap", jzWizardService.getCaseMap(dsr, ah, sfja, larq1, larq2, jarq1, jarq2));
		session.setAttribute("dsr", dsr);
		session.setAttribute("ah", ah);
		session.setAttribute("sfja", sfja);
		session.setAttribute("larq1", larq1);
		session.setAttribute("larq2", larq2);
		session.setAttribute("jarq1", jarq1);
		session.setAttribute("jarq2", jarq2);
	}
	
	/**
	 * 新建卷宗--案件信息列表
	 */
	@RequestMapping("ajxxList.do")
	public String ajxxList(String jzewm,HttpSession session,Model m) {
		m.addAttribute("jzewm",jzewm);
		return "ajuan/ajxxList";
	}
	
	/**
	 * 返回一个easyuidatagrid类型数据给前台datagrid用于显示
	 * @param rows
	 * @param page
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("getAjxxList.do")
	@ResponseBody
	public EasyuiDatagrid<AJuanxx> getAjxxList(int rows,int page,HttpSession session) throws Exception{
		
		String dsr =session.getAttribute("dsr").toString();
		String ah = session.getAttribute("ah").toString();
		String sfja =session.getAttribute("sfja").toString();
		String larq1=session.getAttribute("larq1").toString();
		String larq2=session.getAttribute("larq2").toString();
		String jarq1=session.getAttribute("jarq1").toString();
		String jarq2=session.getAttribute("jarq2").toString();
		return ajxxService.getAjxxList(rows, page, jzWizardService.getCaseMap(dsr, ah, sfja, larq1, larq2, jarq1, jarq2));
	}
	
	/**
	 * 展示案件信息详情页面
	 * @param ahdm
	 * @param action
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("ajxxInfo.do")
	public String Ajxxinfo(String ah,String action,HttpSession session,Model model) throws Exception{
		String dsr =session.getAttribute("dsr").toString();
		String ah1 = session.getAttribute("ah").toString();
		String sfja =session.getAttribute("sfja").toString();
		String larq1=session.getAttribute("larq1").toString();
		String larq2=session.getAttribute("larq2").toString();
		String jarq1=session.getAttribute("jarq1").toString();
		String jarq2=session.getAttribute("jarq2").toString();
		Map<String,CaseInfo> map=jzWizardService.getCaseMap(dsr, ah1, sfja, larq1, larq2, jarq1, jarq2);
		if(action.equals("browse")){
			//将CaseInfo转换为AJuanxx信息,根据key值找到对应的AJuanxx对象
			model.addAttribute("ajxx", ajxxService.getAjxxMap(map).get(ah));
		}
		return "ajuan/ajxxInfo";
	}
	
	/**
	 * 查找J_AJXX表，根据案号查看用户选中的案件是否存在。如果不存在，在J_AJXX中插入一条记录。如果存在，更新相关记录。
	 * @param ahdm
	 * @param session
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("checkAjxx.do")
	@ResponseBody
	public void checkAjxx(String ah,HttpSession session,HttpServletResponse response) throws IOException{
		@SuppressWarnings("unchecked")
		Map<String,CaseInfo> map=(Map<String, CaseInfo>) session.getAttribute("caseInfoMap");
		PrintWriter out=null;
		String result=ajxxService.checkAJxx(ah, ajxxService.getAjxxMap(map).get(ah));
		
		out=response.getWriter();
		out.print(result);
		//当out输出对象不为空时，关闭此资源
		if(out!=null){
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 案卷信息备注
	 * @param remark
	 * @param ewm
	 * @return
	 */
	@RequestMapping("setRemark.do")
	@ResponseBody
	public Integer setRemark(String remark,String ewm){
		if(aJuanInfoService.updateJAJuan(ewm, remark)){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 判断v_ajuand表中是否包含该二维码数据
	 * @param jzewm
	 * @return
	 */
	@RequestMapping("hasVAJuanD.do")
	@ResponseBody
	public int hasVAJuanDDATA(String jzewm){
		return aJuanInfoService.getVAjuanDbyjz_ewm(jzewm)?1:2;
	}
}
