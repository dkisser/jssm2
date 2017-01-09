package org.lf.jssm.service.ajuan;

import java.util.ArrayList;
import java.util.List;

import org.lf.jssm.db.dao.CAjuanMlMapper;
import org.lf.jssm.db.dao.JAjuanDMapper;
import org.lf.jssm.db.dao.JAjuanMapper;
import org.lf.jssm.db.dao.JJzMapper;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.TEwmMapper;
import org.lf.jssm.db.dao.VAJuanDMapper;
import org.lf.jssm.db.dao.VJzMapper;
import org.lf.jssm.db.pojo.CAjuanMl;
import org.lf.jssm.db.pojo.JAjuan;
import org.lf.jssm.db.pojo.JAjuanD;
import org.lf.jssm.db.pojo.JJZ;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.db.pojo.VAJuanD;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.model.JsonResponseModel;
import org.lf.jssm.service.raw.EwmPrintService;
import org.lf.jssm.service.raw.RawPicUploadService;
import org.lf.jssm.service.raw.RawScanService;
import org.lf.jssm.service.sys.JEnvService;
import org.lf.utils.EasyuiComboBoxItem;
import org.lf.utils.EasyuiTree;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 案卷整理服务层
 * 
 * @author sunwill
 *
 */
@Service("ajzlService")
public class AjzlService {
	// private static final Logger logger = LoggerFactory
	// .getLogger(AjzlService.class);
	/**
	 * 图片后缀名
	 */
	private static final String FILE_SUFFIX = ".jpg";
	/**
	 * - 根目录名称
	 * 
	 */
	public static final String ROOT_ML = "根目录";
	@Autowired
	private VAJuanDMapper vAJuandDao;
	@Autowired
	private JEnvService jEnvService;
	@Autowired
	private RawScanService rawScanService;
	@Autowired
	private JAjuanDMapper jAjuanDDao;
	@Autowired
	private JRawfileMapper jRawfileDao;
	@Autowired
	private JAjuanMapper jAjuanDao;
	@Autowired
	private JJzMapper JJzDao;
	@Autowired
	private CAjuanMlMapper cAjMlDao;
	@Autowired
	private VJzMapper vJzDao;
	@Autowired
	private EwmPrintService ewmPrintService;
	@Autowired
	private RawPicUploadService rawPicUploadService;
	@Autowired
	private JZNewService jsNewService;
	@Autowired
	private TEwmMapper tEwmDao;

	/**
	 * 查询案卷主要信息（包括案卷名称和目录名称）
	 * 
	 * @param ajewm
	 * @param mldm
	 * @return
	 */
	public VAJuanD getMajorAjInfo(String ajewm, String mldm) {
		if (StringUtils.isEmpty(ajewm) || StringUtils.isEmpty(mldm)) {
			return null;
		}
		CAjuanMl cajMl = cAjMlDao.getMlInfoByMldm(mldm);
		List<JAjuan> jaList = jAjuanDao.getJajuanByEwm(ajewm);
		if (jaList != null && jaList.size() > 0 && cajMl != null) {
			JAjuan ja = jaList.get(0);
			VAJuanD vad = new VAJuanD();
			vad.setAj_ewm(ajewm);
			vad.setMldm(mldm);
			vad.setMlmc(cajMl.getMc());
			vad.setPcmc(ja.getMc());
			return vad;
		} else {
			return null;
		}
	}

