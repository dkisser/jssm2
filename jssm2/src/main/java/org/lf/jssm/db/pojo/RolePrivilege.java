package org.lf.jssm.db.pojo;


public class RolePrivilege {

	// 新增测试用
	public RolePrivilege(String role_id, String role_name, Integer priv_id,
			String priv_name, String url, String iconClass, String keyName,
			Integer pid, Integer level, Integer flag, Boolean checked) {
		super();
		this.priv_id = priv_id;
		this.priv_name = priv_name;
		this.url = url;
		this.iconClass = iconClass;
		this.keyName = keyName;
		this.pid = pid;
		this.level = level;
		this.flag = flag;
		this.checked = checked;
		
		
		
		
	}

	// 新增
	public RolePrivilege() {

	}

	// coming from chu_menu
	private Integer priv_id; // chu_menu.id

	private String priv_name;

	private String url;

	private String iconClass;

	private String keyName;

	private Integer pid;

	private Integer level;

	private Integer flag;

	private Boolean checked; // 当前角色是否拥有该权限

	public Integer getPriv_id() {
		return priv_id;
	}

	public void setPriv_id(Integer priv_id) {
		this.priv_id = priv_id;
	}

	public String getPriv_name() {
		return priv_name;
	}

	public void setPriv_name(String priv_name) {
		this.priv_name = priv_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

}
