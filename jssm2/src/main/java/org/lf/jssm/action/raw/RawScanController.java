package org.lf.jssm.action.raw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.service.raw.RawScanParameterService;
import org.lf.jssm.service.raw.RawScanService;
import org.lf.jssm.service.sys.JEnvService;
import org.lf.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 原始档案扫描控制层
 * 
 * @author sunwill
 *
 */
@Controller
@RequestMapping("/raw/")
public class RawScanController {
	private static final Logger logger = LoggerFactory
			.getLogger(RawScanController.class);

	@Autowired
	private RawScanService rawScanervice;

	@Autowired
	private JEnvService jEnvService;

	@Autowired
	private RawScanParameterService jScanEnvService;

	/**
	 * 即时扫描初始化dialog
	 * 
	 * @return
	 */
	@RequestMapping("rawScanUI.do")
	public String rawScanUI(HttpServletRequest request, Model m) {
		// 获得扫描配置参数
		Map<String, String> map = jScanEnvService.selectByScaner(request);
		m.addAttribute("jScanEnv", JSON.toJSONString(map));
		return "raw/rawScan";
	}

	/**
	 * 查询指定文件是否存在
	 * 
	 * @param jf
	 * @return
	 */
	@RequestMapping("findEwmInfo.do")
	@ResponseBody
	public JRawfile findEwmInfo(JRawfile jf) {
		jf = rawScanervice.getSavedRawFile(jf);
		if (jf == null) {
			return new JRawfile();
		}
		return jf;
	}

	/**
	 * 删除原始文件信息
	 * 
	 * @param jf
	 * @return
	 */
	@RequestMapping("deleteRawFile.do")
	@ResponseBody
	public String deleteRawFile(JRawfile jf) {
		try {
			// 查询文件信息
			jf = rawScanervice.getRawFileByEwm(jf.getEwm());
			return rawScanervice.deleteRawFile(jf, jEnvService.getFtpDir());
		} catch (Exception e) {
			logger.error("删除文件出错", e);
			return "error";
		}
	}

	/**
	 * 预览确认保存文件
	 * 
	 * @param jf
	 * @return
	 */
	@RequestMapping("confirmRawFile.do")
	@ResponseBody
	public String confirmRawFile(JRawfile jf) {
		try {
			return rawScanervice.confirmRawFile(jf);
		} catch (Exception e) {
			logger.error("确认保存原始文件出错", e);
			return "error";
		}
	}

	/**
	 * 原始图片上传
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rawUpload.do")
	@ResponseBody
	public JRawfile rawUpload(HttpServletRequest request, Integer userId) {
		if (userId == null) {
			return null;
		}
		JRawfile jrf = null;
		try {
			jrf = rawScanervice.rawUpload(request, userId);
			return jrf;
		} catch (Exception e) {
			logger.error("原始文件上传出错", e);
			jrf = new JRawfile();
			jrf.setErrorMsg("uploadErrorMsg");
			return jrf;
		}
	}

	/**
	 * 扫描预览
	 * 
	 * @return
	 */
	@RequestMapping("rawPreviewUI.do")
	public String rawPreview(String currEwm, Integer currFileVersion, Model m) {
		m.addAttribute("currImgEwm", currEwm);
		m.addAttribute("currImgFileVer", currFileVersion);
		return "raw/rawPreview";
	}

	/**
	 * 获得预览大图输出流
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rawPreviewImg.do")
	@ResponseBody
	public void rawPreviewImg(String currEwm, HttpServletResponse response,
			String currFileVersion,HttpServletRequest request) {
		if (StringUtils.isEmpty(currEwm)
				|| StringUtils.isEmpty(currFileVersion)) {
			return;
		}
		if(RawScanService.NO_PIC_EWM.equals(currEwm.trim())){
			rawScanervice.redirectToNoPic(request,response,RawScanService.NO_PIC_URL);
			return;
		}
		FileInputStream fis = null;
		ServletOutputStream os = null;
		String ftpDir = jEnvService.getFtpDir();
		if (StringUtils.isEmpty(ftpDir)) {
			return;
		}
		try {
			JRawfile jf = rawScanervice.getRawFileByEwm(currEwm);
			if (jf == null) {
				rawScanervice.redirectToNoPic(request,response,RawScanService.NO_PIC_URL);
				return;
			}
			// 获得图片地址
			String imgPath = rawScanervice.getImagePath(jf, ftpDir);
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
				rawScanervice.redirectToNoPic(request,response,RawScanService.NO_PIC_URL);
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
	 * 缩略图预览
	 * @param currEwm
	 * @param response
	 * @param currFileVersion
	 */
	@RequestMapping("rawPreviewSmallImg.do")
	@ResponseBody
	public void rawPreviewSmallImg(String currEwm, HttpServletResponse response,
			String currFileVersion,HttpServletRequest request) {
		if (StringUtils.isEmpty(currEwm)
				|| StringUtils.isEmpty(currFileVersion)) {
			return;
		}
		if(RawScanService.NO_PIC_EWM.equals(currEwm.trim())){
			rawScanervice.redirectToNoPic(request,response,RawScanService.NO_PIC_URL_S);
			return;
		}
		FileInputStream fis = null;
		ServletOutputStream os = null;
		String ftpDir = jEnvService.getFtpDir();
		if (StringUtils.isEmpty(ftpDir)) {
			return;
		}
		try {
			JRawfile jf = rawScanervice.getRawFileByEwm(currEwm);
			if (jf == null) {
				rawScanervice.redirectToNoPic(request,response,RawScanService.NO_PIC_URL_S);
				return;
			}
			// 获得图片地址
			String imgPath = rawScanervice.getSmallImagePath(jf, ftpDir);
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
				rawScanervice.redirectToNoPic(request,response,RawScanService.NO_PIC_URL_S);
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
