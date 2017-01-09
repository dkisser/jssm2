package org.lf.jssm.action.ajuan;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.db.pojo.JAjuanD;
import org.lf.jssm.db.pojo.VAJuanD;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.db.pojo.VMlpc;
import org.lf.jssm.service.ajuan.AjuanInfoService;
import org.lf.jssm.service.ajuan.AjzlService;
import org.lf.jssm.service.ajuan.JSJZWizardService;
import org.lf.jssm.service.ajuan.JZNewService;
import org.lf.jssm.service.model.AJuanCode;
import org.lf.jssm.service.model.JZType;
import org.lf.jssm.service.model.JsonResponseModel;
import org.lf.jssm.service.model.NewJzModel;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 采用“即时”卷宗整理方式来整理未归档的卷宗
 */
@Controller
@RequestMapping("/ajuan/")
public class JSJZWizardController implements BaseController {
	@Autowired
	private JZNewService jzNewService;
	@Autowired
	private JSJZWizardService jsjzWizardService;
	@Autowired
	private AjuanInfoService aJuanInfoService;
	@Autowired
	private AjzlService ajzlService;

	/**
	 * 根据封面二维码(jzewm),查找,进行不同的操作
	 */
	@RequestMapping("jsfindJzEwm.do")
	@ResponseBody
	public JSONObject findJzEwm(String jzewm, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		if (jsjzWizardService.findJzByEwm(jzewm) == null) {
			result.put("result", "无结果");
		} else if (jsjzWizardService.findJzByEwm(jzewm).getStatus() == 0) {
			if (!jsjzWizardService.findJzByEwm(jzewm).getZlfs()
					.equals(JZType.即时.getType())) {
				result.put("result", "非即时方式");
				result.put("JzInfo", jsjzWizardService.findJzByEwm(jzewm));
				return result;
			}
			result.put("result", "未归档");
			result.put("JzInfo", jsjzWizardService.findJzByEwm(jzewm)); // 将卷宗信息也要传递给前台
		} else if (jsjzWizardService.findJzByEwm(jzewm).getStatus() == 1) {
			if (!jsjzWizardService.findJzByEwm(jzewm).getZlfs()
					.equals(JZType.即时.getType())) {
				result.put("result", "非即时方式");
				result.put("JzInfo", jsjzWizardService.findJzByEwm(jzewm));
				return result;
			}
			result.put("result", "已归档");
			result.put("JzInfo", jsjzWizardService.findJzByEwm(jzewm)); // 将卷宗信息也要传递给前台
		}
		return result;
	}
	/**
	 * 
		即时整理初始化目录datagrid
	 * @param pcdm
	 * @param jzewm
	 * @return
	 */
	@RequestMapping("VAJuanDList4JS.do")
	@ResponseBody
	public List<VAJuanD> VAJuanDList4JS(String pcdm, String jzewm) {
		return aJuanInfoService.getVajList4JS(pcdm, jzewm);
	}

	@RequestMapping("jsfindfmEwmstatus.do")
	@ResponseBody
	public String findfmEwmstatus(String jz_ewm) throws InterruptedException {
		return jsjzWizardService.findfmEwmstatus(jz_ewm);
	}

