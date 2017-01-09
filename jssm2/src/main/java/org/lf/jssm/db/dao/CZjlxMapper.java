package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CZjlx;
import org.springframework.web.bind.annotation.ResponseBody;
@ResponseBody
public interface CZjlxMapper extends BaseMapper<CZjlx>{
	@Select("select dm,mc from c_zjlx")
	List<CZjlx> selectAllmc();
}