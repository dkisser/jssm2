package org.lf.jssm.db.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.model.AjjyOuterHistoryModel;
import org.lf.jssm.service.model.AjjyOuterHistoryParam;
import org.lf.jssm.service.model.JZQueryParam;

public interface VJzMapper extends BaseMapper<VJz> {
	
	@Select("select * from v_jz where jzewm = #{jzewm}")
	@ResultMap("org.lf.jssm.db.dao.VJzMapper.VJzMap")
	VJz selectJzByEwm(String jzewm);

	@Select("select * from v_jz where status=0")
	@ResultMap("org.lf.jssm.db.dao.VJzMapper.VJzMap")
	List<VJz> selectByStatus();
	
	@Select("select * from v_jz where jzewm =#{ewm} union all select jz.* "
			+ "from j_ajuan aj, v_jz jz where aj.ewm = #{ewm}  and aj.jzid = jz.jzid "
			+ "union all select jz.* from j_ajuan_d ajd, v_jz jz "
			+"where ajd.ewm = #{ewm} and ajd.aj_ewm = jz.ajewm")
	@ResultMap("org.lf.jssm.db.dao.VJzMapper.VJzMap")
	List<VJz> selectListByEwm(String ewm);
	
	List<VJz> selectListByLike(@Param("dsr") String  dsr,
			   				   @Param("ah") String ah,
							   @Param("larqF") Date larqF, 
							   @Param("larqT") Date larqT, 
							   @Param("jarqF") Date jarqF,
							   @Param("jarqT") Date jarqT
							   );
	
	@Select("select * from v_jz where REGEXP_SUBSTR(ah, '[^)]+字') = #{ahz}")
	@ResultMap("org.lf.jssm.db.dao.VJzMapper.VJzMap")
	List<VJz> selectListByAhz(String ahz);
	
	@Select("select * from v_jz where ah = #{ah}")
	@ResultMap("org.lf.jssm.db.dao.VJzMapper.VJzMap")
	List<VJz> selectJzListByAh(String ah);
	
	void deleteJZALLbyjzewm(String jzewm);
	
	List<VJz> select_query_list(JZQueryParam nbcx);
	
	List<AjjyOuterHistoryModel> select_outerHistory_list(AjjyOuterHistoryParam param);
	
	@Select("select  jz.* from v_jz jz, j_jzjy jy, j_wlry ry, c_yldn dn where jz.jzewm = jy.jzewm and jy.wlry_id = ry.id and ry.djdn = dn.dm and dn.ip = #{ip} and ry.status = 1")
	@ResultMap("org.lf.jssm.db.dao.VJzMapper.VJzMap")
	public List<VJz> findAllDatas(@Param("ip") String ip);
	
	@Update("update j_wlry set status = 0 , jsrq = #{endDate} where djdn = (select dm from c_yldn where ip = #{ip})")
	public int updateStatus(@Param("endDate")Date endDate,@Param("ip")String ip);
	
	@Select("select print from j_wlry jw,c_yldn cy where cy.ip = #{ip,jdbcType=VARCHAR} and cy.dm = jw.djdn and jw.status = 1")
	String getPrintByIp(String ip);
}
