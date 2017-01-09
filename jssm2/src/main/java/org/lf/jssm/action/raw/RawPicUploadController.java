package org.lf.jssm.action.raw;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.lf.jssm.db.pojo.JAjuanD;
import org.lf.jssm.service.model.JsonResponseModel;
import org.lf.jssm.service.raw.RawScanParameterService;
import org.lf.jssm.service.raw.RawPicUploadService;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

/**
 * 二维码打印控制层
 * 
 * @author sunwill
 *
 */
@Controller
@RequestMapping("/raw/")
public class RawPicUploadController {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	@Autowired
	private RawPicUploadService rawPicUploadService;
	@Autowired
	private RawScanParameterService jScanEnvService;

	/**
	 * 进入类型和页数设置页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("rawPicUploadUI.do")
	public String ewmPrintUI(Model m) {
		// 获得扫描配置参数
		Map<String, String> map = jScanEnvService.getDefaultEnvMap();
		m.addAttribute("jScanEnv", JSON.toJSONString(map));
		return "raw/rawPicUpload";
	}

	/**
	 * 图片上传
	 * 
	 * @throws Exception
	 */
	@RequestMapping("rawPicUpload.do")
	@ResponseBody
	public JsonResponseModel rawPicUpload(
			@RequestParam(value = "file_upload", required = false) MultipartFile[] file_upload,
			String userId,String isls,String currNode_id,JAjuanD jadIn,String upload4Zl) throws Exception {
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		return rawPicUploadService.zjzlRawPicUpload(file_upload, userId,isls,currNode_id,jadIn,upload4Zl);
	}

	/**
	 * 初始化上传失败的文件列表对话框
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("rawPicUploadErrorUI.do")
	public String rawPicUploadErrorUI(String uploadErrorData, Model m) {
		m.addAttribute("uploadErrorData", uploadErrorData);
		return "raw/rawPicUploadError";
	}

	/**
	 * 下载上传失败的文件信息
	 * 
	 * @param uploadErrorList
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("picUploadErrorInfo.do")
	public void picUploadErrorInfo(String uploadErrorList,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(uploadErrorList)) {
			return;
		}
		String fileNameFormat = "uploadError_" + DATE_FORMAT.format(new Date());
		ByteArrayInputStream bai = null;
		OutputStream out = null;
		try {
			uploadErrorList = uploadErrorList.replace(";", "\r\n");
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.reset(); // 非常重要
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileNameFormat + ".txt");
			bai = new ByteArrayInputStream(uploadErrorList.getBytes("UTF-8"));
			byte[] buf = new byte[1024];
			int len = 0;
			out = response.getOutputStream();
			while ((len = bai.read(buf)) > 0)
				out.write(buf, 0, len);
			bai.close();
			out.close();
		} finally {
			if (bai != null) {
				bai.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
}
