package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.VUserAhz;

public interface VUserAhzMapper extends BaseMapper<VUserAhz>{
	@Select("select ahz from v_user_ahz where  user_id=#{userId} group by ahz")
	@ResultMap("org.lf.jssm.db.dao.VUserAhzMapper.vUserAhz")
	List<VUserAhz> getComboboxListByAhz(@Param("userId")String userId);
}
