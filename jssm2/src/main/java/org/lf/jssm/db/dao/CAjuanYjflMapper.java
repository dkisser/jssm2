package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.CAjuanYjfl;
import org.springframework.stereotype.Repository;

@Repository("cajuanyjflDao")
public interface CAjuanYjflMapper extends BaseMapper<CAjuanYjfl> {
	  @Select("select * from c_ajuan_yjfl order by dm")
	  @ResultMap("org.lf.jssm.db.dao.CAjuanYjflMapper.CAjuanYjfl")
	  List<CAjuanYjfl> selectAllAJuanYjfl();
	  
	  @Select("select yjfl.dm,yjfl.mc from c_ajuan_yjfl yjfl where yjfl.tybz=0")
	  @ResultMap("org.lf.jssm.db.dao.CAjuanYjflMapper.CAjuanYjfl")
	  List<CAjuanYjfl> getAllAjuanYifl ();
	  
	  @Select("select * from c_ajuan_yjfl where tybz='0' order by dm")
	  @ResultMap("org.lf.jssm.db.dao.CAjuanYjflMapper.CAjuanYjfl")
	  List<CAjuanYjfl> selectYjflList();
	  
	  @Select("select * from c_ajuan_yjfl where tybz='0' and mc like CONCAT(#{mc,jdbcType=VARCHAR},'%') or pym like CONCAT(#{mc,jdbcType=VARCHAR},'%') order by dm")
	  @ResultMap("org.lf.jssm.db.dao.CAjuanYjflMapper.CAjuanYjfl")
	  List<CAjuanYjfl> selectYjflListByMcLike(String mc);
	  
	  @Select("select * from c_ajuan_yjfl where mc = #{mc}")
	  @ResultMap("org.lf.jssm.db.dao.CAjuanYjflMapper.CAjuanYjfl")
	  CAjuanYjfl selectByMc(String mc);
	  
	  
	  
	  
	  @Select("select * from c_ajuan_yjfl order by dm ")
		@ResultMap("org.lf.jssm.db.dao.CAjuanYjflMapper.CAjuanYjfl")
		List<CAjuanYjfl> selectAllYjfl();
		
		@Update("update c_ajuan_yjfl set mc = #{mc},pym = #{pym},tybz = #{tybz} where dm = #{dm}")
		int upadteYjflListByDm(@Param("mc")String mc,@Param("pym")String pym,@Param("tybz")String tybz,@Param("dm")String dm);
		
		/**
		 * 通过dm查询该行对应字段
		 */
		@Select("select * from c_ajuan_yjfl where dm = #{dm}")
		@ResultMap("org.lf.jssm.db.dao.CAjuanYjflMapper.CAjuanYjfl")
		CAjuanYjfl selectYjflByDm(String dm);
		
		/**
		 * 通过mc查询对应字段
		 */
		@Select("select * from c_ajuan_yjfl where mc = #{mc}")
		@ResultMap("org.lf.jssm.db.dao.CAjuanYjflMapper.CAjuanYjfl")
		CAjuanYjfl selectYjflByMc(String mc);

		@Select("select * from c_ajuan_yjfl where mc = #{mc}")
		@ResultMap("org.lf.jssm.db.dao.CAjuanYjflMapper.CAjuanYjfl")
		List<CAjuanYjfl>  selectYjflListByMc(String mc);
		/**
		 * 删除dm对应字段
		 */
		@Delete("delete from c_ajuan_yjfl where dm = #{dm}")
		int delYjflListByDm(String dm);
}