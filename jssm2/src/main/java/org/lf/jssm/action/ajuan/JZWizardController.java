package org.lf.jssm.action.ajuan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.CAQJB;
import org.lf.jssm.db.pojo.CSPCX;
import org.lf.jssm.db.pojo.CSPJG;
import org.lf.jssm.db.pojo.VMlpc;
import org.lf.jssm.service.ajuan.JzWizardService;
import org.lf.jssm.service.model.JsonResponseModel;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 案卷整理导航
 */
@Controller
@RequestMapping("/ajuan/")
public class JZWizardController implements BaseController {
//	private static final Logger logger = LoggerFactory
//			.getLogger(JZWizardController.class);
	@Autowired
	private JzWizardService jzWizardService;

	/**
	 * 显示扫描封面二维码界面
	 * 
	 * @param jz_type
	 * @param session
	 * @return
	 */
	@RequestMapping("jzWizardUI.do")
	public String getJzWizardUI(@RequestParam(required = true) String jz_type,
			HttpSession session) {
		if (StringUtils.isEmpty(jz_type)) {
			return null;
		}
		session.setAttribute("currJzType", jz_type);
		return "ajuan/jzWizard";
	}

	/**
	 * 检查封面二维码
	 * 
	 * @param jzewm
	 * @return
	 */
	@RequestMapping("checkFmEwm.do")
	@ResponseBody
	public JsonResponseModel checkFmEwm(String jzewm, String currJzType) {
		return jzWizardService.checkFmEwm(jzewm, currJzType);
	}

	/**
	 * 显示查找案件信息界面
	 * 
	 * @param jzewm
	 * @param model
	 * @return
	 */
	@RequestMapping("jzWizard.do")
	public String jzWizard(String jzewm, Model model) {
		if (StringUtils.isEmpty(jzewm)) {
			return null;
		}
		model.addAttribute("jzewm", jzewm.trim());
		return "ajuan/jzWizard_ajxx";
	}

	/**
	 * 卷宗类型下拉框
	 * 
	 * @return
	 */
	@RequestMapping("getJZLX.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getJZLX() {
		List<EasyuiComboBoxItem<String, String>> list = new ArrayList<>();
		list.add(new EasyuiComboBoxItem<String, String>("正卷", "正卷", true));
		list.add(new EasyuiComboBoxItem<String, String>("副卷", "副卷", false));
		return list;
	}

	/**
	 * 审判程序下拉框
	 * 
	 * @return
	 */
	@RequestMapping("getSPCX.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getSPCX() {
		List<CSPCX> CSPCXlist = jzWizardService.getAllSPCX();
		List<EasyuiComboBoxItem<String, String>> list = new ArrayList<EasyuiComboBoxItem<String, String>>();
		Iterator<CSPCX> it = CSPCXlist.iterator();
		list.add(new EasyuiComboBoxItem<String, String>("0", "空", true));
		while (it.hasNext()) {
			CSPCX cspcx = it.next();
			list.add(new EasyuiComboBoxItem<String, String>(cspcx.getDm(),
					cspcx.getMc(), false));
		}
		return list;
	}

	/**
	 * 审判结果下拉框
	 * 
	 * @return
	 */
	@RequestMapping("getSPJG.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getSPJG() {
		List<CSPJG> CSPJGlist = jzWizardService.getAllSPJG();
		List<EasyuiComboBoxItem<String, String>> list = new ArrayList<EasyuiComboBoxItem<String, String>>();
		Iterator<CSPJG> it = CSPJGlist.iterator();
		while (it.hasNext()) {
			CSPJG cspjg = it.next();
			list.add(new EasyuiComboBoxItem<String, String>(cspjg.getDm(),
					cspjg.getMc(), false));
		}
		return list;
	}

	/**
	 * 保密级别下拉框
	 * 
	 * @return
	 */
	@RequestMapping("getAQJB.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getAQJB() {
		List<CAQJB> CAQJBlist = jzWizardService.getAllAQJB();
		List<EasyuiComboBoxItem<String, String>> list = new ArrayList<>();
		Iterator<CAQJB> it = CAQJBlist.iterator();
		while (it.hasNext()) {
			CAQJB caqjb = it.next();
			if (caqjb.getMc().equals("普通")) {
				list.add(new EasyuiComboBoxItem<String, String>(caqjb.getDm(),
						caqjb.getMc(), true));
			} else {
				list.add(new EasyuiComboBoxItem<String, String>(caqjb.getDm(),
						caqjb.getMc(), false));
			}
		}
		return list;
	}
	/**
	 * 初始化目录批次下拉框
	 * @return
	 */
	@RequestMapping("getMlpc.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getMLPC(){
		List<VMlpc> VMLPClist = jzWizardService.getAllVMlpc();
		List<EasyuiComboBoxItem<String, String>> list = new ArrayList<EasyuiComboBoxItem<String, String>>();
		Iterator<VMlpc> it = VMLPClist.iterator();
		while (it.hasNext()) {
			VMlpc vmlpc = it.next();
			if(vmlpc.getDm().equals("22522016015001")){
				list.add(new EasyuiComboBoxItem<String, String>(vmlpc.getDm(), vmlpc.getPcmc(),true));//默认选中民事一审案件正卷目录
			}
			else list.add(new EasyuiComboBoxItem<String, String>(vmlpc.getDm(), vmlpc.getPcmc()));
		}
		return list;
	}
	
	/**
	 * 卷宗归档归档期限下拉框列表数据
	 */
	@RequestMapping("getGdGdqx.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String,String>> getGdGdqx(String gdqxFlag){
		return jzWizardService.getGDGdqx(gdqxFlag);
	}

}
