package org.lf.jssm.service.raw;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.lf.jssm.db.dao.JEnvMapper;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.service.sys.JEnvService;
import org.lf.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 原始档案服务层
 * 
 * @author sunwill
 *
 */
@Service("rawScanService")
public class RawScanService {
	private static final Logger logger = LoggerFactory
			.getLogger(RawScanService.class);
	/**
	 * 没有图片时显示用的小图
	 */
	public static final String NO_PIC_URL_S = "/etcs/10000_S.jpg";
	/**
	 * 没有图片时显示用的大图
	 */
	public static final String NO_PIC_URL = "/etcs/10000.jpg";
	/**
	 * 没有图片时显示用的图片二维码
	 */
	public static final String NO_PIC_EWM = "10000";
	/**
	 * 缩略图后缀
	 */
	public static final String SMALL_FILE_SUFFIX = "_S";
	@Autowired
	private JRawfileMapper jRawFileDao;

	@Autowired
	private JEnvMapper jenvDao;

	@Autowired
	private JEnvService jEnvService;

	/**
	 * 原始档案图片上传
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public JRawfile rawUpload(HttpServletRequest request, Integer userId)
			throws Exception {
		// 转型为MultipartHttpRequest(重点的所在),spring的上传组件
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得图片,哲林高拍仪入参为"file"
		MultipartFile file = multipartRequest.getFile("file");
		if (file.isEmpty()) {
			return new JRawfile();
		}
		// 获得上传的文件名称
		String fileName = file.getOriginalFilename();

		// 获取二维码
		String ewm = getEwmByOriginalFilename(fileName);
		if (StringUtils.isEmpty(ewm)) {
			return new JRawfile();
		}
		// 将存储信息保存至数据库
		// 查询已存在的文件信息
		JRawfile jf = jRawFileDao.getRawFileByEwm(ewm);
		Integer rawFileVersion = null;
		if (jf != null) {// 存在则删除原来的文件
			// 取出原先的文件版本号，数据库默认为1
			rawFileVersion = jf.getFileVersion();
			if (rawFileVersion == 999) {
				// 版本号最大为999，到999归1
				rawFileVersion = 1;
			}
			// 若二维码文件已存在，则删除文件信息
			deleteRawFile(jf, jEnvService.getFtpDir());
		}
		// 将文件存储到ftp目录,并将文件信息存入数据库
		return saveRawFile(file, userId, rawFileVersion, ewm);
	}

	/**
	 * 根据文件名拆分出二维码
	 * 
	 * @param fileName
	 * @return
	 */
	public String getEwmByOriginalFilename(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return null;
		}
		return fileName.split("\\.")[0];
	}

	/**
	 * 将文件存储到ftp目录,并将文件信息存入数据库
	 * 
	 * @param file
	 * @param userId
	 * @param rawFileVersion
	 *            原先的文件版本号
	 * @param ewm
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public JRawfile saveRawFile(MultipartFile file, Integer userId,
			Integer rawFileVersion, String ewm) throws Exception {
		if (file == null || userId == null) {
			return new JRawfile();
		}
		// 获得上传的文件后缀名称
		String fileSuffix = file.getOriginalFilename().split("\\.")[1];
		if (StringUtils.isEmpty(ewm) || StringUtils.isEmpty(fileSuffix)) {
			return new JRawfile();
		}
		Date curDate = new Date();
		// 获取当前时间
		String curTime = new SimpleDateFormat("yyyy-MM-dd").format(curDate);
		// 获得ftp存储目录
		String ftpDir = jEnvService.getFtpDir();
		// 存储文件夹,
		String dirPath = ftpDir + "\\" + curTime.split("-")[0] + "\\"
				+ curTime.split("-")[1] + "\\" + curTime.split("-")[2];
		File dirFile = new File(dirPath);
		// 如果文件夹不存在则创建
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		if (!dirFile.isDirectory()) {
			return new JRawfile();
		}
		// 文件完整存储路径,如：D:\jssm\PIC\FTP\2016\08\16\2016062901336.jpg
		String filePath = dirPath + "\\" + ewm + "." + fileSuffix;
		// 将文件存储到硬盘中
		File ftpFile = new File(filePath);// 根据（filePath）创建File对象
		file.transferTo(ftpFile);
		// 生成缩略图
		Thumbnails
				.of(ftpFile)
				.size(360, 360)
				// 缩略图大小
				.outputFormat("jpg")
				// 缩略图格式
				// .outputQuality(0.8f)//缩略图质量
				.toFile(dirPath + "\\" + ewm + SMALL_FILE_SUFFIX + "."
						+ fileSuffix);
		JRawfile jf = new JRawfile();
		try {
			// 将文件信息存入数据库
			jf.setEwm(ewm);// 二维码
			jf.setFileSuffix(fileSuffix);// 文件后缀名
			jf.setFileSize(ftpFile.length());// 文件大小，单位字节（byte）
			jf.setFileStatus(2);// 文件状态，初始为1,表示临时存储状态,上传时状态为2
			jf.setModifyDate(curDate);
			jf.setScanYear(curTime.split("-")[0]);
			jf.setScanMonth(curTime.split("-")[1]);
			jf.setScanDay(curTime.split("-")[2]);
			jf.setScanerId(userId);
			if (rawFileVersion != null) {
				// 每次存储图片版本号+1
				jf.setFileVersion(rawFileVersion + 1);
			} else {
				// 版本号默认为1
				jf.setFileVersion(1);
			}
			jRawFileDao.insert(jf);
		} catch (Exception e) {
			// 将文件信息存入数据库出错，则删除文件
			deleteRawFile(jf, ftpDir);
			throw e;
		}
		return jf;
	}

	/**
	 * 查询指定二维码并且状态大于1的文件
	 * 
	 * @param ewm
	 * @return
	 */
	public JRawfile getSavedRawFile(JRawfile jf) {
		if (jf == null || StringUtils.isEmpty(jf.getEwm())) {
			return null;
		}
		return jRawFileDao.getSavedRawFile(jf);
	}

	/**
	 * 删除文件信息，以及相关文件
	 * 
	 * @param jf
	 * @param ftpDir
	 * @return
	 */
	public String deleteRawFile(JRawfile jf, String ftpDir) {
		if (jf == null || StringUtils.isEmpty(jf.getEwm())
				|| StringUtils.isEmpty(ftpDir)) {
			return "error";
		}
		try {
			String imgPath = getImagePath(jf, ftpDir);
			if (StringUtils.isEmpty(imgPath)) {
				return "error";
			}
			// 先删除文件，后删除数据库数据
			File imgFile = new File(imgPath);
			if (imgFile.isFile() && imgFile.exists()) {
				imgFile.delete();// 删除服务器中原图
			}
			File smallImg = new File(getSmallImagePath(jf, ftpDir));
			if (smallImg.isFile() && smallImg.exists()) {
				smallImg.delete();// 删除缩略图
			}
		} catch (Exception e) {
			logger.error("删除磁盘中的图片文件出错", e);
		}
		jRawFileDao.deleteRawFileInfo(jf);
		return "success";
	}

	/**
	 * 根据二维码，删除文件信息，以及相关文件
	 * 
	 * @param ewm
	 * @param ftpDir
	 * @return
	 */
	public String deleteRawFileByEwm(String ewm, String ftpDir) {
		if (StringUtils.isEmpty(ewm) || StringUtils.isEmpty(ftpDir)) {
			return "error";
		}
		JRawfile jf = null;
		try {
			jf = jRawFileDao.getRawFileByEwm(ewm);
			if (jf == null) {
				return "error";
			}
			String imgPath = getImagePath(jf, ftpDir);
			if (StringUtils.isEmpty(imgPath)) {
				return "error";
			}
			// 先删除文件，后删除数据库数据
			File imgFile = new File(imgPath);
			if (imgFile.isFile() && imgFile.exists()) {
				imgFile.delete();// 删除服务器中原图
			}
			File smallImg = new File(getSmallImagePath(jf, ftpDir));
			if (smallImg.isFile() && smallImg.exists()) {
				smallImg.delete();// 删除缩略图
			}
		} catch (Exception e) {
			logger.error("删除磁盘中的图片文件出错", e);
		}
		jRawFileDao.deleteRawFileInfo(jf);
		return "success";
	}

	/**
	 * 预览确认保存
	 * 
	 * @param jf
	 * @return
	 */
	public String confirmRawFile(JRawfile jf) {
		if (jf == null || StringUtils.isEmpty(jf.getEwm())) {
			return "error";
		}
		// 获取当前时间
		Date curDate = new Date();
		// 获取当前时间字符串
		String curTime = new SimpleDateFormat("yyyy-MM-dd").format(curDate);
		jf.setFileStatus(2);
		jf.setModifyDate(curDate);
		jf.setScanYear(curTime.split("-")[0]);
		jf.setScanMonth(curTime.split("-")[1]);
		jf.setScanDay(curTime.split("-")[2]);
		jRawFileDao.updateByJf(jf);
		return "success";
	}

	/**
	 * 根据二维码获得预览图片信息
	 * 
	 * @param jf
	 * @return
	 */
	public JRawfile getRawFileByEwm(String ewm) {
		if (StringUtils.isEmpty(ewm)) {
			return null;
		}
		return jRawFileDao.getRawFileByEwm(ewm);
	}

	/**
	 * 获得图片在服务器上的存储目录
	 * 
	 * @param jf
	 * @return
	 */
	public String getImagePath(JRawfile jf, String ftpDir) {
		if (jf == null || StringUtils.isEmpty(ftpDir)) {
			return null;
		}
		// 拼接图片地址，如：D:\jssm\PIC\FTP\2016\08\16\2016062901336.jpg
		String imgPath = ftpDir + "\\" + jf.getScanYear() + "\\"
				+ jf.getScanMonth() + "\\" + jf.getScanDay() + "\\"
				+ jf.getEwm() + "." + jf.getFileSuffix();
		return imgPath;
	}

	/**
	 * 获得预览缩略图路径
	 * 
	 * @param jf
	 * @param ftpDir
	 * @return
	 */
	public String getSmallImagePath(JRawfile jf, String ftpDir) {
		if (jf == null || StringUtils.isEmpty(ftpDir)) {
			return null;
		}
		// 拼接图片地址，如：D:\jssm\PIC\FTP\2016\08\16\2016062901336.jpg
		String imgPath = ftpDir + "\\" + jf.getScanYear() + "\\"
				+ jf.getScanMonth() + "\\" + jf.getScanDay() + "\\"
				+ jf.getEwm() + SMALL_FILE_SUFFIX + "." + jf.getFileSuffix();
		return imgPath;
	}

	/**
	 * 重定向显示“暂无图片”
	 * 
	 * @param request
	 * @param response
	 * @param noPicUrl
	 */
	public void redirectToNoPic(HttpServletRequest request,
			HttpServletResponse response, String noPicUrl) {
		if (request == null || response == null
				|| StringUtils.isEmpty(noPicUrl)) {
			return;
		}
		try {
			response.sendRedirect(request.getContextPath() + noPicUrl);
		} catch (Exception e) {
		}
	}
}
