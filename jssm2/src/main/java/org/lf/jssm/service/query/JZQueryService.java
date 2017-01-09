package org.lf.jssm.service.query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.lf.jssm.db.dao.CAQJBMapper;
import org.lf.jssm.db.dao.CAhzMapper;
import org.lf.jssm.db.dao.CBgqxMapper;
import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.model.JZQueryParam;
import org.lf.jssm.service.model.VJzModel;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JZQueryService {
	@Autowired
	private CAhzMapper c_ahzDao;
	@Autowired
	private CAQJBMapper c_aqjbDao;
	@Autowired
	private CBgqxMapper c_bgqxDao;
	@Autowired
	private VJzMapper vjzDao;
	
	private PageNavigator<VJzModel> pageNavigator;
	/**
	 * 审判字号：下拉列表框
	 * 
	 * @return
	 */
	public List<EasyuiComboBoxItem<Integer, String>> getAllAhzInfo() {
		List<String> listAhz = c_ahzDao.selectAllAhz();
		return convertDataType(listAhz);
	}

	public EasyuiDatagrid<VJzModel> getVjzList(int rows, int page, JZQueryParam Param) {
		List<VJz> listVjz = vjzDao.select_query_list(Param);
		List<VJzModel> modelList = convertVJzModelList(listVjz); 
		pageNavigator = new PageNavigator<>(modelList, rows);
		EasyuiDatagrid<VJzModel> easyDatagrid = new EasyuiDatagrid<>(pageNavigator.getPage(page), modelList.size());
		pageNavigator = null;
		return easyDatagrid;
	}

	/**
	 * 卷宗类型（JZLX）：下拉列表框
	 * 
	 * @return
	 */
	public List<EasyuiComboBoxItem<Integer, String>> getJzlxInfo() {
		List<EasyuiComboBoxItem<Integer, String>> listCombo = new ArrayList<>();
		EasyuiComboBoxItem<Integer, String> comboItemZ = new EasyuiComboBoxItem<Integer, String>(1, "正卷");
		EasyuiComboBoxItem<Integer, String> comboItemF = new EasyuiComboBoxItem<Integer, String>(2, "副卷");
		listCombo.add(comboItemZ);
		listCombo.add(comboItemF);
		return listCombo;
	}

	/**
	 * 卷宗状态：下拉列表框
	 * 
	 * @return
	 */
	public List<EasyuiComboBoxItem<Integer, String>> getJzztInfo() {
		List<EasyuiComboBoxItem<Integer, String>> listCombo = new ArrayList<>();
		EasyuiComboBoxItem<Integer, String> comboItemG = new EasyuiComboBoxItem<Integer, String>(1, "归档");
		EasyuiComboBoxItem<Integer, String> comboItemW = new EasyuiComboBoxItem<Integer, String>(0, "未归档");
		listCombo.add(comboItemG);
		listCombo.add(comboItemW);
		return listCombo;
	}

	/**
	 * 安全级别（AQJB）：下拉列表框
	 * 
	 * @return
	 */
	public List<EasyuiComboBoxItem<Integer, String>> getAqjbInfo() {
		return convertDataType(c_aqjbDao.selectAllMc());
	}

	/**
	 * 保管期限:下拉列表框
	 */
	public List<EasyuiComboBoxItem<Integer, String>> getBgqxInfo() {
		return convertDataType(c_bgqxDao.selectAllmc());
	}

	/**
	 * 
	 * @param listStr
	 * @return
	 */
	private List<EasyuiComboBoxItem<Integer, String>> convertDataType(List<String> listStr) {
		List<EasyuiComboBoxItem<Integer, String>> listCombo = new ArrayList<>();
		if (listStr.size() == 0) {
			return listCombo;
		}
		EasyuiComboBoxItem<Integer, String> comboItem = null;
		for (int i = 0; i < listStr.size(); i++) {
			comboItem = new EasyuiComboBoxItem<Integer, String>(i + 1, listStr.get(i).toString());
			listCombo.add(comboItem);
		}
		return listCombo;
	}

	private List<VJzModel> convertVJzModelList(List<VJz> listVjz){
		if(listVjz.size() == 0){
			return new ArrayList<VJzModel>();
		}
		List<VJzModel> listModel = new ArrayList<VJzModel>();
		VJzModel vjzModel = null;
		VJz vjz = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0 ; i < listVjz.size() ; i++){
			vjz = listVjz.get(i);
			vjzModel = new VJzModel();
			vjzModel.setAh(vjz.getAh());
			vjzModel.setJzlx(vjz.getJzlx());
			vjzModel.setJh(vjz.getJh()!=null?vjz.getJh().toString():null);
			vjzModel.setAqjb(vjz.getAqjb());
			vjzModel.setStatus(vjz.getStatus());
			vjzModel.setGdrq((vjz.getGdrq()!=null)?sdf.format(vjz.getGdrq()):null);
			vjzModel.setGdhs(vjz.getGdhs()!=null?vjz.getGdhs().toString():null);
			vjzModel.setJzewm(vjz.getJzewm());
			vjzModel.setZlfs(vjz.getZlfs());
			vjzModel.setJzid(vjz.getJzid()!=null?vjz.getJzid().replace("\n", ""):null);
			listModel.add(vjzModel);
		}
		return listModel;
	}
}
