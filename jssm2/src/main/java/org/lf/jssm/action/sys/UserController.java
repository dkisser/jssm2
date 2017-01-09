package org.lf.jssm.action.sys;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.CJgdm;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.db.pojo.VUser;
import org.lf.jssm.service.catalog.CJgdmService;
import org.lf.jssm.service.catalog.CYhzwService;
import org.lf.jssm.service.model.UiautoCompleteBox;
import org.lf.jssm.service.sys.PositionService;
import org.lf.jssm.service.sys.RoleService;
import org.lf.jssm.service.sys.UserService;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.StringUtils;
import org.lf.utils.servlet.WebServiceTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/")
public class UserController implements BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private CYhzwService yhzwService;
	@Autowired
	private CJgdmService jgdmService;
	

	/**
	 * 登录页面初始化
	 * 
	 * @return
	 */
	@RequestMapping("loginUI.do")
	public String loginUI() {
		return "sys/login";
	}

	/**
	 * 登录验证
	 * 
	 * @param uname
	 * @param upw
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("login.do")
	@ResponseBody
	public ChuUser login(String uname, String upw, HttpSession session,HttpServletRequest request) throws Exception {
		ChuUser user = userService.getUser(uname, upw);
		if (user != null) {
			session.setAttribute("loginInfo", user);
			VUser vUser = userService.getVUserByUname(uname);
			session.setAttribute("vUser", vUser);
			session.setAttribute("currentRoleName", roleService.findRoleByid(user.getRoleId()).getName());
			ServletContext application = request.getServletContext();
			if(StringUtils.isEmpty((String) application.getAttribute("picServerUrl"))){
				InputStream inputStream = WebServiceTool.class.getClassLoader()
						.getResourceAsStream("jssm.properties");
				Properties p = new Properties();
				String picServerUrl = null;
				try {
					p.load(inputStream);
					picServerUrl = p.getProperty("picServerUrl");
					if(StringUtils.isEmpty(picServerUrl)){
						return null;
					}
				} catch (Exception e1) {
					logger.error("读取配置文件出错", e1);
					throw e1;
				}
				application.setAttribute("picServerUrl", picServerUrl.trim());
			}
		}
		return user;
	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("quit.do")
	public String quit(HttpSession session) {
		session.removeAttribute("loginInfo");
		// 重定向到登录界面
		return "redirect:loginUI.do";
	}
	
	@RequestMapping("userListUI.do")
	public String userListUI () {
		return "sys/userlist";
	}
	
	/**
	 * 返回显示用户信息的对话框
	 */
	@RequestMapping("currUser.do")
	public String curruser(){
		return "sys/currUser";
	}
	/**
	 * 返回修改密码页面给前台
	 */
	@RequestMapping("updatePwdUI.do")
	public String updatePwdUI(){
		return "sys/updatePwd";
	}
	/**
	 * 进行修改用户密码操作
	 */
	@RequestMapping("updatePwd.do")
	@ResponseBody
	public int updatePwd(String username,String oldpwd,String newpwd,HttpServletResponse response){
		ChuUser user=userService.getUserByUname(username);
		int result=userService.changePasswd(user, oldpwd, newpwd);
		return result;
		//return "{\"message\":\""+result+"\"}";
	}
	
	
	/**
	 * 查找VUser获取所有的用户信息
	 * @param rows
	 * @param page
	 * @return 一个EasyuiDatagrid的类地对象，改对象有page,rows这两个属性
	 */
	@RequestMapping("userList.do")
	@ResponseBody
	public EasyuiDatagrid<VUser> VUserList(int rows, int page) {
		return userService.getCurrentPageUser(rows, page);
	}
	
	/**
	 * 找到所有的Role选项
	 * @return
	 */
	@RequestMapping("findAllRole.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> findAllRole () {
	
		return roleService.findAllRole();
	}
	
	/**
	 * 找到所有的yhzw选项
	 * @return 
	 */
	@RequestMapping("findYHZW.do")
	@ResponseBody
	public List<UiautoCompleteBox> findBrowseInfo(){
		return yhzwService.findBrowseInfo();
	}

	
	/**
	 * 找到所有的部门选项
	 * @return 
	 */
	@RequestMapping("findJG.do")
	@ResponseBody
	public List<CJgdm> findAllJG () {
		return jgdmService.getAllJG();
	}
	/**
	 * 用于给前台的ajax返回值，来判断userId是否已经存在
	 * @param response
	 * @param userId
	 * @throws Exception 
	 */
	@RequestMapping("checkuserId.do")
	@ResponseBody
	public void checkuserId (HttpServletResponse response,Integer userId) throws Exception { 
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			if (userService.getUserById(userId) == null) {
				printWriter.print("{\"success\":1}");
			} else {
				printWriter.print("{\"success\":0}");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	/**
	 * 检验用户的用户名是否重复,返回一个页面参数success(json类型)
	 * success :1表示验证通过
	 * success :0表示验证失败
	 * @param uname
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("checkUname.do")
	@ResponseBody
	public void checkUname (String uname,HttpServletResponse response) throws Exception {
		
		PrintWriter printWriter = null;
	
		try {
		
			printWriter = response.getWriter();
			if (userService.getUserByUname(uname) == null) {
				printWriter.print("{\"success\":1}");
			} else {
				printWriter.print("{\"success\":0}");
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			printWriter.flush();
			printWriter.close();
		}
		
	}
	
	/**
	 *重置密码 
	 * @param uname
	 * @return
	 */
	
	@RequestMapping("resetPassword.do")
	public String resetPassword (String uname) {
		
		ChuUser chuUser = userService.getUserByUname(uname);
		
		if (chuUser == null) {
			return "error";
		} else {
			
			userService.resetPasswd(chuUser);
			return "sys/userlist";
		} 
	}
	
	
	/**
	 * 点击添加按钮后进入这个Controoler中,
	 * 然后将isADD(用于判断是否是新增)的值带到userinfo中
	 * @param m
	 * @return userinfo.jsp
	 */
	@RequestMapping("toAddUserInfo.do")
	public String toAddUserInfo (Model model) {
		
		boolean isADD = true;
		model.addAttribute("isADD", isADD);
		return "sys/userinfo";
		
	}
	
	/**
	 * 点击编辑后进入这个Controller,然后将
	 * user的信息()和isADD的值带到userinfo.jsp中
	 * @param m
	 * @param userid
	 * @return userinfo.jsp
	 */
	@RequestMapping("toEditUserInfo.do")
	public String toEditUserInfo (Model model,String uname) {
		
		boolean isADD = false ;
		model.addAttribute("isADD", isADD);
		ChuUser editUser = userService.getUserByUname(uname);
		VUser vUser = userService.getVUserByUname(uname);
		model.addAttribute("editUser",editUser);
		model.addAttribute("vuser",vUser);
		return "sys/userinfo";
		
	}
	
	/**
	 * 删除Controller,在这里直接执行删除
	 * @param uname
	 * @return userlist.jsp
	 */
	@RequestMapping("deleteUser.do")
	public String deleteUser (String uname) {

		ChuUser delUser = userService.getUserByUname(uname);
		
		if (delUser == null) {
		
			return "error";
		
		} else {
		
			userService.delUser(delUser);
			return "sys/userlist";
		}
		
	}
	
	/**
	 * 用户点击保存时进入该Controller来存数据到数据库中
	 * @param newuser
	 * @param savenew
	 * @return userlist.jsp
	 */
	@RequestMapping("saveUserInfo.do")
	public String saveUserInfo (ChuUser newuser,Boolean savenew) {
		if (savenew) {
			/**
			 * savenew为true时表示添加
			 * 要添加的属性有：
			 * userId,uname,upw,name,position,email,salt,verify,token,roleId,type
			 */

			userService.addUser(newuser);
		} else {
			/**
			 * savenew为false时表示更新
			 * 需要修改的属性有:
			 * uname,upw,position,type,email,role_id
			 */
			userService.updateUser(newuser);
		}
		return "sys/userlist";
	}
	
	/**
	 * 新增用户
	 * @param newuser
	 */
	@RequestMapping("addUserInfo.do")
	@ResponseBody
	public void addUserInfo (ChuUser newuser,String zw,String bm,String role) {
		/**
		 * 要添加的属性有：
		 * userId,uname,upw,name,position,email,salt,verify,token,roleId,type
		 */
		String zwdm = yhzwService.getDmByMc(zw);
		String bmdm = jgdmService.getDmByMc(bm);
		Integer roleId = roleService.getIdByName(role);
		newuser.setZwdm(zwdm);
		newuser.setBmdm(bmdm);
		newuser.setRoleId(roleId); 
		userService.addUser(newuser);
	}
	/**
	 * 修改用户信息
	 * @param newuser
	 */
	@RequestMapping("updateUserInfo.do")
	@ResponseBody
	public void updateUserInfo (ChuUser newuser,String zw,String bm,String role) {
		/**
		 * 需要修改的属性有:
		 * uname,upw,position,type,email,role_id
		 */
		String zwdm = yhzwService.getDmByMc(zw);
		String bmdm = jgdmService.getDmByMc(bm);
		Integer roleId = roleService.getIdByName(role);
		newuser.setZwdm(zwdm);
		newuser.setBmdm(bmdm);
		newuser.setRoleId(roleId);
		userService.updateUser(newuser);
	}
	
	/**
	 * 浏览器版本错误
	 * 
	 * @return
	 */
	@RequestMapping("errorBrowserUI.do")
	public String errorBrowser() {
		return "sys/errorBrowser";
	}
}
