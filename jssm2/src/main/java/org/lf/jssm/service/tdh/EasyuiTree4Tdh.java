package org.lf.jssm.service.tdh;

import java.util.List;

public class EasyuiTree4Tdh {

	private String id;
	private String text;
	private List<EasyuiTree4Tdh> children;
	private String state;
//	private String iconCls;
	private Boolean checked;

	public Boolean isChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

//	public String getIconCls() {
//		return iconCls;
//	}
//
//	public void setIconCls(String iconCls) {
//		this.iconCls = iconCls;
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<EasyuiTree4Tdh> getChildren() {
		return children;
	}

	public void setChildren(List<EasyuiTree4Tdh> children) {
		this.children = children;
	}

	public EasyuiTree4Tdh(String id, String text, List<EasyuiTree4Tdh> children,
			String state, Boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
		this.state = state;
		this.checked = checked;
	}

	public EasyuiTree4Tdh(String id, String text, List<EasyuiTree4Tdh> children,
			Boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
		this.checked = checked;
	}

	public EasyuiTree4Tdh(String id, String text, List<EasyuiTree4Tdh> children,
			String state, String iconCls, Boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
		this.state = state;
//		this.iconCls = iconCls;
		this.checked = checked;
	}

	public EasyuiTree4Tdh(String text, List<EasyuiTree4Tdh> children,
			String state, String iconCls, Boolean checked) {
		super();
		this.text = text;
		this.children = children;
		this.state = state;
//		this.iconCls = iconCls;
		this.checked = checked;
	}
	
	public EasyuiTree4Tdh() {
	}

}
