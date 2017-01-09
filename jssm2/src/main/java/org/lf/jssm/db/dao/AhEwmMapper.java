package org.lf.jssm.db.dao;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.AhEwm;
import org.springframework.stereotype.Repository;
@Repository("ahewmMapper")
public interface AhEwmMapper {
	 
	@Select("select jz.ah, ajjz.jz_ewm from j_jz jz, j_ajjz ajjz where jz.ewm = ajjz.jz_ewm and ajjz.aj_ewm = #{ewm}")
	@ResultMap("org.lf.jssm.db.dao.AhEwmMapper.AhEwmMap")
	AhEwm getAhEwmByEwm (String ewm);
}

