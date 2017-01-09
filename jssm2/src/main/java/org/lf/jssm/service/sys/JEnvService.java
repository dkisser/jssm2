package org.lf.jssm.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.lf.jssm.db.dao.JEnvMapper;
import org.lf.jssm.db.pojo.JEnv;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 环境配置参数服务层
 * 
 * @author sunwill
 * 
 */
@Service("jEnvService")
public class JEnvService {
	@Autowired
	private JEnvMapper jenvDao;

	/**
	 * 查询环境设置参数
	 * 
	 * @param env
	 * @return
	 */
	public JEnv selectEnv(JEnv env) {
		return jenvDao.selectEnv(env);
	}

	/**
	 * 查询出所有的记录
	 */
	public List<JEnv> getAllRecords(){
		return jenvDao.findAllRecords();
	}
	/**
	 * 根据id查询一条记录
	 */
	public JEnv getRecordById(String id){
		Short Id=Short.decode(id);
		return jenvDao.findById(Id);
	}
	
	/**
	 * 获取数据表中所有的环境参数记录，返回一个EasyuiDatagrid数据
	 */
	public EasyuiDatagrid<JEnv> getEnvList(int page,int rows){
		EasyuiDatagrid<JEnv> items=new EasyuiDatagrid<>();
		List<JEnv> list=getAllRecords();
		if(list!=null&&list.size()>0){
			PageNavigator<JEnv> pageBean=new PageNavigator<>(list, rows);
			items.setRows(pageBean.getPage(page));
			items.setTotal(list.size());
		}
		else{
			items.setRows(new ArrayList<JEnv>());
			items.setTotal(0);
		}
		return items;
	}
	
	/**
	 * 修改指定id的环境参数记录的参数值
	 */
	public int updateEnvValue(String envValue,String id){
		JEnv env=new JEnv();
		env.setId(Short.decode(id));
		env.setEnvValue(StringUtils.replaceWithBlank(envValue));
		return jenvDao.updateEnvValue(env);
	}
	/**
	 * 获得ftp目录路径
	 * 
	 * @return
	 */
	public String getFtpDir() {
		JEnv env = new JEnv();
		env.setEnvKey("FTP_DIR");
		env = selectEnv(env);
		if (env == null || StringUtils.isEmpty(env.getEnvValue())) {
			throw new IllegalArgumentException("读取ftp目录出错");
		} else {
			return env.getEnvValue();
		}
	}
}
