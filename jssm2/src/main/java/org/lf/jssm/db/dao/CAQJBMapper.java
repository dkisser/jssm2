package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CAQJB;
import org.springframework.stereotype.Repository;

@Repository("CAQJBDao")
public interface CAQJBMapper {
	
	@Select("select * from C_AQJB")
	public List<CAQJB> selectAll();
	
	@Select("select * from C_AQJB where mc = #{mc}")
	public List<CAQJB> selectByMC(String mc);

	@Select("select * from c_aqjb where mc = #{mc}")
	@ResultMap("org.lf.jssm.db.dao.CAQJBMapper.CAQJB")
	CAQJB selectByMc(String mc);
	
	@Select("select mc from c_aqjb group by mc")
	List<String> selectAllMc();
}
