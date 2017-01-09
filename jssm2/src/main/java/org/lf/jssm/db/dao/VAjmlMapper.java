package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.VAjml;
import org.springframework.stereotype.Repository;
@Repository("vajmlMapper")
public interface VAjmlMapper {
	 
	@Select("select mlid, mlmc,mldm from v_ajuan_ml where pcdm = #{pcdm}")
	List<VAjml> getAjmlByPCDM (String pcdm);
}

