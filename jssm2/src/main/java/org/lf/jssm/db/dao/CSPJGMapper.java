package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CSPJG;
import org.springframework.stereotype.Repository;

@Repository("CSPJGDao")
public interface CSPJGMapper {
	
	@Select("select * from C_SPJG")
	public List<CSPJG> selectAll();
	
	@Insert("insert into C_SPJG values(#{DM},#{newData},#{pym},0,null)")
	public int insertCSPJG(@Param("DM")String DM,@Param("newData")String newData,@Param("pym")String pym);

	@Select("select * from C_SPJG where MC=#{MC}")
	public CSPJG selectByMC(String MC);
}
