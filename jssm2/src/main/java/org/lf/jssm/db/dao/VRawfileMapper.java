package org.lf.jssm.db.dao;

import java.util.List;

import org.lf.jssm.db.pojo.VRawfile;
/**
 * 扫描历史视图到层
 * @author sunwill
 *
 */
public interface VRawfileMapper {
	/**
	 * 根据用户，和年份查询获得一级目录年份
	 * 
	 * @param vrf
	 * @return
	 */
	List<String> getHistoryYear(VRawfile vrf);

	/**
	 * 获得二级目录月份
	 * 
	 * @param vrf
	 * @return
	 */
	List<String> getHistoryMonth(VRawfile vrf);
	/**
	 * 获得三级目录天
	 * @param vrf
	 * @return
	 */
	List<String> getHistoryDay(VRawfile vrf);
	/**
	 * 获得四级目录小时
	 * @param vrf
	 * @return
	 */
	List<String> getHistoryHour(VRawfile vrf);

	/**
	 * 获得扫描历史图片二维码信息和版本号
	 * @param vrf
	 * @return
	 */
	List<VRawfile> getImgsEwm(VRawfile vrf);
}