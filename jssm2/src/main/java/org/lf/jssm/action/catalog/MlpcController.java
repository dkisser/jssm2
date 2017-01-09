package org.lf.jssm.action.catalog;

import java.util.List;

import org.apache.log4j.Logger;
import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.CAjuanMl;
import org.lf.jssm.service.catalog.CAJuanYjflService;
import org.lf.jssm.service.catalog.CMlpcService;
import org.lf.jssm.service.catalog.CSpzhService;
import org.lf.jssm.service.model.ChuCMlpc;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiDatagrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/catalog/")
public class MlpcController implements BaseController {
	Logger logger = Logger.getLogger(MlpcController.class);
	
	@Autowired
	private CMlpcService mlpcService;
	
	@Autowired
	private CAJuanYjflService cajuanYjflService;
	
	@Autowired
	private CSpzhService cspzhService;
	
	@RequestMapping("mlpcListUI.do")
	public String mlpcListUI () {
		return "catalog/mlpcList";
	}
	
	/**
	 * 功能:转换停用和启用状态
	 * @param tybz
	 */
	@RequestMapping("refirmSOS.do")
	@ResponseBody
	public String refirmSOS (String dm,String tybz) {
		ChuCMlpc mlpc = new ChuCMlpc();
		mlpc.setDm(dm);
		if (tybz.equals("1")) {
			mlpc.setTybz("0");
		} else {
			mlpc.setTybz("1");
		}
		mlpcService.refirmSOS(mlpc);
		return "success";
	}
	
