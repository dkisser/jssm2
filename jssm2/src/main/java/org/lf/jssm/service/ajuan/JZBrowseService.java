package org.lf.jssm.service.ajuan;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.VAJuanDMapper;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.db.pojo.VAJuanD;
import org.lf.jssm.service.model.PdfPathInfo;
import org.lf.jssm.service.model.VAjuanDPicture;
import org.lf.jssm.service.raw.RawScanService;
import org.lf.jssm.service.sys.JEnvService;
import org.lf.utils.EasyuiTree;
import org.lf.utils.Image2Pdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 卷宗浏览
 * @author Administrator
 *
 */
@Service
public class JZBrowseService {
	@Autowired
	private VAJuanDMapper vAjuanDDao;
	
	@Autowired
	private JRawfileMapper jRawFileDao;
	
	@Autowired
	private RawScanService rawScanService;
	
	@Autowired
	private JEnvService jEnvService;

	/**
	 * 通过二维码获得V_AJUAND的list
	 * @param ewm
	 * @return
	 */
	public List<VAJuanD> getVAjuanDListByJzEwm(String ewm) {
		return vAjuanDDao.getVAjuanDListByJzEwm(ewm);
	}

	/**
	 * 通过二维码获得jzid
	 */
	public String getVAjuanDJzidByEwm(String ewm) {
		if(vAjuanDDao.getJzid(ewm)!=null){
			return vAjuanDDao.getJzid(ewm).replace("\n", " ");
		}else{
			return null;
		}
	}
	
	/**
	 * 通过二维码获得jh
	 */
	public String getVAjuanDJHByEwm(String ewm){
		return vAjuanDDao.getJzJh(ewm);
	}

	/**
	 * 通过二维码获得aj_ewm
	 */
	public String getVAjuanDAjEwm(String ewm) {
		return vAjuanDDao.getAjEwm(ewm);
	}

	/**
	 * 获得目录树 将pic_ewm设置为案卷的id
	 */
	public List<EasyuiTree> getEasyuiTree(String ewm) {
		if(vAjuanDDao.getJzid(ewm)==null){
			return new ArrayList<EasyuiTree>();
		}
		
		List<VAJuanD> listVAjuanD = getListPicEwmIsNotNull(ewm);
		LinkedHashSet<String> hashSet = new LinkedHashSet<String>();
		for(int i = 0; i < listVAjuanD.size(); i++){
			hashSet.add(listVAjuanD.get(i).getMlmc());
		}
		
		List<EasyuiTree> listEasyuiTree = new ArrayList<EasyuiTree>();
		List<EasyuiTree> root = new ArrayList<EasyuiTree>(); 

		EasyuiTree jzfm = new EasyuiTree(ewm, "卷宗封面", null, "open", "icon-tree-folder-open", true);
		EasyuiTree jzml = new EasyuiTree(getVAjuanDAjEwm(ewm), "卷宗目录", null, "open", "icon-tree-folder-open", true);
		listEasyuiTree.add(jzfm);
		listEasyuiTree.add(jzml);
		Integer i = 0;
		Iterator<String> it = hashSet.iterator();
		while(it.hasNext()){
			EasyuiTree otherNode = new EasyuiTree(i.toString(), it.next().toString(), null, "open", "icon-tree-folder-open", true);
			listEasyuiTree.add(otherNode);
			i++;
		}
		
		EasyuiTree allPicture = new EasyuiTree("allPicture", "全部", listEasyuiTree, "open", true);
		root.add(allPicture);
		return root;
	}

	public List<VAJuanD> getListPicEwmIsNotNull(String jzEwm) {
		return vAjuanDDao.getVAjuanDPicEwmIsNotNull(jzEwm);
	}

