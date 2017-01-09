package org.lf.jssm.service.raw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.lf.jssm.db.dao.JEnvMapper;
import org.lf.jssm.db.dao.JScanEnvMapper;
import org.lf.jssm.db.pojo.ChuUser;
import org.lf.jssm.db.pojo.JEnv;
import org.lf.jssm.db.pojo.JScanEnv;
import org.lf.jssm.service.model.ScanParameterModel;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 扫描参数配置服务层
 * 
 * @author sunwill
 *
 */
@Service("jScanEnvService")
public class RawScanParameterService {
	/**
	 * 是否提示覆盖,1：提示；2：不提示
	 */
	public static final String SCAN_DEFAULT_COVER = "SCAN_DEFAULT_COVER";
	/**
	 * 旋转角度
	 */
	public static final String SCAN_IDEGREE = "SCAN_IDEGREE";
	/**
	 * 图片在本地的存储路径
	 */
	public static final String BASE_IMG_PATH = "BASE_IMG_PATH";
	/**
	 * 是否预览图片，1：预览;2:不预览
	 */
	public static final String IMG_PREVIEW = "IMG_PREVIEW";

	/**
	 * 扫描模式，1：手动扫描，2：自动扫描
	 */
	public static final String SCANNING_MODEL = "SCANNING_MODEL";
	
	/**
	 * 扫描时间间隔
	 */
	public static final String SCAN_INTERVAL = "SCAN_INTERVAL";

	@Autowired
	private JScanEnvMapper jScanEnvDao;

	@Autowired
	private JEnvMapper JEnvDao;

	/**
	 * 根据用户获得扫描配置参数
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, String> selectByScaner(HttpServletRequest request) {
		ChuUser user = (ChuUser) request.getSession().getAttribute("loginInfo");
		JScanEnv jse = new JScanEnv();
		jse.setScanerId(user.getUserId());
		// 根据用户id查询是否设置过扫描参数
		List<JScanEnv> list = jScanEnvDao.selectByJScanEnv(jse);
		Map<String, String> map = null;
		if (list != null && list.size() > 0) {
			map = new HashMap<String, String>();
			for (JScanEnv j : list) {
				map.put(j.getEnvKey(), j.getEnvValue());
			}
		} else {
			// 系统默认参数配置
			map = getDefaultEnvMap();
		}
		return map;
	}

	/**
	 * 获得默认配置参数，以map形式返回
	 * 
	 * @return
	 */
	public Map<String, String> getDefaultEnvMap() {
		Map<String, String> map = null;
		List<JEnv> jenvList = JEnvDao.selectAllEnv();
		if (jenvList != null && jenvList.size() > 0) {
			map = new HashMap<String, String>();
			for (JEnv j : jenvList) {
				map.put(j.getEnvKey(), j.getEnvValue());
			}
		}
		return map;
	}
	

	/**
	 * 更新扫描配置参数,第一次设置则insert新数据
	 * 
	 * @param request
	 * @param scanPaprm
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateScanPaprm(HttpServletRequest request,
			ScanParameterModel scanPaprm) {
		if (scanPaprm == null) {
			return;
		}
		ChuUser user = (ChuUser) request.getSession().getAttribute("loginInfo");
		scanPaprm.setScanerId(user.getUserId());
		JScanEnv jseId = new JScanEnv();
		jseId.setScanerId(user.getUserId());
		// 根据用户id查询是否设置过扫描参数
		List<JScanEnv> list = jScanEnvDao.selectByJScanEnv(jseId);
		if (list != null && list.size() > 0) {
			// 之前设置过扫描参数，则执行update操作
			JScanEnv jse = new JScanEnv();
			// 设置用户id
			jse.setScanerId(user.getUserId());
			updateJScanEnv(jse, scanPaprm);
		} else {
			// 第一次设置扫描参数，则insert数据
			JScanEnv jse = new JScanEnv();
			// 设置用户id
			jse.setScanerId(user.getUserId());
			insertJScanEnv(jse, scanPaprm);
		}
	}

	/**
	 * 更新扫描参数
	 * 
	 * @param jse
	 * @param scanPaprm
	 */
	@Transactional(rollbackFor=Exception.class)
	private void updateJScanEnv(JScanEnv jse, ScanParameterModel scanPaprm) {
		if (!StringUtils.isEmpty(scanPaprm.getScanDefaultCover())) {
			jse.setEnvKey(SCAN_DEFAULT_COVER);
			jse.setEnvValue(scanPaprm.getScanDefaultCover());
			jScanEnvDao.update(jse);
		}
		if (!StringUtils.isEmpty(scanPaprm.getScanIdegree())) {
			jse.setEnvKey(SCAN_IDEGREE);
			jse.setEnvValue(scanPaprm.getScanIdegree());
			jScanEnvDao.update(jse);
		}
		if (!StringUtils.isEmpty(scanPaprm.getBaseImgPath())) {
			jse.setEnvKey(BASE_IMG_PATH);
			jse.setEnvValue(scanPaprm.getBaseImgPath().replace("\\", "\\\\"));
			jScanEnvDao.update(jse);
		}
		if (!StringUtils.isEmpty(scanPaprm.getImgPreview())) {
			jse.setEnvKey(IMG_PREVIEW);
			jse.setEnvValue(scanPaprm.getImgPreview());
			jScanEnvDao.update(jse);
		}
		if (!StringUtils.isEmpty(scanPaprm.getScanningModel())) {
			jse.setEnvKey(SCANNING_MODEL);
			jse.setEnvValue(scanPaprm.getScanningModel());
			jScanEnvDao.update(jse);
		}
		if (!StringUtils.isEmpty(scanPaprm.getScanInterval())) {
			jse.setEnvKey(SCAN_INTERVAL);
			jse.setEnvValue(scanPaprm.getScanInterval());
			jScanEnvDao.update(jse);
		}
	}

