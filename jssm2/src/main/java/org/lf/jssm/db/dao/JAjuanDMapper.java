package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.JAjuanD;

/**
 * 案卷整理子目录dao层
 * @author sunwill
 *
 */
public interface JAjuanDMapper {
	/**
	 * 新增记录
	 * @param record
	 * @return
	 */
    int insert(JAjuanD record);
	/**
	 * 查询最大的内部编号
	 * @param jad
	 * @return
	 */
	Integer getPicMaxNbbh(JAjuanD jad);
	/**
	 * 根据二维码查询已整理的图片数量
	 * @param ewm
	 * @return
	 */
	Integer getCountsByPicEwm(@Param("ewm")String ewm);
	
	
	Integer updateall(JAjuanD record);
	
	@Select("select * from J_AJUAN_D where AJ_EWM = #{aj_ewm,jdbcType=VARCHAR}ORDER BY 3,6")
	List<JAjuanD>selectByajEwmOrder(String aj_ewm);
	
	/**
	 * 删除指定二维码相关信息
	 * @param ewm
	 */
	void deleteByEwm(@Param("ewm")String ewm);
	/**
	 * 查询内部编号比指定记录大的所有记录
	 * @param jad
	 * @return
	 */
	List<JAjuanD> getBehindJad(JAjuanD jad);
	/**
	 * 根据条件查询单条记录
	 * @param jad2
	 * @return
	 */
	JAjuanD getJadInfo(JAjuanD jad);
	/**
	 * 更新整理图片相关信息
	 * @param behindJad
	 */
	void update(JAjuanD jad);
	/**
	 * 查询内部编号位于指定区间的案卷信息(不包含首尾)
	 * @param targetJad
	 * @return
	 */
	List<JAjuanD> getJadBetweenAnd(JAjuanD targetJad);
	/**
	 * 根据条件查询相关案卷整理信息
	 * @param jad
	 * @return
	 */
	List<JAjuanD> getJadsByCondition(JAjuanD jad);
	/**
	 * 将zml信息设为空
	 * @param ewm
	 */
	void setZml2Null(JAjuanD jad);
	/**
	 * 根据目录代码和案卷二维码，查询所有相关标签信息
	 * @param jad
	 * @return
	 */
	List<JAjuanD> getAllLabsInfo(JAjuanD jad);
	
	@Update("UPDATE J_AJUAN_D SET MLDM = NULL,NBBH = NULL where EWM = #{ewm}")
	int updateclear(String ewm);
}