	/**
	 * 通过图片的二维码查找页号 没有包含卷宗封面和目录
	 * @param jzEwm
	 * @return
	 */
	public Map<String, List<VAjuanDPicture>> getPictureMap(String jzEwm) {
		Map<String, List<VAjuanDPicture>> picTureMap = new LinkedHashMap<String, List<VAjuanDPicture>>();
		List<VAJuanD> list = getListPicEwmIsNotNull(jzEwm);
		List<VAjuanDPicture> listPicture = null;
		VAjuanDPicture vAjuanDP = null;
		for (Integer i = 0; i < list.size(); i++) {
			VAJuanD vAjuanD = list.get(i);
			if (picTureMap.get(vAjuanD.getMlmc()) == null) {
				listPicture = new ArrayList<VAjuanDPicture>();
				vAjuanDP = new VAjuanDPicture();
				if(getJRawFileByEwm(vAjuanD.getPic_ewm())!=null){
					vAjuanDP.setPicEwm(vAjuanD.getPic_ewm());
				}else{
					vAjuanDP.setPicEwm(RawScanService.NO_PIC_EWM);
				}
				vAjuanDP.setPicVersion(vAjuanD.getPic_version());
				vAjuanDP.setZml(vAjuanD.getZml());
				vAjuanDP.setMlmc(vAjuanD.getMlmc());
				vAjuanDP.setPageNumber(new DecimalFormat("0000").format(i + 1));
				listPicture.add(vAjuanDP);
				picTureMap.put(vAjuanD.getMlmc(), listPicture);
			} else {
				vAjuanDP = new VAjuanDPicture();
				if(getJRawFileByEwm(vAjuanD.getPic_ewm())!=null){
					vAjuanDP.setPicEwm(vAjuanD.getPic_ewm());
				}else{
					vAjuanDP.setPicEwm(RawScanService.NO_PIC_EWM);
				}
				vAjuanDP.setPicVersion(vAjuanD.getPic_version());
				vAjuanDP.setZml(vAjuanD.getZml());
				vAjuanDP.setMlmc(vAjuanD.getMlmc());
				vAjuanDP.setPageNumber(new DecimalFormat("0000").format(i + 1));
				listPicture.add(vAjuanDP);
				picTureMap.put(vAjuanD.getMlmc(), listPicture);
			}
		}
		return picTureMap;
	}
	
	/**
	 * 通过jzewm,mlmc,zml获得当前的mlid
	 */
	public Integer getMlid(String jzEwm, String mlmc, String zml){
		return vAjuanDDao.getMlid(jzEwm, mlmc, zml);
	}
	
	/**
	 * 获取图片信息
	 */
	public JRawfile getImageInfo(String jzEwm){
//		JRawfile jf = new JRawfile();
//		jf.setEwm(jzEwm);
		return jRawFileDao.getRawFileByEwm(jzEwm);
	}
	
	/**
	 * 获取图片的路径
	 */
	 public String getImagePath(String jzEwm){
		 if(getJRawFileByEwm(jzEwm)==null){
			 return rawScanService.getImagePath(getImageInfo(RawScanService.NO_PIC_EWM),jEnvService.getFtpDir());
		 }else{
			 return rawScanService.getImagePath(getImageInfo(jzEwm),jEnvService.getFtpDir());
		 }
	 }
	 
	 /**
	  * 将图片复制到服务器并生成目录结构
	 * @throws IOException 
	  */
	 public String pdfDownLoad(String jzEwm, String serverDir) throws IOException{
		 String pdfFilePath = serverDir+"temp"+File.separator+"bfajyglpt"+".pdf";
		 File pdfFile = new File(pdfFilePath);
		 Image2Pdf pdf = new Image2Pdf();
		 pdf.translate(getPdfPahtList(jzEwm), pdfFile);
		 return pdfFilePath;
	 }
	 
