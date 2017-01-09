package org.lf.jssm.service.ajuan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.lf.jssm.db.dao.CAQJBMapper;
import org.lf.jssm.db.dao.CBgqxMapper;
import org.lf.jssm.db.dao.CSPCXMapper;
import org.lf.jssm.db.dao.CSPJGMapper;
import org.lf.jssm.db.dao.JAjjzMapper;
import org.lf.jssm.db.dao.JAjuanMapper;
import org.lf.jssm.db.dao.JJzMapper;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.LJzMapper;
import org.lf.jssm.db.dao.VAJuanDMapper;
import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.db.dao.VMlpcMapper;
import org.lf.jssm.db.pojo.CAQJB;
import org.lf.jssm.db.pojo.CBgqx;
import org.lf.jssm.db.pojo.CSPCX;
import org.lf.jssm.db.pojo.CSPJG;
import org.lf.jssm.db.pojo.JAjjz;
import org.lf.jssm.db.pojo.JAjuan;
import org.lf.jssm.db.pojo.JJZ;
import org.lf.jssm.db.pojo.LJz;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.db.pojo.VMlpc;
import org.lf.jssm.service.model.JsonResponseModel;
import org.lf.jssm.service.model.NewJzModel;
import org.lf.utils.EasyuiComboBox;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.StringUtils;
import org.lf.utils.servlet.CaseInfo;
import org.lf.utils.servlet.WebServiceTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("JzWizardService")
public class JzWizardService {
	private static final Logger logger = LoggerFactory
			.getLogger(JzWizardService.class);
	@Autowired
	private JJzMapper jJzDao;
	@Autowired
	private JAjjzMapper jajjzDao;
	@Autowired
	private JAjuanMapper jajuanDao;
	@Autowired
	private VJzMapper vjzDao;
	@Autowired
	private JRawfileMapper jRawfileDao;
	@Autowired
	private VAJuanDMapper vAJuandDao;
	@Autowired
	private CSPCXMapper CSPCXDao;
	@Autowired
	private CSPJGMapper CSPJGDao;
	@Autowired
	private CAQJBMapper CAQJBDao;
	@Autowired
	private VMlpcMapper vmlpcDao;
	@Autowired
	private CBgqxMapper cBgqxDao;
	@Autowired
	private LJzMapper ljzDao;

	/**
	 * 根据封面二维码，查询V_JZ视图
	 */
	public VJz findJzByEwm(String jzewm) {
		return vjzDao.selectJzByEwm(jzewm);
	}

	/**
	 * 通过访问通达海的服务器，根据查询返回xml字符串,解析之后存在map容器中
	 * 
	 * @throws Exception
	 */
	public Map<String, CaseInfo> getCaseMap(String dsr, String ah, String sfja,
			String larq1, String larq2, String jarq1, String jarq2)
			throws Exception {
		String larq = larq1.replace("-", "") + "-" + larq2.replace("-", ""); // 拼接出查询立案日期所需要的字符串样式，如：20130101-20130205
		String jarq = null;
		if (jarq1 == "" && jarq2 == "") {
			jarq = null;
		} else {
			jarq = jarq1.replace("-", "") + "-" + jarq2.replace("-", ""); // 拼接出查询结案日期所需要的字符串样式
		}

		String status = null; // 卷宗状态，即zt字段
		if (sfja.equals("未知")) { // 选项中有未知值，即不给状态赋值，可查出所有状态的案件信息
			status = "";
		} else if (sfja.equals("已结案")) {
			status = "1";
		} else if (sfja.equals("未结案")) {
			status = "0";
		}
		// 通过webservice获得案件信息xml字符串
		String caseInfoXml = WebServiceTool.getCaseXml(ah, dsr, larq, jarq,
				status);
		// 返回解析xml字符串的结果
		return parseCaseXml(caseInfoXml);
	}

