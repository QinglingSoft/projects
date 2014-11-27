package com.chaos.roadbridge.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
@IdClass(CodeId.class)
public class Code {
	@Id
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private CodeTable codeTable;
	@Id
	private String value;
	private String meaning;
	private Integer level;
	@ManyToOne(optional = true)
	@JoinColumns( {
			@JoinColumn(name = "codeTable_name", insertable = false, updatable = false),
			@JoinColumn(name = "parent_value", insertable = false, updatable = false) })
	private Code parent;
	@OneToMany(mappedBy = "parent")
	@OrderBy("value")
	private List<Code> children;

	public CodeTable getCodeTable() {
		return codeTable;
	}

	public void setCodeTable(CodeTable codeTable) {
		this.codeTable = codeTable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Code getParent() {
		return parent;
	}

	public void setParent(Code parent) {
		this.parent = parent;
	}

	public List<Code> getChildren() {
		return children;
	}

	public void setChildren(List<Code> children) {
		this.children = children;
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
		Code other = (Code) obj;
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
		return "Code(" + value + "@" + codeTable + ")";
	}
}