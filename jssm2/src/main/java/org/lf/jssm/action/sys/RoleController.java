package org.lf.jssm.action.sys;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.service.model.ChuRoleMenuList;
import org.lf.jssm.service.sys.PositionService;
import org.lf.jssm.service.sys.RoleService;
import org.lf.jssm.service.sys.UserService;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.EasyuiTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role/")
public class RoleController implements BaseController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PositionService positionService;


	@RequestMapping("checkPrePwd.do")
	@ResponseBody
	public Map<String, String> checkUserprePwd(String pre_pwd, String userName) {
		Map<String, String> data = new HashMap<>();
		data.put("data", "false");
		if (pre_pwd != null && userName != null) {
			ChuUser user = userService.getUser(userName, pre_pwd);
			if (user != null) {
				data.remove("data");
				data.put("data", "true");
			}

		}
		return data;
	}

	@RequestMapping("updatePwdUI.do")
	public String updatePwd(HttpSession session, Model model) {
		ChuUser user = (ChuUser) session.getAttribute("loginInfo");
		System.out.println(user.getUname() + "|" + user.getUpw());
		model.addAttribute("user", user);
		return "sys/updatePwd";
	}
	
	
	
	//转向
	@RequestMapping("roleListUI.do")
	public String RoleListUI() {
		return "sys/rolelist";
	}

	// 转向到对话框页面
	@RequestMapping("roleInfo.do")
	public String RoleInfoUI(String isNew, Model model) {
		boolean flag = false;
		if ("yes".equals(isNew)) {
			flag = true;
		}
		model.addAttribute("flag", flag);
		return "sys/roleinfo";
	}


	// 获取表格信息
	@RequestMapping("roleMeunList.do")
	@ResponseBody
	public EasyuiDatagrid<ChuRoleMenuList> findAllChuRoleMenuList(int page,int rows) {
		return roleService.findAllChuRoleMenuList(page, rows);
	}

	//获取对话框内的权限树信息
	@RequestMapping("RoleMeunPower.do")
	@ResponseBody
	public List<EasyuiTree> findByRoleId(Integer role_id) {
		return roleService.findByRoleListId(role_id);
	}

	
	//唯一性检测
	@RequestMapping("checkRoleInfo.do")
	@ResponseBody
	public Map<String, Integer> checkRoleInfo(String checkname, Integer checkid) {
		return roleService.checkRoleInfo(checkname, checkid);
	}

	// 增加角色信息
	@RequestMapping("addRoleInfo.do")
	@ResponseBody
	public void addRole(int role_id,String role_name,String status,String str_id) {
		roleService.addRole(role_id, role_name, status,str_id);
	};

	// 修改角色信息
	@RequestMapping("updateRoleinfo.do")
	@ResponseBody
	public void updateRoleinfo(int role_id,String role_name,String status,String str_id) {	
				roleService.updateRoleInfo(role_id, role_name, status,str_id);
	}
	

}
