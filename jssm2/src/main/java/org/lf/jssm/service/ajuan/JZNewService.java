package org.lf.jssm.service.ajuan;

import java.util.List;

import org.lf.jssm.db.dao.AhEwmMapper;
import org.lf.jssm.db.dao.CAQJBMapper;
import org.lf.jssm.db.dao.CSPCXMapper;
import org.lf.jssm.db.dao.CSPJGMapper;
import org.lf.jssm.db.dao.JAjjzMapper;
import org.lf.jssm.db.dao.JAjuanDMapper;
import org.lf.jssm.db.dao.JAjuanMapper;
import org.lf.jssm.db.dao.JJzMapper;
import org.lf.jssm.db.dao.JRawfileMapper;
import org.lf.jssm.db.dao.VAJuanDMapper;
import org.lf.jssm.db.dao.VAjmlMapper;
import org.lf.jssm.db.dao.VMlpcMapper;
import org.lf.jssm.db.pojo.AhEwm;
import org.lf.jssm.db.pojo.CAQJB;
import org.lf.jssm.db.pojo.CSPCX;
import org.lf.jssm.db.pojo.CSPJG;
import org.lf.jssm.db.pojo.JAjjz;
import org.lf.jssm.db.pojo.JAjuan;
import org.lf.jssm.db.pojo.JAjuanD;
import org.lf.jssm.db.pojo.JJZ;
import org.lf.jssm.db.pojo.VAjml;
import org.lf.jssm.db.pojo.VMlpc;
import org.lf.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jsNewService")
public class JZNewService {
	@Autowired
	private CSPCXMapper CSPCXDao;
	@Autowired
	private CSPJGMapper CSPJGDao;
	@Autowired
	private CAQJBMapper CAQJBDao;
	@Autowired
	private JAjuanMapper jajuanDao;
	@Autowired
	private AhEwmMapper ahewmMapper;
	@Autowired
	private VMlpcMapper vmlpcDao;
	@Autowired
	private VAjmlMapper vajmlMapper;
	@Autowired
	private JAjjzMapper jajjzDao;
	@Autowired
	private JJzMapper jjzDao;
	@Autowired
	private JRawfileMapper jRawfileDao;
	@Autowired
	private VAJuanDMapper vAJuandDao;
	@Autowired
	private JAjuanDMapper jAjuanDDao;

	public List<CSPCX> getAllSPCX() {
		return CSPCXDao.selectAll();
	}

	public List<CSPCX> getSPCXByMC(String mc) {
		return CSPCXDao.selectByMC(mc);
	}

	public List<CSPJG> getAllSPJG() {
		return CSPJGDao.selectAll();
	}

	public CSPJG getSPJGByMC(String mc) {
		return CSPJGDao.selectByMC(mc);
	}

	public List<CAQJB> getAllAQJB() {
		return CAQJBDao.selectAll();
	}

	public List<CAQJB> getAQJBByMC(String mc) {
		return CAQJBDao.selectByMC(mc);
	}

	public List<VMlpc> getAllVMlpc() {
		return vmlpcDao.selectAll();
	}

	public VMlpc getMlpcByPCDM(String pcdm) {
		return vmlpcDao.selectByPCDM(pcdm);
	}

