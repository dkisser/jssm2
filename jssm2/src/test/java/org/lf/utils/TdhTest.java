package org.lf.utils;

import java.io.File;
import java.io.PrintWriter;

import org.junit.Test;
import org.lf.utils.servlet.WebServiceTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TdhTest {
	private static final Logger logger = LoggerFactory.getLogger(TdhTest.class);

	/**
	 * 通过通达海接口，获得案件信息
	 */
	@Test
	public void getCaseInfo() {
		String dsr, ah,larq;
		dsr = null;
		ah = null;
		larq="20150101-20150205";
		PrintWriter pw = null;
		try {
			String requestXML = WebServiceTool.requestXml4Case(ah, dsr,larq);
			String caseInfo = WebServiceTool.szftService.getPlAj(
					WebServiceTool.userid, WebServiceTool.pwd, requestXML);
			File file = new File("D:\\jssm\\caseinfo\\" + dsr + "20150101-20150205.txt");
			if (file.getParent() != null
					&& !new File(file.getParent()).exists()) {
				new File(file.getParent()).mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			pw = new PrintWriter(file);
			pw.print(caseInfo);
			pw.close();
		} catch (Exception e) {
			logger.error("读取通达海数据出错", e);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
