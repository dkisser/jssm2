package org.lf.jssm.db.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.service.model.DayInfo;
import org.springframework.stereotype.Repository;
/**
 * 原始扫描文件dao层
 * @author sunwill
 *
 */
@Repository("jRawfileDao")
public interface JRawfileMapper extends BaseMapper<JRawfile> {
	/**
	 * 功能:按年月查询你扫描的次数
	 * @param year
	 * @param month
	 * @return
	 */
	public List<DayInfo> SelectByMonth(@Param("year")int year,@Param("month")int month);
	
	/**
	 * 功能:查所有的记录
	 * @return
	 */
	@Select("select * from j_rawfile")
	@ResultMap("org.lf.jssm.db.dao.JRawfileMapper.BaseResultMap")
	List<JRawfile> getAllRawFile ();
	
	/**
	 * 功能:根据所给的year和month查数量
	 * @param year
	 * @param month
	 * @return
	 */
	@Select("select count(*) from j_rawfile where scan_year = #{year} and scan_month = #{month}")
	Integer getPictureNumberByYearAndMonth (@Param("year")String year,@Param("month")String month);
	
	/**
	 * 查询指定二维码并且状态大于1的文件
	 * @param jf
	 * @return
	 */
	JRawfile getSavedRawFile(JRawfile jf);
	/**
	 * 删除指定文件数据库信息
	 * @param jf
	 */
	int deleteRawFileInfo(JRawfile jf);
	
	/**
	 * 根据传入实体更新原始文件信息
	 * @param jf
	 * @return
	 */
	int updateByJf(JRawfile jf);
	/**
	 * 查询扫描历史数量
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Integer getRawFilesCounts4History(@Param("userId")Integer userId, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
	/**
	 * 根据二维码获得图片信息
	 * @param jf
	 * @return
	 */
	JRawfile getRawFileByEwm(@Param("ewm")String ewm);
	
	/**
	 * 查询指定二维码的有效图片（状态大于1）数量
	 * @param ewm
	 * @return
	 */
	Integer getRawFileCountByEwm(@Param("ewm")String ewm);
}