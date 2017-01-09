package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.condition.UserCondition;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.db.pojo.UserRole;
import org.lf.jssm.db.pojo.VUser;
import org.springframework.stereotype.Repository;


@Repository("userDao")
public interface ChuUserMapper extends BaseMapper<ChuUser> {
	/**
	 * 根据条件查询用户信息
	 * @return
	 */
	List<ChuUser> selectByCondition(UserCondition condition);
	
	@Select("select * from chu_user where USER_ID = #{userId,jdbcType=INTEGER}")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.User")
    ChuUser selectByPrimaryKey(Integer userId);

	@Select("select * from chu_user where uname = #{uname} and upw = #{upw}")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.User")
	ChuUser selectBy(@Param("uname") String username, @Param("upw") String passwd);
	
	@Select("select * from chu_user where role_id = #{role_id,jdbcType=INTEGER}")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.User")
	List<ChuUser> selectByRole_id(int role_id);
	
	@Select("select * from chu_user where uname = #{uname}")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.User")
	ChuUser selectByUname(String uname);
	
	
	//查找VUser
	@Select("select * from V_USER ORDER BY USER_ID ASC")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.VUser")
	List<VUser> selectVUser();
	
	//根据uname查找VUser
	@Select("select * from V_USER where uname = #{uname}")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.VUser")
	VUser selectVUserByUname(String uname);
	
	
	//找所有的人
	@Select("select * from CHU_USER ORDER BY USER_ID ASC")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.User")
	List<ChuUser> selectAllChuUser();
	
	
	
	
	@Select("select u.userid, u.uname, u.name, u.role_id role_id, u.Type role_name from chu_user u")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.UserRole")
	List<UserRole> selectAllUserRole();
	
	
	

//更新操作
	@Update("update chu_user set name=#{name}, upw=#{upw},zwdm=#{zwdm,jdbcType=VARCHAR},role_id=#{role_id},bmdm=#{bmdm,jdbcType=VARCHAR}  where user_id = #{userid}")
	int updateUser(@Param("name")String name,@Param("userid") Integer userid,  @Param("upw") String upw, @Param("zwdm") String zwdm, @Param("role_id")Integer role_id, @Param("bmdm") String bmdm);

//	改变制定用户的角色
	@Update("update chu_user set role_id = #{new_role_id} where userid = #{user_id}")
	int changeUserRole(@Param("user_id") Integer user_id, @Param("new_role_id")Integer new_role_id);
	//根据ID更新密码
	@Update("update chu_user set UPW =#{new_password} where USER_ID = #{user_id}")
	int changePassword(@Param("user_id") Integer user_id, @Param("new_password")String new_password);
	
	@Select("select * from chu_user where position = #{position}")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.User")
	ChuUser selectByposition(@Param("position")String position);
	
	
//	@Insert("insert into chu_user (userid," + 
//	"	upw, uname,"+
//	"	name, position, email,"+
//	"	salt, verify, register_ip,"+
//	"	register_date, last_ip, last_login,"+
//	"	token, department_id, role_id,"+
//	"	Type, characters)"+
//	"	values (SQ_USER.NEXTVAL,"+
//	"	#{upw},"+
//	"	#{uname},"+
//	"	#{name},"+
//	"	#{position},"+
//	"	#{email},"+
//	"	#{salt},"+
//	"	#{verify},"+
//	"	#{registerIp},"+
//	"	#{registerDate},"+
//	"	#{lastIp},"+
//	"	#{lastLogin},"+
//	"	#{token},"+
//	"	#{departmentId},"+
//	"	#{roleId},"+
//	"	#{type},"+
//	"	#{characters})")
//	int insertUser(
//						@Param("upw") String upw, 	
//						@Param("uname") String uname, 
//						@Param("name") String name, 
//						@Param("position") String position, 
//						@Param("email") String email,
//						@Param("salt")String salt, 
//						@Param("verify")boolean verify,
//						@Param("registerIp")String registerIp,
//						@Param("registerDate")long registerDate,
//						@Param("lastIp")String lastIp,
//						@Param("lastLogin")long lastLogin,
//						@Param("token")String token,
//						@Param("departmentId")Integer departmentId,
//						@Param("role_id")Integer role_id, 
//						@Param("type") String type, 
//						@Param("characters") Integer characters	
//			);
//	
	/**
	 * 
	 * @查找参会的人
	 */
	@Select("select userid, name, position,type from chu_user where type like '%委员%' or type = '列席人'")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.User")
	List<ChuUser> selectAllMeetingUser();
	
	/**
	 * 查找所有的书记员
	 */
	@Select("select userid, name, position from chu_user where type = '书记员'")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.User")
	List<ChuUser> selectAllClerk();
	
	/**
	 * select 审委会秘书
	 */
	
	@Select("select userid,name,position from chu_user where position = '审委会秘书'")
	@ResultMap("org.lf.jssm.db.dao.ChuUserMapper.User")
	ChuUser selectSwhSecretary();
	
    //根据uname查找password
	@Select("select upw from chu_user where uname=#{uanme}")
	String getPasswordByUname(String uname);

}