package org.lf.jssm.db.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.JJZ;
import org.lf.jssm.service.model.JzStatisticsMonths;
import org.lf.jssm.service.model.NewJzModel;
import org.lf.jssm.service.tdh.TdhJz;
import org.springframework.stereotype.Repository;

@Repository("jjzDao")
public interface JJzMapper {

	int insertNew(JJZ jz);

	@Select("select * from J_JZ")
	List<JJZ> selectAll();

	/**
	 * 获取指定二维码案卷数量
	 * 
	 * @param ewm
	 * @return
	 */
	Integer getCountByEwm(@Param("ewm") String ewm);

	@Select("select * from J_JZ where ewm = #{jzewm,jdbcType=VARCHAR}")
	@ResultMap("org.lf.jssm.db.dao.JJzMapper.JJZ")
	JJZ selectJJZByEwm(@Param("jzewm") String jzewm);

	/**
	 * 根据gdrq做归档号数的唯一性检查
	 */
	@Select("select * from J_JZ where to_char(gdrq,'YYYY')=#{gdrq} and gdhs = #{gdhs}")
	@ResultMap("org.lf.jssm.db.dao.JJzMapper.JJZ")
	List<JJZ> selectJZbyGdrqAndGdhs(@Param("gdrq") String gdrq,
			@Param("gdhs") Integer gdhs);

	/**
	 * 根据卷宗id做归档卷号的唯一性检查
	 */
	@Select("select * from J_JZ where id = #{id} and jh = #{jh}")
	@ResultMap("org.lf.jssm.db.dao.JJzMapper.JJZ")
	List<JJZ> selectJZbyIdAndJh(@Param("id") String id, @Param("jh") Integer jh);

	/**
	 * 更新jz信息
	 */
	@Update("update J_JZ set yjfl=#{yjfl},gdrq=#{gdrq},gdhs=#{gdhs},jbdm=#{jbdm},qxdm=#{qxdm},jh=#{jh},remark=#{remark} where ewm = #{ewm}")
	int updateJJzByEwm(@Param("yjfl") String yjfl, @Param("gdrq") Date gdrq,
			@Param("gdhs") Integer gdhs, @Param("jbdm") String jbdm,
			@Param("qxdm") String qxdm, @Param("jh") Integer jh,
			@Param("remark") String remark, @Param("ewm") String ewm);

	/**
	 * 归档 更新jz信息,将status设为1，保存卷宗归档人员编号和归档时间
	 */
	@Update("update J_JZ set yjfl=#{yjfl},gdrq=#{gdrq},gdhs=#{gdhs},jbdm=#{jbdm},qxdm=#{qxdm},jh=#{jh},remark=#{remark},status=1 where ewm = #{ewm}")
	int FileJJzByEWm(@Param("yjfl") String yjfl, @Param("gdrq") Date gdrq,
			@Param("gdhs") Integer gdhs, @Param("jbdm") String jbdm,
			@Param("qxdm") String qxdm, @Param("jh") Integer jh,
			@Param("remark") String remark, @Param("ewm") String ewm);

	/**
	 * 计算归档号数在当年出现的最大号数加1,注意gdrq为该字符串的年份
	 */
	@Select("select max(gdhs+1) from j_jz where  to_char(gdrq,'YYYY')=#{gdrq}")
	Integer selectGdhsByGdrq(String gdrq);

	/**
	 * 查找当前卷宗下卷号的最大值加1
	 */
	@Select("select max(jh+1) from j_jz where id = #{id}")
	Integer SelectJhById(String id);

	/**
	 * 判断ah存不存在数据表中
	 */
	@Select("select * from j_jz where ah = #{ah,jdbcType=VARCHAR}")
	@ResultMap("org.lf.jssm.db.dao.JJzMapper.JJZ")
	List<JJZ> selectIsHaveJzByAh(@Param("ah")String ah);

	/**
	 * 通过ah和归档日期获取到归档号数，此时，ah相同的情况
	 */
	@Select("select max(gdhs) from j_jz  where ah = #{ah} ")
	Integer selectGdhsByAh(String ah);

