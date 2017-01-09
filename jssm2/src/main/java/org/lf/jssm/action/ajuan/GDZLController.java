package org.lf.jssm.action.ajuan;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.CAQJB;
import org.lf.jssm.db.pojo.CSPCX;
import org.lf.jssm.db.pojo.JJZ;
import org.lf.jssm.db.pojo.LJz;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.ajuan.AjuanInfoService;
import org.lf.jssm.service.ajuan.GDZLService;
import org.lf.jssm.service.ajuan.JZNewService;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 卷宗归档与整理
 * 
 */
@Controller
@RequestMapping("/ajuan/")
public class GDZLController implements BaseController {
	private Logger logger = Logger.getLogger(GDZLController.class);

	@Autowired
	private GDZLService gdzlService;

	@Autowired
	private AjuanInfoService aJuanInfoService;

	@Autowired
	private JZNewService jsNewService;

	@RequestMapping("jzgdWizardUI.do")
	public String gdzlWizard() {
		return "ajuan/gdzlWizard";
	}

	/**
	 * “取消归档”,"归档操作" 在l_jz 增加一条记录 JZEWM为卷宗二维码， OPERATION为U（取消归档），OPERATION为G（归档）
	 * OPER_DATE为系统时间， OPERATOR_ID为当前用户ID
	 * 
	 * @param ljz
	 * @return
	 */
	@RequestMapping("gdzl_gdMethod.do")
	@ResponseBody
	public String qxgdMethod(LJz ljz, Integer status) {
		ljz.setId(StringUtils.getUUID());
		ljz.setOper_date(new Date());
		if (gdzlService.insertLjzRecord(ljz, status) == 1) {
			return "success";
		}
		logger.error("取消归档/归档操作失败！");
		;
		return "error";
	}

	/**
	 * “删除卷宗”操作 JZEWM为卷宗二维码， OPERATION为D（删除） OPER_DATE为系统时间 OPERATOR_ID为当前用户ID
	 * 删除相关表:J_JZ、J_AJUAN和J_AJJZ
	 * 
	 * @return
	 */
	@RequestMapping("gdzl_deleteJz.do")
	@ResponseBody
	public String deleteJz(LJz ljz) {
		if (gdzlService.delectJz(ljz) == 1) {
			return "success";
		}
		logger.error("删除卷宗操作失败！");
		;
		return "error";
	}

	/**
	 * 卷宗归档导航
	 * 
	 * @return
	 */
	@RequestMapping("gdWizardUI.do")
	public String gdWizard() {
		return "ajuan/gdWizard";
	}

	/**
	 * . 判断归档前是否有该卷宗信息
	 */
	@RequestMapping("findIsHaveJzInfo.do")
	@ResponseBody
	public String findIsHaveJzInfo(String jzewm) {
		return gdzlService.findVajuanDByJzEwm(jzewm);
	}

	/**
	 * 返回未归档的datagrid JSON数据
	 */
	@RequestMapping("gdWizardList.do")
	@ResponseBody
	public EasyuiDatagrid<VJz> getGdWizardList(int page, int rows) {
		return gdzlService.getJzDatagridList(page, rows);
	}

	/**
	 * 跳转到卷宗归档页面 gdInfo.jsp
	 */
	@RequestMapping("gdInfo.do")
	public String gdInfo(String jzewm, String pageType, Model m, HttpSession session) {
		m.addAttribute("jjzId", gdzlService.findJJzByEwm(jzewm).getId().replace("\n", ""));
		m.addAttribute("jzewm", jzewm);
		session.setAttribute("jjzId", gdzlService.findJJzByEwm(jzewm).getId());
		session.setAttribute("ah", gdzlService.findJJzByEwm(jzewm).getAh());
		// 当年的归档号数
		if (gdzlService.getCurrGdhs(gdzlService.findJJzByEwm(jzewm).getAh()) == null) {
			m.addAttribute("gdhs", 1);
		} else {
			m.addAttribute("gdhs", gdzlService.getCurrGdhs(gdzlService.findJJzByEwm(jzewm).getAh()));
		}

		// 当前卷宗卷号
		/*
		 * if(gdzlService.getMaxJhById(gdzlService.findJJzByEwm(jzewm).getId())==
		 * null){ m.addAttribute("gdjh", 1); }else{ m.addAttribute("gdjh",
		 * gdzlService.getMaxJhById(gdzlService.findJJzByEwm(jzewm).getId())); }
		 */

		if (pageType.equals("noDlg")) {
			return "ajuan/gdInfoNodlg";
		}
		return "ajuan/gdInfo";
	}

	@RequestMapping("editJz_fm.do")
	public String editJz_fm(String jzewm, Model m) {
		m.addAttribute("jzewm", jzewm);
		VJz jz = aJuanInfoService.getJZInfo(jzewm);
		m.addAttribute("jzfm", jz);
		return "ajuan/jz_fmEdit";
	}

