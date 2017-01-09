package org.lf.jssm.service.catalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lf.jssm.db.dao.CYhzwMapper;
import org.lf.jssm.db.pojo.CYhzw;
import org.lf.jssm.service.model.UiautoCompleteBox;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CYhzwService {
	@Autowired
	private CYhzwMapper yhzwDao;

	private PageNavigator<CYhzw> pageNavigator;
	private EasyuiDatagrid<CYhzw> easyuiDatagrid;

	/**
	 * 获取表格数据
	 */
	public EasyuiDatagrid<CYhzw> findAllYHZWInfo(int page, int rows) {
		List<CYhzw> all = yhzwDao.selectAllYHZWInfo();
		pageNavigator = new PageNavigator<CYhzw>(all, rows);
		easyuiDatagrid = new EasyuiDatagrid<CYhzw>(pageNavigator.getPage(page), all.size());
		pageNavigator = null;
		return easyuiDatagrid;
	}
	
	/**
	 * 增加信息
	 */
	@Transactional(rollbackFor=Exception.class)
	public void addYZHWInfo(String dm, String mc, String tybz, String remark) {
		String pym = StringUtils.toShortPinYin(mc, 5).toLowerCase();
		yhzwDao.insertYHZWInfo(dm, mc, pym,tybz, remark);

	}

	@Transactional(rollbackFor=Exception.class)
	public void updeteYZHWInfo(String dm, String mc, String tybz, String remark) {
		String pym = StringUtils.toShortPinYin(mc, 5).toLowerCase();
		yhzwDao.updateYHZWInfo(dm, mc, pym, tybz, remark);

	}

	/*
	 * 表单验证
	 */
	public Map<String, Integer> checkId_Name(String checkid, String checkname) {
		Map<String, Integer> checkinfo = new HashMap<String, Integer>();
		if (checkid != null && checkid != "") {
			checkinfo.put("id", yhzwDao.CheckId(checkid));
		}
		checkinfo.put("name", yhzwDao.CheckName(checkname));
		return checkinfo;
	}

	/*
	 * 获取自动补全数据
	 */

	public List<UiautoCompleteBox> findBrowseInfo() {
		return yhzwDao.selectBrowseInfo();
	}
	
	
	//根据职位名称获取职位代码
	public String getDmByMc(String mc){
		return yhzwDao.getDmByMc(mc);
	}
}
