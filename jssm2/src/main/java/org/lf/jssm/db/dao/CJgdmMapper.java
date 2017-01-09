package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.CJgdm;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 机构代码表  C_JGDM
 */
@Repository("cjgdmDao")
public interface CJgdmMapper {
	@Select("select * from C_JGDM where tybz = 0")
	@ResultMap("org.lf.jssm.db.dao.CJgdmMapper.CJgdm")
	List<CJgdm> selectAllJG();
	
	@Select("select dm from C_JGDM where mc=#{mc}")
	String getDmByMc(String mc);
	
	@Select("select c1.dm,c1.mc,c2.mc sjmc,c1.pym,c1.tybz,c1.sjdm,c1.remark from c_jgdm c1,c_jgdm c2 where  c1.sjdm=c2.dm and length(c1.dm) = 4 order by c1.dm")
	@RequestMapping("org.lf.jssm.db.dao.CJgdmMapper.CJgdm")
	List<CJgdm> findJgdListInfo();
	
	@Select("select * from c_jgdm t where length(dm) = 2 order by dm")
	@RequestMapping("org.lf.jssm.db.dao.CJgdmMapper.SjmcMap")
	List<CJgdm> findJgdSjmc();
	
	@Select("select count(*) from c_jgdm where mc=#{mc}")
	int checkMc(String mc);
	@Insert("insert into c_jgdm (dm,mc,pym,tybz,remark,sjdm,fydm) values(#{dm},#{mc},#{pym},#{tybz},#{remark},#{sjdm},#{fydm})")
	int Addjgdm(@Param("dm")String dm,@Param("mc")String mc,@Param("pym")String pym,@Param("tybz")char tybz,@Param("remark")String remark,@Param("sjdm")String sjdm,@Param("fydm")String fydm);
	
	@Select("select substr(max(dm), 3, 2) from c_jgdm where dm like '02%'")
	String findMaxDm();
	
	@Select("select count(*) from c_jgdm where dm=#{dm}")
	int checkDm(String dm);
	
	@Update("update c_jgdm set mc=#{mc},pym=#{pym},tybz=#{tybz},remark=#{remark},sjdm=#{sjdm} where dm=#{dm}")
	int updatejgdm(@Param("mc")String mc,@Param("pym")String pym,@Param("tybz")char tybz,@Param("remark")String remark,@Param("sjdm")String sjdm,@Param("dm")String dm);
}
