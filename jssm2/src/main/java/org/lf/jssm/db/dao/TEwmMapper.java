package org.lf.jssm.db.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.lf.jssm.db.pojo.TEwm;


public interface TEwmMapper {

    int insert(TEwm record);
    /**
     * 根据时间查询当天二维码记录
     * @param currDate
     * @return
     */
	TEwm selectByDate(@Param("currDate")Date currDate);
	/**
	 * 根据主键更新该记录
	 * @param tem
	 */
	void updateById(TEwm tem);

}