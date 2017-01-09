package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CBgqx;
import org.springframework.stereotype.Repository;

@Repository("cbgqxDao")
public interface CBgqxMapper extends BaseMapper<CBgqx> {
	
	@Select("select * from c_bgqx")
	@ResultMap("org.lf.jssm.db.dao.CBgqxMapper.CBgqxMap")
	List<CBgqx> selectAll();
	
	@Select("select * from c_bgqx where mc = #{mc}")
	@ResultMap("org.lf.jssm.db.dao.CBgqxMapper.CBgqxMap")
	CBgqx selectByMc(String mc);
	
	@Select("select mc from c_bgqx order by dm")
	List<String> selectAllmc();
}
