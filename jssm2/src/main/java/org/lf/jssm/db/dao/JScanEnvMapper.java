package org.lf.jssm.db.dao;

import java.util.List;

import org.lf.jssm.db.pojo.JScanEnv;
/**
 * 扫描配置参数dao层
 * @author sunwill
 *
 */
public interface JScanEnvMapper extends BaseMapper<JScanEnv> {
    /**
     * 查询扫描配置参数
     * @param jse
     * @return
     */
	List<JScanEnv> selectByJScanEnv(JScanEnv jse);
	/**
	 * 更新扫描参数配置表
	 * @param j
	 */
	void update(JScanEnv j);
}