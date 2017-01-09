package org.lf.jssm.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CAhz;
import org.lf.jssm.db.pojo.CAjuanYjfl;
import org.lf.jssm.db.pojo.CJgdm;
import org.springframework.stereotype.Repository;


@Repository("CAhzDao")
public interface CAhzMapper {
	
	@Select("select ahz from c_ahz group by ahz")
	List<String> selectAllAhz();
	
	public List<CAhz> selectAll();
	
	public CAhz selectById(String Ahzdm);
	
	public List<String> findOtherAhz();
	
	public List<CAjuanYjfl> findAllYJFL();
	
	public List<CJgdm> findAllCBTS();
	
	public Integer findMaxAhzdm();
	
	public int insertRecord(CAhz cu);
	
	public int updateRecord(CAhz cu);

}