package org.lf.jssm.action.ajuan;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.db.pojo.JAjuanD;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.db.pojo.VAJuanD;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.db.pojo.VMlpc;
import org.lf.jssm.service.ajuan.AjuanInfoService;
import org.lf.jssm.service.ajuan.AjzlService;
import org.lf.jssm.service.ajuan.JZNewService;
import org.lf.jssm.service.ajuan.LSJZWizardService;
import org.lf.jssm.service.model.AJuanCode;
import org.lf.jssm.service.model.JZType;
import org.lf.jssm.service.model.JsonResponseModel;
import org.lf.jssm.service.model.NewJzModel;
import org.lf.jssm.service.raw.RawPicUploadService;
import org.lf.jssm.service.raw.RawScanParameterService;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiTree;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 采用“历史”卷宗整理方式来整理未归档的卷宗
 */
@Controller
@RequestMapping("/ajuan/")
public class LSJZWizardController implements BaseController {
	// private static final Logger logger =
	// Logger.getLogger(LSJZWizardController.class);
	@Autowired
	private LSJZWizardService lsjzWizardService;
	@Autowired
	private JZNewService jzNewService;
	@Autowired
	private AjzlService ajzlService;
	@Autowired
	private AjuanInfoService aJuanInfoService;
	@Autowired
	private RawScanParameterService rawScanParaService;
	@Autowired
	private RawPicUploadService rawPicUploadService;

	/**
	 * 初始化封面信息界面
	 * 
	 * @param jzewm
	 * @param ah
	 * @param model
	 * @return
	 */
	@RequestMapping("lsjzWizard_new.do")
	public String lsjzWizard_new(String jzewm, String ah, Model model) {
		if (StringUtils.isEmpty(jzewm) || StringUtils.isEmpty(ah)) {
			return null;
		}
		model.addAttribute("currJzewm", jzewm.trim());
		model.addAttribute("currAh", ah.trim());
		return "ajuan/lsjzWizard_new";
	}

	/**
	 * 根据封面二维码(jzewm),查找,进行不同的操作
	 */
	@RequestMapping("lsfindJzEwm.do")
	@ResponseBody
	public JSONObject findJzEwm(String jzewm, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		if (lsjzWizardService.findJzByEwm(jzewm) == null) {
			result.put("result", "无结果");
		} else if (lsjzWizardService.findJzByEwm(jzewm).getStatus() == 0) {
			if (!lsjzWizardService.findJzByEwm(jzewm).getZlfs()
					.equals(JZType.历史.getType())) {
				result.put("result", "非历史方式");
				result.put("JzInfo", lsjzWizardService.findJzByEwm(jzewm));
				return result;
			}
			result.put("result", "未归档");
			result.put("JzInfo", lsjzWizardService.findJzByEwm(jzewm)); // 将卷宗信息也要传递给前台
		} else if (lsjzWizardService.findJzByEwm(jzewm).getStatus() == 1) {
			if (!lsjzWizardService.findJzByEwm(jzewm).getZlfs()
					.equals(JZType.历史.getType())) {
				result.put("result", "非历史方式");
				result.put("JzInfo", lsjzWizardService.findJzByEwm(jzewm));
				return result;
			}
			result.put("result", "已归档");
			result.put("JzInfo", lsjzWizardService.findJzByEwm(jzewm)); // 将卷宗信息也要传递给前台
		}
		return result;
	}

	/**
	 * 查找“历史”卷宗整理方式的封面二维码状态信息
	 * 
	 * @param jz_ewm
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping("lsfindfmEwmstatus.do")
	@ResponseBody
	public String findfmEwmstatus(String jz_ewm) throws InterruptedException {
		return lsjzWizardService.findfmEwmstatus(jz_ewm);
	}

	/**
	 * 历史卷宗信息
	 * 
	 * @param action
	 * @param jzewm
	 * @param m
	 * @return
	 */
	@RequestMapping("lsjzWizard_info.do")
	public String getlsJZInfo(String jzewm, Model m) {
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
		return "ajuan/lsjzInfo";
	}

	

	/**
	 * 初始化历史案卷整理目录树
	 * 
	 * @param ajInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lsajzlTree.do")
	@ResponseBody
	public List<EasyuiTree> lsajzlTree(JAjuanD ajInfo, String isls) {
		return ajzlService.getAjzlTree(ajInfo, isls);
	}

	/**
	 * 创建历史卷宗
	 * 
	 * @param txspjg1
	 * @param txspjg2
	 * @param njm
	 * @param session
	 * @param file_upload
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("createLsjzWizard.do")
	@ResponseBody
	public JsonResponseModel createLsjzWizard(
			String txspjg1,
			String txspjg2,
			NewJzModel njm,
			HttpSession session,
			@RequestParam(value = "file_upload", required = false) MultipartFile[] file_upload)
			throws Exception {
		ChuUser user = (ChuUser) session.getAttribute("loginInfo");
		String currJzType = (String) session.getAttribute("currJzType");
		JsonResponseModel jrm = new JsonResponseModel();
		if (njm == null || user == null || StringUtils.isEmpty(currJzType)
				|| StringUtils.isEmpty(njm.getEwm())) {
			return jrm;
		}
		njm.setZlfs(currJzType);// 保存整理方式
		njm.setCreator_id(user.getUserId());// 保存创建者id
		return lsjzWizardService.createLsJz(txspjg1, txspjg2, njm, file_upload,
				jrm);
	}

	/**
	 * 快速整理初始化目录datagrid
	 * 
	 * @param pcdm
	 * @param jzewm
	 * @return
	 */
	@RequestMapping("VAJuanDList4LS.do")
	@ResponseBody
	public List<VAJuanD> VAJuanDList4LS(String pcdm, String jzewm) {
		return aJuanInfoService.getVajList4LS(pcdm, jzewm);
	}

