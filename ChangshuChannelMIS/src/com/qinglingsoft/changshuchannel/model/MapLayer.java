package com.qinglingsoft.changshuchannel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class MapLayer {
	@Id
	private Integer id;
	@Column(name="NameCHS")
	private String name;
	private String maplayerName;
	private String tableName;
	private String idName;
	@Transient
	private boolean defaultSelected;

	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}

	public boolean isDefaultSelected() {
		return defaultSelected;
	}

	public String getMaplayerName() {
		return maplayerName;
	}
	public String getTableName() {
		return tableName;
	}
	public String getIdName() {
		return idName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		MapLayer other = (MapLayer) obj;
		if (id == null) {
			return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
