package org.lf.jssm.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.lf.jssm.db.pojo.CMlpc;
import org.lf.jssm.service.model.ChuCMlpc;
import org.springframework.stereotype.Repository;

@Repository("mlpcDao")
public interface CMlpcMapper extends BaseMapper<CMlpc> {

	/**
	 * 根据Dm查询pcmc
	 * 
	 * @param dm
	 * @return
	 */
	@Select("select * from c_mlpc where dm = #{dm}")
	@ResultMap("org.lf.jssm.db.dao.CMlpcMapper.CMlpcMap")
	CMlpc selectMLPCByDm(@Param("dm") String dm);
	
	@Select("select * from c_mlpc where lx=#{lxId} and zhdm=#{zhdm}")
	@ResultMap("org.lf.jssm.db.dao.CMlpcMapper.CMlpcMap")
	CMlpc selectCMplc(@Param("lxId") String lxId, @Param("zhdm") String zhdm);
	
	@Select("select * from c_mlpc where dm=#{pcdm}")
	@ResultMap("org.lf.jssm.db.dao.CMlpcMapper.CMlpcMap")
	CMlpc selectCMplcByPcdm(String pcdm);
	
	@Update("update c_mlpc set pcmc=#{pcmc},remark=#{remark},pym=#{pym} where dm=#{pcdm}")
	int updateCMlpc(@Param("pcmc") String pcmc, @Param("remark") String remark, @Param("pym") String pym,@Param("pcdm") String pcdm);
	
	@Update("update c_mlpc set status=#{status} where dm=#{pcdm}")
	int updateCMlpcStatus(@Param("status") String status,@Param("pcdm") String pcdm);
	
	
	@Select("select mlpc.dm,fy.fymc fydm,mlpc.qynd,yjfl.mc,ajuan.mc lx,mlpc.pcmc,mlpc.tybz,mlpc.status,spzh.zh zhdm,mlpc.remark from c_mlpc mlpc,c_ajuan_lx ajuan,c_fy fy,c_spzh spzh,c_ajuan_yjfl yjfl where mlpc.fydm=fy.dm and mlpc.lx=ajuan.dm and mlpc.zhdm=spzh.dm and spzh.yjfl = yjfl.dm and spzh.yjfl=yjfl.dm")
	@ResultMap("org.lf.jssm.db.dao.CMlpcMapper.ChuCMlpcMap")
	List<ChuCMlpc> findAllCMlpc() ;
	
	@Select("select mlpc.dm,fy.fymc fydm,mlpc.qynd,yjfl.mc,ajuan.mc lx,mlpc.pcmc,mlpc.tybz,mlpc.status,spzh.zh zhdm,mlpc.remark from c_mlpc mlpc,c_ajuan_lx ajuan,c_fy fy,c_spzh spzh,c_ajuan_yjfl yjfl where mlpc.fydm=fy.dm and mlpc.lx=ajuan.dm and mlpc.zhdm=spzh.dm and spzh.yjfl = yjfl.dm and spzh.yjfl=yjfl.dm and mlpc.dm=#{dm}")
	@ResultMap("org.lf.jssm.db.dao.CMlpcMapper.ChuCMlpcMap")
	ChuCMlpc findMlpcByDm (@Param("dm")String dm);

	
	@Select("select * from c_mlpc where pcmc =#{pcmc}")
	@ResultMap("org.lf.jssm.db.dao.CMlpcMapper.ChuCMlpcMap")
	ChuCMlpc findPcmc (@Param("pcmc")String pcmc);
	
	@Update("update c_mlpc set tybz = #{tybz} where dm =#{dm}")
	void refirmSOS(	@Param("tybz")String tybz,@Param("dm")String dm);
	
	@Update("update c_mlpc set pcmc=#{pcmc},tybz=#{tybz},remark=#{remark} where dm=#{dm}")
	void editMlpc (@Param("pcmc")String pcmc,@Param("tybz")String tybz,@Param("remark")String remark,@Param("dm")String dm);
	
	@Insert("insert into c_mlpc(dm,fydm,qynd,zhdm,lx,pcmc,tybz,remark,status) values (#{dm},#{fydm},#{qynd},#{zhdm},#{lx},#{pcmc},#{tybz},#{remark},#{status})")
	void addMlpc (@Param("dm")String dm,@Param("fydm")Short fydm,@Param("qynd")String qynd,@Param("zhdm")String zhdm,@Param("lx")String lx,@Param("pcmc")String pcmc,@Param("tybz")String tybz,@Param("remark")String remark,@Param("status")String status) ;



}
