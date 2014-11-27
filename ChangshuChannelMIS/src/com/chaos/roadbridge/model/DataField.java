package com.chaos.roadbridge.model;

import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(DataFieldId.class)
public class DataField {
	public static enum Operator {
		EQ("等于"), LIKE("包含"), LE("≤"), GE("≥"), IN("∈");
		private String text;

		Operator(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}

	public static enum Type {
		STRING(String.class, Operator.EQ, Operator.LIKE), FILE(byte[].class), DATE(
				Date.class, Operator.EQ, Operator.LE, Operator.GE), DATETIME(
				Date.class, Operator.GE, Operator.LE), NUMBER(Number.class,
				Operator.EQ, Operator.GE, Operator.LE), TEXT(String.class,
				Operator.LIKE), CODE(String.class, Operator.IN);
		private Class<?> javaType;
		private EnumSet<Operator> supportOperators;

		Type(Class<?> javaType, Operator... operators) {
			this.javaType = javaType;
			if (operators.length > 0) {
				this.supportOperators = EnumSet
						.copyOf(Arrays.asList(operators));
			} else {
				this.supportOperators = EnumSet.noneOf(Operator.class);
			}

		}

		public Class<?> getJavaType() {
			return javaType;
		}

		public EnumSet<Operator> getSupportOperators() {
			return supportOperators;
		}

	}

	@Id
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private DataTable dataTable;
	@Id
	private String name;
	private Integer orderWeight;// 排序号，从小到大，不需要连续
	private boolean visible;// 可见性
	private boolean editable;// 可编辑
	private boolean nullable;// 可为空
	private boolean primaryKey;// 是否主键
	private boolean brief;// 是否摘要字段
	private boolean searchCondition;// 是否为查询条件字段
	private String label;
	@Enumerated(EnumType.STRING)
	private Type type;// 字段类型
	private Integer length;// 字段长度
	private Integer fractionDigits;// 小数位
	@ManyToOne(fetch = FetchType.EAGER)
	private CodeTable codeTable;// 代码表
	private String mediaTypeField;
	private String unit;// 单位

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderWeight() {
		return orderWeight;
	}

	public void setOrderWeight(Integer orderWeight) {
		this.orderWeight = orderWeight;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isBrief() {
		return brief;
	}

	public void setBrief(boolean brief) {
		this.brief = brief;
	}

	public boolean isSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(boolean searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getFractionDigits() {
		return fractionDigits;
	}

	public void setFractionDigits(Integer fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	public CodeTable getCodeTable() {
		return codeTable;
	}

	public void setCodeTable(CodeTable codeTable) {
		this.codeTable = codeTable;
	}

	public String getMediaTypeField() {
		return mediaTypeField;
	}

	public void setMediaTypeField(String mediaTypeField) {
		this.mediaTypeField = mediaTypeField;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean isGeneratedByDatabase() {
		return this.isPrimaryKey() || this.type == Type.NUMBER;
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
		DataField other = (DataField) obj;
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
		return "DataField(" + name + "@" + dataTable + ")";
	}

}
