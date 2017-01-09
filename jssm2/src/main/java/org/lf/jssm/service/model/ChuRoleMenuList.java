package org.lf.jssm.service.model;

public class ChuRoleMenuList {
    private Integer role_id;

    private String role_name;
    
    private String status;
    
    private String priv_list;
    
    public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriv_list() {
		return priv_list;
	}

	public void setPriv_list(String priv_list) {
		this.priv_list = priv_list;
	}

	
}
