package org.lf.jssm.db.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.VAJuanD;
import org.springframework.stereotype.Repository;
@Repository("vAJuandDao")
public interface VAJuanDMapper extends BaseMapper<VAJuanD> {
/**
 * 获取V_ajuan_D
 * @param pcdm
 * @return
 */
	List<VAJuanD> selectVajuanDByjzewm(@Param("jzewm") String jzewm);
	
	@Select("select ml.mlid, ml.mlmc, ml.mldm"
			+ " from v_ajuan_ml ml "
			+ "where ml.pcdm = #{pcdm}"
			+ "order by ml.mlid")
	@ResultMap("org.lf.jssm.db.dao.VAJuanDMapper.vajuand")
	List<VAJuanD> selectVajuanDBypcdm(@Param("pcdm") String pcdm);
	
	@Select("select * from v_ajuan_d where jz_ewm=#{jzEwm,jdbcType=VARCHAR}")
	@ResultMap("org.lf.jssm.db.dao.VAJuanDMapper.vajuand")
	List<VAJuanD> getVAjuanDListByJzEwm(String jzEwm);
	
	@Select("select jzid from v_ajuan_d where jz_ewm=#{jzEwm} group by jzid")
	String getJzid(String jzEwm);
	
	@Select("select aj_ewm from v_ajuan_d where jz_ewm=#{jzEwm} group by aj_ewm")
	String getAjEwm(String jzEwm);
	
	@Select("select jh from v_ajuan_d where pic_ewm is not null and jz_ewm=#{jzEwm} group by jh")
	String getJzJh(String jzEwm);
	
	@Select("select mlmc from v_ajuan_d t where jz_ewm=#{jzEwm} pic_ewm is not null group by mlmc")
	List<String> getMlmc(String jzEwm);
	
	@Select("select mldm,mlid,mlmc,zml from v_ajuan_d where jz_ewm=#{jzEwm} group by mldm,mlid,mlmc,zml order by mlid")
	@ResultMap("org.lf.jssm.db.dao.VAJuanDMapper.vajuand")
	List<VAJuanD> getmlmcAndzml(String jzEwm);
	
	@Select("select * from v_ajuan_d t where pic_ewm is not null and jz_ewm=#{jzEwm}")
	@ResultMap("org.lf.jssm.db.dao.VAJuanDMapper.vajuand")
	List<VAJuanD> getVAjuanDPicEwmIsNotNull(String jzEwm);
	
	@Select("select mlid from v_ajuan_d t where jz_ewm=#{jzEwm} and mlmc=#{mlmc} and zml=#{zml} group by mlid")
	Integer getMlid(@Param("jzEwm")String jzEwm, @Param("mlmc")String mlmc, @Param("zml")String zml);
	
	@Select("select * from j_ajuan_d where ewm = #{jzEwm} or aj_ewm = #{jzEwm}")
	@ResultMap("org.lf.jssm.db.dao.VAJuanDMapper.vajuand")
	List<VAJuanD> isJzewmhaveUsed(String jzEwm);
	
	@Select("select ewm from j_ajuan_d where ewm = #{ajEwm} union all select ewm from j_jz where ewm =  #{ajEwm}")
	@ResultMap("org.lf.jssm.db.dao.VAJuanDMapper.vajuand")
	List<VAJuanD> isAjewmhaveUsed(String ajEwm);
	
	/**
	 * 查询案卷视图信息
	 * @param vajd
	 * @return
	 */
	List<VAJuanD> getAjInfo(VAJuanD vajd);
	
	@Select("select * from v_ajuan_d where jz_ewm = #{jzEwm}")
	@ResultMap("org.lf.jssm.db.dao.VAJuanDMapper.vajuand")
	List<VAJuanD> selectIsHaveVjzByJzewm(@Param("jzEwm")String jzewm);
	/**
	 * 根据卷宗二维码查询批次代码
	 * @param jzewm
	 * @return
	 */
	@Select("select DISTINCT PCDM from v_ajuan_d where jz_ewm=#{jzEwm,jdbcType=VARCHAR}")
	String getPcdmByJzewm(String jzewm);

	/**
	 * 根据条件查询图片二维码
	 * @param vd
	 * @return
	 */
	List<String> getPicEwmByCondition(VAJuanD vd);

	/**
	 * 根据条件查询起始内部编号
	 * @param vd
	 * @return
	 */
	Integer getStartNbbhByCondition(VAJuanD vd);

	/**
	 * 查询内容图片数量
	 * @param vd
	 * @return
	 */
	Integer getPicCounts(VAJuanD vd);
}