	/**
	 * 初始化案卷整理树
	 * 
	 * @param ajInfo
	 * @param isls
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<EasyuiTree> getAjzlTree(JAjuanD ajInfo, String isls) {
		List<EasyuiTree> treeList = new ArrayList<EasyuiTree>();
		if (ajInfo == null || StringUtils.isEmpty(ajInfo.getAj_ewm())
				|| StringUtils.isEmpty(ajInfo.getMldm())
				|| StringUtils.isEmpty(ajInfo.getZmlid())) {
			return treeList;
		}
		if ("全部".equals(ajInfo.getZmlid())) {
			ajInfo.setZmlid(null);// 标签显示为全部
		}
		if ("true".equals(isls)) {
			ajInfo.setMldm(null);// 历史整理时，显示该案卷下所有图片
		}
		// 子目录为.的是根目录,只有一个根目录
		EasyuiTree treeRoot = new EasyuiTree();
		treeRoot.setText(ROOT_ML);
		treeRoot.setState("closed");
		List<EasyuiTree> rootChildren = null;
		List<JAjuanD> ajdList = jAjuanDDao.getJadsByCondition(ajInfo);
		if (ajdList != null && ajdList.size() > 0) {
			// 存在图片
			rootChildren = new ArrayList<EasyuiTree>();
			for (JAjuanD ajd : ajdList) {
				EasyuiTree treePic = new EasyuiTree();
				// 用图片二维码作为id
				treePic.setId(ajd.getEwm());
				// 获得编号
				String orderNumber = StringUtils.lpad(ajd.getNbbh() + "", '0',
						4);
				String text = ajd.getEwm() + "_" + orderNumber + FILE_SUFFIX;
				if (!StringUtils.isEmpty(ajd.getZml())) {
					text = text + "(" + ajd.getZml() + ")";
				}
				treePic.setText(text);
				treePic.setState("open");
				rootChildren.add(treePic);
			}
		} else {
			treeRoot.setState("open");
			treeRoot.setIconCls("icon-tree-folder");
		}
		treeRoot.setChildren(rootChildren);
		treeList.add(treeRoot);
		return treeList;
	}

	/**
	 * 获得整理图片信息
	 * 
	 * @param currPicEwm
	 * @return
	 */
	public JRawfile getZlImgInfo(String currPicEwm) {
		if (StringUtils.isEmpty(currPicEwm)) {
			return null;
		}
		return jRawfileDao.getRawFileByEwm(currPicEwm);
	}

	/**
	 * 图片存入指定目录
	 * 
	 * @param addPicEwms
	 * @param currNode_id
	 *            当前树节点id，即图片二维码
	 * @param jad2
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String ajzlAddPic(String addPicEwms, String currNode_id, JAjuanD jad2) {
		if (StringUtils.isEmpty(addPicEwms) || jad2 == null
				|| StringUtils.isEmpty(jad2.getAj_ewm())
				|| StringUtils.isEmpty(jad2.getMldm())) {
			return "error";
		}
		String[] ewms = addPicEwms.split(";");
		if (ewms == null || ewms.length == 0) {
			return "error";
		}
		if (StringUtils.isEmpty(currNode_id)) {
			// 根目录添加图片
			for (String ewm : ewms) {
				if (StringUtils.isEmpty(ewm)) {
					continue;
				}
				if (!"success".equals(ajzlCheckEwm(ewm))) {
					// 检查不通过
					continue;
				}
				Integer maxNbbh = jAjuanDDao.getPicMaxNbbh(jad2);
				if (maxNbbh == null) {
					maxNbbh = 0;
				}
				// 内部编号从1开始，每次加1
				jad2.setNbbh(maxNbbh + 1);
				jad2.setEwm(ewm);
				jAjuanDDao.insert(jad2);
			}
		} else {
			// 在当前图片下方添加图片
			jad2.setEwm(currNode_id);
			// 当前图片相关信息
			JAjuanD jad = jAjuanDDao.getJadInfo(jad2);
			if (jad == null) {
				return "error";
			}
			// 获取当前文件后面的文件信息
			List<JAjuanD> behindList = jAjuanDDao.getBehindJad(jad);
			Integer currNbbh = jad.getNbbh();
			if (currNbbh == null) {
				currNbbh = 0;
			}
			// 先插入新数据
			for (String ewm : ewms) {
				if (StringUtils.isEmpty(ewm)) {
					continue;
				}
				if (!"success".equals(ajzlCheckEwm(ewm))) {
					// 检查不通过
					continue;
				}
				// 内部编号从1开始，每次加1
				currNbbh++;
				jad2.setNbbh(currNbbh);
				jad2.setEwm(ewm);
				jAjuanDDao.insert(jad2);
			}
			// 更新后面数据的内部编号
			if (behindList != null && behindList.size() > 0) {
				// 插入新数据后的最大编号
				Integer maxNbbh = jad2.getNbbh();
				if (maxNbbh != null) {
					for (JAjuanD behindJad : behindList) {
						maxNbbh++;
						behindJad.setNbbh(maxNbbh);
						jAjuanDDao.update(behindJad);
					}
				}
			}
		}
		return "success";
	}

	/**
	 * 判断整型数字是否有效
	 * 
	 * @param num
	 * @return 有效返回true
	 */
	private boolean validInt(Integer num) {
		return num != null && num > 0;
	}