	/**
	 * 解析xml案件信息，将数据保存在map中
	 */
	private Map<String, CaseInfo> parseCaseXml(String caseInfoXml)
			throws Exception {
		Document document = DocumentHelper.parseText(caseInfoXml);
		Element root = document.getRootElement();
		Element resultEle = root.element("Result");
		if (!"0".equals(StringUtils.base64Decode(resultEle.element("Code")
				.getText()))) {
			// Code不为0表示 查询失败
			// 返回其他值，表示失败原因描述
			logger.info(StringUtils.base64Decode(resultEle.element("Msg")
					.getText()));
			return null;
		}
		Element dataEle = root.element("Data");
		@SuppressWarnings("rawtypes")
		List caseXmlList = dataEle.elements("EAJ"); // Data子节点的List
		if (caseXmlList == null || caseXmlList.size() == 0) {
			return null;
		}
		Map<String, CaseInfo> caseInfoMap = new HashMap<String, CaseInfo>();
		@SuppressWarnings("rawtypes")
		Iterator it = caseXmlList.iterator();
		while (it.hasNext()) {
			Element elm = (Element) it.next();
			CaseInfo caseInfo = new CaseInfo();
			caseInfo.setAhdm(StringUtils.base64Decode(elm.elementText("AHDM")));
			caseInfo.setAh(StringUtils.base64Decode(elm.elementText("AH")));
			caseInfo.setFydm(StringUtils.base64Decode(elm.elementText("FYDM")));
			caseInfo.setFydmms(StringUtils.base64Decode(elm
					.elementText("FYDMMS")));
			caseInfo.setDz(StringUtils.base64Decode(elm.elementText("DZ")));
			caseInfo.setAjlx(StringUtils.base64Decode(elm.elementText("AJLX")));
			caseInfo.setLarq(StringUtils.base64Decode(elm.elementText("LARQ")));
			caseInfo.setJarq(StringUtils.base64Decode(elm.elementText("JARQ")));
			caseInfo.setSxjmrq(StringUtils.base64Decode(elm
					.elementText("SXJMRQ")));
			caseInfo.setSaay(StringUtils.base64Decode(elm.elementText("SAAY")));
			caseInfo.setSaayms(StringUtils.base64Decode(elm
					.elementText("SAAYMS")));
			caseInfo.setAyms(StringUtils.base64Decode(elm.elementText("AYMS")));
			caseInfo.setDsr(StringUtils.base64Decode(elm.elementText("DSR")));
			caseInfo.setJafs(StringUtils.base64Decode(elm.elementText("JAFS")));
			caseInfo.setJafsms(StringUtils.base64Decode(elm
					.elementText("JAFSMS")));
			caseInfo.setCbbm1(StringUtils.base64Decode(elm.elementText("CBBM1")));
			caseInfo.setCbbmms(StringUtils.base64Decode(elm
					.elementText("CBBMMS")));
			caseInfo.setCbr(StringUtils.base64Decode(elm.elementText("CBR")));
			caseInfo.setCbrms(StringUtils.base64Decode(elm.elementText("CBRMS")));
			caseInfo.setCbrbs(StringUtils.base64Decode(elm.elementText("CBRBS")));
			caseInfo.setZt(StringUtils.base64Decode(elm.elementText("ZT")));
			caseInfo.setAjztms(StringUtils.base64Decode(elm
					.elementText("AJZTMS")));
			caseInfo.setSpz(StringUtils.base64Decode(elm.elementText("SPZ")));
			caseInfo.setSpzms(StringUtils.base64Decode(elm.elementText("SPZMS")));
			caseInfo.setSpzbs(StringUtils.base64Decode(elm.elementText("SPZBS")));
			caseInfo.setHytcy(StringUtils.base64Decode(elm.elementText("HYTCY")));
			caseInfo.setSjy(StringUtils.base64Decode(elm.elementText("SJY")));
			caseInfo.setSjyms(StringUtils.base64Decode(elm.elementText("SJYMS")));
			caseInfo.setSjybs(StringUtils.base64Decode(elm.elementText("SJYBS")));
			caseInfo.setCpwsxh(StringUtils.base64Decode(elm
					.elementText("CPWSXH")));
			caseInfo.setYsfydm(StringUtils.base64Decode(elm
					.elementText("YSFYDM")));
			caseInfo.setYsfyms(StringUtils.base64Decode(elm
					.elementText("YSFYMS")));
			caseInfo.setBdje(StringUtils.base64Decode(elm.elementText("BDJE")));
			caseInfo.setAjmc(StringUtils.base64Decode(elm.elementText("AJMC")));
			caseInfo.setSxqsrq(StringUtils.base64Decode(elm
					.elementText("SXQSRQ")));
			// 根据案号获得当事人身份证号码
			caseInfoMap.put(StringUtils.base64Decode(elm.elementText("AH")),
					caseInfo);
		}
		return caseInfoMap;
	}

