package org.lf.jssm.service.ajuan;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.lf.jssm.db.dao.CAQJBMapper;
import org.lf.jssm.db.dao.CAjuanYjflMapper;
import org.lf.jssm.db.dao.CBgqxMapper;
import org.lf.jssm.db.dao.JJzMapper;
import org.lf.jssm.db.dao.LJzMapper;
import org.lf.jssm.db.dao.VAJuanDMapper;
import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.db.pojo.CAQJB;
import org.lf.jssm.db.pojo.CAjuanYjfl;
import org.lf.jssm.db.pojo.CBgqx;
import org.lf.jssm.db.pojo.JJZ;
import org.lf.jssm.db.pojo.LJz;
import org.lf.jssm.db.pojo.VJz;
import org.lf.utils.DateUtils;
import org.lf.utils.EasyuiComboBox;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GDZLService {

	@Autowired
	private VJzMapper vjzDao;

	@Autowired
	private LJzMapper ljzDao;

	@Autowired
	private JJzMapper jjzDao;

	@Autowired
	private CAjuanYjflMapper cajuanyjflDao;

	@Autowired
	private CAQJBMapper CAQJBDao;

	@Autowired
	private CBgqxMapper cBgqxDao;

	@Autowired
	private VAJuanDMapper vAJuandDao;

	@Autowired
	private AjzlService ajzlService;

	/**
	 * “取消归档”操作，数据库增添一条记录 并将j_jz的归档状态置为0
	 * 
	 * @param ljz
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int insertLjzRecord(LJz ljz, Integer status) {
		ljzDao.insert(ljz);
		jjzDao.updatestatusJJzByEWm(status, ljz.getJzewm());
		return 1;
	}

	/**
	 * “删除卷宗” 删除相关表 J_JZ,J_AJUAN,J_AJJZ,J_AJUAN_D 并删除图片相关信息以及磁盘中的图片文件
	 * 
	 * @param rows
	 * @param page
	 * @param Param
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int delectJz(LJz ljz) {
		if (ljz == null || StringUtils.isEmpty(ljz.getJzewm())) {
			return 0;
		}
		ljz.setId(StringUtils.getUUID());
		ljz.setOper_date(new Date());
		ljzDao.insert(ljz);// 插入删除记录
		try {
			ajzlService.deleteAllPicByJzewm(ljz.getJzewm());//根据卷宗二维码删除所有相关图片文件及数据库信息
		} catch (Exception e) {
			// catch异常防止回滚，无论删除文件是否出错，都删除卷宗信息
		}
		vjzDao.deleteJZALLbyjzewm(ljz.getJzewm());// 删除卷宗相关信息（这之前要先删除图片）
		return 1;
	}

	/**
	 * 查找那些未归档（status=0）的卷宗,以Easyui对应的格式返回
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            每一页记录数
	 * @return
	 */
	public EasyuiDatagrid<VJz> getJzDatagridList(int page, int rows) {
		List<VJz> vjzList = vjzDao.selectByStatus();
		EasyuiDatagrid<VJz> easyuiDatagrid = new EasyuiDatagrid<>();
		if (vjzList != null) {
			PageNavigator<VJz> pageNavigator = new PageNavigator<>(vjzList,
					rows);
			easyuiDatagrid.setRows(pageNavigator.getPage(page));
			easyuiDatagrid.setTotal(vjzList.size());
		} else {
			easyuiDatagrid.setRows(null);
			easyuiDatagrid.setTotal(0);
		}
		return easyuiDatagrid;

	}

	/**
	 * 根据ewm查找对应的卷宗
	 */
	public JJZ findJJzByEwm(String jzewm) {
		return jjzDao.selectJJZByEwm(jzewm);
	}

	/**
	 * 取当前用户输入年份归档号数最大值加一
	 */
	/*
	 * public Integer getMaxGdhs(String gdrq){ String gdrqYear =
	 * gdrq.substring(0,4); if(jjzDao.selectGdhsByGdrq(gdrqYear)==null){ return
	 * 1; } return jjzDao.selectGdhsByGdrq(gdrqYear); }
	 */

	/**
	 * 归档号数的初始值： 若该卷宗案号（ah）存在于数据表中，则归档号数为该案号的归档号数，若归档号数全为空，则取当前年份中最大的归档号数，加一
	 */
	public Integer getCurrGdhs(String ah) {
		if (jjzDao.selectGdhsByAh(ah) == null) {
			return jjzDao.selectGdhsByGdrq(DateUtils.toString(new Date())
					.substring(0, 4));
		} else {
			return jjzDao.selectGdhsByAh(ah);
		}
	}

	/**
	 * 当前卷宗卷号的最大值加1
	 */
	/*
	 * public Integer getMaxJhById(String id){
	 * if(jjzDao.SelectJhById(id)==null){ return 1; }else{ return
	 * jjzDao.SelectJhById(id); } }
	 */

	/**
	 * 查找卷宗分类下拉框的数据，返回List
	 * 
	 * @param yjflFlag
	 */
	public List<EasyuiComboBoxItem<String, String>> getGdJzfl(String yjflFlag) {
		List<CAjuanYjfl> yjflList = cajuanyjflDao.getAllAjuanYifl();
		TreeMap<String, String> map = new TreeMap<String, String>();
		if ("ks".equals(yjflFlag)) {
			for (CAjuanYjfl yjfl : yjflList) {
				map.put(yjfl.getDm(), yjfl.getMc());
			}
		} else {
			for (CAjuanYjfl yjfl : yjflList) {
				map.put(yjfl.getMc(), yjfl.getMc());
			}
		}
		EasyuiComboBox<String, String> ecb = new EasyuiComboBox<String, String>(
				map);
		return ecb.getRecords();
	}

	/**
	 * 归档号数（GDHS）做唯一性检查
	 * 
	 * @param ah4ks
	 */
	/*
	 * public String checkGdhsByGdrq(String gdrq, Integer gdhs,String ah, String
	 * ah4ks) { String gdrqYear = gdrq.substring(0,4); if(gdhs!=null){
	 * if(jjzDao.selectJZbyGdrqAndGdhs(gdrqYear, gdhs).size()==0){ return
	 * "success"; }else{ if(!StringUtils.isEmpty(ah4ks)){ ah = ah4ks;//快速整理时的案号
	 * } if(jjzDao.selectGdhsByAh(ah)==gdhs){ return "success"; }else{ return
	 * "false"; } } } return null; }
	 */

	/**
	 * 卷号（JH）做唯一性检查
	 * 
	 * @param jzewm4ks
	 */
	public String checkJhById(String id, Integer jh, String jzewm4ks) {
		if (jh != null) {
			if (StringUtils.isEmpty(jzewm4ks)) {
				if (jjzDao.selectJZbyIdAndJh(id, jh).size() == 0) {
					return "success";
				} else {
					return "false";
				}
			} else {
				// 快速整理，卷号检查
				JJZ jjz = new JJZ();
				jjz.setEwm(jzewm4ks);
				jjz.setJh(jh);
				List<JJZ> jzInfos = jjzDao.selectBycondition(jjz);
				if (jzInfos == null || jzInfos.size() == 0) {
					return "success";
				} else {
					return "false";
				}
			}
		}
		return null;
	}

	/**
	 * 对输入的归档信息做唯一性检查
	 */
	/*
	 * public String checkGDInfo(String gdrq, Integer gdhs, String id, Integer
	 * jh,String ah) { if (checkGdhsByGdrq(gdrq, gdhs,ah,null).equals("success")
	 * && checkJhById(id, jh,null).equals("success")) { return "success"; } else
	 * { return "false"; }
	 * 
	 * }
	 */

	/**
	 * 更新JJZ的信息,即保存用户输入信息到数据库
	 * 
	 * @throws ParseException
	 */
	public int SaveINputInfo(String yjfl, String gdrq, Integer gdhs,
			String jbdm, String qxdm, Integer jh, String remrak, String ewm)
			throws ParseException {
		Date gdrqDate = DateUtils.toDateymd(gdrq); // 将归档日期转换为日期类型
		CAjuanYjfl yjflDm = cajuanyjflDao.selectByMc(yjfl);
		CAQJB jbdmStr = CAQJBDao.selectByMc(jbdm);
		CBgqx qxdmStr = cBgqxDao.selectByMc(qxdm);
		return jjzDao.updateJJzByEwm(yjflDm.getDm(), gdrqDate, gdhs,
				jbdmStr.getDm(), qxdmStr.getDm(), jh, remrak, ewm);
	}

	/**
	 * 保存用户输入的信息到J_JZ表中。  creator_id，create_date指的是卷宗归档人员编号和归档时间。
	 * 修改卷宗状态（status）为归档（1）
	 * 
	 * @throws ParseException
	 */
	public int gdInfoFile(String yjfl, String gdrq, Integer gdhs, String jbdm,
			String qxdm, Integer jh, String remrak, String ewm)
			throws ParseException {
		Date gdrqDate = DateUtils.toDateymd(gdrq); // 将归档日期转换为日期类型
		CAjuanYjfl yjflDm = cajuanyjflDao.selectByMc(yjfl);
		CAQJB jbdmStr = CAQJBDao.selectByMc(jbdm);
		CBgqx qxdmStr = cBgqxDao.selectByMc(qxdm);
		return jjzDao.FileJJzByEWm(yjflDm.getDm(), gdrqDate, gdhs,
				jbdmStr.getDm(), qxdmStr.getDm(), jh, remrak, ewm);
	}

	/**
	 * 数据归档前判断是否有该卷宗的信息
	 */
	public String findVajuanDByJzEwm(String jzewm) {
		if (vAJuandDao.selectIsHaveVjzByJzewm(jzewm).size() == 0) {
			return "false";
		}
		return "success";
	}

	/**
	 * 归档 更新jz信息,将status设为0
	 */
	public boolean updateJJZStatus(String ewm) {
		if (jjzDao.updatestatusJJzByEWm(0,ewm) == 1) {
			return true;
		}
		return false;
	}

}
