package org.lf.jssm.service.catalog;

import java.util.List;
import java.util.TreeMap;

import org.lf.jssm.db.dao.CJgdmMapper;
import org.lf.jssm.db.dao.JEnvMapper;
import org.lf.jssm.db.pojo.CJgdm;
import org.lf.jssm.db.pojo.JEnv;
import org.lf.utils.EasyuiComboBox;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CJgdmService {
	@Autowired
	private CJgdmMapper cJgdmMapper;

	public List<CJgdm> getAllJG() {
		return cJgdmMapper.selectAllJG();
	}
	
	public String getDmByMc(String mc){
		return cJgdmMapper.getDmByMc(mc);
	}
	
	@Autowired
	private JEnvMapper jEnvMapperDao;
	
	private PageNavigator<CJgdm> pageNavigator;
	
	/*
	 * 查找所有信息
	 * */
	public EasyuiDatagrid<CJgdm> findJgdmListInfo(int page,int rows){
		List<CJgdm> all=cJgdmMapper.findJgdListInfo();
		pageNavigator=new PageNavigator<>(all, rows);
		return new EasyuiDatagrid<>(pageNavigator.getPage(page), all.size());
	}
	
	
	/*
	 * 查找所有上级名称
	 * */
	public List<EasyuiComboBoxItem<String, String>> findSjmc(){
		TreeMap<String,String> map=new TreeMap<>();
		for(CJgdm cg :cJgdmMapper.findJgdSjmc()){
			map.put(cg.getDm(), cg.getMc());
		}
		return new EasyuiComboBox<String, String>(map).getRecords();
	}
	/*
	 * 
	 * 对机构名称做唯一性检查
	 * */
	public int checkMc(String mc){		
		return cJgdmMapper.checkMc(mc);
	}
	
	/*
	 * 
	 * 添加新机构
	 * */
	@Transactional(rollbackFor=Exception.class)
	public int Addjgdm(String mc,char tybz,String remark,String sjdm){
		String  dm="02"+String.valueOf(Integer.parseInt(cJgdmMapper.findMaxDm())+1);
		//对机构dm做唯一性检查
		if(cJgdmMapper.checkDm(dm)!=0){
			return 0;
		}
		String pym = StringUtils.toShortPinYin(mc, 5).toLowerCase();
		JEnv env=new JEnv();
		env.setEnvKey("SYS_FYDM");
		JEnv fydm=jEnvMapperDao.selectEnv(env);
		return cJgdmMapper.Addjgdm(dm, mc, pym, tybz, remark.replaceAll("[\n\r]","").trim(), sjdm, fydm==null ? null : fydm.getEnvValue());
	}
	
	
	/*
	 * 更新机构
	 * */
	@Transactional(rollbackFor=Exception.class)
	public int updatejgdm(String dm,String mc,char tybz,String remark,String sjdm){
		//存在性检查
		if(cJgdmMapper.checkDm(dm)!=1){
			return 0;
		}
		String pym = StringUtils.toShortPinYin(mc, 5).toLowerCase();	
		return cJgdmMapper.updatejgdm(mc, pym, tybz, remark, sjdm, dm);
	}

}
