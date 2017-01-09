package org.lf.jssm.service.query;

import java.util.Date;
import java.util.List;

import org.lf.jssm.db.dao.JWlryMapper;
import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.db.pojo.VJz;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AJJYOuterQueryService {

	@Autowired
	private VJzMapper vjzDao;

	@Autowired
	private JWlryMapper jWlryDao;

	/**
	 * 查询所有数据
	 * **/
	public EasyuiDatagrid<VJz> findAllDatas(int rows, int page, String ip) {
		List<VJz> all = vjzDao.findAllDatas(ip);
		PageNavigator<VJz> pages = new PageNavigator<>(all, rows);
		EasyuiDatagrid<VJz> datas = new EasyuiDatagrid<>(pages.getPage(page), all.size());
		return datas;
	}

	/**
	 * 关闭后修改Status 和 jsrq 的值
	 * **/
	@Transactional
	public int updateStatus(String ip) {
		Date endDate = new Date();
		return vjzDao.updateStatus(endDate, ip);
	}

	/**
	 * 获得当前案卷借阅的信息
	 * 
	 * @param ip
	 * @return
	 */
	public String getPrintInfo(String ip) {
		return vjzDao.getPrintByIp(ip);
	}

	/**
	 * 检查是否登记
	 * 
	 * @param ip
	 * @return : 1 表示已经登记 0 表示没有登记
	 */
	public int CheckIsRegister(String ip) {
		int is = 0;
		if (jWlryDao.CheckIsRegister(ip) != null) {
			is = 1;
		}
		return is;
	}
}
