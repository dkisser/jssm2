package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.JAjjz;
import org.lf.jssm.service.model.AJuanCode;
import org.springframework.stereotype.Repository;
@Repository("jajjzDao")
public interface JAjjzMapper {
	@Insert("insert into J_AJJZ values(#{id},#{jzewm},#{ajewm})")
	int insertAjjz (@Param("id")String id,@Param("jzewm")String jzewm,@Param("ajewm")String ajewm);
	
	@Select("select aj.pcdm,ajjz.aj_ewm from j_ajjz ajjz, j_ajuan aj where aj.ewm = ajjz.aj_ewm and ajjz.jz_ewm = #{jz_ewm}")
	AJuanCode selectpcdmbyJzewm(@Param("jz_ewm")String jzewm);
	
	@Select("select jz_ewm from j_ajjz where aj_ewm = #{ajewm}")
	List<JAjjz> isAjewmHaveUsed(String ajewm);
	
	@Select("select jz_ewm from j_ajjz where jz_ewm = #{jzewm}")
	List<JAjjz> isJzewmHaveUsed(String jzewm);
}

