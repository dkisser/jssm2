package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.CAjuanMl;
import org.springframework.stereotype.Repository;

@Repository("cajmlListDao")
public interface CAjuanMlMapper extends BaseMapper<CAjuanMl>{

	@Select("select * from c_ajuan_ml where pcdm = #{pcdm} order by id")
	@ResultMap("org.lf.jssm.db.dao.CAjuanMlMapper.CAjuanMlMap")
	List<CAjuanMl> selectmlByPcdm(@Param("pcdm") String pcdm);
	
	/**
	 * 通过当前id和pcdm查询案卷目录
	 */
	@Select("select * from c_ajuan_ml where pcdm = #{pcdm} and id = #{id}")
	@ResultMap("org.lf.jssm.db.dao.CAjuanMlMapper.CAjuanMlMap")
	CAjuanMl selectAjmlByIdAndPcdm(@Param("pcdm")String pcdm,@Param("id")Integer id);
	
	/**
	 * 更新案卷目录
	 */
	@Update("update c_ajuan_ml set mc = #{mc} ,pym = #{pym},tybz = #{tybz},remark = #{remark} where pcdm = #{pcdm} and id = #{id}")
	int updateAjml(@Param("mc")String mc,@Param("pym")String pym,@Param("tybz")String tybz,@Param("remark")String remark,@Param("pcdm")String pcdm,@Param("id")int id);

	/**
	 * 删除一条案卷目录
	 */
	@Delete("delete from c_ajuan_ml where pcdm = #{pcdm} and id = #{id}")
	int delAjuanMl(@Param("pcdm")String pcdm,@Param("id")Integer id);
	
	/**
	 * 根据批次代码pcdm删除所对应的案卷目录
	 */
	@Delete("delete from c_ajuan_ml where pcdm = #{pcdm}")
	int delAjuanMlByPcdm(@Param("pcdm")String pcdm);
	
	@Select("select * from c_ajuan_ml")
	@ResultMap("org.lf.jssm.db.dao.CAjuanMlMapper.CAjuanMlMap")
	List<CAjuanMl> selectAllAJuanMl();

	/**
	 * 根据目录代码查询目录信息
	 * @param mldm
	 * @return
	 */
	CAjuanMl getMlInfoByMldm(@Param("mldm")String mldm);
}