	/**
	 * 检查单个二维码的有效性
	 * 
	 * @param ewm
	 * @return
	 */
	public String ajzlCheckEwm(String ewm) {
		if (StringUtils.isEmpty(ewm)) {
			return "empty";
		}
		// 检查二维码是否存在
		Integer count = jRawfileDao.getRawFileCountByEwm(ewm);
		if (!validInt(count)) {
			return "notExsit";
		}
		if (validInt(jAjuanDDao.getCountsByPicEwm(ewm))) {
			return "beUsed";// 二维码被使用
		}
		if (validInt(jAjuanDao.getCountByEwm(ewm))) {
			return "beUsed";// 二维码被使用
		}
		if (validInt(JJzDao.getCountByEwm(ewm))) {
			return "beUsed";// 二维码被使用
		}
		return "success";
	}

	/**
	 * 删除指定的图片
	 * 
	 * @param nodeIds
	 * @param isls
	 * @param currJzewm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String ajzlDeletePic(String nodeIds, String isls) {
		if (StringUtils.isEmpty(nodeIds)) {
			return "error";
		}
		String[] ewms = nodeIds.split(";");
		if (ewms == null || ewms.length == 0) {
			return "error";
		}
		String ftpDir = jEnvService.getFtpDir();
		for (String ewm : ewms) {
			if (StringUtils.isEmpty(ewm)) {
				continue;
			}
			// 其后的每条记录的内部编号减1
			JAjuanD jad = new JAjuanD();
			jad.setEwm(ewm);
			jad = jAjuanDDao.getJadInfo(jad);
			List<JAjuanD> jadList = jAjuanDDao.getBehindJad(jad);
			updateJadDecrease(jadList, 1);
			// 删除jAjuanD数据
			jAjuanDDao.deleteByEwm(ewm);
			if ("true".equals(isls)) {
				//历史整理,删除图片源文件
				rawScanService.deleteRawFileByEwm(ewm, ftpDir);// 删除文件以及相关j_rawfile数据库信息
			}
		}
		return "success";
	}

	/**
	 * 图片拖动保存
	 * 
	 * @param sourceId
	 * @param targetId
	 * @param point
	 * @param isls
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String ajzlUpdatePicOrder(String sourceId, String targetId,
			String point, String isls) {
		if (StringUtils.isEmpty(sourceId) || StringUtils.isEmpty(targetId)
				|| StringUtils.isEmpty(point)) {
			return "error";
		}
		// 只允许上下拖动,不允许加入某个节点
		if ("append".equals(point)) {
			return "error";
		}
		JAjuanD targetJad = new JAjuanD();
		targetJad.setEwm(targetId);
		targetJad = jAjuanDDao.getJadInfo(targetJad);
		JAjuanD sourceJad = new JAjuanD();
		sourceJad.setEwm(sourceId);
		sourceJad = jAjuanDDao.getJadInfo(sourceJad);
		// 获取当前文件后面的文件信息
		if (targetJad == null || sourceId == null) {
			return "error";
		}
		if ("true".equals(isls)) {
			targetJad.setMldm(null);
			sourceJad.setMldm(null);
		}
		Integer targetNbbh = targetJad.getNbbh();
		Integer sourceNbbh = sourceJad.getNbbh();
		if (targetNbbh < sourceNbbh) {
			// 上移
			if ("top".equals(point)) {
				Integer sourceNewNbbh = targetNbbh;
				Integer startNbbh = targetNbbh - 1;
				Integer endNbbh = sourceNbbh;
				update4DragUp(sourceJad, sourceNewNbbh, startNbbh, endNbbh);
			} else {
				Integer sourceNewNbbh = targetNbbh + 1;
				Integer startNbbh = targetNbbh;
				Integer endNbbh = sourceNbbh;
				update4DragUp(sourceJad, sourceNewNbbh, startNbbh, endNbbh);
			}
		} else {
			// 下移
			if ("top".equals(point)) {
				Integer sourceNewNbbh = targetNbbh - 1;
				Integer startNbbh = sourceNbbh;
				Integer endNbbh = targetNbbh;
				update4DragDown(sourceJad, sourceNewNbbh, startNbbh, endNbbh);
			} else {
				Integer sourceNewNbbh = targetNbbh;
				Integer startNbbh = sourceNbbh;
				Integer endNbbh = targetNbbh + 1;
				update4DragDown(sourceJad, sourceNewNbbh, startNbbh, endNbbh);
			}
		}
		return "success";
	}

	/**
	 * 向上拖动更新图片编号
	 * 
	 * @param sourceJad
	 * @param sourceNewNbbh
	 * @param startNbbh
	 * @param endNbbh
	 */
	@Transactional(rollbackFor = Exception.class)
	private void update4DragUp(JAjuanD sourceJad, Integer sourceNewNbbh,
			Integer startNbbh, Integer endNbbh) {
		// 先批量更新
		sourceJad.setStartNbbh(startNbbh);
		sourceJad.setEndNbbh(endNbbh);
		List<JAjuanD> updateList = jAjuanDDao.getJadBetweenAnd(sourceJad);
		updateJadIncrease(updateList, 1);
		// 更新源数据
		sourceJad.setNbbh(sourceNewNbbh);
		jAjuanDDao.update(sourceJad);
	}

