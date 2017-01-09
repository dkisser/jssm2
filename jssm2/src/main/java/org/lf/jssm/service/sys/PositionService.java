package org.lf.jssm.service.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.lf.jssm.db.dao.ChuPositionMapper;
import org.lf.jssm.db.pojo.ChuPosition;
import org.lf.utils.EasyuiComboBox;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("positionService")
public class PositionService {
	@Autowired
	private ChuPositionMapper positionDao;

	/**
	 * 功能：得到所有的ChuPosition
	 */
	public List<ChuPosition> findAllPosition() {
		return positionDao.selectAll();
	}

	public List<ChuPosition> findbyPosition(String pname) {
		return positionDao.findbyPosition(pname);
	}

	/**
	 * 功能：在chu_position中添加一条数据
	 * @param position
	 */
	public boolean addPosition(ChuPosition position) {
		// 参数检查
		if (position == null) {
			return false;
		}

		// 是否存在检查

		return (positionDao.insert(position) == 1);

	}

	/**
	 * 功能：在chu_position中删除一条数据
	 * @param position_id
	 */
	public boolean delPosition(Integer position_id) {
		// 参数检查
		if (position_id == null) {
			return false;
		}
		// 是否存在检查

		return (positionDao.deleteByPrimaryKey(position_id) == 1);
	}

	/**
	 * 通过ID查找Position
	 * @param id
	 * @return
	 */
	public ChuPosition findPositionById(Integer id) {
		return positionDao.selectbyIdInt(id);
	}

	public ChuPosition findPositionByName(String name) {
		return positionDao.selectbyName(name);
	}

	public boolean updatePosition(ChuPosition chuname) {
		return positionDao.updatePositionName(chuname.getId(), chuname.getName()) == 1;
	}

	/**
	 * 获取数据库中职位列表的所有信息
	 */
	public EasyuiDatagrid<ChuPosition> getPositionListInfo(int page, int rows, String pname) {
		EasyuiDatagrid<ChuPosition> items = new EasyuiDatagrid<>();
		List<ChuPosition> Records = null;
		if (pname.equals("全部")) {
			Records = findAllPosition();
		} else {
			Records = findbyPosition(pname);
		}
		if (Records != null && Records.size() > 0) {
			PageNavigator<ChuPosition> pagebean = new PageNavigator<>(Records, rows);
			items.setRows(pagebean.getPage(page));
			items.setTotal(Records.size());
		} else {
			items.setRows(new ArrayList<ChuPosition>());
			items.setTotal(0);
		}
		return items;
	}

	/**
	 * 对职位编号进行唯一性检查
	 */
	public String checkpid(Integer pid) {
		String result = null;
		if (pid != null) {
			if (findPositionById(pid) == null) {
				result = "{\"success\":1}";
			} else {
				result = "{\"success\":0}";
			}
		}
		return result;
	}

	/**
	 * 对职位名称进行唯一性检查
	 */
	public String checkpname(String pname, String oldpname, String isnew) {
		String result = null;
		if (pname != null) {
			result = "{\"success\":0}";
			if (isnew.equals("true")) {
				if (findPositionByName(pname) == null) {
					result = "{\"success\":1}";
				}
			} else {
				if (oldpname.equals(pname)) {
					result = "{\"success\":1}";
				} else {
					if (findPositionByName(pname) == null) {
						result = "{\"success\":1}";
					}
				}
			}
		}
		return result;
	}

	/**
	 * 功能: 将所有的position放到List<EasyuiComboBoxItem<String ,String>>
	 * 可以直接传到combobox中
	 * @return
	 */
	public List<EasyuiComboBoxItem<String, String>> getAllPosition() {
		List<ChuPosition> positionList = this.findAllPosition();
		TreeMap<String, String> map = new TreeMap<String, String>();
		for (ChuPosition position : positionList) {
			map.put(position.getName(), position.getName());
		}
		EasyuiComboBox<String, String> ecb = new EasyuiComboBox<String, String>(map);
		return ecb.getRecords();
	}
}
