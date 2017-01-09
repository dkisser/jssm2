package org.lf.jssm.action.ajuan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.lf.jssm.action.BaseController;
import org.lf.jssm.db.pojo.VJz;
import org.lf.jssm.service.ajuan.AjuanInfoService;
import org.lf.jssm.service.ajuan.JZBrowseService;
import org.lf.jssm.service.model.VAjuanDPicture;
import org.lf.jssm.service.raw.RawScanService;
import org.lf.utils.EasyuiTree;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 已归档的卷宗浏览
 */
@Controller
@RequestMapping("/ajuan/")
public class JZBrowseController implements BaseController {
	private Logger logger = Logger.getLogger("JZBrowseController");
	@Autowired
	private JZBrowseService jzBroService;
	@Autowired
	private AjuanInfoService aJuanInfoService;

	@RequestMapping("jzBrowse.do")
	public String browse(String jzEwm, String print, Model m, HttpSession session, String hasdlg) {
		String jzid = jzBroService.getVAjuanDJzidByEwm(jzEwm);
		VJz jz = aJuanInfoService.getJZInfo(jzEwm);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String larq = null, jarq = null, gdrq = null;
		if (jz.getLarq() != null)
			larq = formatter.format(jz.getLarq());
		if (jz.getJarq() != null)
			jarq = formatter.format(jz.getJarq());
		if (jz.getGdrq() != null)
			gdrq = formatter.format(jz.getGdrq());

		m.addAttribute("larq", larq);
		m.addAttribute("jarq", jarq);
		m.addAttribute("gdrq", gdrq);
		m.addAttribute("jzid", jzid);
		m.addAttribute("jzEwm", jzEwm);
		m.addAttribute("jz", jz);
		/**
		 * 用户是否能够导出pdf
		 */

		if (print != null && print.equals("1")) {
			m.addAttribute("print", true);
		} else {
			m.addAttribute("print", false);
		}
		if (!StringUtils.isEmpty(hasdlg) && "no".equals(hasdlg.trim())) {
			return "ajuan/jzBrowseNodlg";
		}
		return "ajuan/jzBrowse";

	}

	@RequestMapping("jzBrowsetreeData.do")
	@ResponseBody
	public List<EasyuiTree> getEasyuiTree(String jzEwm) {
		return jzBroService.getEasyuiTree(jzEwm);
	}