	/**
	 * 插入扫描参数
	 * 
	 * @param jse
	 * @param scanPaprm
	 */
	@Transactional(rollbackFor=Exception.class)
	private void insertJScanEnv(JScanEnv jse, ScanParameterModel scanPaprm) {
		if(!StringUtils.isEmpty(scanPaprm.getBaseImgPath())){
			jse.setEnvKey(BASE_IMG_PATH);
			jse.setEnvValue(scanPaprm.getBaseImgPath().replace("\\", "\\\\"));
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
		}
		if(!StringUtils.isEmpty(scanPaprm.getScanDefaultCover())){
			jse.setEnvKey(SCAN_DEFAULT_COVER);
			jse.setEnvValue(scanPaprm.getScanDefaultCover());
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
		}
		if(!StringUtils.isEmpty(scanPaprm.getScanIdegree())){
			jse.setEnvKey(SCAN_IDEGREE);
			jse.setEnvValue(scanPaprm.getScanIdegree());
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
		}
		if(!StringUtils.isEmpty(scanPaprm.getImgPreview())){
			jse.setEnvKey(IMG_PREVIEW);
			jse.setEnvValue(scanPaprm.getImgPreview());
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
		}
		if(!StringUtils.isEmpty(scanPaprm.getScanningModel())){
			jse.setEnvKey(SCANNING_MODEL);
			jse.setEnvValue(scanPaprm.getScanningModel());
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
		}
		if (!StringUtils.isEmpty(scanPaprm.getScanInterval())) {
			jse.setEnvKey(SCAN_INTERVAL);
			jse.setEnvValue(scanPaprm.getScanInterval());
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
		}
	}

	/**
	 * 重置扫描参数
	 * 
	 * @param request
	 *            成功返回success
	 * @param scanningModel 
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String resetScanPaprm(HttpServletRequest request, String scanningModel) {
		ChuUser user = (ChuUser) request.getSession().getAttribute("loginInfo");
		Map<String, String> map = getDefaultEnvMap();
		if (map == null) {
			return "error";
		}
		JScanEnv jse = new JScanEnv();
		jse.setScanerId(user.getUserId());
		List<JScanEnv> list = jScanEnvDao.selectByJScanEnv(jse);
		if (list != null && list.size() > 0) {
			// 不是第一次设置参数
			if("2".equals(scanningModel)){
				//自动扫描，默认覆盖、不预览
				jse.setEnvKey(SCAN_DEFAULT_COVER);
				jse.setEnvValue("2");
				jScanEnvDao.update(jse);
				jse.setEnvKey(IMG_PREVIEW);
				jse.setEnvValue("2");
				jScanEnvDao.update(jse);
			}else{
				jse.setEnvKey(SCAN_DEFAULT_COVER);
				jse.setEnvValue(map.get(SCAN_DEFAULT_COVER));
				jScanEnvDao.update(jse);
				jse.setEnvKey(IMG_PREVIEW);
				jse.setEnvValue(map.get(IMG_PREVIEW));
				jScanEnvDao.update(jse);
			}
			jse.setEnvKey(SCAN_IDEGREE);
			jse.setEnvValue(map.get(SCAN_IDEGREE));
			jScanEnvDao.update(jse);
			jse.setEnvKey(BASE_IMG_PATH);
			jse.setEnvValue(map.get(BASE_IMG_PATH));
			jScanEnvDao.update(jse);
			jse.setEnvKey(SCAN_INTERVAL);
			jse.setEnvValue(map.get(SCAN_INTERVAL));
			jScanEnvDao.update(jse);
			jse.setEnvKey(SCANNING_MODEL);
			jse.setEnvValue(scanningModel);
			jScanEnvDao.update(jse);
		} else {
			// 之前都没有设置过参数
			if("2".equals(scanningModel)){
				//自动扫描，默认覆盖、不预览
				jse.setEnvKey(SCAN_DEFAULT_COVER);
				jse.setEnvValue("2");
				jse.setId(StringUtils.getUUID());
				jScanEnvDao.insert(jse);
				jse.setEnvKey(IMG_PREVIEW);
				jse.setEnvValue("2");
				jse.setId(StringUtils.getUUID());
				jScanEnvDao.insert(jse);
			}else{
				jse.setEnvKey(SCAN_DEFAULT_COVER);
				jse.setEnvValue(map.get(SCAN_DEFAULT_COVER));
				jse.setId(StringUtils.getUUID());
				jScanEnvDao.insert(jse);
				jse.setEnvKey(IMG_PREVIEW);
				jse.setEnvValue(map.get(IMG_PREVIEW));
				jse.setId(StringUtils.getUUID());
				jScanEnvDao.insert(jse);
			}
			jse.setEnvKey(SCAN_IDEGREE);
			jse.setEnvValue(map.get(SCAN_IDEGREE));
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
			jse.setEnvKey(BASE_IMG_PATH);
			jse.setEnvValue(map.get(BASE_IMG_PATH));
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
			jse.setEnvKey(SCAN_INTERVAL);
			jse.setEnvValue(map.get(SCAN_INTERVAL));
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
			jse.setEnvKey(SCANNING_MODEL);
			jse.setEnvValue(scanningModel);
			jse.setId(StringUtils.getUUID());
			jScanEnvDao.insert(jse);
		}
		return "success";
	}

}