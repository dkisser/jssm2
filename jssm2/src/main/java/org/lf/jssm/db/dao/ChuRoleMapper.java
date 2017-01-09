package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.ChuRole;
import org.lf.jssm.db.pojo.ChuRoleMenu;
import org.lf.jssm.db.pojo.RolePrivilege;
import org.lf.jssm.service.model.ChuRoleMenuList;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public interface ChuRoleMapper extends BaseMapper<ChuRole>{
	
	@Select("select * from chu_role")
    @ResultMap("org.lf.jssm.db.dao.ChuRoleMapper.Role")
	List<ChuRole> selectAll();
	
	//修改角色信息
	@Update("update chu_role set name=#{name},status=#{status} where id=#{id}")
	int updateRole(ChuRole role);
	//删除之前的权限
	@Delete("delete from chu_role_menu where role_id=#{role_id}")
	void deleteAllPower(@Param("role_id")int role_id);
	//插入新的权限
	void insertIntoPower(ChuRoleMenu rolemenu);
	//增加新的角色
	@Insert("insert into chu_role values(#{id},#{name},#{status})")
	int insertRole(ChuRole role);
	//获取表格数据
	List<ChuRoleMenuList> selectChuRoleMenuList();
	//id唯一性检测
	@Select("select count(*) from chu_role where id=#{id}")
	int checkbyid(@Param("id") int id);
	//name唯一性检测
	@Select("select count(*) from chu_role where name=#{name}")
	int checkbyname(@Param("name") String name);
	//获取权限数据
    List<RolePrivilege> selectByRoleId(int id);
	
	
	//新添加的
	@Select("select * from chu_role where id =#{id}")
	@ResultMap("org.lf.jssm.db.dao.ChuRoleMapper.Role")
	ChuRole selectById(Integer id);
	
	
	//新添加的
	@Select("select * from chu_role where name =#{name}")
	@ResultMap("org.lf.jssm.db.dao.ChuRoleMapper.Role")
	ChuRole selectByName(String name);
	
	//自己写的

	
	//根据name查找Id
	@Select("select id from chu_role where name =#{name}")
	Integer getIdByName(String name);
	
}