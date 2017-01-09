package org.lf.jssm.service.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.lf.jssm.db.pojo.RolePrivilege;

/**
 * 角色权限数据封装
 * @author Administrator
 *
 */
public class RolePrivilegeList {
	private SortedMap<Integer, RolePrivilege> privList = new TreeMap<>();
	
	//新增测试用
	public SortedMap<Integer, RolePrivilege> getMap(){
		return this.privList;
	}
	
	/**
	 * 构造函数：将Collection中的值存入SortedMap中
	 * @param rolelist
	 */
	public  RolePrivilegeList(Collection<RolePrivilege> rolelist){
		
		//参数检查
		if(rolelist == null){
			throw new IllegalArgumentException("collection shoule be not null");
		}
		
		Integer i = 0;																				//从0开始
		Iterator<RolePrivilege> it = rolelist.iterator();
		while(it.hasNext()){
			privList.put(i, it.next());
			i++;
		}
	}
	
	public List<RolePrivilege> getOneLevelRolePrivilegeList(){
		
		List<RolePrivilege> list = new ArrayList<RolePrivilege>();
		
		Iterator<Integer> it = privList.keySet().iterator();
		while(it.hasNext()){
			RolePrivilege r = privList.get(it.next());
			if(r.getLevel() == 1){
				list.add(r);
			}
		}
		return list;
	}
	
	public List<RolePrivilege> getTwoLevelRolePrivilegeList(RolePrivilege One){
		
		List<RolePrivilege> list = new ArrayList<RolePrivilege>();
		
		Iterator<Integer> it = privList.keySet().iterator();
		while(it.hasNext()){
			RolePrivilege r = privList.get(it.next());
			if(r.getPid()== One.getPriv_id()){
				list.add(r);
			}
		}
		return list;
	}
	
	
}