	@RequestMapping("jzfmEdit.do")
	@ResponseBody
	public String jzfmEdit(String jzewm, JJZ j_jz, String txspjg1, String txspjg2, HttpServletRequest request) {
		String id = j_jz.getAh();
		id += "\n";
		id += j_jz.getJzmc();
		id += "\n";
		id += j_jz.getJzlx();
		j_jz.setId(id);
		j_jz.setStatus(0);
		j_jz.setTybz("0");

		// 处理所有的自动补全数据
		// 1.正卷
		if ((!j_jz.getJzlx().equals("正卷")) && (!j_jz.getJzlx().equals("副卷")))
			return "请选择正确的卷宗类型";
		// 2.机密
		List<CAQJB> aqjblist = jsNewService.getAQJBByMC(j_jz.getJbdm());
		if (aqjblist.size() == 0) {
			return "请选择正确的安全级别";
		} else {
			j_jz.setJbdm(aqjblist.get(0).getDm());
		}
		if (j_jz.getSpcx1() == null || j_jz.getSpcx1().equals("空")) {
			j_jz.setSpcx1(null);
			j_jz.setSpcx2(null);
		} else {
			if (j_jz.getSpcx2() == null || j_jz.getSpcx2().equals("空")) {
				j_jz.setSpcx2(null);
			}
		}
		// 3.审判程序1
		if (j_jz.getSpcx1() != null && !StringUtils.isEmpty(j_jz.getSpcx1())) {
			List<CSPCX> spcxlist = jsNewService.getSPCXByMC(j_jz.getSpcx1());
			if (spcxlist.size() == 0) {
				return "请选择正确的审判程序1";
			} else {
				j_jz.setSpcx1(spcxlist.get(0).getDm());
			}
			// 3.1审判结果1
			if (!StringUtils.isEmpty(txspjg1)) {
				j_jz.setSpjg1(txspjg1);
			}
		} else {
			j_jz.setSpcx1(null);
		}
		// 4.审判程序2
		if (j_jz.getSpcx2() != null && !StringUtils.isEmpty(j_jz.getSpcx2())) {
			List<CSPCX> spcxlist = jsNewService.getSPCXByMC(j_jz.getSpcx2());
			if (spcxlist.size() == 0) {
				return "请选择正确的审判程序2";
			} else {
				j_jz.setSpcx2(spcxlist.get(0).getDm());
			}
			// 3.1审判结果1
			if (!StringUtils.isEmpty(txspjg2)) {
				j_jz.setSpjg2(txspjg2);
			}
		} else {
			j_jz.setSpcx2(null);
		}
		j_jz.setEwm(jzewm);
		if (jsNewService.updataJzfm(j_jz) == 1)
			return "success";
		else
			return "false";
	}

	/**
	 * 卷宗分类的JSON数据，即一级分类
	 */
	@RequestMapping("getGdJzfl.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getGdJzfl(String yjflFlag) {
		return gdzlService.getGdJzfl(yjflFlag);
	}

	/**
	 * 表单提交归档卷宗信息
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("gdInfoFile.do")
	@ResponseBody
	public Integer gdInfoFile(String yjfl, String gdrq, Integer gdhs, String jbdm, String qxdm, Integer jh, String remrak, String ewm)
			throws ParseException {
		return gdzlService.gdInfoFile(yjfl, gdrq, gdhs, jbdm, qxdm, jh, remrak, ewm);
	}

	/**
	 * 获取输入归档日期下，当年最大的归档号数加1
	 */
	/*
	 * @RequestMapping("getMaxGdhsByGdrq.do")
	 * 
	 * @ResponseBody public Integer getMaxGdhsByGdrq(String gdrq){ return
	 * gdzlService.getMaxGdhs(gdrq); }
	 */

	/**
	 * 归档号数做唯一性检查
	 */
	/*
	 * @RequestMapping("checkGdhsByGdrq.do")
	 * 
	 * @ResponseBody public String checkGdhsByGdrq(String gdrq,Integer
	 * gdhs,HttpSession session,String ah4ks){ String ah = (String)
	 * session.getAttribute("ah"); return gdzlService.checkGdhsByGdrq(gdrq,
	 * gdhs,ah,ah4ks); }
	 */

	/**
	 * 卷号做唯一性检查
	 */
	/*
	 * @RequestMapping("checkJhbyId.do")
	 * 
	 * @ResponseBody public String checkJhbyId(Integer jh,HttpSession
	 * session,String jzewm4ks){ String id = (String)
	 * session.getAttribute("jjzId"); return gdzlService.checkJhById(id,
	 * jh,jzewm4ks); }
	 */
}