	/**
	 * 向下拖动更新图片编号
	 * 
	 * @param sourceJad
	 * @param sourceNewNbbh
	 * @param startNbbh
	 * @param endNbbh
	 */
	@Transactional(rollbackFor = Exception.class)
	private void update4DragDown(JAjuanD sourceJad, Integer sourceNewNbbh,
			Integer startNbbh, Integer endNbbh) {
		// 先批量更新
		sourceJad.setStartNbbh(startNbbh);
		sourceJad.setEndNbbh(endNbbh);
		List<JAjuanD> updateList = jAjuanDDao.getJadBetweenAnd(sourceJad);
		updateJadDecrease(updateList, 1);
		// 更新源数据
		sourceJad.setNbbh(sourceNewNbbh);
		jAjuanDDao.update(sourceJad);
	}

	/**
	 * 将集合中的每条记录的内部编号减去指定数值
	 * 
	 * @param updateList
	 * @param count
	 */
	@Transactional(rollbackFor = Exception.class)
	private void updateJadDecrease(List<JAjuanD> updateList, int count) {
		if (updateList == null || updateList.size() == 0) {
			return;
		}
		for (JAjuanD jad : updateList) {
			jad.setNbbh(jad.getNbbh() - count);
			jAjuanDDao.update(jad);
		}
	}

	/**
	 * 将集合中的每条记录的内部编号加上指定数值
	 * 
	 * @param updateList
	 * @param count
	 */
	@Transactional(rollbackFor = Exception.class)
	private void updateJadIncrease(List<JAjuanD> updateList, int count) {
		if (updateList == null || updateList.size() == 0) {
			return;
		}
		for (JAjuanD jad : updateList) {
			jad.setNbbh(jad.getNbbh() + count);
			jAjuanDDao.update(jad);
		}
	}

