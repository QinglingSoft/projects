package com.qinglingsoft.changshuchannel.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class CodeTable {
	@Id
	private String name;
	private String description;
	@OneToMany(mappedBy = "codeTable", fetch = FetchType.EAGER)
	@OrderBy("value")
	@MapKeyColumn(name = "value")
	private Map<String, Code> codes = new HashMap<String, Code>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, Code> getCodes() {
		return codes;
	}

	public void setCodes(Map<String, Code> codes) {
		this.codes = codes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CodeTable other = (CodeTable) obj;
		if (name == null) {
			return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CodeTable(" + name + ")";
	}

}
