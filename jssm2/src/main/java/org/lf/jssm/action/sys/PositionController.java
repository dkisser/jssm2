package org.lf.jssm.action.sys;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.ChuPosition;
import org.lf.jssm.service.sys.PositionService;
import org.lf.jssm.service.sys.RoleService;
import org.lf.jssm.service.sys.UserService;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/position/")
public class PositionController implements BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PositionService positionService;

	
	
	/**
	 * 返回positionlist.jsp页面
	 */
	@RequestMapping("positionListUI.do")
	public String positionlist(){
		return "sys/positionlist";
	}
	/**
	 * 获取数据库的职位列表数据,并传给前台的datagrid表格进行显示
	 */
	@RequestMapping("positionList.do")
	@ResponseBody
	public EasyuiDatagrid<ChuPosition> getPositionList(int page,int rows,
			@RequestParam(value="pname",required=false,defaultValue="全部")String pname){
		return positionService.getPositionListInfo(page, rows, pname);
	}

	
	/**
	 * 返回职位列表的新增页面
	 */
	@RequestMapping("addpositionInfo.do")
	public String addpositionInfo(Model m){
		boolean isnew =true;
		m.addAttribute("isnew", isnew);
		return "sys/positioninfo";
	}
	/**
	 * 返回职位列表的修改页面
	 */
	@RequestMapping("updatepositionInfo.do")
	public String updatepositionInfo(Model m,Integer pid){
		ChuPosition position=positionService.findPositionById(pid);
		boolean isnew =false;
		m.addAttribute("isnew", isnew);
		m.addAttribute("editpos", position);
		return "sys/positioninfo";
	}
	/**
	 * 对职位列表进行删除操作
	 */
	@RequestMapping("deletepositionInfo.do")
	public String deletepositionInfo(Integer pid){
		if(positionService.delPosition(pid)){
			return "sys/positionlist";
		}else{
			return "error";
		}		
	}
	/**
	 * 对职位列表进行保存操作
	 */
	@RequestMapping("savepositionInfo.do")
	public String savepositionInfo(ChuPosition cu,String savenew){
		if(savenew.equals("true")){
			positionService.addPosition(cu);
			return "sys/positionlist";
		}else{
			positionService.updatePosition(cu);
			return "sys/positionlist";
		}
	}
	/**
	 * 对职位编号进行唯一性检查
	 * @throws IOException 
	 */
	@RequestMapping("checkpid.do")
	@ResponseBody
	public void checkpid(Integer pid,HttpServletResponse response) throws IOException{
			PrintWriter out=null;
			String result=positionService.checkpid(pid);
			out=response.getWriter();
			out.print(result);
			//当out输出对象不为空时，关闭此资源
			if(out!=null){
				out.flush();
				out.close();
			}
	}
	/**
	 * 对职位名称进行唯一性检查
	 * @throws IOException 
	 */
	@RequestMapping("checkpname.do")
	@ResponseBody
	public void checkpname(String pname,String oldpname,String isnew,HttpServletResponse response) throws IOException{
			PrintWriter out=null;
			String result=positionService.checkpname(pname, oldpname, isnew);
			out=response.getWriter();
			out.print(result);
			//当out输出对象不为空时，关闭此资源
			if(out!=null){
				out.flush();
				out.close();
			}
	}
	
}
