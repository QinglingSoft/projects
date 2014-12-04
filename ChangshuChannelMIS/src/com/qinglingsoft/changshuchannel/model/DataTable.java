package com.qinglingsoft.changshuchannel.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class DataTable {
	@Id
	private String name;
	private String catalog;// 栏目，信息、检查、病害
	private String label;// 标签
	private Integer orderWeight;
	private boolean multi;
	@ManyToOne(optional = true)
	private DataTable parent;
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@OrderBy("orderWeight")
	private List<DataTable> children;
	@OneToMany(mappedBy = "dataTable", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@OrderBy("orderWeight")
	private Set<DataField> fields = new HashSet<DataField>();
	transient private List<DataField> primaryKeys;
	transient private List<DataField> briefFields;
	transient private Map<String, List<DataTable>> catalogChildrenMap;
	transient private Set<String> primaryKeyNames;
	transient private Map<String, DataField> fieldsMap;
	transient private List<DataField> searchConditionFields;
	transient private Map<String, DataTable> childrenMap;
	transient private Set<String> briefFieldNames;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getOrderWeight() {
		return orderWeight;
	}

	public void setOrderWeight(Integer orderWeight) {
		this.orderWeight = orderWeight;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public DataTable getParent() {
		return parent;
	}

	public void setParent(DataTable parent) {
		this.parent = parent;
	}

	public List<DataTable> getChildren() {
		return children;
	}

	public Map<String, DataTable> getChildrenMap() {
		if (childrenMap == null) {
			childrenMap = new HashMap<String, DataTable>();
			for (DataTable child : children) {
				childrenMap.put(child.getName(), child);
			}
		}
		return childrenMap;
	}

	public void setChildren(List<DataTable> children) {
		this.children = children;
	}

	public Set<DataField> getFields() {
		return fields;
	}

	public void setFields(Set<DataField> dataFields) {
		this.fields = dataFields;
	}

	public Set<String> getPrimaryKeyNames() {
		if (primaryKeyNames == null) {
			primaryKeyNames = new HashSet<String>();
			for (DataField field : getPrimaryKeys()) {
				primaryKeyNames.add(field.getName());
			}
		}
		return primaryKeyNames;
	}

	public List<DataField> getPrimaryKeys() {
		if (primaryKeys == null) {
			primaryKeys = new ArrayList<DataField>();
			for (DataField field : this.fields) {
				if (field.isPrimaryKey()) {
					primaryKeys.add(field);
				}
			}
		}
		return primaryKeys;
	}

	public List<DataField> getBriefFields() {
		if (briefFields == null) {
			briefFields = new ArrayList<DataField>();
			for (DataField field : this.fields) {
				if (field.isBrief()) {
					briefFields.add(field);
				}
			}
		}
		return briefFields;
	}

	public List<DataField> getSearchConditionFields() {
		if (searchConditionFields == null) {
			searchConditionFields = new ArrayList<DataField>();
			for (DataField field : this.fields) {
				if (field.isSearchCondition()) {
					searchConditionFields.add(field);
				}
			}
		}
		return searchConditionFields;
	}

	public Map<String, List<DataTable>> getCatalogChildrenMap() {
		if (catalogChildrenMap == null) {
			buildCatalogChildrenMap();
		}
		return catalogChildrenMap;
	}

	private void buildCatalogChildrenMap() {
		HashMap<String, List<DataTable>> ccm = new HashMap<>();
		for (DataTable child : children) {
			List<DataTable> catalogChildren = ccm.get(child.getCatalog());
			if (catalogChildren == null) {
				catalogChildren = new LinkedList<DataTable>();
				ccm.put(child.getCatalog(), catalogChildren);
			}
			catalogChildren.add(child);
		}
		catalogChildrenMap = Collections.unmodifiableMap(ccm);
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
		DataTable other = (DataTable) obj;
		if (name == null) {
			return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataTable(" + name + ")";
	}

	public Map<String, DataField> getFieldsMap() {
		if (fieldsMap == null) {
			fieldsMap = new HashMap<String, DataField>();
			for (DataField field : fields) {
				fieldsMap.put(field.getName(), field);
			}
		}
		return fieldsMap;
	}

	public DataField getField(String fieldName) {
		return getFieldsMap().get(fieldName);
	}

	public Set<String> getBriefFieldNames() {
		if (briefFieldNames == null) {
			briefFieldNames = new HashSet<String>();
			for (DataField field : getBriefFields()) {
				briefFieldNames.add(field.getName());
			}
		}
		return briefFieldNames;
	}

}
