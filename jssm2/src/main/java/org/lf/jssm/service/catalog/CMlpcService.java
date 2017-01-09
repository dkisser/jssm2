package org.lf.jssm.service.catalog;

import java.util.List;
import java.util.TreeMap;

import org.lf.jssm.db.dao.CAjuanLxMapper;
import org.lf.jssm.db.dao.CAjuanMlMapper;
import org.lf.jssm.db.dao.CFyMapper;
import org.lf.jssm.db.dao.CMlpcMapper;
import org.lf.jssm.db.dao.CSpzhMapper;
import org.lf.jssm.db.pojo.CAjuanLx;
import org.lf.jssm.db.pojo.CAjuanMl;
import org.lf.jssm.db.pojo.CFy;
import org.lf.jssm.db.pojo.CMlpc;
import org.lf.jssm.service.model.ChuCMlpc;
import org.lf.utils.EasyuiComboBox;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CMlpcService {

	@Autowired
	private CAjuanMlMapper cajmlListDao;

	@Autowired
	private CMlpcMapper mlpcDao;

	@Autowired
	private CSpzhMapper cspzhMapper;
	
	@Autowired
	private CFyMapper cfyDao;
	
	@Autowired
	private CAjuanLxMapper cajuanlxDao;

	private PageNavigator<CAjuanMl> pageNavigator;
	private PageNavigator<ChuCMlpc> pageNavigator1;

	/**
	 * 功能: 更新数据 只限于根据dm更新pcmc,tybz,remark
	 * 
	 * @param mlpc
	 */
	public void editMlpc(ChuCMlpc mlpc) {
		String remark = mlpc.getRemark().replace("\r\n", "");
		mlpc.setRemark(remark);
		mlpcDao.editMlpc(mlpc.getPcmc(),mlpc.getTybz(),mlpc.getRemark(),mlpc.getDm());
	}

	/**
	 * 功能:在C_Mlpc表中插入一条记录
	 * 
	 * @param mlpc
	 */
	public void addMlpc(ChuCMlpc mlpc) {
		CMlpc newmlpc = new CMlpc();
		Integer fydm = Integer.parseInt(mlpc.getFydm());
		newmlpc.setFydm(fydm.shortValue());
		newmlpc.setQynd(mlpc.getQynd());
		String lx = cajuanlxDao.findLxByLxMc(mlpc.getLx()).getDm();
		newmlpc.setLx(lx);
		String zhdm;
		if (mlpc.getZhdm() == null || mlpc.getZhdm() == "") {
			zhdm = "";
			newmlpc.setZhdm("");
		} else {
			zhdm = cspzhMapper.findZhdmByZh(mlpc.getZhdm()).getDm();
			newmlpc.setZhdm(zhdm);
		}
		String dm = mlpc.getFydm() + mlpc.getQynd() + zhdm + lx;
		newmlpc.setDm(dm);
		newmlpc.setPcmc(mlpc.getPcmc());
		newmlpc.setTybz(mlpc.getTybz());
		String remark = mlpc.getRemark().replace("\r\n", "");
		newmlpc.setRemark(remark);
		newmlpc.setStatus("1");
		mlpcDao.addMlpc(newmlpc.getDm(),newmlpc.getFydm(),newmlpc.getQynd(),newmlpc.getZhdm(),newmlpc.getLx(),newmlpc.getPcmc(),newmlpc.getTybz(),newmlpc.getRemark(),newmlpc.getStatus());
	}

	/**
	 * 功能: 通过dm找到一个mlpc
	 * 
	 * @param dm
	 * @return CMlpc.class
	 */
	public ChuCMlpc getMlpcByDm(String dm) {
		return mlpcDao.findMlpcByDm(dm);
	}

	/**
	 * 功能:通过fydm找到c_mlpc下的dm
	 * 
	 * @param dm
	 * @return CMlpc.class;
	 */
	public ChuCMlpc getFydmByDm(String dm) {
		return mlpcDao.findMlpcByDm(dm);
	}

	/**
	 * 功能: 通过pcmc返回一条记录
	 * 
	 * @param pcmc
	 * @return CMlpc
	 */
	public ChuCMlpc findPcmc(String pcmc) {
		return mlpcDao.findPcmc(pcmc);
	}

	/**
	 * 功能:根据传进来的fydm查找到该条记录,为获取fjm准备
	 * 
	 * @param fydm
	 * @return
	 */
	public CFy findFymcByFydm(Integer fydm) {
		return cfyDao.findFymcByFydm(fydm);
	}

	/**
	 * 功能:根据fydm到fy中找到相应的fjm
	 * 
	 * @return List<CMlpc>;
	 */
	public List<CFy> findFymcList(String fjm) {
		return cfyDao.findFymcList(fjm);
	}

	/**
	 * 功能:根据传进来的dm找到相应的fydm然后根据fydm找到相应的fjm,然后进行模糊查询
	 * 
	 * @param dm
	 * @return List<EasyuiComboBoxItem<String, String>>
	 */
	public List<EasyuiComboBoxItem<String, String>> getFymcBydm(String dm) {
		Integer fydm = Integer.parseInt(this.getFydmByDm(dm).getFydm());
		CFy cfy = this.findFymcByFydm(fydm);
		String newfjm = cfy.getFjm().substring(0, 2);
		List<CFy> fyList = this.findFymcList(newfjm);
		TreeMap<String, String> fyTreeMap = new TreeMap<String, String>();
		for (CFy fy : fyList) {
			fyTreeMap.put(fy.getDm().toString(), fy.getFymc());
		}
		EasyuiComboBox<String, String> comboboxItem = new EasyuiComboBox<>(fyTreeMap);
		return comboboxItem.getRecords();
	}

	/**
	 * 功能:查询显示武汉市中级法院下属法院
	 * 
	 * @return List<EasyuiComboBoxItem<String, String>>；
	 */
	public List<EasyuiComboBoxItem<String, String>> getAllFymc() {
		List<CFy> fyList = cfyDao.findAllFymc();
		TreeMap<String, String> fyTreeMap = new TreeMap<String, String>();
		for (CFy fy : fyList) {
			fyTreeMap.put(fy.getDm(), fy.getFymc());
		}
		EasyuiComboBox<String, String> comboboxItem = new EasyuiComboBox<>(fyTreeMap);
		return comboboxItem.getRecords();
	}

	/**
	 * 功能: 将通过连接查询将所有的lx的名称放到List<EasyuiComboBoxItem<String, String>> 方便前台显示
	 * 
	 * @return List<EasyuiComboBoxItem<String, String>>
	 */
	public List<EasyuiComboBoxItem<String, String>> getAllLxName() {
		List<CAjuanLx> lxList = cajuanlxDao.findAllLxType();
		TreeMap<String, String> lxTree = new TreeMap<String, String>();
		for (CAjuanLx lx : lxList) {
			lxTree.put(lx.getMc(), lx.getMc());
		}
		EasyuiComboBox<String, String> comboboxItem = new EasyuiComboBox<>(lxTree);
		return comboboxItem.getRecords();
	}

	/**
	 * 功能:对所有的数据进行分页
	 * 
	 * @param page
	 * @param rows
	 * @return EasyuiDatagrid<CMlpc>.class
	 */
	public EasyuiDatagrid<ChuCMlpc> mlpcList(int page, int rows) {
		List<ChuCMlpc> mlpcList = mlpcDao.findAllCMlpc();
		pageNavigator1 = new PageNavigator<>(mlpcList, rows);
		List<ChuCMlpc> pageNavigatorList = pageNavigator1.getPage(page);
		EasyuiDatagrid<ChuCMlpc> pageList = new EasyuiDatagrid<>(pageNavigatorList, mlpcList.size());
		pageNavigator1 = null;
		return pageList;
	}

	/**
	 * 功能: 根据前台传过来的tybz和dm来转换状态
	 * 
	 * @param tybz
	 * @param dm
	 */
	public void refirmSOS(ChuCMlpc mlpc) {
		mlpcDao.refirmSOS(mlpc.getTybz(),mlpc.getDm());
	}

	/**
	 * 根据pcdm查询mlpc对应下的目录,分页显示在页面中
	 * 
	 * @param pcdm
	 * @return
	 */
	public EasyuiDatagrid<CAjuanMl> findMlByPcdm(String pcdm, int page, int rows) {
		List<CAjuanMl> ajmlList = cajmlListDao.selectmlByPcdm(pcdm);
		pageNavigator = new PageNavigator<>(ajmlList, rows);
		List<CAjuanMl> pageList = pageNavigator.getPage(page);
		EasyuiDatagrid<CAjuanMl> easyuiDatagrid = new EasyuiDatagrid<>(pageList, ajmlList.size());
		pageNavigator = null;
		return easyuiDatagrid;
	}

	/**
	 * 根据pcdm找到pcmc
	 * 
	 * @param dm
	 * @return
	 */
	public CMlpc findpcmcbydm(String pcdm) {
		return mlpcDao.selectMLPCByDm(pcdm);
	}

	/**
	 * 根据pcdm和id查询案卷目录
	 */
	public CAjuanMl findAjuanMlByIdAndPcdm(String pcdm, int id) {
		return cajmlListDao.selectAjmlByIdAndPcdm(pcdm, id);
	}

	/**
	 * 新增一条案卷目录
	 */
	public int addAjml(String mc, String tybz, String remark, int id, String pcdm) {
		CAjuanMl ajuanMl = new CAjuanMl();
		// 拼音码的生成
		String pym =  StringUtils.toShortPinYin(mc, 5).toLowerCase();
		// 生成目录代码（DM）
		String newId = String.format("%02d", id);
		String dm = pcdm + newId;
		// 备注去除换行符
		String newRemark = remark.replace("\r\n", "");
		ajuanMl.setDm(dm);
		ajuanMl.setId(id);
		ajuanMl.setMc(mc);
		ajuanMl.setPcdm(pcdm);
		ajuanMl.setPym(pym);
		ajuanMl.setRemark(newRemark);
		// 停用标志转换
		if (tybz.equals("启用")) {
			tybz = "0";
		} else {
			tybz = "1";
		}
		ajuanMl.setTybz(tybz);
		return cajmlListDao.insert(ajuanMl);
	}

	/**
	 * 更新案卷目录
	 */
	public int updateAjml(String mc, String tybz, String remark, int id, String pcdm) {
		// 拼音码的生成
		String pym =  StringUtils.toShortPinYin(mc, 5).toLowerCase();
		String newRemark = remark.replace("\r\n", "");
		// 停用标志转换
		if (tybz.equals("启用")) {
			tybz = "0";
		} else {
			tybz = "1";
		}
		return cajmlListDao.updateAjml(mc, pym, tybz, newRemark, pcdm, id);
	}

	/**
	 * 删除一条案卷目录
	 */
	public int delAjuanMl(int id, String pcdm) {
		return cajmlListDao.delAjuanMl(pcdm, id);
	}

	/**
	 * 清空案卷目录
	 */
	public int clearAjuanMl(String pcdm) {
		return cajmlListDao.delAjuanMlByPcdm(pcdm);
	}

	/**
	 * 查询案卷目录中最大序号
	 */
	public int getMaxId(String pcdm) {
		List<CAjuanMl> ajmlList = cajmlListDao.selectmlByPcdm(pcdm);
		int maxId = 0;
		for (int i = 0; i < ajmlList.size(); i++) {
			if (ajmlList.get(i).getId() > maxId) {
				maxId = ajmlList.get(i).getId();
			}
		}
		return maxId;
	}

	/**
	 * 批量增加案卷目录
	 */
	@Transactional(rollbackFor=Exception.class)
	public int importAjmlList(int id, String mlList, String pcdm) {
		int result = 0;
		
		String temp[] = mlList.trim().split("\r\n");
		if (id + temp.length > 100) {
			result = 0;
		} else {
			for (String s : temp) {
					CAjuanMl ajuanMl = new CAjuanMl();
					// 拼音码的生成
					String pym = StringUtils.toShortPinYin(s, 5).toLowerCase();
					// 生成目录代码（DM）
					String newId = String.format("%02d", id);
					String dm = pcdm + newId;
					ajuanMl.setDm(dm);
					ajuanMl.setId(id);
					id++; // 每次插入一条书记id都要+1
					ajuanMl.setMc(s);
					ajuanMl.setPcdm(pcdm);
					ajuanMl.setPym(pym);
					ajuanMl.setRemark(null);
					ajuanMl.setTybz("0");
					result = cajmlListDao.insert(ajuanMl);
			}
		}
		return result;
	}

}
