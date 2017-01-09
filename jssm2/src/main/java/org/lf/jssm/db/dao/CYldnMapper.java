package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CYldn;
import org.springframework.web.bind.annotation.ResponseBody;
@ResponseBody
public interface CYldnMapper extends BaseMapper<CYldn>{
	@Select("select dm,mc from c_yldn")
	List<CYldn> selectAllmc();
}