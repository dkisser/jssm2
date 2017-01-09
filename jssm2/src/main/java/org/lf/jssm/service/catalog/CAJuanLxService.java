package org.lf.jssm.service.catalog;

import java.util.List;

import org.lf.jssm.db.dao.CAjuanLxMapper;
import org.lf.jssm.db.pojo.CAjuanLx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CAJuanLxService {
	@Autowired
	private CAjuanLxMapper ajlxListDao;

	/**
	 * 查询案卷类型
	 */
	public List<CAjuanLx> findAllAjlxList() {
		return ajlxListDao.selectAllAjlxList();
	}

	/**
	 * 通过DM找到对应的案卷类型字段
	 */
	public CAjuanLx findAjlxByDm(String dm) {
		return ajlxListDao.selectAjlxByDm(dm);
	}

	/**
	 * 通过MC找到对应的案卷类型字段
	 */
	public CAjuanLx findAjlxByMc(String mc) {
		return ajlxListDao.selectAjlxByMc(mc);
	}

	/**
	 * insert案卷类型的字段
	 */
	public boolean addAjlxList(CAjuanLx chu) {
		return ajlxListDao.insert(chu) > 0 ? true : false;
	}

	/**
	 * 通过dm更新案卷类型中的字段
	 */
	public boolean updateAjlxList(String dm, String mc, String pym, String tybz) {
		return ajlxListDao.updateAjlxList(dm, mc, pym, tybz) > 0 ? true : false;
	}

	/**
	 * 通过dm删除案卷类型中的字段
	 */
	public boolean delAjlxList(String dm) {
		return ajlxListDao.delAjlxListByDm(dm) > 0 ? true : false;
	}
}