	// 查询归档和未归档数据
	@Select("select status, sum(decode(to_char(create_date,'mm'),'01',1,0)) Jan,sum(decode(to_char(create_date,'mm'),'02',1,0)) Feb,"
			+ "sum(decode(to_char(create_date,'mm'),'03',1,0)) Mar,sum(decode(to_char(create_date,'mm'),'04',1,0)) Apr, "
			+ "sum(decode(to_char(create_date,'mm'),'05',1,0)) May,sum(decode(to_char(create_date,'mm'),'06',1,0)) Jun,"
			+ "sum(decode(to_char(create_date,'mm'),'07',1,0)) Jul,sum(decode(to_char(create_date,'mm'),'08',1,0)) Aug,"
			+ "sum(decode(to_char(create_date,'mm'),'09',1,0)) Sep,sum(decode(to_char(create_date,'mm'),'10',1,0)) Oct,"
			+ "sum(decode(to_char(create_date,'mm'),'11',1,0)) Nov,sum(decode(to_char(create_date,'mm'),'12',1,0)) Dec "
			+ "from j_jz  where to_char(create_date,'yyyy')=#{year}  group by status")
	List<JzStatisticsMonths> findAllygd(@Param("year") Integer year);

	// 合计近5年已归档的总卷数
	@Select("select count(status) from j_jz where status = 1 and to_char(create_date,'YYYY') between #{startYear} and #{endYear}")
	int sumHasGdJzStatic(@Param("startYear") Integer startYear,
			@Param("endYear") Integer endYear);

	// 合计近5年未归档的总卷数
	@Select("select count(status) from j_jz where status = 0 and to_char(create_date,'YYYY') between #{startYear} and #{endYear}")
	Integer sumHasNotGdJzStatic(@Param("startYear") Integer startYear,
			@Param("endYear") Integer endYear);

	// 通过月份合计近5年，已归档的卷数
	@Select("select count(*) from j_jz where to_char(create_date,'mm')=#{month} and to_char(create_date,'YYYY') between #{startYear} and #{endYear} and status = #{status}")
	Integer countJZStatusByMonth(@Param("month") Integer month,
			@Param("startYear") Integer startYear,
			@Param("endYear") Integer endYear, @Param("status") Integer status);
	
	/**
	 * 归档 更新jz信息,将status设为0
	 */
	@Update("update J_JZ set status=${status} where ewm = #{ewm}")
	int updatestatusJJzByEWm(@Param("status")Integer status, @Param("ewm") String ewm );
	
	@Update("update J_JZ set jzmc=#{jzmc,jdbcType=VARCHAR},jzlx=#{jzlx,jdbcType=VARCHAR},spcx1=#{spcx1,jdbcType=VARCHAR},spjg1=#{spjg1,jdbcType=VARCHAR},"
			+ "spcx2=#{spcx2,jdbcType=VARCHAR},spjg2=#{spjg2,jdbcType=VARCHAR},jbdm=#{jbdm,jdbcType=VARCHAR} where ewm=#{ewm,jdbcType=VARCHAR}")
	int updatajzfm(@Param("jzmc")String jzmc,@Param("jzlx")String jzlx,@Param("spcx1")String spcx1,@Param("spjg1")String spjg1,
			@Param("spcx2")String spcx2,@Param("spjg2")String spjg2,@Param("jbdm")String jbdm,@Param("ewm")String ewm);

	/**
	 * 根据条件查询卷宗信息
	 * @param jjz
	 * @return
	 */
	List<JJZ> selectBycondition(JJZ jjz);
	/**
	 * 通达海根据案号查询案件信息
	 * @param archiveno
	 * @return
	 */
	List<TdhJz> selectByAh4Tdh(@Param("archiveno")String archiveno);

	/**
	 * 查询卷宗二维码
	 * @param tj
	 * @return
	 */
	String getJzEwm4Tdh(TdhJz tj);
	/**
	 * 创建卷宗时，插入卷宗信息到数据库
	 * @param njm
	 * @return
	 */
	int insert4CreateJz(NewJzModel njm);
}