	/**
	 * 初始化标签下拉框
	 * 
	 * @param jad
	 * @param combo4Query
	 * @return
	 */
	public List<EasyuiComboBoxItem<String, String>> ajzlGetAllLab(JAjuanD jad,
			String combo4Query) {
		List<EasyuiComboBoxItem<String, String>> cbList = new ArrayList<EasyuiComboBoxItem<String, String>>();
		if (jad == null || StringUtils.isEmpty(jad.getAj_ewm())) {
			return cbList;
		}
		// 筛选窗口中的标签下拉框
		if ("1".equals(combo4Query)) {
			EasyuiComboBoxItem<String, String> first = new EasyuiComboBoxItem<String, String>();
			first.setId("全部");
			first.setText("全部");
			first.setSelected(true);
			cbList.add(first);
		}
		List<JAjuanD> jadList = jAjuanDDao.getAllLabsInfo(jad);
		if (jadList != null && jadList.size() > 0 && jadList.get(0) != null) {
			for (JAjuanD j : jadList) {
				if (!StringUtils.isEmpty(j.getZml())
						&& !StringUtils.isEmpty(j.getZmlid())) {
					EasyuiComboBoxItem<String, String> ecb = new EasyuiComboBoxItem<String, String>();
					ecb.setId(j.getZmlid());
					ecb.setText(j.getZml());
					cbList.add(ecb);
				}
			}
		}
		return cbList;
	}

	/**
	 * 为节点添加标签
	 * 
	 * @param nodeIds
	 * @param jad
	 * @return
	 */
	public String ajzlSavePicLab(String nodeIds, JAjuanD jad) {
		if (StringUtils.isEmpty(nodeIds) || jad == null
				|| StringUtils.isEmpty(jad.getAj_ewm())
				|| StringUtils.isEmpty(jad.getZml())) {
			return "error";
		}
		String[] ewms = nodeIds.split(";");
		if (ewms == null || ewms.length == 0) {
			return "error";
		}
		// 根据ajewm和zml查询相关信息
		List<JAjuanD> jads = jAjuanDDao.getJadsByCondition(jad);
		// 保证同一个标签的id相同
		if (jads != null && jads.size() > 0) {
			jad.setZmlid(jads.get(0).getZmlid());
		} else {
			jad.setZmlid(StringUtils.getUUID());
		}
		for (String ewm : ewms) {
			if (StringUtils.isEmpty(ewm)) {
				continue;
			}
			jad.setEwm(ewm);
			jAjuanDDao.update(jad);
		}
		return "success";
	}

	/**
	 * 删除所有指定图片标签
	 * 
	 * @param nodeIds
	 * @return
	 */
	public String ajzlDelPicLabel(String nodeIds) {
		if (StringUtils.isEmpty(nodeIds)) {
			return "error";
		}
		String[] ewms = nodeIds.split(";");
		if (ewms == null || ewms.length == 0) {
			return "error";
		}
		for (String ewm : ewms) {
			if (StringUtils.isEmpty(ewm)) {
				continue;
			}
			JAjuanD jad = new JAjuanD();
			jad.setEwm(ewm);
			jAjuanDDao.setZml2Null(jad);
		}
		return "success";
	}

	/**
	 * 图片存入指定目录
	 * 
	 * @param addPicEwms
	 * @param currNode_id
	 *            当前树节点id，即图片二维码
	 * @param jad2
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String lsajzlAddPic(String addPicEwms, String currNode_id,
			JAjuanD jad2) {
		if (StringUtils.isEmpty(currNode_id)) { // 如果是根目录
			List<CAjuanMl> mllist = cAjMlDao.selectmlByPcdm(jad2.getMldm());
			jad2.setMldm(mllist.get(0).getDm()); // 获取第一个子目录的代码
		} else {
			JAjuanD jad3 = new JAjuanD();
			jad3.setEwm(currNode_id);
			jad3 = jAjuanDDao.getJadsByCondition(jad3).get(0);
			jad2.setMldm(jad3.getMldm());
		}
		return ajzlAddPic(addPicEwms, currNode_id, jad2);
	}

	/**
	 * 快速整理封面二维码检查
	 * 
	 * @param jzewm
	 * @return
	 */
	public JsonResponseModel checkEwm4KsWizard(String jzewm) {
		JsonResponseModel jri = new JsonResponseModel();
		if (StringUtils.isEmpty(jzewm)) {
			jri.setMsgInfo("isEmpty");
			return jri;
		}
		VJz cjz = vJzDao.selectJzByEwm(jzewm);
		if (cjz == null) {
			jri.setMsgInfo("newJz");// 新卷宗
			return jri;
		}
		Integer status = cjz.getStatus();
		if (status == 1) {
			jri.setMsgInfo("ygd");// 已归档
		} else {
			jri.setMsgInfo("wgd");// 未归档
		}
		return jri;
	}

