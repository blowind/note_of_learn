package com.zxf.zxfbatis.simple.model;

public class SysRolePrivilege {
	private Long roleId;
	private Long privilegeId;

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}
}