	 public String pdfDownLoad(String jzEwm, String str, String serverDir) throws IOException{
		 String pdfFilePath = serverDir+"temp"+File.separator+"bfajyglpt"+".pdf";
		 File pdfFile = new File(pdfFilePath);
		 Image2Pdf pdf = new Image2Pdf();
		 pdf.translate(getPdfPahtList(jzEwm, str), pdfFile);
		 return pdfFilePath;
	 }
	 /**
	  * 获得所有图片的路径。
	  */
	 public List<PdfPathInfo> getPdfPahtList(String jzEwm){
		 List<PdfPathInfo> pathInfoList = new ArrayList<PdfPathInfo>();
		 int currentPageNumber = 1;
		 String jzFmPath = getImagePath(jzEwm);
		 String jzMlPath = getImagePath(getVAjuanDAjEwm(jzEwm));
		 String jzid = getVAjuanDJzidByEwm(jzEwm);
		 
		 /**
		  * 添加封面和目录
		  */
		 if(jzFmPath!=null){
			 if(!new File(jzFmPath).exists()){
				 PdfPathInfo ppiFm = new PdfPathInfo();
				 ppiFm.setPath(getImagePath(RawScanService.NO_PIC_EWM));
				 ppiFm.setJzid(jzid);
				 ppiFm.setZml(".");
				 ppiFm.setMlmc("卷宗封面");
				 ppiFm.setZmlId("卷宗封面");
				 ppiFm.setCurrentPageNumber(currentPageNumber);
				 pathInfoList.add(ppiFm);
				 currentPageNumber++;
			 }else{
				 PdfPathInfo ppiFm = new PdfPathInfo();
				 ppiFm.setPath(jzFmPath);
				 ppiFm.setJzid(jzid);
				 ppiFm.setZml(".");
				 ppiFm.setMlmc("卷宗封面");
				 ppiFm.setZmlId("卷宗封面");
				 ppiFm.setCurrentPageNumber(currentPageNumber);
				 pathInfoList.add(ppiFm);
				 currentPageNumber++;
			 }
		 }
		 
		 if(jzMlPath!=null){
			 if(!new File(jzMlPath).exists()){
				 PdfPathInfo ppiMl = new PdfPathInfo();
				 ppiMl.setPath(getImagePath(RawScanService.NO_PIC_EWM));
				 ppiMl.setJzid(jzid);
				 ppiMl.setZml(".");
				 ppiMl.setMlmc("卷宗目录");
				 ppiMl.setZmlId("卷宗目录");
				 ppiMl.setCurrentPageNumber(currentPageNumber);
				 pathInfoList.add(ppiMl);
				 currentPageNumber++;
			 }else{
				 PdfPathInfo ppiMl = new PdfPathInfo();
				 ppiMl.setPath(jzMlPath);
				 ppiMl.setJzid(jzid);
				 ppiMl.setZml(".");
				 ppiMl.setMlmc("卷宗目录");
				 ppiMl.setZmlId("卷宗目录");
				 ppiMl.setCurrentPageNumber(currentPageNumber);
				 pathInfoList.add(ppiMl);
				 currentPageNumber++;
			 }
		 }
		 
		 /**
		  * 添加其他的图片
		  */
		 List<VAJuanD> vdList = vAjuanDDao.getVAjuanDPicEwmIsNotNull(jzEwm);
		 PdfPathInfo ppi = null;
		 for(int i = 0; i<vdList.size(); i++){
			 VAJuanD vd = vdList.get(i);
			 if(getImagePath(vd.getPic_ewm())!=null){
				 if(!new File(getImagePath(vd.getPic_ewm())).exists()){
					 ppi = new PdfPathInfo();
					 ppi.setJzid(jzid);
					 ppi.setPath(getImagePath(RawScanService.NO_PIC_EWM));
					 ppi.setMlmc(vd.getMlmc());
					 ppi.setZml(vd.getZml());
					 ppi.setZmlId(vd.getZml());
					 ppi.setCurrentPageNumber(currentPageNumber);
					 pathInfoList.add(ppi);
					 currentPageNumber++;
				 }else{
					 ppi = new PdfPathInfo();
					 ppi.setJzid(jzid);
					 ppi.setPath(getImagePath(vd.getPic_ewm()));
					 ppi.setMlmc(vd.getMlmc());
					 ppi.setZml(vd.getZml());
					 ppi.setZmlId(vd.getZml());
					 ppi.setCurrentPageNumber(currentPageNumber);
					 pathInfoList.add(ppi);
					 currentPageNumber++;
				 }
			 }
		 }
		 return pathInfoList;
	 }
	 
