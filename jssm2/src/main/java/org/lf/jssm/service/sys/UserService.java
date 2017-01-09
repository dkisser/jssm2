package org.lf.jssm.service.sys;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.lf.jssm.db.dao.ChuUserMapper;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.db.pojo.VUser;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.NumberUtils;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private PageNavigator<VUser> pageNavigator;
	private final int success = 1; // updatePwd success
	private final int error1 = 2; // oldpwdError
	private final int error2 = 3; // newpwd can not match with oldpwd
	private final int error3 = 4; // updatePwd failed

	@Autowired
	private ChuUserMapper userDao;

	@Autowired
	private RoleService roleService;

	public String DEFAULT_PASSWD = "123456";

	/**
	 * 通过表单，实现ChuUser的更新 在更新之前还要对一些属性进行操作
	 * @param chuUser
	 * @return
	 */
	public boolean updateUser(ChuUser newuser) {
		/**
		 * 需要修改的属性有: uname,upw,position,type,email,role_id
		 */
		ChuUser chuUser = new ChuUser();
		ChuUser person = this.getUserByUname(newuser.getUname());
		// 由于我们是通过Id来对它进行操作的，所以我们还要将ID传进去
		chuUser.setUserId(newuser.getUserId());
		chuUser.setName(newuser.getName());
		if(newuser.getUpw()!=userDao.getPasswordByUname(newuser.getUname())){
			String salt = person.getSalt();
			String upw = newuser.getUpw();
			String newupw = StringUtils.toMD5(upw + salt);
			chuUser.setUpw(newupw);
		}else{ chuUser.setUpw(newuser.getUpw());}
		chuUser.setZwdm(newuser.getZwdm());
		DecimalFormat df=new DecimalFormat("0000");
		chuUser.setBmdm(df.format(Integer.parseInt(newuser.getBmdm())));
		chuUser.setRoleId(newuser.getRoleId());
		return (userDao.updateUser(chuUser.getName(),chuUser.getUserId(), chuUser.getUpw(), chuUser.getZwdm(), chuUser.getRoleId(),chuUser.getBmdm()) == 1);
	}

	/**
	 * 功能：通过ID找到某个ChuUser
	 * @param userId
	 */
	public ChuUser getUserById(int userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	public ChuUser getUserByUname(String uname) {
		return userDao.selectByUname(uname);
	}

	/**
	 * 功能：通过username和password找到某个ChuUser
	 * @param username
	 * @param passwd
	 */
	public ChuUser getUser(String username, String passwd) {

		// 参数检查
		if (username == null || passwd == null) {
			return null;
		}

		ChuUser cu = userDao.selectByUname(username);
		if (cu == null) {
			return null;
		}
		String salt = cu.getSalt();
		String password = StringUtils.toMD5(passwd + salt);

		// 检查密码是否正确
		if (cu.getUpw().equalsIgnoreCase(password)) {
			return userDao.selectBy(username, password);
		} else
			return null;
	}

	/**
	 * 功能：向chu_user中添加一条数据 再添加之前我们还对一些属性进行操作
	 * @param user
	 */
	public boolean addUser(ChuUser newuser) {
		// 参数检查
		/**
		 * 要添加的属性有：
		 * userId,uname,upw,name,position,email,salt,verify,token,roleId,type
		 */
		if (newuser == null) {
			return false;
		} else {
			String salt = String.valueOf(NumberUtils.getRandomNum(100000, 999999));
			@SuppressWarnings("unused")
			String newupw = StringUtils.toMD5(newuser.getUpw() + salt);
			newuser.setSalt(salt);
			newuser.setVerify((short) 1);
			return (userDao.insert(newuser) == 1);
		}
	}

	/**
	 * 功能：在chu_user中删除一条记录
	 * @param user
	 */
	public boolean delUser(ChuUser user) {
		// 参数检查
		if (user == null) {
			return false;
		}

		// 存在检查以后再添加

		Integer id = user.getUserId();
		return (userDao.deleteByPrimaryKey(id) == 1);
	}

	/**
	 * 功能： 重置密码
	 * @param user
	 */
	public boolean resetPasswd(ChuUser user) {
		String salt = user.getSalt();
		String staticpwd = this.DEFAULT_PASSWD + salt;
		return (userDao.changePassword(user.getUserId(), StringUtils.toMD5(staticpwd)) == 1);
	}

	/**
	 * 功能：修改密码
	 * @param user
	 * @param old_password
	 * @param new_password
	 */
	public int changePasswd(ChuUser user, String old_password, String new_password) {
		String salt = user.getSalt();
		String oldpwd = StringUtils.toMD5(old_password + salt);
		if (!oldpwd.equalsIgnoreCase(user.getUpw())) {
			return error1;
		}
		String newpwd = StringUtils.toMD5(new_password + salt);
		if (newpwd.equalsIgnoreCase(user.getUpw())) {
			return error2;
		}
		Integer result = userDao.changePassword(user.getUserId(), newpwd);
		if (result == 1) {
			return success;
		} else {
			return error3;
		}
	}

	/**
	 * 功能：得到所有的VUser
	 */
	public List<VUser> getAllUsers() {
		return userDao.selectVUser();
	}
	
	public VUser getVUserByUname(String uname){
		return userDao.selectVUserByUname(uname);
	}
	

	public List<ChuUser> getUsersByType(int role_id) {
		return userDao.selectByRole_id(role_id);
	}

	/**
	 * 得到当前页的所有的用户信息表
	 * @param rows
	 * @param page
	 * @return 一个EasyuiDatagrid的类地对象，改对象有page,rows这两个属性
	 */
	public EasyuiDatagrid<VUser> getCurrentPageUser(int rows, int page) {
		EasyuiDatagrid<VUser> pageDatas = new EasyuiDatagrid<VUser>();
		try {
			List<VUser> userList = this.getAllUsers();
			if (userList != null && userList.size() > 0) {
				pageNavigator = new PageNavigator<VUser>(userList, rows);
				pageDatas.setRows(pageNavigator.getPage(page));
				pageDatas.setTotal(userList.size());
			} else {
				pageDatas.setRows(new ArrayList<VUser>());
				pageDatas.setTotal(0);
			}

		} catch (Exception e) {
			logger.error("获取用户列表失败!");
			throw e;
		}
		return pageDatas;
	}

}
