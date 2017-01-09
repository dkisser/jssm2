package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.JWlry;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface JWlryMapper extends BaseMapper<JWlry> {
	int deleteByPrimaryKey(String id);

	JWlry selectByPrimaryKey(String id);

	int updateByPrimaryKey(JWlry record);

	@Select("select * from j_wlry where djdn = #{djdn} and status = 1")
	@ResultMap("org.lf.jssm.db.dao.JWlryMapper.BaseResultMap")
	JWlry selectWlryBydjdn(String djdn);
	
	@Select("select * from j_wlry")
    @ResultMap("org.lf.jssm.db.dao.JWlryMapper.BaseResultMap")
    List<JWlry> getAllJWlry ();
	
	@Select("select * from J_WLRY where KSRQ BETWEEN to_date ( #{beginYear,jdbcType=VARCHAR} , 'YYYY-MM-DD HH24:MI:SS' ) AND to_date ( #{endYear,jdbcType=VARCHAR} , 'YYYY-MM-DD HH24:MI:SS' )")
    @ResultMap("org.lf.jssm.db.dao.JWlryMapper.BaseResultMap")
    List<JWlry> getJWlryByYear(@Param("beginYear")String beginYear,@Param("endYear")String endYear);
	
	@Select("SELECT count(status) from J_WLRY where PRINT = #{print} and KSRQ BETWEEN to_date(#{beginYear},'YYYY-MM-DD HH24:MI:SS') and to_date(#{endYear},'YYYY-MM-DD HH24:MI:SS')")
    Integer getSumPermitORUnPermit (@Param("beginYear")String beginYear,@Param("endYear")String endYear,@Param("print")String print);

	@Select("select  ry.* from j_wlry ry, c_yldn dn where  ry.djdn = dn.dm and dn.ip = #{ip} and ry.status = 1")
	@ResultMap("org.lf.jssm.db.dao.JWlryMapper.BaseResultMap")
	public JWlry CheckIsRegister(@Param("ip") String ip);
}