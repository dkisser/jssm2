package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CSPCX;
import org.springframework.stereotype.Repository;

@Repository("CSPCXDao")
public interface CSPCXMapper {
	
	@Select("select * from C_SPCX")
	public List<CSPCX> selectAll();
	
	@Select("select * from C_SPCX where mc = #{mc}")
	public List<CSPCX> selectByMC(String mc);

}
