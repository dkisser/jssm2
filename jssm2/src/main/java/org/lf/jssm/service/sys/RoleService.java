package org.lf.jssm.service.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.lf.jssm.db.dao.ChuMenuMapper;
import org.lf.jssm.db.dao.ChuRoleMapper;
import org.lf.jssm.db.pojo.ChuRole;
import org.lf.jssm.db.pojo.ChuRoleMenu;
import org.lf.jssm.db.pojo.RolePrivilege;
import org.lf.jssm.service.model.ChuRoleMenuList;
import org.lf.jssm.service.model.RolePrivilegeList;
import org.lf.utils.EasyuiComboBox;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.EasyuiTree;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
public class RoleService {
	@Autowired
	private ChuRoleMapper roleDao; // 用的时候一直是Null
	@Autowired
	private ChuMenuMapper menuDao; // 用的时候一直是Null
	private PageNavigator<ChuRoleMenuList> RoleNavigator;

	/**
	 * 修改角色信息
	 * @param role_id
	 * @param role_name
	 * @param status
	 * @param str_id
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateRoleInfo(int role_id, String role_name, String status, String str_id) {
		ChuRole role = new ChuRole();
		role.setId(role_id);
		role.setName(role_name);
		role.setStatus(status);
		roleDao.deleteAllPower(role_id);
		String[] ids = str_id.split(";");
		for (int i = 0; i < ids.length; i++) {
			int menu_id = Integer.parseInt(ids[i]);
			ChuRoleMenu rm = new ChuRoleMenu();
			rm.setId(StringUtils.getUUID().toUpperCase());
			rm.setRoleId(role_id);
			rm.setMenuId(menu_id);
			roleDao.insertIntoPower(rm);
		}
		return roleDao.updateRole(role);
	}

	/**
	 * 增加角色
	 * @param role_id
	 * @param role_name
	 * @param status
	 * @param str_id
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public int addRole(int role_id, String role_name, String status, String str_id) {
		ChuRole role = new ChuRole();
		role.setId(role_id);
		role.setName(role_name);
		role.setStatus(status);
		String[] ids = str_id.split(";");
		for (int i = 0; i < ids.length; i++) {
			int menu_id = Integer.parseInt(ids[i]);
			ChuRoleMenu rm = new ChuRoleMenu();
			rm.setId(StringUtils.getUUID().toUpperCase());
			rm.setRoleId(role_id);
			rm.setMenuId(menu_id);
			roleDao.insertIntoPower(rm);
		}
		return roleDao.insertRole(role);
	}

	/**
	 * 获取表格数据
	 * @param page
	 * @param rows
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public EasyuiDatagrid<ChuRoleMenuList> findAllChuRoleMenuList(int page, int rows) {
		List<ChuRoleMenuList> cmList = roleDao.selectChuRoleMenuList();
		RoleNavigator = new PageNavigator<ChuRoleMenuList>(cmList, rows);
		EasyuiDatagrid<ChuRoleMenuList> dg = new EasyuiDatagrid<>(RoleNavigator.getPage(page), cmList == null ? 0 : cmList.size());
		return dg;
	}

	/**
	 *获取对话框内的权限树信息
	 * @param role_id
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public List<EasyuiTree> findByRoleListId(Integer role_id) {
		List<EasyuiTree> allTre = new ArrayList<EasyuiTree>();
		List<RolePrivilege> allRolePrivilege = null;
		if (role_id != null) {
			allRolePrivilege = roleDao.selectByRoleId(role_id);
			;
		} else {
			allRolePrivilege = roleDao.selectByRoleId(0);
		}
		RolePrivilegeList allRolePrivilegeList = new RolePrivilegeList(allRolePrivilege);
		// 一级目录
		List<RolePrivilege> oneallRolePrivilege = allRolePrivilegeList.getOneLevelRolePrivilegeList();
		for (RolePrivilege one : oneallRolePrivilege) {
			List<EasyuiTree> children = new ArrayList<EasyuiTree>();
			List<RolePrivilege> twoRolePrivilege = allRolePrivilegeList.getTwoLevelRolePrivilegeList(one);
			for (RolePrivilege two : twoRolePrivilege) {
				children.add(new EasyuiTree(two.getPriv_id()+"", two.getPriv_name(), null, two.getChecked()));
			}
			allTre.add(new EasyuiTree(one.getPriv_id()+"", one.getPriv_name(), children, false));
		}
		return allTre;
	}

	// 唯一性检测
	public Map<String, Integer> checkRoleInfo(String checkname, Integer checkid) {
		Map<String, Integer> check = new HashMap<>();
		if (checkid != null) {
			check.put("isId", roleDao.checkbyid(checkid));
		}
		check.put("isName", roleDao.checkbyname(checkname));
		return check;
	}

	/**
	 * 向chu_role_menu表中插入一行数据，达到将权限赋予某一用户的功能
	 * @param role_id
	 * @param menu_id
	 */
	public boolean addChuRoleMenu(Integer role_id, Integer menu_id) {
		return (menuDao.insertRoleMenu(role_id, menu_id) == 1);
	}

	/**
	 * 删除某一个用户的所有权限
	 * @param role_id
	 */
	public boolean delRoleAllMenu(Integer role_id) {
		return menuDao.deleteRoleAllMenu(role_id) > 0;
	}


	/**
	 * 功能： 得到所有的Roles
	 */
	public List<ChuRole> getAllRoles() {
		return roleDao.selectAll();
	}

	public ChuRole findRoleByName(String rolename) {
		return roleDao.selectByName(rolename);
	}

	/**
	 * 功能：根据RoleID找到Role
	 * @param id
	 */
	public ChuRole findRoleByid(int id) {
		return roleDao.selectById(id);
	}

	/**
	 * 功能：根据ID删除某条数据
	 * @param role
	 */
	public boolean delRole(ChuRole role) {
		// 参数检查
		if (role == null) {
			return false;
		}

		// 当找不到这项数据时返回false
		Integer id = role.getId();
		if (findRoleByid(id) == null) {
			return false;
		}

		return roleDao.deleteByPrimaryKey(id) == 1;
	}

	/**
	 * 功能:将所有的role以 List<EasyuiComboBoxItem<String, String>>的形式保存
	 * 可以直接传到combobox中
	 */
	public List<EasyuiComboBoxItem<String, String>> findAllRole() {
		List<ChuRole> roleList = this.getAllRoles();
		TreeMap<String, String> map = new TreeMap<String, String>();
		for (ChuRole role : roleList) {
			map.put(role.getName(),role.getName());
		}
		EasyuiComboBox<String, String> ecb = new EasyuiComboBox<String, String>(map);
		return ecb.getRecords();
	}

	/**
	 *根据name查找Id
	 * @param name
	 * @return
	 */
	public Integer getIdByName(String name){
		return roleDao.getIdByName(name);
	}
	
}