	/**
	 * 快速整理，上传图片
	 * 
	 * @param file_upload
	 * @param userIdNo2
	 * @param jzewm
	 * @param pcdm
	 * @param jri2
	 * @param jh
	 * @param qxdm
	 * @param jbdm
	 * @param gdhs
	 * @param gdrq
	 * @param yjfl
	 * @param jjz
	 * @param currJzType
	 * @param syym
	 * @return
	 * @throws Exception
	 * 
	 *             public JsonResponseModel ksPicUpload(MultipartFile[]
	 *             file_upload, Integer userIdNo, String jzewm, JJZ jjz, String
	 *             yjfl, String gdrq, Integer gdhs, String qxdm, Integer jh,
	 *             JsonResponseModel jri, String pcdm, String currJzType,
	 *             Integer syym) throws Exception { if
	 *             (StringUtils.isEmpty(jzewm) || userIdNo == null) { return
	 *             jri; } if (file_upload == null || file_upload.length == 0) {
	 *             return jri; } jri = checkJzInfo(jjz, jh, jri);// 验证卷号 if (jri
	 *             == null || !"ok".equals(jri.getMsgInfo())) { return jri; }
	 *             Arrays.sort(file_upload, new Comparator4File());// 根据文件名称排序
	 *             Date currDate = new Date(); // Integer lastId =
	 *             ewmPrintService.getLastId(currDate); TEwm tem =
	 *             tEwmDao.selectByDate(currDate); Integer lastId; if (tem ==
	 *             null || tem.getLastId() == null) { lastId = -1;//
	 *             新序号比当前最大序号大一 } else { lastId = tem.getLastId(); } if (lastId
	 *             >= EwmPrintService.MAX_EWN_NUMBER) { return jri; } JEnv jev =
	 *             ewmPrintService.gerFydmInfo();// 获得法院代码 if (jev == null ||
	 *             StringUtils.isEmpty(jev.getEnvValue())) { return jri; }
	 *             String uploadErrorFiles = ""; int fmNum = 0; int mlNum = 0;
	 *             String ajEwm = null; String addPicEwms = ""; String
	 *             currDateStr = new
	 *             SimpleDateFormat("yyyyMMdd").format(currDate); for
	 *             (MultipartFile multiFile : file_upload) { if (multiFile ==
	 *             null || multiFile.isEmpty()) { break; } String fileName =
	 *             multiFile.getOriginalFilename(); if
	 *             (fileName.split("\\.").length != 2) { uploadErrorFiles +=
	 *             (fileName + ";");// 文件名称中有且只能有一个. continue; } lastId++;//
	 *             二维码序号加1 String ewm =
	 *             ewmPrintService.generateEwm(fileName.split("\\.")[0], jzewm,
	 *             lastId, currDateStr, jev.getEnvValue()); if
	 *             (StringUtils.isEmpty(ewm)) { uploadErrorFiles += (fileName +
	 *             ";");// 文件名称中只能有一个. continue; } if
	 *             (ewm.endsWith(EwmDecode.EWM_TYPE_ML)) { mlNum++; if (mlNum >
	 *             1) { jri.setMsgInfo("mlCountError"); return jri;// 多张目录图片 }
	 *             ajEwm = ewm; } if (ewm.endsWith(EwmDecode.EWM_TYPE_FM)) {
	 *             fmNum++; if (fmNum > 1) { jri.setMsgInfo("fmCountError");
	 *             return jri;// 多张封面图片 } jzewm = ewm; jjz.setEwm(jzewm);//
	 *             将封面二维码插入数据库 } // 将存储信息保存至数据库 JRawfile jf = getZlImgInfo(ewm);
	 *             Integer rawFileVersion = null; if (jf != null) {// 存在则删除原来的文件
	 *             rawFileVersion = jf.getFileVersion();// 取出原先的文件版本号，数据库默认为1 if
	 *             (rawFileVersion == 999) { rawFileVersion = 1;//
	 *             版本号最大为999，到999归1 } rawScanService.deleteRawFile(jf,
	 *             jEnvService.getFtpDir());// 若二维码文件已存在，则删除文件信息 } try { jf =
	 *             rawPicUploadService.saveRawFile(multiFile, userIdNo,
	 *             rawFileVersion, ewm); } catch (Exception e) {
	 *             uploadErrorFiles += (fileName + ";"); logger.error("保存" +
	 *             fileName + "文件到磁盘上出错，或者保存文件信息到数据库出错", e); continue; } //
	 *             将文件存储到ftp目录,并将文件信息存入数据库 if (jf == null || jf.getEwm() ==
	 *             null) { uploadErrorFiles += (fileName + ";"); continue; } if
	 *             (ewm.endsWith(EwmDecode.EWM_TYPE_NR)) { addPicEwms += (ewm +
	 *             ";"); } } if (tem == null) { // 当天存不在记录则插入新数据 tem = new
	 *             TEwm(); tem.setId(StringUtils.getUUID());
	 *             tem.setCreateDate(currDate); tem.setLastId(lastId);
	 *             tEwmDao.insert(tem); } else { // 当天存在记录则更新
	 *             tem.setCreateDate(currDate); tem.setLastId(lastId);
	 *             tEwmDao.updateById(tem); } if (fmNum != 1) {
	 *             jri.setMsgInfo("fmCountError"); return jri;// 必须有且仅有一张封面图片 }
	 *             if (mlNum != 1) { jri.setMsgInfo("mlCountError"); return
	 *             jri;// 必须有且仅有一张目录图片 } jri = saveJzInfo(userIdNo, jzewm,
	 *             ajEwm, jjz, yjfl, gdrq, gdhs, qxdm, jh, jri, pcdm,
	 *             currJzType, currDate, syym); if
	 *             (!"saveJzSuccess".equals(jri.getMsgInfo())) {
	 *             jri.setMsgInfo("saveJzError"); return jri;// 保存卷宗信息失败 } //
	 *             批量整理 if (!StringUtils.isEmpty(addPicEwms)) { addPicEwms =
	 *             addPicEwms.substring(0, addPicEwms.length() - 1); JAjuanD
	 *             jad2 = new JAjuanD(); jad2.setAj_ewm(ajEwm);
	 *             jad2.setMldm(pcdm);// 批量添加，通过批次代码，查询最后一个目录 if
	 *             ("success".equals(lsajzlAddPic(addPicEwms, null, jad2))) {
	 *             jri.setSuccessMsgInfo("newJzSuccess"); } } if
	 *             (!StringUtils.isEmpty(uploadErrorFiles)) { // 添加上传失败的文件名称
	 *             jri.setMsgInfo(uploadErrorFiles); } return jri; }
	 */

