package org.lf.jssm.service.raw;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.TEwmMapper;
import org.lf.jssm.db.pojo.JAjuanD;
import org.lf.jssm.db.pojo.JEnv;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.db.pojo.TEwm;
import org.lf.jssm.service.ajuan.AjzlService;
import org.lf.jssm.service.model.JsonResponseModel;
import org.lf.jssm.service.sys.JEnvService;
import org.lf.utils.Comparator4File;
import org.lf.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 原始图片批量上传
 * 
 * @author sunwill
 *
 */
@Service("rawPicUploadService")
public class RawPicUploadService {
	private static final Logger logger = LoggerFactory
			.getLogger(RawPicUploadService.class);
	// public static final String FILE_NAME_SEPARATOR = "_EWM_";
	
	@Autowired
	private JRawfileMapper jRawFileDao;
	@Autowired
	private JEnvService jEnvService;
	@Autowired
	private AjzlService ajzlService;
	@Autowired
	private TEwmMapper tEwmDao;
	@Autowired
	private EwmPrintService ewmPrintService;
	@Autowired
	private JRawfileMapper jRawfileDao;
	@Autowired
	private RawScanService rawScanService;

	/**
	 * 即时扫描上传图片，存入磁盘和数据库（文件名为二维码）
	 * 
	 * @param file_upload
	 * @param userId
	 * @return
	 */
	public JsonResponseModel rawPicUpload(MultipartFile[] file_upload,
			String userId) {
		int userIdNo = Integer.parseInt(userId);
		if (file_upload == null || file_upload.length == 0) {
			return null;
		}
		// Arrays.sort(file_upload, new Comparator4File());// 即时扫描上传不排序
		String uploadErrorFiles = "";
		EwmDecode decode = new EwmDecode();
		String successEwms = "";
		for (MultipartFile multiFile : file_upload) {
			if (multiFile == null || multiFile.isEmpty()) {
				break;
			}
			String fileName = multiFile.getOriginalFilename();
			String ewm = rawScanService.getEwmByOriginalFilename(fileName);
			if (!decode.isValidEwm(ewm, null)) {
				// 文件名不是有效二维码,则解析图片中的二维码
				InputStream is = null;
				try {
					is = multiFile.getInputStream();
					ewm = decode.readQRCodeResult(is).getText();
				} catch (Exception e) {
					uploadErrorFiles += (fileName + ";");
					logger.error("解析" + fileName + "文件二维码出错", e);
					continue;
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							logger.error("关闭" + fileName + "文件流出错", e);
						}
					}
				}
			}
			// 将存储信息保存至数据库
			// 查询已存在的文件信息
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
				jf = rawScanService.saveRawFile(multiFile, userIdNo, rawFileVersion, ewm);
			} catch (Exception e) {
				uploadErrorFiles += (fileName + ";");
				logger.error("保存" + fileName + "文件到磁盘上出错，或者保存文件信息到数据库出错", e);
				continue;
			}
			// 将文件存储到ftp目录,并将文件信息存入数据库
			if (jf == null || jf.getEwm() == null) {
				uploadErrorFiles += (fileName + ";");
			}
			successEwms += ewm + ";";
		}
		JsonResponseModel jri = new JsonResponseModel();
		if (!StringUtils.isEmpty(uploadErrorFiles)) {
			// 添加上传失败的文件名称
			jri.setMsgInfo(uploadErrorFiles);
		}
		if (!StringUtils.isEmpty(successEwms)) {
			successEwms = successEwms.substring(0, successEwms.length() - 1);// 去掉末尾分号
			// 添加上传成功的图片二维码
			jri.setSuccessMsgInfo(successEwms);
		}
		return jri;
	}



	/**
	 * 图片上传
	 * 
	 * @param file_upload
	 * @param userId
	 * @param isls
	 * @param currNode_id
	 * @param jadIn
	 * @param upload4Zl
	 * @return
	 */
	public JsonResponseModel zjzlRawPicUpload(MultipartFile[] file_upload,
			String userId, String isls, String currNode_id, JAjuanD jadIn,
			String upload4Zl) {
		JsonResponseModel result = null;
		if ("true".equals(upload4Zl)) {
			if ("true".equals(isls)) {
				// 快速整理
				result = rawPicUpload4Kszl(file_upload, userId);
			} else {
				// 即时整理，解析并获得上传图片中的二维码
				result = rawPicUpload4Zl(file_upload);
			}
		} else {
			// 上传图片，存入磁盘和数据库
			result = rawPicUpload(file_upload, userId);
		}
		if (result != null) {
			if (!StringUtils.isEmpty(result.getSuccessMsgInfo())) {
				if ("true".equals(isls)) {
					// 历史案卷整理
					if (!"success".equals(ajzlService.lsajzlAddPic(
							result.getSuccessMsgInfo(), currNode_id, jadIn))) {
						result.setSuccessMsgInfo("ajzlError");
					}
				} else if ("false".equals(isls)) {
					// 即时案卷整理
					if (!"success".equals(ajzlService.ajzlAddPic(
							result.getSuccessMsgInfo(), currNode_id, jadIn))) {
						result.setSuccessMsgInfo("ajzlError");
					}
				}
			}
		}
		return result;
	}

	/**
	 * 历史整理批量上传，系统生成二维码
	 * 
	 * @param file_upload
	 * @param userId
	 * @return
	 */
	private JsonResponseModel rawPicUpload4Kszl(MultipartFile[] file_upload,
			String userId) {
		JsonResponseModel jri = new JsonResponseModel();
		if (file_upload == null || file_upload.length == 0) {
			return jri;
		}
		Integer userIdNo = Integer.parseInt(userId);
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
			return jri;
		}
		JEnv jev = ewmPrintService.gerFydmInfo();// 获得法院代码
		if (jev == null || StringUtils.isEmpty(jev.getEnvValue())) {
			return jri;
		}
		String uploadErrorFiles = "";
		String addPicEwms = "";
		String currDateStr = new SimpleDateFormat("yyyyMMdd").format(currDate);
		for (MultipartFile multiFile : file_upload) {
			if (multiFile == null || multiFile.isEmpty()) {
				break;
			}
			String fileName = multiFile.getOriginalFilename();
			if (fileName.split("\\.").length != 2
					|| fileName.split("\\.")[0].toUpperCase().endsWith(
							EwmDecode.EWM_TYPE_FM)
					|| fileName.split("\\.")[0].toUpperCase().endsWith(
							EwmDecode.EWM_TYPE_ML)) {
				uploadErrorFiles += (fileName + ";");// 文件名称中有且只能有一个.，去除封面和目录
				continue;
			}
			lastId++;// 二维码序号加1
			// String ewm = currDateStr + jev.getEnvValue()
			// + StringUtils.lpad(lastId + "", '0', 5) +
			// EwmDecode.EWM_TYPE_NR;// 整理时所有二维码都是Y结尾
			String ewm = ewmPrintService.generateEwm(fileName.split("\\.")[0],
					null, lastId, currDateStr, jev.getEnvValue());
			if (StringUtils.isEmpty(ewm)) {
				uploadErrorFiles += (fileName + ";");
				continue;
			}
			// 将文件存储到ftp目录,并将文件信息存入数据库(新二维码直接保存，不用查询jrawfile,删除原图片)
			JRawfile jf = null;
			try {
				jf = rawScanService.saveRawFile(multiFile, userIdNo, 1, ewm);
			} catch (Exception e) {
				uploadErrorFiles += (fileName + ";");
				logger.error("保存" + fileName + "文件到磁盘上出错，或者保存文件信息到数据库出错", e);
				continue;
			}
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
		jri.setSuccessMsgInfo(addPicEwms);
		if (!StringUtils.isEmpty(uploadErrorFiles)) {
			jri.setMsgInfo(uploadErrorFiles);
		}
		return jri;
	}

	/**
	 * 案卷整理上传图片，图片不入数据库和磁盘，只识别出二维码(文件名为二维码则不识别)
	 * 
	 * @param file_upload
	 * @param userId
	 * @return
	 */
	private JsonResponseModel rawPicUpload4Zl(MultipartFile[] file_upload) {
		if (file_upload == null || file_upload.length == 0) {
			return null;
		}
		String uploadErrorFiles = "";
		EwmDecode decode = new EwmDecode();
		String successEwms = "";
		Arrays.sort(file_upload, new Comparator4File());// 根据文件名称排序
		for (MultipartFile multiFile : file_upload) {
			if (multiFile == null || multiFile.isEmpty()) {
				break;
			}
			String fileName = multiFile.getOriginalFilename();
			String ewm = rawScanService.getEwmByOriginalFilename(fileName);
			if (StringUtils.isEmpty(ewm)) {
				uploadErrorFiles += (fileName + ";");
				continue;
			}
			if (!decode.isValidEwm(ewm, null)) {
				// 文件名不是有效二维码,则解析图片中的二维码
				InputStream is = null;
				try {
					is = multiFile.getInputStream();
					ewm = decode.readQRCodeResult(is).getText();
				} catch (Exception e) {
					uploadErrorFiles += (fileName + ";");
					logger.error("解析" + fileName + "文件二维码出错", e);
					continue;
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							logger.error("关闭" + fileName + "文件流出错", e);
						}
					}
				}
			}
			successEwms += ewm + ";";
		}
		JsonResponseModel jri = new JsonResponseModel();
		if (!StringUtils.isEmpty(uploadErrorFiles)) {
			// 添加上传失败的文件名称
			jri.setMsgInfo(uploadErrorFiles);
		}
		if (!StringUtils.isEmpty(successEwms)) {
			successEwms = successEwms.substring(0, successEwms.length() - 1);// 去掉末尾分号
			// 添加上传成功的图片二维码
			jri.setSuccessMsgInfo(successEwms);
		}
		return jri;
	}

}
