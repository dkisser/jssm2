package org.lf.jssm.service.query;

import java.util.ArrayList;
import java.util.List;

import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.service.model.AjjyOuterHistoryModel;
import org.lf.jssm.service.model.AjjyOuterHistoryParam;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AJJYOuterHistoryService {
	@Autowired
	private VJzMapper vjzDao;
	
	public EasyuiDatagrid<AjjyOuterHistoryModel> getAjjyOutHistory(int rows, int page, AjjyOuterHistoryParam param){
		if(param == null){
			return new EasyuiDatagrid<AjjyOuterHistoryModel>(new ArrayList<AjjyOuterHistoryModel>(),0);
		}
		List<AjjyOuterHistoryModel> list = vjzDao.select_outerHistory_list(param);
		if(list.size()==0){
			return new EasyuiDatagrid<AjjyOuterHistoryModel>(new ArrayList<AjjyOuterHistoryModel>(),0);
		}
		PageNavigator<AjjyOuterHistoryModel> pageNavigator = new PageNavigator<>(list, rows);
		EasyuiDatagrid<AjjyOuterHistoryModel> pojoList = new EasyuiDatagrid<>(pageNavigator.getPage(page), list.size());
		return pojoList;
	}
}
