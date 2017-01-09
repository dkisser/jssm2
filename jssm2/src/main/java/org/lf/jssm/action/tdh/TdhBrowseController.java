package org.lf.jssm.action.tdh;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lf.jssm.action.raw.RawScanController;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.service.model.VAjuanDPicture;
import org.lf.jssm.service.raw.RawScanService;
import org.lf.jssm.service.sys.JEnvService;
import org.lf.jssm.service.tdh.EasyuiTree4Tdh;
import org.lf.jssm.service.tdh.TdhJz;
import org.lf.jssm.service.tdh.TdhService;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.StringUtils;
import org.lf.utils.servlet.WebServiceTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通达海接口
 * 
 * @author sunwill
 *
 */
@Controller
@RequestMapping("/tdh/")
public class TdhBrowseController {
	private static final Logger logger = LoggerFactory
			.getLogger(RawScanController.class);
	@Autowired
	private TdhService tdhService;
	@Autowired
	private JEnvService jEnvService;
	@Autowired
	private RawScanService rawScanervice;

	/**
	 * 
	 * @param court
	 *            法院代码
	 * @param userid
	 *            用户代码
	 * @param archiveno
	 *            案号
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("caseList.do")
	public String caseList(String court, String userid, String archiveno,
			HttpServletRequest request, Model m) throws Exception {
		m.addAttribute("ah4Tdh", archiveno == null ? null : archiveno.trim());
		m.addAttribute("fydm4Tdh", court == null ? null : court.trim());
		m.addAttribute("userId4Tdh", userid == null ? null : userid.trim());
		ServletContext application = request.getServletContext();// 读取图片地址
		if (StringUtils.isEmpty((String) application
				.getAttribute("picServerUrl"))) {
			InputStream inputStream = WebServiceTool.class.getClassLoader()
					.getResourceAsStream("jssm.properties");
			Properties p = new Properties();
			String picServerUrl = null;
			try {
				p.load(inputStream);
				picServerUrl = p.getProperty("picServerUrl");
				if (StringUtils.isEmpty(picServerUrl)) {
					throw new RuntimeException("图片服务器地址为空");
				}
			} catch (Exception e1) {
				logger.error("读取配置文件出错", e1);
				throw e1;
			}
			application.setAttribute("picServerUrl", picServerUrl.trim());
		}
		return "tdh/caseList4Tdh";
	}

	/**
	 * 根据案号获得案件列表
	 * 
	 * @param archiveno
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping("getCaseList.do")
	@ResponseBody
	public EasyuiDatagrid<TdhJz> getCaseList(String archiveno, int rows,
			int page) {
		return tdhService.getCaseList(archiveno, rows, page);
	}

	/**
	 * 显示卷宗预览界面
	 * 
	 * @param tj
	 * @param m
	 * @return
	 */
	@RequestMapping("caseBrowse4Tdh.do")
	public String getJzDoc(TdhJz tj, Model m) {
		// String jzewm = tdhService.getJzEwm4Tdh(tj);
		m.addAttribute("jzewm4Tdh", tj == null ? null : tj.getEwm());
		return "tdh/caseBrowse4Tdh";
	}

	@RequestMapping("tdhTreeData.do")
	@ResponseBody
	public List<EasyuiTree4Tdh> tdhTreeData(String jzEwm) {
		return tdhService.getEasyuiTree4Tdh(jzEwm);
	}

