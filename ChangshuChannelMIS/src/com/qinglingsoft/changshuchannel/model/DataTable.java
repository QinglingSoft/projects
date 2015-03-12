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

/**
 * 业务数据表描述实体
 *
 */
@Entity
public class DataTable {
	/**
	 * 数据库中的表名，作为有意义的主键
	 */
	@Id
	private String name;
	/**
	 * 本表所述的栏目，父表可能关联很多字表，但有的页面仅需要展示其中一部分子表，便将子表按栏目划分，展示时只列出特定栏目的子表
	 */
	private String catalog;// 所属栏目
	/**
	 * 用于页面展示的文字
	 */
	private String label;
	/**
	 * 排序权重，不必连续，初始设置时以10为跨度，以便于日后调整安插
	 */
	private Integer orderWeight;
	/**
	 * 与父表是否为多对一关系，在本项目中目前有父表的都是多对一，该字段暂时没有发挥作用
	 */
	private boolean multi;
	/**
	 * 本表的父表对象
	 */
	@ManyToOne(optional = true)
	private DataTable parent;
	/**
	 * 本表的子表列表
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@OrderBy("orderWeight")
	private List<DataTable> children;
	/**
	 * 本表包含的字段列表
	 */
	@OneToMany(mappedBy = "dataTable", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@OrderBy("orderWeight")
	private Set<DataField> fields = new HashSet<DataField>();
	/**
	 * 主键字段列表，不持久化，第一次访问时通过对fields属性分析产生，为不在每次访问时都轮询fields列表进行分析而存在
	 */
	transient private List<DataField> primaryKeys;
	/**
	 * 摘要字段列表，不持久化，第一次访问时通过对fields属性分析产生，为不在每次访问时都轮询fields列表进行分析而存在
	 */
	transient private List<DataField> briefFields;
	/**
	 * 栏目-子表映射关系，不持久化，第一次访问时通过对fields属性分析产生，为不在每次访问时都轮询fields列表进行分析而存在
	 */
	transient private Map<String, List<DataTable>> catalogChildrenMap;
	/**
	 * 主键名集合，不持久化，第一次访问时通过对fields属性分析产生，为不在每次访问时都轮询fields列表进行分析而存在
	 */
	transient private Set<String> primaryKeyNames;
	/**
	 * 字段名-字段实体映射表，不持久化，第一次访问时通过对fields属性分析产生，为不在每次访问时都轮询fields列表进行分析而存在
	 */
	transient private Map<String, DataField> fieldsMap;
	/**
	 * 查询条件字段列表，不持久化，第一次访问时通过对fields属性分析产生，为不在每次访问时都轮询fields列表进行分析而存在
	 * 过往项目曾有需求，在不修改代码的情况下，通过调整数据库来决定哪些字段作为查询条件
	 */
	transient private List<DataField> searchConditionFields;
	/**
	 * 子表名-字表实体映射关系，不持久化，第一次访问时通过对fields属性分析产生，为不在每次访问时都轮询fields列表进行分析而存在
	 */
	transient private Map<String, DataTable> childrenMap;
	/**
	 * 摘要字段集合，不持久化，第一次访问时通过对fields属性分析产生，为不在每次访问时都轮询fields列表进行分析而存在
	 */
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
			for (DataTable child : children) {
				for (DataField field : child.getFields()) {
					if (field.isSearchCondition()) {
						searchConditionFields.add(field);
					}
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
