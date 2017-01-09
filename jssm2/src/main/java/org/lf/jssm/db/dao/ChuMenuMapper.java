package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.ChuMenu;
import org.lf.jssm.db.pojo.RolePrivilege;
import org.springframework.stereotype.Repository;

@Repository("menuDao")
public interface ChuMenuMapper extends BaseMapper<ChuMenu>{
    @Select("select * from chu_menu where flag = 0")
    @ResultMap("org.lf.jssm.db.dao.ChuMenuMapper.Menu")
    List<ChuMenu> selectAll();
    
//  select 
//    m.*, 
//    case when nvl(rm.menu_id, 0) = 0 then 0 else 1 end checked
//  from chu_menu m 
//  left outer join chu_role_menu rm on rm.menu_id = m.id
//  and rm.role_id = 17;
    @Select("select m.*, case when nvl(rm.menu_id, 0) = 0 then 0 else 1 end checked from chu_menu m left outer join chu_role_menu rm on rm.menu_id = m.id and rm.role_id = #{role_id}")
    @ResultMap("org.lf.jssm.db.dao.ChuMenuMapper.RolePrivilege")
    List<RolePrivilege> selectByRoleId(Integer role_id);
    
    @Insert("insert into chu_role_menu values(#{role_id},#{menu_id})")
    int insertRoleMenu(@Param("role_id")Integer role_id,@Param("menu_id")Integer menu_id);
    
    @Delete("delete from chu_role_menu where role_id = #{role_id}")
    int deleteRoleAllMenu(Integer role_id);
    
}