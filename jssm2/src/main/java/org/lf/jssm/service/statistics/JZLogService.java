package org.lf.jssm.service.statistics;

import java.util.ArrayList;
import java.util.List;

import org.lf.jssm.db.dao.LJzMapper;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.service.model.LJzModel;
import org.lf.jssm.service.model.LJzParam;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jZLogService")
public class JZLogService {
	
	@Autowired
	private LJzMapper ljzDao;
	
	/**
	 * 查找表格数据
	 * */
	public EasyuiDatagrid<LJzModel> findjzLogInfo(int rows,int page,LJzParam param){
		PageNavigator<LJzModel> pageNavigator;
		List<LJzModel> all = ljzDao.findjzLogInfo(param);
		pageNavigator = new PageNavigator<LJzModel>(all,rows);
		EasyuiDatagrid<LJzModel> data = new EasyuiDatagrid<>(pageNavigator.getPage(page),all.size());
		return data;
	}
	
	/**
	 * 查出所有的操作者
	 * **/
	public List<EasyuiComboBoxItem<Integer,String>> findOperators(){
		List<EasyuiComboBoxItem<Integer,String>> list = new ArrayList<>();
		EasyuiComboBoxItem<Integer,String> item;
		List<ChuUser> all = ljzDao.findOperators();
		for(ChuUser c : all){
			item = new EasyuiComboBoxItem<>();
			item.setId(c.getUserId());
			item.setText(c.getName());
			list.add(item);
		}
		return list;
	}

}