	/**
	 * 卷宗信息——内容
	 * 
	 * @param ajewm
	 * @param mldm
	 * @param m
	 * @param isls
	 * @return
	 */
	@RequestMapping("lsAjzl.do")
	public String lsAjzl(String ajewm, String mldm, Model m, String isls) {
		if (StringUtils.isEmpty(ajewm) || StringUtils.isEmpty(mldm)) {
			return null;
		}
		VAJuanD ajInfo = ajzlService.getMajorAjInfo(ajewm, mldm);
		m.addAttribute("zjzlInfo", ajInfo);
		m.addAttribute("isls", isls);
		m.addAttribute("ajewm4ls", ajewm);
		m.addAttribute("mldm4ls", mldm);
		m.addAttribute("rootMlName", AjzlService.ROOT_ML);
//		m.addAttribute("currJzewm", currJzewm);
		return "ajuan/lsAjzl";
	}

	/**
	 * 初始化案卷整理目录树
	 * 
	 * @param ajInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("lsAjzlTree.do")
	@ResponseBody
	public List<EasyuiTree> ajzlTree(JAjuanD ajInfo, String isls) {
		return ajzlService.getAjzlTree(ajInfo, isls);
	}

	/**
	 * 获得当前目录的所有标签
	 * 
	 * @param jad
	 * @return
	 */
	@RequestMapping("lsAjzlGetAllLab.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> ajzlGetAllLab(JAjuanD jad,
			String combo4Query) {
		return ajzlService.ajzlGetAllLab(jad, combo4Query);
	}

	/**
	 * 显示整理完成的图片
	 * 
	 * @param currPicEwm
	 * @return
	 */
	@RequestMapping("showLsZlImg.do")
	public String showZlImg(String currPicEwm, Model m, String isls) {
		JRawfile zlImgInfo = ajzlService.getZlImgInfo(currPicEwm);
		m.addAttribute("zlImgInfo", zlImgInfo);
		m.addAttribute("isls", isls);
		return "ajuan/ajzl_img";
	}

	/**
	 * 案卷整理时批量上传图片
	 * 
	 * @param currPicEwm
	 * @param m
	 * @return
	 */
	@RequestMapping("lsAjzlPicUploadUI.do")
	public String ajzlPicUploadUI(String currNode_id, Model m, JAjuanD jad,
			String isls) {
		// 获得扫描配置参数
		Map<String, String> map = rawScanParaService.getDefaultEnvMap();
		m.addAttribute("jScanEnv", JSON.toJSONString(map));
		m.addAttribute("currNode_id", currNode_id);
		m.addAttribute("JadInfo", jad);
		m.addAttribute("isls", isls);
		return "ajuan/lsAjzlPicUpload";
	}

	/**
	 * 图片上传
	 * 
	 * @throws Exception
	 */
	@RequestMapping("lsRawPicUpload.do")
	@ResponseBody
	public JsonResponseModel rawPicUpload(
			@RequestParam(value = "file_upload", required = false) MultipartFile[] file_upload,
			String userId, String isls, String currNode_id, JAjuanD jadIn,
			String upload4Zl) throws Exception {
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		return rawPicUploadService.zjzlRawPicUpload(file_upload, userId, isls,
				currNode_id, jadIn, upload4Zl);
	}

	/**
	 * 删除指定的图片
	 * 
	 * @param nodeIds
	 * @return
	 */
	@RequestMapping("lsAjzlDeletePic.do")
	@ResponseBody
	public String ajzlDeletePic(String nodeIds,String isls) {
		return ajzlService.ajzlDeletePic(nodeIds,isls);
	}

	/**
	 * 初始化添加图片标签对话框
	 * 
	 * @param ewm
	 * @param m
	 * @return
	 */
	@RequestMapping("ajzlAddPicLabelUI.do")
	public String ajzlAddPicLabelUI(String nodeIds, JAjuanD jad, Model m) {
		m.addAttribute("ajzlAPLabJad", jad);
		m.addAttribute("checkedNodeIds", nodeIds);
		return "ajuan/ajzlAddPicLabel";
	}

	/**
	 * 为节点添加标签
	 * 
	 * @param jad
	 * @return
	 */
	@RequestMapping("ajzlSavePicLab.do")
	@ResponseBody
	public String ajzlSavePicLab(String nodeIds, JAjuanD jad) {
		return ajzlService.ajzlSavePicLab(nodeIds, jad);
	}

	/**
	 * 删除所有指定图片的标签
	 * 
	 * @param nodeIds
	 * @return
	 */
	@RequestMapping("ajzlDelPicLabel.do")
	@ResponseBody
	public String ajzlDelPicLabel(String nodeIds) {
		return ajzlService.ajzlDelPicLabel(nodeIds);
	}

	/**
	 * 图片拖动保存
	 * 
	 * @param sourceId
	 * @param targetId
	 * @param point
	 * @return
	 */
	@RequestMapping("ajzlUpdatePicOrder.do")
	@ResponseBody
	public String ajzlUpdatePicOrder(String sourceId, String targetId,
			String point, String isls) {
		return ajzlService.ajzlUpdatePicOrder(sourceId, targetId, point, isls);
	}

	/**
	 * 快速整理，保存页次
	 * 
	 * @return
	 */
	@RequestMapping("ksjzUpdata.do")
	@ResponseBody
	public String ksjzUpdata(String indexCode, String aj_ewm, String remark,
			Integer syym) {
		aJuanInfoService.updateJAJuan(aj_ewm, remark);
		return jzNewService.updataOrderJajuanD4Ks(aj_ewm, indexCode, syym);
	}

}