	 /**
	  * 获得部分图片的路径。
	  */
	 public List<PdfPathInfo> getPdfPahtList(String jzEwm, String str){
		 List<PdfPathInfo> pathInfoList = new ArrayList<PdfPathInfo>();
		 String mlmc[] = str.split(";");
		 String jzid = getVAjuanDJzidByEwm(jzEwm);
		 int currentPageNumber = 1;
		 /**
		  * 添加封面或目录
		  */
		 if(mlmc[0].trim().equals("卷宗封面")){
			 String jzFmPath = getImagePath(jzEwm);
			 if(null!=jzFmPath){
				 if(new File(jzFmPath).exists()){
					 PdfPathInfo ppiFm = new PdfPathInfo();
					 ppiFm.setPath(jzFmPath);
					 ppiFm.setJzid(jzid);
					 ppiFm.setZml(".");
					 ppiFm.setMlmc("卷宗封面");
					 ppiFm.setZmlId("卷宗封面");
					 ppiFm.setCurrentPageNumber(currentPageNumber);
					 pathInfoList.add(ppiFm);
					 currentPageNumber++;
				 }else{
					 PdfPathInfo ppiFm = new PdfPathInfo();
					 ppiFm.setPath(getImagePath(RawScanService.NO_PIC_EWM));
					 ppiFm.setJzid(jzid);
					 ppiFm.setZml(".");
					 ppiFm.setMlmc("卷宗封面");
					 ppiFm.setZmlId("卷宗封面");
					 ppiFm.setCurrentPageNumber(currentPageNumber);
					 pathInfoList.add(ppiFm);
					 currentPageNumber++;
				 }
			 }
		 }
		 /**
		  * 添加目录
		  */
		 if(mlmc[0].trim().equals("卷宗目录")){
			 String jzMlPath = getImagePath(getVAjuanDAjEwm(jzEwm));
			 if(null!=jzMlPath){
				 if(new File(jzMlPath).exists()){
					 PdfPathInfo ppiMl = new PdfPathInfo();
					 ppiMl.setPath(jzMlPath);
					 ppiMl.setJzid(jzid);
					 ppiMl.setZml(".");
					 ppiMl.setMlmc("卷宗目录");
					 ppiMl.setZmlId("卷宗目录");
					 ppiMl.setCurrentPageNumber(currentPageNumber);
					 pathInfoList.add(ppiMl);
					 currentPageNumber++;
				 }else{
					 PdfPathInfo ppiMl = new PdfPathInfo();
					 ppiMl.setPath(getImagePath(RawScanService.NO_PIC_EWM));
					 ppiMl.setJzid(jzid);
					 ppiMl.setZml(".");
					 ppiMl.setMlmc("卷宗目录");
					 ppiMl.setZmlId("卷宗目录");
					 ppiMl.setCurrentPageNumber(currentPageNumber);
					 pathInfoList.add(ppiMl);
					 currentPageNumber++;
				 }
			 }
		 }
		 
		 if(mlmc.length>2&&mlmc[1].trim().equals("卷宗目录")){
			 String jzMlPath = getImagePath(getVAjuanDAjEwm(jzEwm));
			 if(null!=jzMlPath){
				 if(new File(jzMlPath).exists()){
					 PdfPathInfo ppiMl = new PdfPathInfo();
					 ppiMl.setPath(jzMlPath);
					 ppiMl.setJzid(jzid);
					 ppiMl.setZml(".");
					 ppiMl.setMlmc("卷宗目录");
					 ppiMl.setZmlId("卷宗目录");
					 ppiMl.setCurrentPageNumber(currentPageNumber);
					 pathInfoList.add(ppiMl);
					 currentPageNumber++;
				 }else{
					 PdfPathInfo ppiMl = new PdfPathInfo();
					 ppiMl.setPath(getImagePath(RawScanService.NO_PIC_EWM));
					 ppiMl.setJzid(jzid);
					 ppiMl.setZml(".");
					 ppiMl.setMlmc("卷宗目录");
					 ppiMl.setZmlId("卷宗目录");
					 ppiMl.setCurrentPageNumber(currentPageNumber);
					 pathInfoList.add(ppiMl);
					 currentPageNumber++;
				 }
			 }
		 }
		 
		 /**
		  * 添加其他的图片
		  */
		 List<VAJuanD> vdList = vAjuanDDao.getVAjuanDPicEwmIsNotNull(jzEwm);
		 Map<String, List<VAJuanD>> mlmcMap = convertAlltoPart(vdList);
		 List<VAJuanD> convertList = null;
		 for(int k=0;k<mlmc.length;k++){
			 convertList = new ArrayList<VAJuanD>();
			 PdfPathInfo ppi = null;
			 if(mlmcMap.get(mlmc[k])!=null){
				 convertList = mlmcMap.get(mlmc[k]);
				 for(int j=0; j<convertList.size(); j++){
					 VAJuanD vd = convertList.get(j);
					 if(getImagePath(vd.getPic_ewm())!=null){
						 if(new File(getImagePath(vd.getPic_ewm())).exists()){
							 ppi = new PdfPathInfo();
							 ppi.setJzid(jzid);
							 ppi.setPath(getImagePath(vd.getPic_ewm()));
							 ppi.setMlmc(vd.getMlmc());
							 ppi.setZml(vd.getZml());
							 ppi.setZmlId(vd.getZml());
							 ppi.setCurrentPageNumber(currentPageNumber);
							 pathInfoList.add(ppi);
							 currentPageNumber++;
						 }else{
							 ppi = new PdfPathInfo();
							 ppi.setJzid(jzid);
							 ppi.setPath(getImagePath(RawScanService.NO_PIC_EWM));
							 ppi.setMlmc(vd.getMlmc());
							 ppi.setZml(vd.getZml());
							 ppi.setZmlId(vd.getZml());
							 ppi.setCurrentPageNumber(currentPageNumber);
							 pathInfoList.add(ppi);
							 currentPageNumber++;
						 }
					 }
				 }
			 }
		 }
		 return pathInfoList;
	 }
	 
	 private Map< String, List<VAJuanD>> convertAlltoPart(List<VAJuanD> vdList){
		 Map< String, List<VAJuanD>> mlmcMap = new HashMap<>();
		 List<VAJuanD> list = null;
		 for(int i=0;i<vdList.size();i++){
			 VAJuanD vd = vdList.get(i);
			 if(mlmcMap.get(vd.getMlmc()) == null){
				 list = new ArrayList<VAJuanD>();
				 list.add(vd);
				 mlmcMap.put(vd.getMlmc(), list);
			 }else{
				 list.add(vd);
				 mlmcMap.put(vd.getMlmc(), list);
			 }
		 }
		 return mlmcMap;
	 }
	 /**
	  * 通过二维码查找照片是否存在
	  */
	 public JRawfile getJRawFileByEwm(String ewm){
//		 JRawfile jRawFile = new JRawfile();
//		 jRawFile.setEwm(ewm);
		 return jRawFileDao.getRawFileByEwm(ewm);
	 }
}
