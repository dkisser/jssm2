package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.CYhzw;
import org.lf.jssm.service.model.UiautoCompleteBox;
import org.springframework.stereotype.Repository;

@Repository("yhzwDao")
public interface CYhzwMapper extends BaseMapper<CYhzw> {
	/*
	 * 获取表格数据
	 */
	@Select("select * from c_yhzw order by dm")
	@ResultMap("org.lf.jssm.db.dao.CYhzwMapper.ChuYhzwMap")
	List<CYhzw> selectAllYHZWInfo();
	
	/*
	 * 
	 * 表单验证
	 */
	@Select("select count(*) from c_yhzw where dm=#{checkid}")
	int CheckId(@Param("checkid") String checkid);
	
	@Select("select count(*) from c_yhzw where	mc=#{checkname} ")
	int CheckName(@Param("checkname") String checkname);
	
	/*
	 * 增加
	 * 
	 */
	@Insert("insert into c_yhzw(dm,mc,pym,tybz,remark) values(#{dm},#{mc},#{pym},#{tybz},#{remark})")
	int insertYHZWInfo(@Param("dm")String dm,@Param("mc")String mc,@Param("pym")String pym,@Param("tybz")String tybz,@Param("remark")String remark);
	
	/*
	 * 修改
	 */
	@Update("update c_yhzw set mc=#{mc},pym=#{pym},tybz=#{tybz},remark=#{remark} where dm=#{dm}")
	int updateYHZWInfo(@Param("dm")String dm,@Param("mc")String mc,@Param("pym")String pym,@Param("tybz")String tybz,@Param("remark")String remark);
	
	/*
	 * 获取自动补全的数据
	 */
	@Select("select dm id,mc text,pym from c_yhzw where tybz = 0")
	@ResultMap("org.lf.jssm.db.dao.CYhzwMapper.UiautoCompleteBoxMap")
	List<UiautoCompleteBox> selectBrowseInfo();
	
	//根据职位名称获取职位代码
	@Select("select dm from c_yhzw where mc=#{mc}")
	String getDmByMc(String mc);
	
}
