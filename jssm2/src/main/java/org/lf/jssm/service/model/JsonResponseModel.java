package org.lf.jssm.service.model;

/**
 * ajax请求时，返回json字符串，用于接收数据的对象
 * 
 * @author sunwill
 *
 */
public class JsonResponseModel {
	private String msgInfo;
	private String successMsgInfo;

	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo == null ? null : msgInfo.trim();
	}

	public String getSuccessMsgInfo() {
		return successMsgInfo;
	}

	public void setSuccessMsgInfo(String successMsgInfo) {
		this.successMsgInfo = successMsgInfo == null ? null : successMsgInfo
				.trim();
	}
}
