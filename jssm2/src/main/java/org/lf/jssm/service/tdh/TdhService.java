package org.lf.jssm.service.tdh;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.lf.jssm.db.dao.JJzMapper;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.VAJuanDMapper;
import org.lf.jssm.db.pojo.JRawfile;
import org.lf.jssm.db.pojo.VAJuanD;
import org.lf.jssm.service.model.VAjuanDPicture;
import org.lf.jssm.service.raw.RawScanService;
import org.lf.utils.EasyuiDatagrid;
import org.lf.utils.PageNavigator;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tdhService")
public class TdhService {
	@Autowired
	private JJzMapper tdhJzDao;
	@Autowired
	private VAJuanDMapper vAjuanDDao;
	@Autowired
	private JRawfileMapper jRawFileDao;

	/**
	 * 根据案号获得案件列表
	 * 
	 * @param archiveno
	 * @param rows
	 * @param page
	 * @return
	 */
	public EasyuiDatagrid<TdhJz> getCaseList(String archiveno, int rows,
			int page) {
		EasyuiDatagrid<TdhJz> data = new EasyuiDatagrid<TdhJz>();
		List<TdhJz> list = tdhJzDao.selectByAh4Tdh(archiveno);
		if (list != null && list.size() > 0) {
			PageNavigator<TdhJz> nav = new PageNavigator<TdhJz>(list, rows);
			data.setRows(nav.getPage(page));
			data.setTotal(list.size());
		} else {
			data.setRows(new ArrayList<TdhJz>());
			data.setTotal(0);
		}
		return data;
	}

	/**
	 * 获得卷宗二维码
	 * 
	 * @param tj
	 * @return
	 */
	public String getJzEwm4Tdh(TdhJz tj) {
		return tdhJzDao.getJzEwm4Tdh(tj);
	}

	/**
	 * 初始化卷宗目录树
	 * 
	 * @param jzEwm
	 * @return
	 */
	public List<EasyuiTree4Tdh> getEasyuiTree4Tdh(String ewm) {
		if (StringUtils.isEmpty(ewm)) {
			return new ArrayList<EasyuiTree4Tdh>();
		}
		List<VAJuanD> listVAjuanD = getListPicEwmIsNotNull(ewm);
		if (listVAjuanD == null || listVAjuanD.size() == 0) {
			return new ArrayList<EasyuiTree4Tdh>();
		}
		LinkedHashSet<String> hashSet = new LinkedHashSet<String>();
		for (int i = 0; i < listVAjuanD.size(); i++) {
			hashSet.add(listVAjuanD.get(i).getMlmc());
		}

		List<EasyuiTree4Tdh> listEasyuiTree4Tdh = new ArrayList<EasyuiTree4Tdh>();
		List<EasyuiTree4Tdh> root = new ArrayList<EasyuiTree4Tdh>();

		EasyuiTree4Tdh jzfm = new EasyuiTree4Tdh(ewm, "卷宗封面", null, "open",
				"icon-tree-folder-open", true);
		EasyuiTree4Tdh jzml = new EasyuiTree4Tdh(vAjuanDDao.getAjEwm(ewm),
				"卷宗目录", null, "open", "icon-tree-folder-open", true);
		listEasyuiTree4Tdh.add(jzfm);
		listEasyuiTree4Tdh.add(jzml);
		Integer i = 0;
		Iterator<String> it = hashSet.iterator();
		while (it.hasNext()) {
			EasyuiTree4Tdh otherNode = new EasyuiTree4Tdh(i.toString(), it
					.next().toString(), null, "open", "icon-tree-folder-open",
					true);
			listEasyuiTree4Tdh.add(otherNode);
			i++;
		}

		EasyuiTree4Tdh allPicture = new EasyuiTree4Tdh("allPicture", "全部",
				listEasyuiTree4Tdh, "open", true);
		root.add(allPicture);
		return root;
	}

	/**
	 * 通过二维码获得jzid
	 */
	public String getVAjuanDJzidByEwm(String ewm) {
		if (vAjuanDDao.getJzid(ewm) != null) {
			return vAjuanDDao.getJzid(ewm).replace("\n", " ");
		} else {
			return null;
		}
	}

	/**
	 * 通过二维码查找照片是否存在
	 */
	public JRawfile getJRawFileByEwm(String ewm) {
		if (StringUtils.isEmpty(ewm)) {
			return null;
		}
		return jRawFileDao.getRawFileByEwm(ewm);
	}

	/**
	 * 通过二维码获得aj_ewm
	 */
	public String getVAjuanDAjEwm(String ewm) {
		return vAjuanDDao.getAjEwm(ewm);
	}

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
				if (getJRawFileByEwm(vAjuanD.getPic_ewm()) != null) {
					vAjuanDP.setPicEwm(vAjuanD.getPic_ewm());
				} else {
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
				if (getJRawFileByEwm(vAjuanD.getPic_ewm()) != null) {
					vAjuanDP.setPicEwm(vAjuanD.getPic_ewm());
				} else {
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

	public List<VAJuanD> getListPicEwmIsNotNull(String jzEwm) {
		return vAjuanDDao.getVAjuanDPicEwmIsNotNull(jzEwm);
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
	 * 获得图片的缩略图在服务器上的存储目录
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
				+ jf.getEwm() + RawScanService.SMALL_FILE_SUFFIX + "."
				+ jf.getFileSuffix();
		return imgPath;
	}
}
