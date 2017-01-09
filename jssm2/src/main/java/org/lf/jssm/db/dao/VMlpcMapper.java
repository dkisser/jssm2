package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.VMlpc;
import org.springframework.stereotype.Repository;
@Repository("vmlpcDao")
public interface VMlpcMapper {
	@Select("select * from v_mlpc")
	public List<VMlpc> selectAll();
	
	@Select("select * from v_mlpc t where dm = #{pcdm}")
	public VMlpc selectByPCDM(String pcdm);
}

