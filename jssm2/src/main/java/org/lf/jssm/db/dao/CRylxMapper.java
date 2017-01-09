package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.lf.jssm.db.pojo.CRylx;
import org.springframework.web.bind.annotation.ResponseBody;
@ResponseBody
public interface CRylxMapper extends BaseMapper<CRylx>{
	@Select("select dm,mc from c_rylx")
	List<CRylx> selectCRylxMc();
}