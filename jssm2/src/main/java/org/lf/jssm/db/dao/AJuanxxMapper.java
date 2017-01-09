package org.lf.jssm.db.dao;

import java.util.List;

import org.lf.jssm.db.pojo.AJuanxx;
import org.springframework.stereotype.Repository;

@Repository("AJuanxxDao")
public interface AJuanxxMapper {
	
	public List<AJuanxx> selectAll();
	
	public int insertRecord(AJuanxx record);
	
	public int updateRecord(AJuanxx record);
	
	public AJuanxx selectByAh(String ah);
	
}
