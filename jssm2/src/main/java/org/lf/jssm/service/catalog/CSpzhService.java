package org.lf.jssm.service.catalog;

import java.util.List;
import java.util.TreeMap;

import org.lf.jssm.db.dao.CSpzhMapper;
import org.lf.jssm.db.pojo.CSpzh;
import org.lf.utils.EasyuiComboBox;
import org.lf.utils.EasyuiComboBoxItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** * @author  wenchen 
 * @date 创建时间：2016年8月21日 下午6:56:10 
 * @version 1.0 
 * @parameter 
 * c_spzh的业务层
 * */
@Service
public class CSpzhService {

	@Autowired
	private CSpzhMapper cspzhMapper;
	
	/**
	 * 功能:通过AJuanYjfl来得到相应的spzh
	 * @param dm
	 * @return List<CSpzh>;
	 */
	public List<CSpzh> findSpzhByAJuamYjfl (String dm) {
		return cspzhMapper.findSpzhByAJuamYjfl(dm);
	}
	
	/**
	 * 功能: 通过AJuanYjfl来得到相应的spzh
	 * 并转换成前台Combobox的Item
	 * @param dm (根据Yjfl得到的yjfl.dm)
	 * @return List<EasyuiComboBoxItem<String,String>>;
	 */
	public List<EasyuiComboBoxItem<String,String>> getSpzhByAJuanYjfl (String dm) {
		List<CSpzh> spzhList = this.findSpzhByAJuamYjfl(dm);
		TreeMap<String,String> spzhTreeMap = new TreeMap<String,String>();
		for (CSpzh spzh : spzhList) {
			spzhTreeMap.put(spzh.getZh(), spzh.getZh());
		}
		EasyuiComboBox<String, String> comboboxItem = new EasyuiComboBox<String,String>(spzhTreeMap);
		return comboboxItem.getRecords();
	}
}