	/**
	 * 将卷宗信息存入数据库
	 * 
	 * @param userIdNo
	 * @param jzewm
	 * @param ajEwm
	 * @param jjz
	 * @param yjfl
	 * @param gdrq
	 * @param gdhs
	 * @param jbdm
	 * @param qxdm
	 * @param jh
	 * @param jri
	 * @param pcdm
	 * @param currJzType
	 * @param currDate
	 * @param syym
	 * @return
	 * @throws Exception
	 * @Transactional(rollbackFor = Exception.class) private JsonResponseModel
	 *                            saveJzInfo(Integer userIdNo, String jzewm,
	 *                            String ajEwm, JJZ jjz, String yjfl, String
	 *                            gdrq, Integer gdhs, String qxdm, Integer jh,
	 *                            JsonResponseModel jri, String pcdm, String
	 *                            currJzType, Date currDate, Integer syym)
	 *                            throws Exception { JAjuan jajuan = new
	 *                            JAjuan(); JAjjz ajjz = new JAjjz();
	 *                            jajuan.setJzid(jjz.getId());
	 *                            jajuan.setEwm(ajEwm); jajuan.setPcdm(pcdm); //
	 *                            VMlpc mlpc = jsNewService.getMlpcByPCDM(pcdm);
	 *                            // jajuan.setMc(mlpc.getPcmc());
	 *                            jajuan.setCreatorId(userIdNo);
	 *                            jajuan.setCreateDate(currDate);
	 *                            jajuan.setStatus(Short.parseShort("0"));
	 *                            ajjz.setId
	 *                            (StringUtils.getShortUUID().toLowerCase());
	 *                            ajjz.setAj_ewm(ajEwm); ajjz.setJz_ewm(jzewm);
	 *                            jjz.setCreate_date(currDate);
	 *                            jjz.setCreator_id(userIdNo);
	 *                            jjz.setSyym(syym);// 添加首页页码 // 归档信息
	 *                            jjz.setGdhs(gdhs); if
	 *                            (!StringUtils.isEmpty(gdrq)) { jjz.setGdrq(new
	 *                            SimpleDateFormat("yyyy-MM-dd").parse(gdrq)); }
	 *                            jjz.setYjfl(yjfl); jjz.setQxdm(qxdm);
	 *                            jjz.setJh(jh); jjz.setStatus(0);
	 *                            jjz.setTybz("0");// 停用标识
	 *                            jjz.setZlfs(currJzType);// 存储当前卷宗处理方式（历史或即时）
	 *                            if (jsNewService.jzNewAciton(jjz, jajuan,
	 *                            ajjz)) { jri.setMsgInfo("saveJzSuccess"); }
	 *                            return jri; }
	 */