	/**
	 * 第一次进去显示所有的图片(卷宗封面和目录以及其他图片) 将存有图片页号的picTureMap放到session中
	 * 避免多次数据库操作(卷宗封面和目录没有页号).
	 * 
	 * @param jzEwm
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("jzBrowsePicture.do")
	public String jzBrowsePicture(String jzEwm, HttpServletRequest request, HttpSession session) {
		if (jzBroService.getVAjuanDJzidByEwm(jzEwm) == null) {
			return "ajuan/jzBrowsePicture";
		} else {
			VAjuanDPicture pictureFm = new VAjuanDPicture();
			if (jzBroService.getJRawFileByEwm(jzEwm) != null) {
				pictureFm.setPicEwm(jzEwm);
			} else {
				pictureFm.setPicEwm(RawScanService.NO_PIC_EWM);
			}
			pictureFm.setPageNumber("卷宗封面");
			pictureFm.setPicVersion(0);

			VAjuanDPicture pictureMl = new VAjuanDPicture();
			if (jzBroService.getJRawFileByEwm(jzBroService.getVAjuanDAjEwm(jzEwm)) != null) {
				pictureMl.setPicEwm(jzBroService.getVAjuanDAjEwm(jzEwm));
			} else {
				pictureMl.setPicEwm(RawScanService.NO_PIC_EWM);
			}
			pictureMl.setPageNumber("卷宗目录");
			pictureMl.setPicVersion(1);

			List<VAjuanDPicture> listPictureInfo = new ArrayList<VAjuanDPicture>();
			listPictureInfo.add(pictureFm);
			listPictureInfo.add(pictureMl);

			Map<String, List<VAjuanDPicture>> picTureMap = jzBroService.getPictureMap(jzEwm);
			Iterator<String> it = picTureMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				listPictureInfo.addAll(picTureMap.get(key));
			}

			session.setAttribute("pictureMap", picTureMap);
			request.setAttribute("listPictureInfo", listPictureInfo);
			return "ajuan/jzBrowsePicture";
		}
	}

	/**
	 * 获得每张图片对应的pic_ewm和pic_version,以及每张图片对应的页号,卷宗封面和卷宗目录单独处理没有页号
	 * 
	 * @return
	 */
	@RequestMapping("jzBrowsePictureShowAll.do")
	public String getPicturePath(String jzEwm, String nodeText, HttpServletRequest request, HttpSession session) {
		List<VAjuanDPicture> listPictureInfo = null;
		if (nodeText.trim().equals("卷宗封面")) {
			listPictureInfo = new ArrayList<VAjuanDPicture>();
			VAjuanDPicture pictureFm = new VAjuanDPicture();
			if (jzBroService.getJRawFileByEwm(jzEwm) != null) {
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
			if (jzBroService.getJRawFileByEwm(jzBroService.getVAjuanDAjEwm(jzEwm)) != null) {
				pictureMl.setPicEwm(jzBroService.getVAjuanDAjEwm(jzEwm));
			} else {
				pictureMl.setPicEwm(RawScanService.NO_PIC_EWM);
			}
			pictureMl.setPageNumber("卷宗目录");
			pictureMl.setPicVersion(1);
			listPictureInfo.add(pictureMl);
		} else {
			listPictureInfo = new ArrayList<VAjuanDPicture>();
			@SuppressWarnings("unchecked")
			LinkedHashMap<Integer, List<VAjuanDPicture>> map = (LinkedHashMap<Integer, List<VAjuanDPicture>>) session.getAttribute("pictureMap");
			listPictureInfo.addAll(map.get(nodeText));
		}

		request.setAttribute("listPictureInfo", listPictureInfo);
		return "ajuan/jzBrowsePicture";
	}

	/**
	 * 将图片文件转为pdf文件，并保存到本地服务器，然后将本地服务器的地址通过session保存，在下载的时候调用。
	 * 将图片的文件名保存到session中 pdfFileName 并存储在服务器
	 * 
	 * 分为部分导出和全部导出，部分导出由jzwem以及mlmc查询v_ajuand视图。
	 * mlmc由只需解析前台传来的字符串str即可获得，(每个mlmc中间由';'隔开) 全部导出只需要知道jzewm即可查询v_ajuand视图即可。
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("jzFileIntoPdf.do")
	@ResponseBody
	public JSONObject jzFileIntoPdf(String jzEwm, String str, HttpServletResponse response, HttpServletRequest request, HttpSession session)
			throws IOException {
		String serverDir = request.getSession().getServletContext().getRealPath("/");
		String pdfFilePath = null;
		if (null == str) {
			pdfFilePath = jzBroService.pdfDownLoad(jzEwm, serverDir);
		} else {
			pdfFilePath = jzBroService.pdfDownLoad(jzEwm, str, serverDir);
		}
		JSONObject jObject = new JSONObject();
		jObject.put("success", "0");
		session.setAttribute("pdfFilePath", pdfFilePath);
		session.setAttribute("pdfFileName", jzBroService.getVAjuanDJzidByEwm(jzEwm) + "-" + "第" + jzBroService.getVAjuanDJHByEwm(jzEwm) + "卷"
				+ ".pdf");
		session.setAttribute("pdfFilePathDir", serverDir + File.separator + "temp");
		return jObject;
	}

	/**
	 * 文件下载 通过session获得本地服务器的生成pdf文件，转成流的方式下载。
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@RequestMapping("jzPdfDownload.do")
	public String download(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException,
			Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		String fileName = (String) session.getAttribute("pdfFileName");
		String name = fileName.replace(" ", "");
		String pdfFilePath = (String) session.getAttribute("pdfFilePath");// 获得pdf在服务器中的临时文件路径
		String pdfFilePathDir = (String) session.getAttribute("pdfFilePathDir");
		File file = new File(pdfFilePath);
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment;fileName=" + new String(name.getBytes("gb2312"), "ISO8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			// 当设定的文件名中包含有中文字符的时候，加" + java.net.URLEncoder.encode(fileName, "UTF-8");文件名超过16字出现乱码
			// 慎用。
			// 若文件名字超过16个文字的时候会出现乱码问题 ，则应该设置为中文编码集，台湾同胞用的话把gb2312改成big5就行。
		} catch (UnsupportedEncodingException e1) {
			throw e1;
		}
		// Content-Disposition 的作用当Content-Type 的类型为要下载的类型时 ,
		// 这个信息头会告诉浏览器这个文件的名字和类型。
		// 此处fileName表示的是文件下载时候弹出框显示的文件名，且必须添加
		try {
			InputStream inputStream = new FileInputStream(new File(pdfFilePath));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}

		try {
			if (new File(pdfFilePathDir).exists()) {
				FileUtils.deleteDirectory(new File(pdfFilePathDir));
			} else {
				logger.info("File not exist: " + pdfFilePathDir);
			}
		} catch (IOException e) {
			logger.info("Unable to delete file: " + pdfFilePathDir);
		}
		return null;
	}

	@RequestMapping("rawFile4Qtip.do")
	@ResponseBody
	public String rawFileImg(String currEwm, HttpServletRequest request) {
		ServletContext application = request.getServletContext();
		String serverPictureUrl = (String) application.getAttribute("picServerUrl");
		if (jzBroService.getJRawFileByEwm(currEwm) == null) {
			String uri = serverPictureUrl + "/raw/rawPreviewImg.do?currEwm=10000&currFileVersion=1";
			return uri;
		} else {
			String uri = serverPictureUrl + "/raw/rawPreviewImg.do?currEwm=" + currEwm + "&currFileVersion=1";
			return uri;
		}
	}
}