	/**
	 * 功能: 校验pcmc是否重复
	 * @param pcmc
	 * @return json
	 */
	@RequestMapping("checkPcmc.do")
	@ResponseBody
	public JSONObject checkPcmc (String pcmc) {
		JSONObject jsonobj = null;
		try {
			jsonobj = new JSONObject();
			if (mlpcService.findPcmc(pcmc) ==null) {
				jsonobj.put("success", 1);
			} else {
				jsonobj.put("success", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonobj;
	}
	
	/**
	 * 功能: 从c_fy中获取所有的fydm的fymc,
	 * 并将其转化成 List<EasyuiComboBoxItem<String, String>>
	 * 使前台获得数据
	 * @return List<EasyuiComboBoxItem<String, String>>
	 */
	@RequestMapping("getAllFymc.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getAllFymc (String dm) {
		
		return mlpcService.getAllFymc();
	}
	
	/**
	 * 功能: 将连接查询得到的lx的名称找到,
	 * 并将其转化成 List<EasyuiComboBoxItem<String, String>>
	 * 使前台获得数据
	 * @return List<EasyuiComboBoxItem<String, String>>
	 */
	@RequestMapping("getAllLxName.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getAllLxName () {
		return mlpcService.getAllLxName();
	}
	
	/**
	 * 功能：找到所有的ajuanYjfl
	 * 并将其转化成 List<EasyuiComboBoxItem<String, String>>
	 * 使前台获得数据
	 * @return List<EasyuiComboBoxItem<String, String>>;
	 */
	@RequestMapping("getAllCAjuanYjfl.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getAllCAjuanYjfl () {
		return cajuanYjflService.getAllCAjuanYjfl(); 
	}

	/**
	 * 功能:用于根据前台传过来的yjfl.dm找到相应的spzh.mc
	 * @param dm (yjfl.dm)
	 * @return List<EasyuiComboBoxItem<String, String>>;
	 */
	@RequestMapping("getSpzhByAJuanYjfl.do")
	@ResponseBody
	public List<EasyuiComboBoxItem<String, String>> getSpzhByAJuanYjfl (String dm) {
		return cspzhService.getSpzhByAJuanYjfl(dm); 
	}

	/**
	 * 功能:得到分页后的数据,并返回
	 * @param page
	 * @param rows
	 * @return 分页后的数据
	 */
	@RequestMapping("mlpcList.do")
	@ResponseBody
	public EasyuiDatagrid<ChuCMlpc> mlpcList (int page,int rows) {
		
		return mlpcService.mlpcList(page, rows);
	}
	
	/**
	 * 功能: 接受前台请求,跳转页面到mlpcInfo.jsp
	 * @param model 用于标识是否是新建
	 * @return mlpcInfo.jsp
	 */
	@RequestMapping("addMlpcInfo.do")
	public String addMlpcInfo (Model model) {
		boolean isADD = true;
		model.addAttribute("isADD", isADD);
		return "catalog/mlpcInfo";
	}
	
	/**
	 * 功能:接受前台请求,进行页面跳转,同时,通过前台传过来的dm查到要编辑的mlpc
	 * 并传到前台
	 * @param model 用于标识是否是编辑和存放编辑的mlpc信息
	 * @param dm
	 * @return mlpcInfo.jsp
	 */
	@RequestMapping("editMlpcInfo.do")
	public String editMlpcInfo (Model model,String dm) {
		boolean isADD = false;
		ChuCMlpc editMlpc = mlpcService.getMlpcByDm(dm);
		model.addAttribute("isADD",isADD);
		model.addAttribute("editMlpc", editMlpc);
		return "catalog/mlpcInfo";
	}
	
	@RequestMapping("saveMlpc.do")
	@ResponseBody
	public void saveMlpc (Boolean savenew,ChuCMlpc mlpc) {
		if (savenew) {
			//savenew为真,表示添加
			mlpcService.addMlpc(mlpc);
		} else {
			//savenew为假,表示编辑
			mlpcService.editMlpc(mlpc);
		} 
	}
	
	/**
	 *  案卷目录批次列表传过来浏览(browse)和维护(maintain)参数，显示不同的功能
	 * @param model
	 * @param action
	 * @param pcdm
	 * @return
	 */
	@RequestMapping("ajmlList.do")
	public String getAjmlpcList(Model model, String action,String pcdm) {
		if (action.equals("browse")) {
			model.addAttribute("action", "browse");
			model.addAttribute("mlpc", mlpcService.findpcmcbydm(pcdm));
			return "catalog/ajmlList";
		}else if(action.equals("maintain")){
			model.addAttribute("action", "maintain");
			model.addAttribute("mlpc", mlpcService.findpcmcbydm(pcdm));
			return "catalog/ajmlList";
		}
		return null;
	}
	
	
	/**
	 * 用于datagrid列表中数据的显示
	 * @param pcdm
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("mlpcBrowse.do")
	@ResponseBody
	public EasyuiDatagrid<CAjuanMl> getmlpcList(String pcdm,int page,int rows){
		return mlpcService.findMlByPcdm(pcdm, page, rows);
	}
	
	@RequestMapping("ajmlInfo_add.do")
	public String getAddAjml(Model model,String pcdm){
		model.addAttribute("action", "add");
		model.addAttribute("pcdm", pcdm);
		return "catalog/ajmlInfo";
	}
	
	@RequestMapping("ajmlInfo_del.do")
	@ResponseBody
	public JSONObject getDelAjml(int id,String pcdm,Model model){
		JSONObject json = new JSONObject();
		if(mlpcService.delAjuanMl(id, pcdm)==1){
			logger.info("删除一条案卷目录");
			json.put("success", 1);
		}
		return json;
	}
	
	@RequestMapping("ajmlInfo_update.do")
	public String getUpadteAjml(Model model,String pcdm,int id){
		model.addAttribute("action", "update");
		model.addAttribute("pcdm", pcdm);
		model.addAttribute("ajml",mlpcService.findAjuanMlByIdAndPcdm(pcdm, id));
		return "catalog/ajmlInfo";
	}
	
	@RequestMapping("addAjml.do")
	@ResponseBody
	public void addAjml(String mc,String tybz,String remark,int id,String pcdm){
		if(mlpcService.addAjml(mc, tybz, remark, id, pcdm)==1){
			logger.info("新增一条案卷目录");
		}
	}
	
	@RequestMapping("updateAjml.do")
	@ResponseBody
	public void updateAjml(String mc,String tybz,String remark,int id,String pcdm){
		if(mlpcService.updateAjml(mc, tybz, remark, id, pcdm)==1){
			logger.info("更新一条案卷目录");
		}
	}
	
	/**
	 * ajax唯一性检测
	 * @param id
	 * @param pcdm
	 * @return
	 */
	@RequestMapping("checkAjmlList.do")
	@ResponseBody
	public JSONObject checkMlId(int id,String pcdm){
		JSONObject json = new JSONObject();
		if(mlpcService.findAjuanMlByIdAndPcdm(pcdm, id)==null){
			json.put("success", 1);
		}
		return json;
	}
	
	@RequestMapping("clearAjuanMl.do")
	@ResponseBody
	public JSONObject clearAjuanMl(String pcdm,Model model){
		JSONObject json = new JSONObject();
		if(mlpcService.clearAjuanMl(pcdm)>0){
			logger.info("清空案卷目录数据成功！");
			json.put("success", 1);
		}
		return json;
	}
	
	@RequestMapping("ajmlInfo_import.do")
	public String getImportAjmlInfo(String pcdm,Model model){
		model.addAttribute("action", "import");
		model.addAttribute("pcdm", pcdm);
		model.addAttribute("id", mlpcService.getMaxId(pcdm)+1);
		return "catalog/ajmlInfo2";
	}
	
	@RequestMapping("importAjuanMl.do")
	@ResponseBody
	public JSONObject importAjml(int id,String mlList,String pcdm){
		JSONObject json = new JSONObject();
		if(mlpcService.importAjmlList(id, mlList,pcdm)==1){
			logger.info("批量导入目录书记成功目录数据");
		}else if(mlpcService.importAjmlList(id, mlList,pcdm)==0){
			logger.info("导入数据太多，数据溢出");
			json.put("success", 0);
		}
		return json;
	}
}
