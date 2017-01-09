package org.lf.jssm.service.catalog;

import java.util.List;
import java.util.TreeMap;

import org.lf.jssm.db.dao.CAjuanYjflMapper;
import org.lf.jssm.db.pojo.CAjuanYjfl;
import org.lf.utils.EasyuiComboBox;
import org.lf.utils.EasyuiComboBoxItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** * @author  wenchen 
 * @date 创建时间：2016年8月21日 下午4:45:32 
 * @version 1.0 
 * @parameter 
 * c_ajuan_yjfl对应的业务层
 * */
@Service
public class CAJuanYjflService {
	
	@Autowired
	private CAjuanYjflMapper cajuanYjflMapper;
	
	
	/**
	 * 功能: 将你得到的c_ajaun_yjfl中的dm和mc获得
	 * 然后存入List<EasyuiComboBoxItem<String, String>>
	 * 然后前台获取
	 * @return List<EasyuiComboBoxItem<String, String>>;
	 */
	public List<EasyuiComboBoxItem<String, String>> getAllCAjuanYjfl () {
		List<CAjuanYjfl> yjflList = cajuanYjflMapper.getAllAjuanYifl();
		TreeMap<String,String> yjflTreeMap = new TreeMap<String,String>();
		for (CAjuanYjfl yjfl: yjflList) {
			yjflTreeMap.put(yjfl.getDm(), yjfl.getMc());
		}
		EasyuiComboBox<String,String> combobox = new EasyuiComboBox<>(yjflTreeMap);
		return combobox.getRecords();
	}
	
	
	
	
	/**
	 * 查询所有一级分类
	 */
	public List<CAjuanYjfl> findAllYjflList() {
		return cajuanYjflMapper.selectAllYjfl();
	}

	/**
	 * insert案卷一级分类的字段
	 */
	public boolean addYjflList(String dm, String mc, String pym, String tybz) {
		CAjuanYjfl chutjfl = new CAjuanYjfl();
		chutjfl.setDm(dm);
		chutjfl.setMc(mc);
		chutjfl.setPym(pym);
		chutjfl.setTybz(tybz);
		return cajuanYjflMapper.insert(chutjfl) > 0 ? true : false;

	}

	/**
	 * 查询dm对应的相应案卷一级分类字段
	 */
	public CAjuanYjfl findYjflByDm(String dm) {
		return cajuanYjflMapper.selectYjflByDm(dm);
	}

	/**
	 * 通过DM更新案卷一级分类的字段
	 */
	public boolean updateYjflList(String mc, String pym, String tybz, String dm) {
		return cajuanYjflMapper.upadteYjflListByDm(mc, pym, tybz, dm) > 0 ? true : false;
	}

	/**
	 * 查询mc对应的案卷一级分类字段
	 */
	public CAjuanYjfl findYjflByMc(String mc) {
		return cajuanYjflMapper.selectYjflByMc(mc);
	}

	/**
	 * 删除dm对应案卷一级分类字段
	 */
	public boolean delYjflByDm(String dm) {
		return cajuanYjflMapper.delYjflListByDm(dm) > 0 ? true : false;
	}
}
