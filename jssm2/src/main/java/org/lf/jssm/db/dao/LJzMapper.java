package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.db.pojo.LJz;
import org.lf.jssm.service.model.LJzModel;
import org.lf.jssm.service.model.LJzParam;
import org.springframework.stereotype.Repository;

@Repository("ljzDao")
public interface LJzMapper {
	public Integer insert(LJz ljz);
	
	/**
	 * 查找表格数据
	 * */
	public List<LJzModel> findjzLogInfo(LJzParam ljz);
	
	/**
	 * 查出所有的操作者
	 * **/
	@Select("select * from chu_user")
	@ResultMap("org.lf.jssm.db.dao.LJzMapper.User")
	public List<ChuUser> findOperators();

}
