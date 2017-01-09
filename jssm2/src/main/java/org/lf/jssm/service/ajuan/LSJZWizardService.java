package org.lf.jssm.service.ajuan;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.lf.jssm.db.dao.CAjuanMlMapper;
import org.lf.jssm.db.dao.JAjjzMapper;
import org.lf.jssm.db.dao.JAjuanDMapper;
import org.lf.jssm.db.dao.JAjuanMapper;
import org.lf.jssm.db.dao.JJzMapper;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.TEwmMapper;
import org.lf.jssm.db.dao.VAJuanDMapper;
import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.db.pojo.CAQJB;
import org.lf.jssm.db.pojo.CSPCX;
import org.lf.jssm.db.pojo.JAjuanD;
import org.lf.jssm.db.pojo.JEnv;
import org.lf.jssm.db.pojo.JJZ;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.db.pojo.TEwm;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.model.JsonResponseModel;
import org.lf.jssm.service.model.NewJzModel;
import org.lf.jssm.service.raw.EwmDecode;
import org.lf.jssm.service.raw.EwmPrintService;
import org.lf.jssm.service.raw.RawScanService;
import org.lf.jssm.service.sys.JEnvService;
import org.lf.utils.Comparator4File;
import org.lf.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LSJZWizardService {
	private static final Logger logger = LoggerFactory
			.getLogger(LSJZWizardService.class);
	@Autowired
	private AjzlService ajzlService;
	@Autowired
	private RawScanService rawScanService;
	@Autowired
	private JEnvService jEnvService;
	@Autowired
	private JzWizardService jzWizardService;
	@Autowired
	private EwmPrintService ewmPrintService;
	@Autowired
	private JRawfileMapper jRawFileDao;
	@Autowired
	private VAJuanDMapper vAJuandDao;
	@Autowired
	private VJzMapper vjzDao;
	@Autowired
	private JJzMapper jJzDao;
	@Autowired
	private JAjjzMapper jajjzDao;
	@Autowired
	private JAjuanMapper jajuanDao;
	@Autowired
	private TEwmMapper tEwmDao;
	@Autowired
	private JAjuanDMapper jAjuanDDao;
	@Autowired
	private CAjuanMlMapper cAjMlDao;

	public String findfmEwmstatus(String jz_ewm) {
		if (jRawFileDao.getRawFileByEwm(jz_ewm) == null) {
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
	 * 根据封面二维码，查询J_JZ视图
	 */
	public JJZ findJzByEwm(String jzewm) {
		return jJzDao.selectJJZByEwm(jzewm);
	}

	/**
	 * 根据封面二维码，查询V_JZ视图
	 * 
	 * @param jzewm
	 * @return
	 */
	public VJz getJZInfo(String jzewm) {
		return vjzDao.selectJzByEwm(jzewm);
	}

	/**
	 * 创建卷宗
	 * 
	 * @param txspjg1
	 * @param txspjg2
	 * @param njm
	 * @param file_upload
	 * @param jrm
	 * @return
	 * @throws Exception
	 */
	public JsonResponseModel createLsJz(String txspjg1, String txspjg2,
			NewJzModel njm, MultipartFile[] file_upload, JsonResponseModel jrm)
			throws Exception {
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
		return saveLsJzInfo(njm, file_upload, jrm);
	}

	/**
	 * 保存历史卷宗信息
	 * 
	 * @param njm
	 * @param file_upload
	 * @param jrm
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonResponseModel saveLsJzInfo(NewJzModel njm,
			MultipartFile[] file_upload, JsonResponseModel jrm)
			throws Exception {
		if (njm == null || StringUtils.isEmpty(njm.getEwm())
				|| njm.getCreator_id() == null) {
			return jrm;
		}
		if (file_upload == null || file_upload.length == 0) {
			return jrm;
		}
		jrm = jzWizardService.checkJzInfo(njm, jrm);// 验证卷号
		if (jrm == null || !"ok".equals(jrm.getMsgInfo())) {
			return jrm;
		}
		Arrays.sort(file_upload, new Comparator4File());// 根据文件名称排序
		Date currDate = new Date();
		TEwm tem = tEwmDao.selectByDate(currDate);
		Integer lastId;
		if (tem == null || tem.getLastId() == null) {
			lastId = -1;// 新序号比当前最大序号大一
		} else {
			lastId = tem.getLastId();
		}
		if (lastId >= EwmPrintService.MAX_EWN_NUMBER) {
			return jrm;
		}
		JEnv jev = ewmPrintService.gerFydmInfo();// 获得法院代码
		if (jev == null || StringUtils.isEmpty(jev.getEnvValue())) {
			return jrm;
		}
		String uploadErrorFiles = "";
		int fmNum = 0;
		int mlNum = 0;
		String ajEwm = null;
		String addPicEwms = "";
		String currDateStr = new SimpleDateFormat("yyyyMMdd").format(currDate);
		String jzewm = njm.getEwm();
		for (MultipartFile multiFile : file_upload) {
			if (multiFile == null || multiFile.isEmpty()) {
				break;
			}
			String fileName = multiFile.getOriginalFilename();
			if (fileName.split("\\.").length != 2) {
				uploadErrorFiles += (fileName + ";");// 文件名称中有且只能有一个.
				continue;
			}
			lastId++;// 二维码序号加1
			String ewm = ewmPrintService.generateEwm(fileName.split("\\.")[0],
					jzewm, lastId, currDateStr, jev.getEnvValue());
			if (StringUtils.isEmpty(ewm)) {
				uploadErrorFiles += (fileName + ";");// 文件名称中只能有一个.
				continue;
			}
			if (ewm.endsWith(EwmDecode.EWM_TYPE_ML)) {
				mlNum++;
				if (mlNum > 1) {
					jrm.setMsgInfo("mlCountError");
					return jrm;// 多张目录图片
				}
				ajEwm = ewm;
			}
			if (ewm.endsWith(EwmDecode.EWM_TYPE_FM)) {
				fmNum++;
				if (fmNum > 1) {
					jrm.setMsgInfo("fmCountError");
					return jrm;// 多张封面图片
				}
				jzewm = ewm;
				// njm.setEwm(jzewm);// 将封面二维码插入数据库
			}
			// 将存储信息保存至数据库
			JRawfile jf = jRawFileDao.getRawFileByEwm(ewm);
			Integer rawFileVersion = null;
			if (jf != null) {// 存在则删除原来的文件
				rawFileVersion = jf.getFileVersion();// 取出原先的文件版本号，数据库默认为1
				if (rawFileVersion == 999) {
					rawFileVersion = 1;// 版本号最大为999，到999归1
				}
				rawScanService.deleteRawFile(jf, jEnvService.getFtpDir());// 若二维码文件已存在，则删除文件信息
			}
			try {
				jf = rawScanService.saveRawFile(multiFile,
						njm.getCreator_id(), rawFileVersion, ewm);
			} catch (Exception e) {
				uploadErrorFiles += (fileName + ";");
				logger.error("保存" + fileName + "文件到磁盘上出错，或者保存文件信息到数据库出错", e);
				continue;
			}
			// 将文件存储到ftp目录,并将文件信息存入数据库
			if (jf == null || jf.getEwm() == null) {
				uploadErrorFiles += (fileName + ";");
				continue;
			}
			if (ewm.endsWith(EwmDecode.EWM_TYPE_NR)) {
				addPicEwms += (ewm + ";");
			}
		}
		if (tem == null) {
			// 当天存不在记录则插入新数据
			tem = new TEwm();
			tem.setId(StringUtils.getUUID());
			tem.setCreateDate(currDate);
			tem.setLastId(lastId);
			tEwmDao.insert(tem);
		} else {
			// 当天存在记录则更新
			tem.setCreateDate(currDate);
			tem.setLastId(lastId);
			tEwmDao.updateById(tem);
		}
		if (fmNum != 1) {
			jrm.setMsgInfo("fmCountError");
			return jrm;// 必须有且仅有一张封面图片
		}
		if (mlNum != 1) {
			jrm.setMsgInfo("mlCountError");
			return jrm;// 必须有且仅有一张目录图片
		}
		jrm = jzWizardService.saveJzInfo(ajEwm, njm, jrm, currDate);// 储存卷宗信息
		if (!"saveJzSuccess".equals(jrm.getMsgInfo())) {
			jrm.setMsgInfo("saveJzError");
			return jrm;// 保存卷宗信息失败
		}
		// 批量整理
		if (!StringUtils.isEmpty(addPicEwms)) {
			addPicEwms = addPicEwms.substring(0, addPicEwms.length() - 1);
			JAjuanD jad2 = new JAjuanD();
			jad2.setAj_ewm(ajEwm);
			jad2.setMldm(njm.getPcdm());// 批量添加，通过批次代码，查询最后一个目录
			if ("success".equals(ajzlService.lsajzlAddPic(addPicEwms, null, jad2))) {
				jrm.setSuccessMsgInfo("newJzSuccess");
			}
		}
		if (!StringUtils.isEmpty(uploadErrorFiles)) {
			// 添加上传失败的文件名称
			jrm.setMsgInfo(uploadErrorFiles);
		}
		return jrm;
	}

}
