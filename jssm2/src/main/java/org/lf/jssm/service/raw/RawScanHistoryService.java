package org.lf.jssm.service.raw;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.VRawfileMapper;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.db.pojo.VRawfile;
import org.lf.utils.EasyuiTree;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 扫描参数配置服务层
 * 
 * @author sunwill
 *
 */
@Service("scanHistoryService")
public class RawScanHistoryService {
	@Autowired
	private JRawfileMapper jRawfileDao;
	@Autowired
	private VRawfileMapper vRawfileDao;

	/**
	 * 查询是否存在扫描历史
	 * 
	 * @param session
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String hasHistory(HttpSession session, String startDate,
			String endDate) throws Exception {
		// 结尾日期加一天，到当天晚上0点
		Date end = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(endDate)
				.getTime() + 24 * 60 * 60 * 1000);
		ChuUser user = (ChuUser) session.getAttribute("loginInfo");
		Integer counts = jRawfileDao.getRawFilesCounts4History(
				user.getUserId(),
				new SimpleDateFormat("yyyy-MM-dd").parse(startDate), end);
		if (counts > 0) {
			return "success";
		}
		return null;
	}

	/**
	 * 初始化扫描历史树目录
	 * 
	 * @param session
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<EasyuiTree> getScanHistoryTree(HttpSession session,
			String startDate, String endDate) throws Exception {
		List<EasyuiTree> treeList = new ArrayList<EasyuiTree>();
		if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
			return treeList;
		}
		// 结尾日期加一天，到当天晚上0点
		Date end = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(endDate)
				.getTime() + 24 * 60 * 60 * 1000);
		ChuUser user = (ChuUser) session.getAttribute("loginInfo");
		VRawfile vrf = new VRawfile();
		vrf.setScanerId(user.getUserId());
		vrf.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
		vrf.setEndDate(end);
		List<String> yearList = vRawfileDao.getHistoryYear(vrf);
		if (yearList != null && yearList.size() > 0) {
			for (String year : yearList) {
				// 遍历年份
				EasyuiTree yearTree = new EasyuiTree();
				yearTree.setText(year + "年");
				yearTree.setState("closed");
				List<EasyuiTree> yearChildren = null;
				vrf.setYear(year);
				List<String> monthList = vRawfileDao.getHistoryMonth(vrf);
				if (monthList != null && monthList.size() > 0) {
					yearChildren = new ArrayList<EasyuiTree>();
					for (String month : monthList) {
						// 遍历月份
						EasyuiTree monthTree = new EasyuiTree();
						monthTree.setText(month + "月");
						monthTree.setState("closed");
						List<EasyuiTree> monthChildren = null;
						vrf.setMonth(month);
						List<String> dayList = vRawfileDao.getHistoryDay(vrf);
						if (dayList != null && dayList.size() > 0) {
							monthChildren = new ArrayList<EasyuiTree>();
							for (String day : dayList) {
								// 遍历天
								EasyuiTree dayTree = new EasyuiTree();
								// dayTree.setId("cykj_"+year + month + day);
								dayTree.setText(day + "日");
								dayTree.setState("closed");
								List<EasyuiTree> dayChildren = null;
								vrf.setDay(day);
								List<String> hourList = vRawfileDao
										.getHistoryHour(vrf);
								if (hourList != null && hourList.size() > 0) {
									dayChildren = new ArrayList<EasyuiTree>();
									for (String hour : hourList) {
										// 遍历小时
										EasyuiTree hourTree = new EasyuiTree();
										hourTree.setText(hour + "时");
										hourTree.setState("open");// 最下层节点打开状态
										dayChildren.add(hourTree);
									}
								}
								dayTree.setChildren(dayChildren);
								monthChildren.add(dayTree);
							}
						}
						monthTree.setChildren(monthChildren);
						yearChildren.add(monthTree);
					}
				}
				yearTree.setChildren(yearChildren);
				treeList.add(yearTree);
			}
		}
		return treeList;
	}

	/**
	 * 获得扫描历史图片二维码信息和版本号
	 * 
	 * @param session
	 * @param vrf
	 * @return
	 */
	public List<VRawfile> getImgsEwm(HttpSession session, VRawfile vrf) {
		ChuUser user = (ChuUser) session.getAttribute("loginInfo");
		vrf.setScanerId(user.getUserId());
		return vRawfileDao.getImgsEwm(vrf);
	}

}