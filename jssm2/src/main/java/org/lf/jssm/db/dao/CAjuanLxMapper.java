package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.CAjuanLx;
import org.springframework.stereotype.Repository;
@Repository("cajuanlxDao")
public interface CAjuanLxMapper extends BaseMapper<CAjuanLx> {
	  @Select("select * from c_ajuan_lx order by dm")
	  @ResultMap("org.lf.jssm.db.dao.CAjuanLxMapper.CAjuanLx")
	  List<CAjuanLx> selectAllAJuanLx();
	  
	  @Select("select * from c_ajuan_lx where tybz = '0'")
	  @ResultMap("org.lf.jssm.db.dao.CAjuanLxMapper.CAjuanLx")
	  List<CAjuanLx> selectAJuanLxListByTybz();
	  
	  @Select("select * from c_ajuan_lx lx where tybz = '0'")
	  @ResultMap("org.lf.jssm.db.dao.CAjuanLxMapper.CAjuanLx")
	  List<CAjuanLx> findAllLxType ();
	  
	  @Select("select * from c_ajuan_lx where mc=#{mc}")
	  @ResultMap("org.lf.jssm.db.dao.CAjuanLxMapper.CAjuanLx")
	  CAjuanLx findLxByLxMc (@Param("mc")String mc);
	  
	  @Select("select dm, mc, pym, tybz from c_ajuan_lx order by 1")
		@ResultMap("org.lf.jssm.db.dao.CAjuanLxMapper.CAjuanLx")
		List<CAjuanLx> selectAllAjlxList();

		/**
		 * 通过案卷类型dm找到相应字段
		 */
		@Select("select * from c_ajuan_lx where dm = #{dm}")
		@ResultMap("org.lf.jssm.db.dao.CAjuanLxMapper.CAjuanLx")
		CAjuanLx selectAjlxByDm(String dm);
		
		/**
		 * 通过案卷类型mc找到相应字段
		 */
		@Select("select * from c_ajuan_lx where mc = #{mc}")
		@ResultMap("org.lf.jssm.db.dao.CAjuanLxMapper.CAjuanLx")
		List<CAjuanLx> selectAjlxListByMc(String mc);

		@Select("select * from c_ajuan_lx where mc = #{mc}")
		@ResultMap("org.lf.jssm.db.dao.CAjuanLxMapper.CAjuanLx")
		CAjuanLx selectAjlxByMc(String mc);
		
		/**
		 * 通过dm更新案卷类型中的字段
		 */
		@Update("update c_ajuan_lx set mc = #{mc},pym = #{pym},tybz = #{tybz} where dm = #{dm}")
		int updateAjlxList(@Param("dm")String dm,@Param("mc")String mc,@Param("pym")String pym,@Param("tybz")String tybz);
		
		/**
		 * 通过dm删除案卷类型中的字段
		 */
		@Delete("delete from c_ajuan_lx where dm = #{dm}")
		int delAjlxListByDm(String dm);
}