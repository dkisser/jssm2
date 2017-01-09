package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CFy;


public interface CFyMapper {
	@Select("select * from c_fy where fymc=#{fymc}")
	@ResultMap("org.lf.jssm.db.dao.CFyMapper.CFyMap")
	CFy findFydmByFymc (@Param("fymc")String fymc);
	
	@Select("select * from c_fy fy where fy.fjm like 'H1%' and fy.fjm != 'H10'")
	@ResultMap("org.lf.jssm.db.dao.CFyMapper.CFyMap")
	List<CFy> findAllFymc ();
	
	@Select("select fy.dm,fy.fymc from c_fy fy where fy.fjm like #{fjm}")
	@ResultMap("org.lf.jssm.db.dao.CFyMapper.CFyMap")
	List<CFy> findFymcList (@Param("fjm")String fjm);
	
	@Select("select * from c_fy fy where fy.dm = #{dm}")
	@ResultMap("org.lf.jssm.db.dao.CFyMapper.CFyMap")
	CFy findFymcByFydm (@Param("fydm")Integer fydm);
}