	/*-
	@RequestMapping("jsjzInfo.do")
	public String getJZInfo(String action, String jzewm, Model m,
			String pageType) {
		if (action.equals(BROWSE)) {
			VJz jz = aJuanInfoService.getJZInfo(jzewm);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String larq = null, jarq = null, gdrq = null;
			AJuanCode ac = aJuanInfoService.getpcdmbyJzewm(jzewm);
			String remark = "";
			if (ac != null
					&& aJuanInfoService.getJAjuanByEwm(ac.getAj_ewm()) != null) {
				remark = aJuanInfoService.getJAjuanByEwm(ac.getAj_ewm())
						.getRemark();
			}
			VMlpc mlpc = null;
			if (ac != null) {
				mlpc = jzNewService.getMlpcByPCDM(ac.getPcdm());
			}
			if (jz != null && jz.getLarq() != null)
				larq = formatter.format(jz.getLarq());
			if (jz != null && jz.getJarq() != null)
				jarq = formatter.format(jz.getJarq());
			if (jz != null && jz.getGdrq() != null)
				gdrq = formatter.format(jz.getGdrq());
			m.addAttribute("AJuanCode", ac == null ? null : ac);
			m.addAttribute("pcmc", mlpc == null ? null : mlpc.getPcmc());
			m.addAttribute("remark", remark);
			m.addAttribute("larq", larq);
			m.addAttribute("jarq", jarq);
			m.addAttribute("gdrq", gdrq);
			m.addAttribute("jz", jz);
			return "ajuan/jsjzInfo";
		} else {
			VJz jz = aJuanInfoService.getJZInfo(jzewm);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String larq = null, jarq = null, gdrq = null;
			AJuanCode ac = aJuanInfoService.getpcdmbyJzewm(jzewm);
			String remark = "";
			if (aJuanInfoService.getJAjuanByEwm(ac.getAj_ewm()) != null) {
				remark = aJuanInfoService.getJAjuanByEwm(ac.getAj_ewm())
						.getRemark();
			}
			VMlpc mlpc = jzNewService.getMlpcByPCDM(ac.getPcdm());
			if (jz.getLarq() != null)
				larq = formatter.format(jz.getLarq());
			if (jz.getJarq() != null)
				jarq = formatter.format(jz.getJarq());
			if (jz.getGdrq() != null)
				gdrq = formatter.format(jz.getGdrq());
			m.addAttribute("AJuanCode", ac);
			m.addAttribute("pcmc", mlpc.getPcmc());
			m.addAttribute("remark", remark);
			m.addAttribute("larq", larq);
			m.addAttribute("jarq", jarq);
			m.addAttribute("gdrq", gdrq);
			m.addAttribute("jz", jz);
			return "ajuan/jsjzInfoNodlg";
		}
	}
	 * 
	 */

	/**
	 * 初始化即时卷宗新建界面
	 * 
	 * @param jzewm
	 * @param ah
	 * @param model
	 * @return
	 */
	@RequestMapping("jsjzWizard_new.do")
	public String jsjzWizard_new(String jzewm, String ah, Model model) {
		if (StringUtils.isEmpty(jzewm) || StringUtils.isEmpty(ah)) {
			return null;
		}
		model.addAttribute("currJzewm", jzewm.trim());
		model.addAttribute("currAh", ah.trim());
		return "ajuan/jsjzWizard_new";
	}

	/**
	 * 新建即时卷宗
	 * 
	 * @param txspjg1
	 * @param txspjg2
	 * @param njm
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("createJsjzWizard.do")
	@ResponseBody
	public JsonResponseModel createJsjzWizard(String txspjg1, String txspjg2,
			NewJzModel njm, HttpSession session) throws Exception {
		ChuUser user = (ChuUser) session.getAttribute("loginInfo");
		String currJzType = (String) session.getAttribute("currJzType");
		JsonResponseModel jrm = new JsonResponseModel();
		if (njm == null || user == null || StringUtils.isEmpty(currJzType)
				|| StringUtils.isEmpty(njm.getEwm())) {
			return jrm;
		}
		njm.setZlfs(currJzType);// 保存整理方式
		njm.setCreator_id(user.getUserId());// 保存创建者id
		return jsjzWizardService.createJsJz(txspjg1, txspjg2, njm, jrm);
	}

	/**
	 * 显示即时卷宗信息
	 * 
	 * @param action
	 * @param jzewm
	 * @param m
	 * @return
	 */
	@RequestMapping("jsjzWizard_info.do")
	public String getjsJZInfo(String jzewm, Model m) {
		if (StringUtils.isEmpty(jzewm)) {
			return "error";
		}
		VJz jz = aJuanInfoService.getJZInfo(jzewm);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String larq = null, jarq = null, gdrq = null;
		AJuanCode ac = aJuanInfoService.getpcdmbyJzewm(jzewm);
		String remark = "";
		if (aJuanInfoService.getJAjuanByEwm(ac.getAj_ewm()) != null) {
			remark = aJuanInfoService.getJAjuanByEwm(ac.getAj_ewm())
					.getRemark();
		}
		VMlpc mlpc = jzNewService.getMlpcByPCDM(ac.getPcdm());
		if (jz.getLarq() != null)
			larq = formatter.format(jz.getLarq());
		if (jz.getJarq() != null)
			jarq = formatter.format(jz.getJarq());
		if (jz.getGdrq() != null)
			gdrq = formatter.format(jz.getGdrq());
		m.addAttribute("AJuanCode", ac);
		m.addAttribute("pcmc", mlpc.getPcmc());
		m.addAttribute("remark", remark);
		m.addAttribute("larq", larq);
		m.addAttribute("jarq", jarq);
		m.addAttribute("gdrq", gdrq);
		m.addAttribute("jz", jz);
		m.addAttribute("syym4ks", jz.getSyym());
		return "ajuan/jsjzInfo";
	}