	public String findfmEwmstatus(String jz_ewm) {
		// JRawfile jf = new JRawfile();
		// jf.setEwm(jz_ewm);
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
	 * 检查封面二维码
	 * 
	 * @param jzewm
	 * @param currJzType
	 * @return
	 */
	public JsonResponseModel checkFmEwm(String jzewm, String currJzType) {
		JsonResponseModel jrm = new JsonResponseModel();
		if (StringUtils.isEmpty(jzewm)) {
			jrm.setMsgInfo("isEmpty");
			return jrm;
		}
		if (StringUtils.isEmpty(currJzType)) {
			jrm.setMsgInfo("typeError");
			return jrm;
		}
		// TODO 测试取消验证
		// if (JZType.即时.getType().equals(currJzType)
		// && jRawfileDao.getRawFileByEwm(jzewm) == null) {
		// jrm.setMsgInfo("ewmError");
		// return jrm;
		// }
		JJZ jjz = jJzDao.selectJJZByEwm(jzewm);
		if (jjz == null) {
			jrm.setMsgInfo("newJz");// 新卷宗
			return jrm;
		} else if (!currJzType.equals(jjz.getZlfs())) {
			jrm.setMsgInfo("typeError");// 卷宗类型不符
			return jrm;
		}
		if (jjz.getStatus() == 1) {
			jrm.setMsgInfo("ygd");// 已归档
		} else {
			jrm.setMsgInfo("wgd");// 未归档
		}
		return jrm;
	}

	/**
	 * 查询所有审判程序
	 * 
	 * @return
	 */
	public List<CSPCX> getAllSPCX() {
		return CSPCXDao.selectAll();
	}

	/**
	 * 查询所有审判结果
	 * 
	 * @return
	 */
	public List<CSPJG> getAllSPJG() {
		return CSPJGDao.selectAll();
	}

	/**
	 * 查询所有安全级别
	 * 
	 * @return
	 */
	public List<CAQJB> getAllAQJB() {
		return CAQJBDao.selectAll();
	}

	/**
	 * 根据名称查询安全级别信息
	 * 
	 * @param mc
	 * @return
	 */
	public List<CAQJB> getAQJBByMC(String mc) {
		return CAQJBDao.selectByMC(mc);
	}

	/**
	 * 根据名称查询审判程序信息
	 * 
	 * @param mc
	 * @return
	 */
	public List<CSPCX> getSPCXByMC(String mc) {
		return CSPCXDao.selectByMC(mc);
	}

	/**
	 * 获得所有目录批次
	 * 
	 * @return
	 */
	public List<VMlpc> getAllVMlpc() {
		return vmlpcDao.selectAll();
	}

	/**
	 * 初始化归档期限
	 * 
	 * @param gdqxFlag
	 * @return
	 */
	public List<EasyuiComboBoxItem<String, String>> getGDGdqx(String gdqxFlag) {
		List<CBgqx> CAQJBList = cBgqxDao.selectAll();
		TreeMap<String, String> map = new TreeMap<String, String>();
		if ("newJz".equals(gdqxFlag)) {
			for (CBgqx cbgqx : CAQJBList) {
				map.put(cbgqx.getDm(), cbgqx.getMc());
			}
		} else {
			for (CBgqx cbgqx : CAQJBList) {
				map.put(cbgqx.getMc(), cbgqx.getMc());
			}
		}
		EasyuiComboBox<String, String> ecb = new EasyuiComboBox<String, String>(
				map);
		return ecb.getRecords();
	}

	/**
	 * 设置卷宗数据并入库
	 * 
	 * @param ajEwm
	 * @param njm
	 * @param jrm
	 * @param currDate
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonResponseModel saveJzInfo(String ajEwm, NewJzModel njm,
			JsonResponseModel jrm, Date currDate) throws Exception {
		JAjuan jajuan = new JAjuan();// 案卷信息
		jajuan.setJzid(njm.getId());
		jajuan.setEwm(ajEwm);
		jajuan.setPcdm(njm.getPcdm());
		jajuan.setCreatorId(njm.getCreator_id());
		jajuan.setCreateDate(currDate);
		jajuan.setStatus(Short.parseShort("0"));
		jajuan.setMc(njm.getPcmc());
		JAjjz ajjz = new JAjjz();// ajjz表
		ajjz.setId(StringUtils.getUUID());
		ajjz.setAj_ewm(ajEwm);
		ajjz.setJz_ewm(njm.getEwm());
		njm.setCreate_date(currDate);// 卷宗创建日期
		// 归档信息
		if (!StringUtils.isEmpty(njm.getGdrq4Db())) {
			njm.setGdrq(new SimpleDateFormat("yyyy-MM-dd").parse(njm
					.getGdrq4Db()));
		}
		LJz ljz = new LJz();// 卷宗操作日志
		ljz.setId(StringUtils.getUUID().toLowerCase());
		ljz.setJzewm(njm.getEwm());
		ljz.setOper_date(currDate);
		ljz.setOperation("A");//新增卷宗，操作为“A”
		ljz.setOperator_id(njm.getCreator_id());
		if (jzNewAciton(njm, jajuan, ajjz, ljz)) {
			jrm.setMsgInfo("saveJzSuccess");
		}
		return jrm;
	}

	/**
	 * 卷宗数据入库
	 * 
	 * @param njm
	 * @param jajuan
	 * @param jajjz
	 * @param ljz
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean jzNewAciton(NewJzModel njm, JAjuan jajuan, JAjjz jajjz,
			LJz ljz) {
		int flag = 0;
		if (jajjzDao.insertAjjz(jajjz.getId(), jajjz.getJz_ewm(),
				jajjz.getAj_ewm()) == 1)
			flag++;
		if (jajuanDao.insertNew(jajuan) == 1)
			flag++;
		if (jJzDao.insert4CreateJz(njm) == 1)
			flag++;
		if (ljzDao.insert(ljz) == 1)
			flag++;
		if (flag == 4)
			return true;
		else
			return false;
	}

	/**
	 * 验证卷号的唯一性
	 * 
	 * @param jjz
	 * @param jh
	 * @param jrm
	 * @return
	 */
	public JsonResponseModel checkJzInfo(NewJzModel njm, JsonResponseModel jrm) {
		if (jrm == null || njm == null || StringUtils.isEmpty(njm.getId())
				|| njm.getJh() == null) {
			jrm.setMsgInfo("jhError");
			return jrm;
		}
		List<JJZ> jjList = jJzDao.selectJZbyIdAndJh(njm.getId(), njm.getJh());
		if (jjList == null || jjList.size() == 0) {
			jrm.setMsgInfo("ok");
		} else {
			jrm.setMsgInfo("jhError");
		}
		return jrm;
	}

}