	@RequestMapping("jzBrowseImg4Tdh.do")
	public String jzBrowse4Tdh(String jzEwm, HttpServletRequest request,
			HttpSession session) {
		if (tdhService.getVAjuanDJzidByEwm(jzEwm) == null) {
			return "tdh/jzBrowseImg4Tdh";
		} else {
			VAjuanDPicture pictureFm = new VAjuanDPicture();
			if (tdhService.getJRawFileByEwm(jzEwm) != null) {
				pictureFm.setPicEwm(jzEwm);
			} else {
				pictureFm.setPicEwm(RawScanService.NO_PIC_EWM);
			}
			pictureFm.setPageNumber("卷宗封面");
			pictureFm.setPicVersion(0);

			VAjuanDPicture pictureMl = new VAjuanDPicture();
			if (tdhService.getJRawFileByEwm(tdhService.getVAjuanDAjEwm(jzEwm)) != null) {
				pictureMl.setPicEwm(tdhService.getVAjuanDAjEwm(jzEwm));
			} else {
				pictureMl.setPicEwm(RawScanService.NO_PIC_EWM);
			}
			pictureMl.setPageNumber("卷宗目录");
			pictureMl.setPicVersion(1);

			List<VAjuanDPicture> listPictureInfo = new ArrayList<VAjuanDPicture>();
			listPictureInfo.add(pictureFm);
			listPictureInfo.add(pictureMl);

			Map<String, List<VAjuanDPicture>> picTureMap = tdhService
					.getPictureMap(jzEwm);
			Iterator<String> it = picTureMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				listPictureInfo.addAll(picTureMap.get(key));
			}

			session.setAttribute("pictureMap", picTureMap);
			request.setAttribute("listPictureInfo", listPictureInfo);
			request.setAttribute("listPicCounts", listPictureInfo == null ? 0
					: listPictureInfo.size());
			return "tdh/jzBrowseImg4Tdh";
		}
	}

	/**
	 * 大图预览
	 * 
	 * @param currEwm
	 * @param response
	 * @param currFileVersion
	 */
	@RequestMapping("previewImg4Tdh.do")
	@ResponseBody
	public void rawPreviewImg(String currEwm, HttpServletResponse response,
			String currFileVersion, HttpServletRequest request) {
		if (StringUtils.isEmpty(currEwm)
				|| StringUtils.isEmpty(currFileVersion)) {
			return;
		}
		if (RawScanService.NO_PIC_EWM.equals(currEwm.trim())) {
			rawScanervice.redirectToNoPic(request, response,
					RawScanService.NO_PIC_URL);
			return;
		}
		FileInputStream fis = null;
		ServletOutputStream os = null;
		String ftpDir = jEnvService.getFtpDir();
		if (StringUtils.isEmpty(ftpDir)) {
			return;
		}
		try {
			JRawfile jf = tdhService.getRawFileByEwm(currEwm);
			if (jf == null) {
				rawScanervice.redirectToNoPic(request, response,
						RawScanService.NO_PIC_URL);
				return;
			}
			// 获得图片地址
			String imgPath = tdhService.getImagePath(jf, ftpDir);
			if (StringUtils.isEmpty(imgPath)) {
				return;
			}
			// 获得图片输入流
			byte[] b = new byte[1024 * 4];
			response.setContentType("image/jpeg"); // 设置返回的文件类型
			// response.reset();
			os = response.getOutputStream();// 获得响应输出流
			int count = 0;
			try {
				fis = new FileInputStream(imgPath);
			} catch (FileNotFoundException fe) {
				logger.error("图片不存在", fe);// 图片不存在，显示默认图片
				rawScanervice.redirectToNoPic(request, response,
						RawScanService.NO_PIC_URL);
				return;
			}
			while ((count = fis.read(b)) != -1) {
				os.write(b, 0, count);
			}
			os.flush();
			os.close();
			fis.close();
		} catch (Exception e) {
			logger.error("读取图片文件出错", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				logger.error("关闭文件流出错", e);
			}
		}
	}

	/**
	 * 获得每张图片对应的pic_ewm和pic_version,以及每张图片对应的页号,卷宗封面和卷宗目录单独处理没有页号
	 * 
	 * @return
	 */
	@RequestMapping("jzBrowseImgAll4Tdh.do")
	public String getPicturePath(String jzEwm, String nodeText,
			HttpSession session, Model m) {
		List<VAjuanDPicture> listPictureInfo = null;
		if (nodeText.trim().equals("卷宗封面")) {
			listPictureInfo = new ArrayList<VAjuanDPicture>();
			VAjuanDPicture pictureFm = new VAjuanDPicture();
			if (tdhService.getJRawFileByEwm(jzEwm) != null) {
				pictureFm.setPicEwm(jzEwm);
			} else {
				pictureFm.setPicEwm(RawScanService.NO_PIC_EWM);
			}
			pictureFm.setPageNumber("卷宗封面");
			pictureFm.setPicVersion(0);
			listPictureInfo.add(pictureFm);

		} else if (nodeText.trim().equals("卷宗目录")) {
			listPictureInfo = new ArrayList<VAjuanDPicture>();
			VAjuanDPicture pictureMl = new VAjuanDPicture();
			if (tdhService.getJRawFileByEwm(tdhService.getVAjuanDAjEwm(jzEwm)) != null) {
				pictureMl.setPicEwm(tdhService.getVAjuanDAjEwm(jzEwm));
			} else {
				pictureMl.setPicEwm(RawScanService.NO_PIC_EWM);
			}
			pictureMl.setPageNumber("卷宗目录");
			pictureMl.setPicVersion(1);
			listPictureInfo.add(pictureMl);
		} else {
			listPictureInfo = new ArrayList<VAjuanDPicture>();
			@SuppressWarnings("unchecked")
			LinkedHashMap<Integer, List<VAjuanDPicture>> map = (LinkedHashMap<Integer, List<VAjuanDPicture>>) session
					.getAttribute("pictureMap");
			listPictureInfo.addAll(map.get(nodeText));
		}
		m.addAttribute("listPictureInfo", listPictureInfo);
		m.addAttribute("listPicCounts", listPictureInfo == null ? 0
				: listPictureInfo.size());
		// request.setAttribute("listPictureInfo", listPictureInfo);
		return "tdh/jzBrowseImg4Tdh";
	}

	/**
	 * 大图预览
	 * 
	 * @return
	 */
	@RequestMapping("showBigImg4Tdh.do")
	public String showBigImg4Tdh(String picEwm, Model m) {
		m.addAttribute("picEwm4Tdh", picEwm);
		return "tdh/bigImg4Tdh";
	}

	/**
	 * 预览缩略图
	 * 
	 * @param currEwm
	 * @param response
	 * @param currFileVersion
	 */
	@RequestMapping("previewSmallImg4Tdh.do")
	@ResponseBody
	public void previewSmallImg4Tdh(String currEwm,
			HttpServletResponse response, String currFileVersion,
			HttpServletRequest request) {
		if (StringUtils.isEmpty(currEwm)
				|| StringUtils.isEmpty(currFileVersion)) {
			return;
		}
		if (RawScanService.NO_PIC_EWM.equals(currEwm.trim())) {
			rawScanervice.redirectToNoPic(request, response,
					RawScanService.NO_PIC_URL);
			return;
		}
		FileInputStream fis = null;
		ServletOutputStream os = null;
		String ftpDir = jEnvService.getFtpDir();
		if (StringUtils.isEmpty(ftpDir)) {
			return;
		}
		try {
			JRawfile jf = tdhService.getRawFileByEwm(currEwm);
			if (jf == null) {
				rawScanervice.redirectToNoPic(request, response,
						RawScanService.NO_PIC_URL);
				return;
			}
			// 获得图片地址
			String imgPath = tdhService.getSmallImagePath(jf, ftpDir);
			if (StringUtils.isEmpty(imgPath)) {
				return;
			}
			// 获得图片输入流
			byte[] b = new byte[1024 * 4];
			response.setContentType("image/jpeg"); // 设置返回的文件类型
			os = response.getOutputStream();// 获得响应输出流
			int count = 0;
			try {
				fis = new FileInputStream(imgPath);
			} catch (FileNotFoundException fe) {
				logger.error("图片不存在", fe);// 图片不存在，显示默认图片
				rawScanervice.redirectToNoPic(request, response,
						RawScanService.NO_PIC_URL);
				return;
			}
			while ((count = fis.read(b)) != -1) {
				os.write(b, 0, count);
			}
			os.flush();
			os.close();
			fis.close();
		} catch (Exception e) {
			logger.error("读取图片文件出错", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
				logger.error("关闭文件流出错", e);
			}
		}

	}

}
