package com.qinglingsoft.changshuchannel.model;

import java.io.Serializable;

/**
 * DataField实体的ID实体，完全为ORM而存在
 *
 */
public class DataFieldId implements Serializable {

	private static final long serialVersionUID = 5L;
	private String dataTable;
	private String name;

	public DataFieldId() {
	}

	public DataFieldId(String dataTable, String name) {
		this.dataTable = dataTable;
		this.name = name;
	}

	public String getDataTable() {
		return dataTable;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataTable == null) ? 0 : dataTable.hashCode());
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
		DataFieldId other = (DataFieldId) obj;
		if (dataTable == null) {
			return false;
		} else if (!dataTable.equals(other.dataTable))
			return false;
		if (name == null) {
			return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataFieldId(" + name + "@" + dataTable + ")";
	}

}
