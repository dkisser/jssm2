package org.lf.jssm.service.ajuan;

import java.util.Date;
import java.util.List;

import org.lf.jssm.db.dao.JAjjzMapper;
import org.lf.jssm.db.dao.JAjuanMapper;
import org.lf.jssm.db.dao.JJzMapper;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.VAJuanDMapper;
import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.db.pojo.CAQJB;
import org.lf.jssm.db.pojo.CSPCX;
import org.lf.jssm.db.pojo.JJZ;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.model.JsonResponseModel;
import org.lf.jssm.service.model.NewJzModel;
import org.lf.jssm.service.raw.EwmDecode;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * “即时”卷宗整理方式数据层
 * 
 * @author Administrator
 *
 */
@Service
public class JSJZWizardService {
	@Autowired
	private JzWizardService jzWizardService;
	@Autowired
	private JZNewService jzNewService;
	@Autowired
	private AjuanInfoService aJuanInfoService;
	@Autowired
	private JRawfileMapper jRawfileDao;
	@Autowired
	private VAJuanDMapper vAJuandDao;
	@Autowired
	private VJzMapper vjzDao;
	@Autowired
	private JJzMapper jJzDao;
	@Autowired
	private JAjjzMapper jajjzDao;
	@Autowired
	private JAjuanMapper jAjuanDao;

	public String findfmEwmstatus(String jz_ewm) {
		if (jRawfileDao.getRawFileByEwm(jz_ewm) == null) {
			return "您输入的二维码在本系统中无对应的扫描文件，请重新输入！";
		} else {
			if (vAJuandDao.isJzewmhaveUsed(jz_ewm).size() != 0
					|| jajjzDao.isAjewmHaveUsed(jz_ewm).size() != 0) {
				return "您输入的二维码对应的扫描文件为不是一个案卷封面，请重新输入";
			} else {
				return "1";
			}
		}
	}

	/**
	 * 根据封面二维码，查询V_JZ视图
	 */
	public JJZ findJzByEwm(String jzewm) {
		return jJzDao.selectJJZByEwm(jzewm);
	}

	public VJz getJZInfo(String jzewm) {
		return vjzDao.selectJzByEwm(jzewm);
	}

	/**
	 * 新建即时卷宗
	 * 
	 * @param txspjg1
	 * @param txspjg2
	 * @param njm
	 * @param jrm
	 * @return
	 * @throws Exception
	 */
	public JsonResponseModel createJsJz(String txspjg1, String txspjg2,
			NewJzModel njm, JsonResponseModel jrm) throws Exception {
		if (njm == null) {
			return jrm;
		}
		String id = njm.getAh();
		id += "\n";
		id += njm.getJzmc();
		id += "\n";
		id += njm.getJzlx();
		njm.setId(id);// 卷宗id
		njm.setStatus(0);// 卷宗状态
		njm.setTybz("0");// 停用标识
		// 处理所有的自动补全数据
		// 1.正卷
		if ((!njm.getJzlx().equals("正卷")) && (!njm.getJzlx().equals("副卷"))) {
			jrm.setMsgInfo("请选择正确的卷宗类型");
			return jrm;
		}
		// 2.机密
		List<CAQJB> aqjblist = jzWizardService.getAQJBByMC(njm.getJbdm());
		if (aqjblist.size() == 0) {
			jrm.setMsgInfo("请选择正确的安全级别");
			return jrm;
		} else {
			njm.setJbdm(aqjblist.get(0).getDm());
		}
		if (njm.getSpcx1() == null || njm.getSpcx1().equals("空")) {
			njm.setSpcx1(null);
			njm.setSpcx2(null);
		} else {
			if (njm.getSpcx2() == null || njm.getSpcx2().equals("空")) {
				njm.setSpcx2(null);
			}
		}
		// 3.审判程序1
		if (njm.getSpcx1() != null && !StringUtils.isEmpty(njm.getSpcx1())) {
			List<CSPCX> spcxlist = jzWizardService.getSPCXByMC(njm.getSpcx1());
			if (spcxlist.size() == 0) {
				jrm.setMsgInfo("请选择正确的审判程序1");
				return jrm;
			} else {
				njm.setSpcx1(spcxlist.get(0).getDm());
			}
			// 3.1审判结果1
			// j_jz.setSpjg1(j_jz.getSpjg1());
			if (!StringUtils.isEmpty(txspjg1)) {
				njm.setSpjg1(txspjg1);
			}
		} else {
			njm.setSpcx1(null);
		}
		// 4.审判程序2
		if (njm.getSpcx2() != null && !StringUtils.isEmpty(njm.getSpcx2())) {
			List<CSPCX> spcxlist = jzWizardService.getSPCXByMC(njm.getSpcx2());
			if (spcxlist.size() == 0) {
				jrm.setMsgInfo("请选择正确的审判程序2");
				return jrm;
			} else {
				njm.setSpcx2(spcxlist.get(0).getDm());
			}
			// 3.1审判结果1
			// j_jz.setSpjg2(j_jz.getSpjg2());
			if (!StringUtils.isEmpty(txspjg2)) {
				njm.setSpjg2(txspjg2);
			}
		} else {
			njm.setSpcx2(null);
		}
		return saveJsJzInfo(njm, jrm);
	}

	/**
	 * 保存即时卷宗信息
	 * 
	 * @param njm
	 * @param jrm
	 * @return
	 * @throws Exception
	 */
	private JsonResponseModel saveJsJzInfo(NewJzModel njm, JsonResponseModel jrm)
			throws Exception {
		if (njm == null || StringUtils.isEmpty(njm.getEwm())
				|| StringUtils.isEmpty(njm.getAjewm())
				|| njm.getCreator_id() == null) {
			return jrm;// 注 案卷二维码不能为空
		}
		// 判断目录二维码是否有效
		if (new EwmDecode().isValidEwm(njm.getAjewm(), EwmDecode.EWM_TYPE_ML)) {
			if (jRawfileDao.getRawFileByEwm(njm.getAjewm()) == null) {
				jrm.setMsgInfo("mlEwmNotExsit");// 目录二维码对应的图片文件不存在
				return jrm;
			}
			if (jAjuanDao.getCountByEwm(njm.getAjewm()) > 0) {
				jrm.setMsgInfo("mlEwmIsUsed");// 目录二维码已被使用
				return jrm;
			}
		} else {
			jrm.setMsgInfo("ewmFormatError");// 二维码格式错误
			return jrm;
		}
		// 验证卷号
		jrm = jzWizardService.checkJzInfo(njm, jrm);
		if (jrm == null || "jhError".equals(jrm.getMsgInfo())) {
			return jrm;// 卷号错误
		}
		jrm = jzWizardService.saveJzInfo(njm.getAjewm(), njm, jrm, new Date());
		if (!"saveJzSuccess".equals(jrm.getMsgInfo())) {
			jrm.setMsgInfo("saveJzError");// 保存卷宗信息失败
		} else {
			jrm.setSuccessMsgInfo("newJzSuccess");// 新建卷宗成功
		}
		return jrm;
	}

}