	/**
	 * 即时案卷整理
	 * 
	 * @param ajewm
	 * @param mldm
	 * @param m
	 * @param isls
	 * @return
	 */
	@RequestMapping("jsAjzl.do")
	public String jsAjzl(String ajewm, String mldm, Model m, String isls) {
		if (StringUtils.isEmpty(ajewm) || StringUtils.isEmpty(mldm)) {
			return null;
		}
		VAJuanD ajInfo = ajzlService.getMajorAjInfo(ajewm, mldm);
		m.addAttribute("zjzlInfo", ajInfo);
		m.addAttribute("isls", isls);
		m.addAttribute("ajewm4ls", ajewm);
		m.addAttribute("mldm4ls", mldm);
		m.addAttribute("rootMlName", AjzlService.ROOT_ML);
		return "ajuan/jsAjzl";
	}

	/**
	 * 即时案卷整理添加图片
	 * 
	 * @param currNode_id
	 * @param jad
	 * @param m
	 * @param isls
	 * @return
	 */
	@RequestMapping("jsAjzlAddPicUI.do")
	public String jsAjzlAddPicUI(String currNode_id, JAjuanD jad, Model m,
			String isls) {
		m.addAttribute("isls", isls);
		m.addAttribute("currJadInfo", jad);
		m.addAttribute("currNode_id",
				currNode_id == null ? null : currNode_id.trim());
		return "ajuan/jsAjzl_addPic";
	}

	/**
	 * 检查单个二维码的有效性
	 * 
	 * @param ewm
	 * @return
	 */
	@RequestMapping("jsAjzlCheckEwm.do")
	@ResponseBody
	public String ajzlCheckEwm(String ewm) {
		return ajzlService.ajzlCheckEwm(ewm);
	}

	/**
	 * 添加图片
	 * 
	 * @param addPicEwms
	 * @param currNode_id
	 * @return
	 */
	@RequestMapping("jsAjzlAddPic.do")
	@ResponseBody
	public String ajzlAddPic(String addPicEwms, String currNode_id,
			JAjuanD jad, String isls) {
		// if (isls.equals("true")) {
		// return ajzlService.lsajzlAddPic(addPicEwms, currNode_id, jad);
		// } else
		return ajzlService.ajzlAddPic(addPicEwms, currNode_id, jad);
	}
	
	/**
	 * 删除指定的图片
	 * 
	 * @param nodeIds
	 * @return
	 */
	@RequestMapping("jsAjzlDeletePic.do")
	@ResponseBody
	public String jsAjzlDeletePic(String nodeIds,String isls) {
		return ajzlService.ajzlDeletePic(nodeIds,isls);
	}
}
