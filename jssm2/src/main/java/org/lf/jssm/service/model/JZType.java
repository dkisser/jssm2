package org.lf.jssm.service.model;

public enum JZType {
	历史("ls"), 即时("js"), 归档("gd");
	public String type;

	JZType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void settype(String type) {
		this.type = type;
	}
}
