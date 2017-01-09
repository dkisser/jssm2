package org.lf.jssm.service.ajuan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.lf.jssm.db.dao.AJuanxxMapper;
import org.lf.jssm.db.pojo.AJuanxx;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.lf.utils.servlet.CaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 通达海案卷信息服务层
 */
@Service("ajxxService")
public class AJuanxxService {
	@Autowired
	private AJuanxxMapper AJuanxxDao; 
	
	public List<AJuanxx> getAllRecords(){
		return AJuanxxDao.selectAll();
	}
	
	public int updateRecord(AJuanxx record){
		return AJuanxxDao.updateRecord(record);
	}
	
	public int insertRecord(AJuanxx record){
		return AJuanxxDao.insertRecord(record);
	}
	
	public AJuanxx getRecordByAh(String ah){
		return AJuanxxDao.selectByAh(ah);
	}
	
	public EasyuiDatagrid<AJuanxx> getAjxxList(int rows,int page,Map<String,CaseInfo> map){
		List<AJuanxx> list=new ArrayList<>();
		EasyuiDatagrid<AJuanxx> items=new EasyuiDatagrid<>();
		if(map!=null){
				//将从通达海查询的CaseInfo转换为ajxx案卷信息
			for (Entry<String,AJuanxx> entry : getAjxxMap(map).entrySet()) {
				list.add(entry.getValue());
			}
			PageNavigator<AJuanxx> pageUtil=new PageNavigator<>(list, rows);
			items.setRows(pageUtil.getPage(page));
			items.setTotal(list.size());
		}else{
			items.setRows(new ArrayList<AJuanxx>());
			items.setTotal(0);
		}
		
		return items;
	}
	
	public String checkAJxx(String ah,AJuanxx ajxx){
		AJuanxx loc_ajxx=getRecordByAh(ah);
		int i=0;
		String result="{\"success\":0}";
		if(loc_ajxx!=null){
			i=updateRecord(ajxx);
		}
		else{
			i=insertRecord(ajxx);
		}
		if(i==1){
			result="{\"success\":1}";
		}
		return result;
	}
	
	/**
	 *将通达海的数据转换为案卷信息的javaBean
	 * @param map
	 * @return
	 */
	public Map<String,AJuanxx> getAjxxMap(Map<String,CaseInfo> map){
		Map<String,AJuanxx> ajxxMap = new HashMap<String,AJuanxx>();
	
		if(map!=null){
			for (Entry<String,CaseInfo> entry : map.entrySet()) {
				//将从通达海查询的CaseInfo转换为ajxx案卷信息
				AJuanxx ajxx = new AJuanxx();
				ajxx.setAh(entry.getValue().getAh());
				ajxx.setAhdm(entry.getValue().getAhdm());
				ajxx.setAjlx(entry.getValue().getAjlx());
				ajxx.setAjmc(entry.getValue().getAjmc());
				ajxx.setAjztms(entry.getValue().getAjztms());
				ajxx.setAyms(entry.getValue().getAyms());
				ajxx.setCbbm(entry.getValue().getCbbm1());
				ajxx.setCbbmms(entry.getValue().getCbbmms());
				ajxx.setCbr(entry.getValue().getCbr());
				ajxx.setCbrms(entry.getValue().getCbrms());
				ajxx.setDsr(entry.getValue().getDsr());
				ajxx.setDz(entry.getValue().getDz());
				ajxx.setFydm(entry.getValue().getFydm());
				ajxx.setFydmms(entry.getValue().getFydmms());
				ajxx.setJafs(entry.getValue().getJafs());
				ajxx.setJafsms(entry.getValue().getJafsms());
				if(!StringUtils.isEmpty(entry.getValue().getJarq())){
					ajxx.setJarq(entry.getValue().getJarq().replace("-", ""));
				}
				if(!StringUtils.isEmpty(entry.getValue().getLarq())){
					ajxx.setLarq(entry.getValue().getLarq().replace("-", ""));
				}
				ajxx.setSaay(Integer.parseInt(entry.getValue().getSaay()));
				ajxx.setSaayms(entry.getValue().getSaayms());
				ajxx.setSjy(entry.getValue().getSjy());
				ajxx.setSjyms(entry.getValue().getSjyms());
				ajxx.setSpz(entry.getValue().getSpz());
				ajxx.setSpzms(entry.getValue().getSpzms());
				ajxx.setSxjmrq(entry.getValue().getSxjmrq());
				ajxx.setZt(entry.getValue().getZt());
				
				ajxxMap.put(entry.getValue().getAh(), ajxx);
				
			}
		}
		return ajxxMap;
	}

}
