package org.lf.jssm.db.pojo;

import java.util.Date;

public class LJz {

	private String id;
	private String jzewm;
	private String operation;
	private Date oper_date;
	private Integer operator_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJzewm() {
		return jzewm;
	}
	public void setJzewm(String jzewm) {
		this.jzewm = jzewm;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Date getOper_date() {
		return oper_date;
	}
	public void setOper_date(Date oper_date) {
		this.oper_date = oper_date;
	}
	public Integer getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(Integer operator_id) {
		this.operator_id = operator_id;
	}
	
	
}
