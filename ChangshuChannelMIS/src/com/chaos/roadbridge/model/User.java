package com.chaos.roadbridge.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	public static final String SUPER_ADMIN_LOGIN_NAME = "!superadmin";
	public static final String GUEST_LOGIN_NAME = "!guest";

	public enum Role {
		ADMIN("管理员"), USER("普通用户");
		private String text;

		Role(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}

	@Id
	private String loginName;
	private String name;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String deptCode;

	public User() {
	}

	public User(String loginName, String name, Role role) {
		super();
		this.loginName = loginName;
		this.name = name;
		this.role = role;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * 部门代码无效部分长度数组，省级10位，市级8位，县级6位，乡级3位
	 */
	static private final int[] deptCodeNonactiveDigits = { 10, 8, 6, 3 };

	/**
	 * @return 部门代码有效部分
	 */
	public String getDeptCodeActivePart() {
		if (deptCode == null) {
			return null;
		}
		int deptCodeLength = deptCode.length();
		for (int ignoreDigits : deptCodeNonactiveDigits) {
			int activeDigits = deptCodeLength - ignoreDigits;
			if (Integer.parseInt(deptCode.substring(activeDigits,
					deptCodeLength)) != 0) {
				return deptCode.substring(0, activeDigits);
			}
		}
		return deptCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (loginName == null) {
			return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		return true;
	}

	public boolean isSuperAdmin() {
		return isAdmin() && SUPER_ADMIN_LOGIN_NAME.equals(this.loginName);
	}

	public boolean isGuest() {
		return GUEST_LOGIN_NAME.equals(this.loginName);
	}

	public boolean isAdmin() {
		return this.role == Role.ADMIN;
	}
}
