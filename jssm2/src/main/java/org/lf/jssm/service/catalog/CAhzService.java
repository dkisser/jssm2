package org.lf.jssm.service.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.lf.jssm.db.dao.CAhzMapper;
import org.lf.jssm.db.pojo.CAhz;
import org.lf.jssm.db.pojo.CAjuanYjfl;
import org.lf.jssm.db.pojo.CJgdm;
import org.lf.utils.EasyuiComboBox;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CAhzService {
	
	@Autowired
	private CAhzMapper CAhzdao;
	
	public List<CAhz> getAllRecords(){
		return CAhzdao.selectAll();
	}
	
	public CAhz getRecordById(String Ahzdm){
		return CAhzdao.selectById(Ahzdm);
	}
	
	//获取c_ahz表的所有记录
	public EasyuiDatagrid<CAhz> getAhzList(int rows,int page){
		EasyuiDatagrid<CAhz> items=new EasyuiDatagrid<>();
		List<CAhz> list=CAhzdao.selectAll();
		if(list!=null&&list.size()>0){
			PageNavigator<CAhz> pageUtils=new PageNavigator<>(list, rows);
			items.setRows(pageUtils.getPage(page));
			items.setTotal(list.size());
		}
		else{
			items.setRows(new ArrayList<CAhz>());
			items.setTotal(0);
		}
		return items;
	}
	
	//获取所有c_ahz表中不存在的案号字
	public List<EasyuiComboBoxItem<String, String>> findOtherAhz(){
		List<String> list=CAhzdao.findOtherAhz();
		TreeMap<String, String> map=new TreeMap<>();
		if(list!=null&&list.size()>0){
			for (String str : list) {
				map.put(str,str);
			}
		}
		else{
			map.put("暂无新案号字", "暂无新案号字");
		}
		EasyuiComboBox< String, String> items=new EasyuiComboBox<>(map);
		return items.getRecords();
	}
	
	//获取所有一级分类名称
	public List<EasyuiComboBoxItem<String, String>> findAllYJFL(){
		List<CAjuanYjfl> list=CAhzdao.findAllYJFL();
		TreeMap<String, String> map=new TreeMap<>();
		for (CAjuanYjfl cu : list) {
			map.put(cu.getDm(),cu.getMc());
		}
		EasyuiComboBox< String, String> items=new EasyuiComboBox<>(map);
		return items.getRecords();
	}
	
	//获取所有承办庭名称
	public List<EasyuiComboBoxItem<String, String>> findAllCBTS(){
		List<CJgdm> list=CAhzdao.findAllCBTS();
		TreeMap<String, String> map=new TreeMap<>();
		for (CJgdm cu : list) {
			map.put(cu.getDm(),cu.getMc());
		}
		EasyuiComboBox< String, String> items=new EasyuiComboBox<>(map);
		return items.getRecords();
	}
	
	public String saveAhzInfo(String isnew,CAhz cu,String cbt){
		int i=0;
		if(isnew.equals("true")){
			int dm_3=CAhzdao.findMaxAhzdm();
			cu.setDm(cu.getYjfl()+cu.getCbts()+String.valueOf(dm_3+1));
			String pym=StringUtils.toShortPinYin(cbt,cbt.length()).toLowerCase();
			cu.setPym(pym);
			String str=StringUtils.replaceWithBlank(cu.getRemark());
			cu.setRemark(str);
			i=CAhzdao.insertRecord(cu);
		}
		else{
			String pym=StringUtils.toShortPinYin(cbt,cbt.length()).toLowerCase();
			cu.setPym(pym);
			String str=StringUtils.replaceWithBlank(cu.getRemark());
			cu.setRemark(str);
			i=CAhzdao.updateRecord(cu);
		}
		if(i==1){
			return "success";
		}
		return "failed";
	}
	
//	public static void main(String[] args) {
//		ApplicationContext ac=new ClassPathXmlApplicationContext("spring-mybatis.xml");
//		AHZService AhzService=(AHZService) ac.getBean("AhzService");
//		List<CAhz> list=AhzService.getAllRecords();
//		for (CAhz e : list) {
//			System.out.println(e.getAhz());
//		}
//		System.out.println(list.size());
//	}

}
