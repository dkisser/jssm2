package org.lf.jssm.service.query;

import java.util.ArrayList;
import java.util.List;

import org.lf.jssm.db.dao.CRylxMapper;
import org.lf.jssm.db.dao.CYldnMapper;
import org.lf.jssm.db.dao.CZjlxMapper;
import org.lf.jssm.db.dao.JJzjyMapper;
import org.lf.jssm.db.dao.JWlryMapper;
import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.db.pojo.CRylx;
import org.lf.jssm.db.pojo.CYldn;
import org.lf.jssm.db.pojo.CZjlx;
import org.lf.jssm.db.pojo.JJzjy;
import org.lf.jssm.db.pojo.JWlry;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.model.JZQueryParam;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AJJYOuterRegisterService {
	@Autowired
	private CRylxMapper cRylxDao;
	
	@Autowired
	private CYldnMapper cYldnDao;
	
	@Autowired
	private CZjlxMapper cZjlxDao;
	
	@Autowired
	private JWlryMapper jWlryDao;
	
	@Autowired 
	private VJzMapper vjzDao;
	
	@Autowired
	private JJzjyMapper jJzjyDao;
	
	/**
	 * 外来人员登记
	 * j_wlry,j_jzjy
	 * 
	 * @param jWlry
	 * @return
	 */
	@Transactional(noRollbackFor=Exception.class)
	public int insertWlry(JWlry jWlry, String jzewm){
		String uuid = StringUtils.getUUID();
		jWlry.setId(uuid);
		jWlryDao.insert(jWlry);
		
		String[] temp = jzewm.split(";");
		JJzjy jzjy = null;
		for(int i = 0; i < temp.length; i++){
			jzjy = new JJzjy();
			jzjy.setId(StringUtils.getUUID());
			jzjy.setJzewm(temp[i]);
			jzjy.setWlryId(uuid);
			jJzjyDao.insert(jzjy);
		}
		return 1;
	}
	
	/**
	 * 根据电脑dm查询当前电脑是否被占用
	 * @return
	 */
	public JWlry getDjdnStatus(String djdn){
		JWlry jWlry = jWlryDao.selectWlryBydjdn(djdn);
		if(jWlry==null){
			return null;
		}else{
			return jWlry;
		}
	}
	
	/**
	 * 下拉列表:外来人员类型
	 * @return
	 */
	public List<EasyuiComboBoxItem<String, String>> getRylx(){
		List<CRylx> list = cRylxDao.selectCRylxMc();
		if(list == null){
			return new ArrayList<EasyuiComboBoxItem<String,String>>();
		}
		List<EasyuiComboBoxItem<String, String>> listEasy = new ArrayList<EasyuiComboBoxItem<String,String>>();
		EasyuiComboBoxItem<String, String> easyItem = null;
		for(int i = 0; i < list.size(); i++){
			easyItem = new EasyuiComboBoxItem<String, String>(list.get(i).getDm(),list.get(i).getMc());
			listEasy.add(easyItem);
		}
		return listEasy;
	}
	
	/**
	 * 下拉列表:证件类型
	 * @return
	 */
	public List<EasyuiComboBoxItem<String, String>> getZjlx(){
		List<CZjlx> list = cZjlxDao.selectAllmc();
		if(list == null){
			return new ArrayList<EasyuiComboBoxItem<String,String>>();
		}
		List<EasyuiComboBoxItem<String, String>> listEasy = new ArrayList<EasyuiComboBoxItem<String,String>>();
		EasyuiComboBoxItem<String, String> easyItem = null;
		for(int i = 0; i < list.size(); i++){
			easyItem = new EasyuiComboBoxItem<String, String>(list.get(i).getDm(),list.get(i).getMc());
			listEasy.add(easyItem);
		}
		return listEasy;
	}
	
	/**
	 * 下拉列表:使用电脑
	 * @return
	 */
	public List<EasyuiComboBoxItem<String, String>> getYldn(){
		List<CYldn> list = cYldnDao.selectAllmc();
		if(list == null){
			return new ArrayList<EasyuiComboBoxItem<String,String>>();
		}
		List<EasyuiComboBoxItem<String, String>> listEasy = new ArrayList<EasyuiComboBoxItem<String,String>>();
		EasyuiComboBoxItem<String, String> easyItem = null;
		for(int i = 0; i < list.size(); i++){
			easyItem = new EasyuiComboBoxItem<String, String>(list.get(i).getDm(),list.get(i).getMc());
			listEasy.add(easyItem);
		}
		return listEasy;
	}
	
	public List<VJz> getQueryList(JZQueryParam param){
		return vjzDao.select_query_list(param);
	}
	
	
	
}
