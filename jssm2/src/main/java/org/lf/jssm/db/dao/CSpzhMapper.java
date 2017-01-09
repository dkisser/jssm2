package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CSpzh;
import org.springframework.stereotype.Repository;

@Repository
public interface CSpzhMapper {
	
	@Select("select spzh.dm,spzh.zh from c_spzh spzh where spzh.yjfl=#{dm}")
	@ResultMap("org.lf.jssm.db.dao.CSpzhMapper.CSpzhtMap")
	List<CSpzh> findSpzhByAJuamYjfl (@Param("dm")String dm);
	
	@Select("select * from c_spzh where zh=#{zh}")
	@ResultMap("org.lf.jssm.db.dao.CSpzhMapper.CSpzhtMap")
	CSpzh findZhdmByZh (@Param("zh")String zh);
	
	
	@Select("select * from c_spzh where yjfl = #{yjfl} and tybz = '0'")
	@ResultMap("org.lf.jssm.db.dao.CSpzhMapper.CSpzhtMap")
	List<CSpzh> selectCpzhByYjfl(String yjfl);
	
	@Select("select spzh.* from c_spzh spzh,c_ajuan_yjfl yjfl where yjfl.mc=#{mc} and yjfl.dm=spzh.yjfl and spzh.tybz = '0'")
	@ResultMap("org.lf.jssm.db.dao.CSpzhMapper.CSpzhtMap")
	List<CSpzh> selectCpzhByYjflMc(String mc);
}