	/**
	 * 验证卷号是否重复
	 * 
	 * @param jjz
	 * @param jh
	 * @param jri
	 * @return
	 */
	public JsonResponseModel checkJzInfo(JJZ jjz, Integer jh,
			JsonResponseModel jri) {
		if (jri == null || jjz == null || StringUtils.isEmpty(jjz.getId())
				|| jh == null) {
			return jri;
		}
		List<JJZ> jjList = JJzDao.selectJZbyIdAndJh(jjz.getId(), jh);
		if (jjList == null || jjList.size() == 0) {
			jri.setMsgInfo("ok");
		} else {
			jri.setMsgInfo("jhError");
		}
		return jri;
	}

	/**
	 * 根据卷宗二维码删除所有相关图片文件及数据库信息
	 * 
	 * @param jzewm
	 * @return
	 */
	public boolean deleteAllPicByJzewm(String jzewm) {
		if (StringUtils.isEmpty(jzewm)) {
			return false;
		}
		// 删除图片文件及数据库信息
		String ftpDir = jEnvService.getFtpDir();
		if (!StringUtils.isEmpty(ftpDir)) {
			List<VAJuanD> vadList = vAJuandDao.getVAjuanDListByJzEwm(jzewm);
			if (vadList != null && vadList.size() > 0) {
				rawScanService.deleteRawFileByEwm(jzewm, ftpDir);// 删除卷宗二维码
				rawScanService.deleteRawFileByEwm(vadList.get(0).getAj_ewm(),
						ftpDir);// 删除案卷二维码
				for (VAJuanD vad : vadList) {
					rawScanService.deleteRawFileByEwm(vad.getPic_ewm(), ftpDir);// 删除内容二维码
				}
			}
			return true;
		}
		return false;
	}

}
