package com.qinglingsoft.changshuchannel.model;

import java.io.Serializable;

/**
 * 代码表条目ID，仅为ORM存在
 *
 */
public class CodeId implements Serializable {

	private static final long serialVersionUID = 4L;
	/**
	 * 所属代码表
	 */
	private String codeTable;
	/**
	 * 代码值
	 */
	private String value;

	public String getCodeTable() {
		return codeTable;
	}

	public void setCodeTable(String codeTable) {
		this.codeTable = codeTable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTable == null) ? 0 : codeTable.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		CodeId other = (CodeId) obj;
		if (codeTable == null) {
			return false;
		} else if (!codeTable.equals(other.codeTable))
			return false;
		if (value == null) {
			return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CodeId(" + value + "@" + codeTable + ")";
	}

}
