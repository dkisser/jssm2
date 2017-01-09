package org.lf.utils.servlet;

import java.io.InputStream;
import java.util.Properties;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.lf.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServiceTool {
	private static final Logger logger = LoggerFactory
			.getLogger(WebServiceTool.class);
	/**
	 * 法院代码，H11表示汉阳法院
	 */
	public static String fydm;
	/**
	 * 访问通达海webService所需的用户名
	 */
	public static String userid;
	/**
	 * 访问通达海webService所需的密码
	 */
	public static String pwd;
	/**
	 * CXF框架工厂类
	 */
	private static final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

	public static SzftWebService szftService;

	static {
		InputStream inputStream = WebServiceTool.class.getClassLoader()
				.getResourceAsStream("jssm.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
			fydm = p.getProperty("tdh.fydm");
			userid = p.getProperty("tdh.userid");
			pwd = p.getProperty("tdh.pwd");
			// 设置服务接口
			factory.setServiceClass(SzftWebService.class);
			// 设置服务地址
			factory.setAddress(p.getProperty("tdh.serviceUrl"));
			szftService = (SzftWebService) factory.create();
		} catch (Exception e1) {
			logger.error("配置webService出错", e1);
			throw new RuntimeException(e1);
		}

	}
	/**
	 * 根据案号或者当事人查询
	 * @param ah
	 * @param dsr
	 * @return
	 * @throws Exception
	 */
	public static final String getCaseXml(String ah, String dsr)
			throws Exception {
		return StringUtils.base64Decode(szftService.getPlAj(userid, pwd,
				requestXml4Case(ah, dsr,null)));
	}
	
	/**
	 * 重载     根据条件查询
	 * @param ah
	 * @param dsr
	 * @param larq
	 * @param jarq
	 * @param sfja
	 * @return
	 * @throws Exception
	 */
	public static final String getCaseXml(String ah,String dsr,String larq,String jarq,String status) throws Exception{
		return StringUtils.base64Decode(szftService.getPlAj(userid, pwd,
				requestXml4Case(ah, dsr, larq, jarq, status)));
	}

	/**
	 * 生成查询案件信息的入参xml
	 * 
	 * @param ah
	 *            案号
	 * @param dsr
	 *            当事人
	 * @param larq
	 *            立案日期
	 * @return
	 * @throws Exception
	 */
	public static final String requestXml4Case(String ah, String dsr,
			String larq) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<Request>");
		if (!StringUtils.isEmpty(larq)) {
			sb.append("<LARQ>" + StringUtils.base64Encode(larq) + "</LARQ>");
		}
		sb.append("<ZT></ZT>");
		// 当事人,模糊搜索
		if (!StringUtils.isEmpty(ah)) {
			// 案号,精确搜索
			sb.append("<AH>" + StringUtils.base64Encode(ah) + "</AH>");
		}
		if (!StringUtils.isEmpty(dsr)) {
			sb.append("<DSR>" + StringUtils.base64Encode(dsr) + "</DSR>");
		}
		// 法院分级码 H11表示汉阳法院
		sb.append("<FYDM>" + StringUtils.base64Encode(fydm) + "</FYDM>");
		sb.append("<AHDM></AHDM>");
		sb.append("</Request>");
		return StringUtils.base64Encode(sb.toString());
	}
	
	/**
	 * 生成查询案件信息的入参xml
	 * @param ah    案号
	 * @param dsr   当事人
	 * @param larq  立案日期区间
	 * @param jarq  结案日期区间
	 * @param status  是否结案（状态）
	 * @return
	 * @throws Exception 
	 */
	public static final String requestXml4Case(String ah,String dsr,String larq,String jarq,String status) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<Request>");
		if (!StringUtils.isEmpty(larq)) {
			sb.append("<LARQ>" + StringUtils.base64Encode(larq) + "</LARQ>");   //立案日期区间
		}
		
		if (!StringUtils.isEmpty(jarq)) {
			sb.append("<JARQ>" + StringUtils.base64Encode(jarq) + "</JARQ>");   //结案日期区间
		}
		
		if(!StringUtils.isEmpty(status)){	  //	是否结案（状态）,有个未知状态，必须判断是否为空
			sb.append("<ZT>"+StringUtils.base64Encode(status)+"</ZT>");
		}		
				     				
		// 当事人,模糊搜索
		if (!StringUtils.isEmpty(ah)) {
			// 案号,精确搜索
			sb.append("<AH>" + StringUtils.base64Encode(ah) + "</AH>");
		}
		if (!StringUtils.isEmpty(dsr)) {
			sb.append("<DSR>" + StringUtils.base64Encode(dsr) + "</DSR>");
		}
		// 法院分级码 H11表示汉阳法院
		sb.append("<FYDM>" + StringUtils.base64Encode(fydm) + "</FYDM>");
		sb.append("<AHDM></AHDM>");
		sb.append("</Request>");
		return StringUtils.base64Encode(sb.toString());
	}

}
