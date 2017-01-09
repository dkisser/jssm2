package org.lf.jssm.db.pojo;

public class ChuUser {
	
	private Integer userId;

	private String upw;

	private String uname;

	private String name;

	private String zwdm;

	private String email;

	private String salt;

	private Short verify;

	private String token;

	private String bmdm;

	private Integer roleId;

	private String type;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUpw() {
		return upw;
	}

	public void setUpw(String upw) {
		this.upw = upw;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Short getVerify() {
		return verify;
	}

	public void setVerify(Short verify) {
		this.verify = verify;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getType() {
		return type;
	}

	public String getZwdm() {
		return zwdm;
	}

	public void setZwdm(String zwdm) {
		this.zwdm = zwdm;
	}

	public String getBmdm() {
		return bmdm;
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	public void setType(String type) {
		this.type = type;
	}
}