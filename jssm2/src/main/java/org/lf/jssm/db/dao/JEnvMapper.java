package org.lf.jssm.db.dao;

import java.util.List;

import org.lf.jssm.db.pojo.JEnv;
import org.springframework.stereotype.Repository;
/**
 * 环境配置dao层
 * @author sunwill
 *
 */
@Repository("jEnvMapperDao")
public interface JEnvMapper {
	
    JEnv selectEnv(JEnv env);
    
    public List<JEnv> findAllRecords();
    
    public List<JEnv> selectAllEnv();
    
    public JEnv findById(Short id);
    
    public int updateEnvValue(JEnv env);
}