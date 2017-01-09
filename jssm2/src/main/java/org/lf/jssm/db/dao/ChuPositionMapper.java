package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.ChuPosition;
import org.springframework.stereotype.Repository;


@Repository("positionDao")
public interface ChuPositionMapper extends BaseMapper<ChuPosition> {
	
	@Select("select * from chu_position order by id asc")
	@ResultMap("org.lf.jssm.db.dao.ChuPositionMapper.BaseResultMap")
	List<ChuPosition> selectAll();
	
	@Select("select * from chu_position where name=#{name}")
	@ResultMap("org.lf.jssm.db.dao.ChuPositionMapper.BaseResultMap")
	ChuPosition selectbyName(String name);
	
	@Select("select * from chu_position where id = #{id}")
	@ResultMap("org.lf.jssm.db.dao.ChuPositionMapper.BaseResultMap")
	ChuPosition selectbyId(String id);
	
	@Select("select * from chu_position where id = #{id}")
	@ResultMap("org.lf.jssm.db.dao.ChuPositionMapper.BaseResultMap")
	ChuPosition selectbyIdInt(Integer id);
	
//更新操作
	@Update("update chu_position set name =#{name} where id = #{id}")
	int updatePositionName(@Param("id") Integer id,@Param("name")String name);
	
	//根据pname进行模糊查询
	public List<ChuPosition> findbyPosition(String pname);

}
