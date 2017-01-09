package org.lf.jssm.service.ajuan;

import java.util.ArrayList;
import java.util.List;

import org.lf.jssm.db.dao.JAjjzMapper;
import org.lf.jssm.db.dao.JAjuanMapper;
import org.lf.jssm.db.dao.JJzMapper;
import org.lf.jssm.db.dao.VAJuanDMapper;
import org.lf.jssm.db.dao.VAjmlMapper;
import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.db.pojo.JAjuan;
import org.lf.jssm.db.pojo.JJZ;
import org.lf.jssm.db.pojo.VAJuanD;
import org.lf.jssm.db.pojo.VAjml;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.model.AJuanCode;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("aJuanInfoService")
public class AjuanInfoService {
	@Autowired
	private JAjjzMapper jajjzDao;
	@Autowired
	private JAjuanMapper jajuanDao;
	@Autowired
	private VAJuanDMapper vAJuandDao;
	@Autowired
	private VAjmlMapper vAjmlDao;
	@Autowired
	private JJzMapper jJzDao;
	@Autowired
	private VJzMapper jzMapper;

	public VJz getJZInfo(String jzewm) {
		return jzMapper.selectJzByEwm(jzewm);
	}

	/**
	 * 通过jzewm获取AJuanCode
	 * 
	 * @param jzewm
	 * @return
	 */
	public AJuanCode getpcdmbyJzewm(String jzewm) {
		AJuanCode ajcode = jajjzDao.selectpcdmbyJzewm(jzewm);
		return ajcode;
	}

	/**
	 * 获取V_ajuan_D
	 * 
	 * @param pcdm
	 *            jzewm
	 * @return
	 * 
	 *         public List<VAJuanD> getVAjuanDbyjzewm(String pcdm, String jzewm)
	 *         { // List<VAJuanD> lvad = vAJuandDao.selectVajuanDByjzewm(jzewm);
	 *         String jz_pcdm = vAJuandDao.getPcdmByJzewm(jzewm); List<VAjml>
	 *         mlList = vAjmlDao.getAjmlByPCDM(jz_pcdm); List<VAJuanD> lvad =
	 *         null; if (mlList != null && mlList.size() > 0) { lvad = new
	 *         ArrayList<VAJuanD>(); for (VAjml val : mlList) { VAJuanD vd = new
	 *         VAJuanD(); vd.setJz_ewm(jzewm); vd.setMldm(val.getMldm());
	 *         vd.setMlid(Integer.parseInt(val.getMlid()));
	 *         vd.setMlmc(val.getMlmc()); List<String> picEwms =
	 *         vAJuandDao.getPicEwmByCondition(vd); if (picEwms == null ||
	 *         picEwms.size() == 0) { lvad.add(vd); } else { for (String picEwm
	 *         : picEwms) { vd.setPic_ewm(picEwm); lvad.add(vd); } } } } if
	 *         (lvad == null || lvad.size() == 0) { lvad =
	 *         vAJuandDao.selectVajuanDBypcdm(pcdm); } return lvad; }
	 */

	/**
	 * 即时整理初始化目录datagrid
	 * 
	 * @param pcdm
	 * @param jzewm
	 * @return
	 */
	public List<VAJuanD> getVajList4JS(String pcdm, String jzewm) {
		List<VAJuanD> vdList = new ArrayList<VAJuanD>();
		if (StringUtils.isEmpty(pcdm) || StringUtils.isEmpty(jzewm)) {
			return vdList;
		}
		JJZ jjz = jJzDao.selectJJZByEwm(jzewm);
		if (jjz == null || jjz.getSyym() == null) {
			return vdList;
		}
		// String jz_pcdm = vAJuandDao.getPcdmByJzewm(jzewm);
		JAjuan jaj = jajuanDao.getJajuanByJzewm(jzewm);
		if (jaj == null || StringUtils.isEmpty(jaj.getPcdm())) {
			return vdList;
		}
		List<VAjml> mlList = vAjmlDao.getAjmlByPCDM(jaj.getPcdm());
		Integer prePicCount = 0;
		if (mlList != null && mlList.size() > 0) {
			for (VAjml val : mlList) {
				VAJuanD vd = new VAJuanD();
				vd.setJz_ewm(jzewm);
				vd.setMldm(val.getMldm());
				vd.setMlid(Integer.parseInt(val.getMlid()));
				vd.setMlmc(val.getMlmc());
				Integer picCounts = vAJuandDao.getPicCounts(vd);
				if (picCounts != null && picCounts > 0) {
					// vd.setPage((startNbbh + jjz.getSyym() - 1) + "");
					if(picCounts > 1){
						vd.setPage((prePicCount + jjz.getSyym()) + "-"
								+ (prePicCount + jjz.getSyym() + picCounts - 1));// 设置页码(XX-XX显示)
					}else{
						vd.setPage(prePicCount + jjz.getSyym() + "");
					}
					prePicCount += picCounts;
				}
				vdList.add(vd);
			}
		}
		return vdList;
	}

	/**
	 * 更新remark
	 * 
	 * @param ewm
	 * @param remark
	 * @return
	 */
	public boolean updateJAJuan(String ewm, String remark) {
		if (StringUtils.isEmpty(ewm) || StringUtils.isEmpty(remark)) {
			return false;
		}
		JAjuan jaj = new JAjuan();
		jaj.setEwm(ewm);
		jaj.setRemark(remark);
		if (jajuanDao.update(jaj) == 1) {
			return true;
		}
		return false;
	}

	public JAjuan getJAjuanByEwm(String ewm) {
		JAjuan ja = jajuanDao.getJajuanByEwm(ewm).get(0);
		return ja;
	}

	public boolean getVAjuanDbyjz_ewm(String jzewm) {
		List<VAJuanD> list = vAJuandDao.getVAjuanDListByJzEwm(jzewm);
		if (list == null || list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 历史整理初始化目录datagrid
	 * 
	 * @param pcdm
	 * @param jzewm
	 * @return
	 */
	public List<VAJuanD> getVajList4LS(String pcdm, String jzewm) {
		List<VAJuanD> vdList = new ArrayList<VAJuanD>();
		if (StringUtils.isEmpty(pcdm) || StringUtils.isEmpty(jzewm)) {
			return vdList;
		}
		JJZ jjz = jJzDao.selectJJZByEwm(jzewm);
		if (jjz == null || jjz.getSyym() == null) {
			return vdList;
		}
		// String jz_pcdm = vAJuandDao.getPcdmByJzewm(jzewm);
		JAjuan jaj = jajuanDao.getJajuanByJzewm(jzewm);
		if (jaj == null || StringUtils.isEmpty(jaj.getPcdm())) {
			return vdList;
		}
		List<VAjml> mlList = vAjmlDao.getAjmlByPCDM(jaj.getPcdm());
		if (mlList != null && mlList.size() > 0) {
			for (VAjml val : mlList) {
				VAJuanD vd = new VAJuanD();
				vd.setJz_ewm(jzewm);
				vd.setMldm(val.getMldm());
				vd.setMlid(Integer.parseInt(val.getMlid()));
				vd.setMlmc(val.getMlmc());
				Integer startNbbh = vAJuandDao.getStartNbbhByCondition(vd);
				if (startNbbh != null) {
					vd.setPage((startNbbh + jjz.getSyym() - 1) + "");// 设置页码
				}
				vdList.add(vd);
			}
		}
		return vdList;
	}

}