	public boolean insertSPJG(String newData) {
		if (CSPJGDao.selectByMC(newData) != null) {
			return false;
		}
		List<CSPJG> spjglist = getAllSPJG();
		Integer size = spjglist.size() + 1;
		String DM = "";
		if (size < 10) {
			DM += "0";
			DM += size.toString();
		} else {
			DM = size.toString();
		}

		String pym = StringUtils.toShortPinYin(newData, 5).toLowerCase();
		if (CSPJGDao.insertCSPJG(DM, newData, pym) == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能:通过传进来的ewm去数据库中查找J_AJUAN表,并返回是否已经存在数据库中
	 * 
	 * @return
	 */
	public boolean checkEwm(String ewm) {
		return jajuanDao.getJajuanByEwm(ewm).size() == 0;
	}

	/**
	 * 功能: 根据二维码查数据库得到ah和jz_ewm
	 * 
	 * @return
	 */
	public AhEwm getAhEwmByEwm(String ewm) {
		return ahewmMapper.getAhEwmByEwm(ewm);
	}

	public List<VAjml> getAjmlListByPCDM(String pcdm) {
		return vajmlMapper.getAjmlByPCDM(pcdm);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean jzNewAciton(JJZ jjz, JAjuan jajuan, JAjjz jajjz) {
		int flag = 0;
		if (jajjzDao.insertAjjz(jajjz.getId(), jajjz.getJz_ewm(),
				jajjz.getAj_ewm()) == 1)
			flag++;
		if (jajuanDao.insertNew(jajuan) == 1)
			flag++;
		if (jjzDao.insertNew(jjz) == 1)
			flag++;
		if (flag == 3)
			return true;
		else
			return false;
	}

	public String findmlEwmstatus(String ajewm) {
//		JRawfile jf = new JRawfile();
//		jf.setEwm(ajewm);
		if (jRawfileDao.getRawFileByEwm(ajewm) == null) {
			return "您输入的二维码在本系统中无对应的扫描文件，请重新输入！";
		} else {
			if (jajjzDao.isAjewmHaveUsed(ajewm).size() != 0) {
				List<JAjjz> jjzlist = jajjzDao.isAjewmHaveUsed(ajewm);
				String showjz_ewm = jjzlist.get(0).getJz_ewm();
				return "您输入的二维码对应的扫描文件已经在编号为" + showjz_ewm + "的卷宗中使用过了，请重新输入！";
			}
			if (vAJuandDao.isAjewmhaveUsed(ajewm).size() != 0) {
				return "您输入的二维码对应的扫描文件不是一个案卷目录，请重新输入！";
			} else {
				return "1";
			}
		}
	}

	public Integer getJajuanDSizeByajewm(String ewm) {
		JAjuanD jad = new JAjuanD();
		jad.setAj_ewm(ewm);
		List<JAjuanD> jadlist = jAjuanDDao.getJadsByCondition(jad);
		if (jadlist != null)
			return jadlist.size();
		else
			return 0;
	}

	public boolean updataOrderJajuanD(String ewm, String[] pageArr) {

		List<JAjuanD> jadlist = jAjuanDDao.selectByajEwmOrder(ewm);
		JAjuan aj = jajuanDao.getJajuanByEwm(ewm).get(0);
		List<VAjml> ajmllist = vajmlMapper.getAjmlByPCDM(aj.getPcdm());
		int begin = 0;
		int end = 0;
		JAjuanD currjad = new JAjuanD();

		// 清空原有的
		for (JAjuanD temajd : jadlist) {
			jAjuanDDao.updateclear(temajd.getEwm());
		}

		for (int i = 0; i < pageArr.length; i++) {
			if (pageArr[i].equals("dadaodai"))
				continue;
			String[] page = pageArr[i].split("-");
			if (page.length > 1) {
				begin = Integer.parseInt(page[0]) - 1;
				end = Integer.parseInt(page[1]) - 1;
			} else {
				begin = Integer.parseInt(page[0]) - 1;
				end = Integer.parseInt(page[0]) - 1;
			}
			for (int j = begin; j <= end; j++) {
				currjad = jadlist.get(j);
				JAjuanD updatajad = new JAjuanD();
				JAjuanD searchjad = new JAjuanD();
				searchjad.setAj_ewm(ewm);
				searchjad.setMldm(ajmllist.get(i).getMldm());
				Integer maxNbbh = jAjuanDDao.getPicMaxNbbh(searchjad);

				updatajad.setEwm(currjad.getEwm());
				updatajad.setMldm(ajmllist.get(i).getMldm());
				if (maxNbbh == null) {
					maxNbbh = 0;
				}
				// 内部编号从1开始，每次加1
				updatajad.setNbbh(maxNbbh + 1);

				// 更新
				jAjuanDDao.updateall(updatajad);
			}
		}
		return false;
	}

	public int updataJzfm(JJZ j_jz) {
		return jjzDao.updatajzfm(j_jz.getJzmc(), j_jz.getJzlx(),
				j_jz.getSpcx1(), j_jz.getSpjg1(), j_jz.getSpcx2(),
				j_jz.getSpjg2(), j_jz.getJbdm(), j_jz.getEwm());
	}

	/**
	 * 快速整理，更新页次
	 * 
	 * @param aj_ewm
	 * @param indexCode
	 * @param syym
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String updataOrderJajuanD4Ks(String aj_ewm, String indexCode,
			Integer syym) {
		if (StringUtils.isEmpty(aj_ewm) || StringUtils.isEmpty(indexCode)
				|| syym == null) {
			return "error";
		}
		List<JAjuanD> jadlist = jAjuanDDao.selectByajEwmOrder(aj_ewm);
		if (jadlist == null || jadlist.size() == 0) {
			return "noPicError";//图片不存在
		}
		List<JAjuan> ajList = jajuanDao.getJajuanByEwm(aj_ewm);
		if (ajList == null || ajList.size() == 0) {
			return "error";
		}
		String[] pageArr = indexCode.split(";");
		if (pageArr == null || pageArr.length == 0) {
			return "error";
		}
		int max = jadlist.size();
		int numCount = 0;
		int lastPageNum = -1;
		for (int i = (pageArr.length - 1); i >= 0; i--) {
			try {
				lastPageNum = Integer.parseInt(pageArr[i]) - syym + 1;// 判断有效页次
				if (lastPageNum > max) {
					return "maxPageError";// 最后一页太大
				}
				numCount++;// 有效页次个数
				if (numCount > 0) {
					break;
				}
			} catch (Exception e) {
			}
		}
		if (numCount == 0) {
			return "error";// 没有有效页码
		}
		// TODO 确定目录顺序和前台显示顺序相同
		List<VAjml> ajmllist = vajmlMapper.getAjmlByPCDM(ajList.get(0)
				.getPcdm());
		if (ajmllist == null || ajmllist.size() == 0) {
			return "error";
		}
		int prePageNum = -1;
		for (int i = (pageArr.length - 1); i >= 0; i--) {
			int currPageNum = -1;
			try {
				currPageNum = Integer.parseInt(pageArr[i]) - syym + 1;// 获得有效页次
			} catch (Exception e) {
				continue;// 无效页码则跳过
			}
			if (currPageNum == -1) {
				return "error";
			}
			String mldm = ajmllist.get(i).getMldm();
			if (prePageNum == -1) {
				// 最后一个页码
				for (int j = currPageNum; j <= max; j++) {
					JAjuanD updatajad = jadlist.get(j - 1);
					updatajad.setMldm(mldm);
					updatajad.setNbbh(j);
					jAjuanDDao.updateall(updatajad);
				}
				prePageNum = currPageNum;
			} else {
				// 非最后一个页码
				for (int j = currPageNum; j < prePageNum; j++) {
					JAjuanD updatajad = jadlist.get(j - 1);
					updatajad.setMldm(mldm);
					updatajad.setNbbh(j);
					jAjuanDDao.updateall(updatajad);
				}
				prePageNum = currPageNum;
			}
		}
		return "success";
	}
}
