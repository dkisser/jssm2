package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.JAjuan;
import org.springframework.stereotype.Repository;
@Repository("jajuanDao")
public interface JAjuanMapper {
	
	@Select("select * from j_ajuan where ewm = #{ewm}")
	@ResultMap("org.lf.jssm.db.dao.JAjuanMapper.JajuanMap")
	List<JAjuan> getJajuanByEwm (@Param("ewm")String ewm);
	
	@Select("select * from j_ajuan where ewm=#{ewm}")
	@ResultMap("org.lf.jssm.db.dao.JAjuanMapper.JajuanMap")
	List<JAjuan> getListByEwm(String ewm);
	
	int insertNew(JAjuan ajuan);
	
	int update(JAjuan ajuan);
	
	/**
	 * 获取指定二维码案卷数量
	 * @param ewm
	 * @return
	 */
	Integer getCountByEwm(@Param("ewm")String ewm);

	/**
	 * 根据卷宗二维码获得案卷信息
	 * @param jzewm
	 * @return
	 */
	JAjuan getJajuanByJzewm(@Param("jzewm")String jzewm);
}