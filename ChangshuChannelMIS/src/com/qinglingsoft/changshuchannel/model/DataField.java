package com.qinglingsoft.changshuchannel.model;

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

/**
 * 业务表字段描述
 */
@Entity
@IdClass(DataFieldId.class)
public class DataField {
	/**
	 * 用于查询条件的操作符
	 */
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

	/**
	 * 字段类型，人本类型，与显示有关，不等同于数据库类型或编程语言类型
	 *
	 */
	public static enum Type {
		/**
		 * STRING为短字符串，一般用输入框（input标签）展示； TEXT为长字符串，一般用文本框（textarea标签）展示
		 */
		STRING(String.class, Operator.EQ, Operator.LIKE), FILE(byte[].class), DATE(
				Date.class, Operator.EQ, Operator.LE, Operator.GE), DATETIME(
				Date.class, Operator.GE, Operator.LE), NUMBER(Number.class,
				Operator.EQ, Operator.GE, Operator.LE), TEXT(String.class,
				Operator.LIKE), CODE(String.class, Operator.IN);
		/**
		 * 相对应的Java类型
		 */
		private Class<?> javaType;
		/**
		 * 支持的查询条件操作符
		 */
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

	/**
	 * 所属数据表
	 */
	@Id
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private DataTable dataTable;
	/**
	 * 字段名称
	 */
	@Id
	private String name;
	/**
	 * 配需权重，小的优先，不必连续，初始设置时跨度为10，以便于日后调整安插
	 */
	private Integer orderWeight;
	/**
	 * 字段在详情展示页是否可见，一般无意义主键和外键不必显示
	 */
	private boolean visible;
	/**
	 * 字段是否可编辑，日后不可修改的字段即为不可编辑，例如有意义主键。不可编辑字段必须在记录创建时就把内容填充好
	 */
	private boolean editable;
	/**
	 * 字段是否可为空
	 */
	private boolean nullable;
	/**
	 * 字段是否为所属表的主键
	 */
	private boolean primaryKey;
	/**
	 * 字段是否为所属表的摘要字段，所属表的列表或树状展示时将列出摘要字段
	 */
	private boolean brief;
	/**
	 * 是否为查询条件字段
	 */
	private boolean searchCondition;
	/**
	 * 字段在界面上显示出的文字
	 */
	private String label;
	/**
	 * 字段的人本类型
	 */
	@Enumerated(EnumType.STRING)
	private Type type;
	/**
	 * 字段长度，用于显示时先定输入框长度，避免超出数据库允许范围
	 */
	private Integer length;
	/**
	 * 字段小数位，用于展示时规定截断
	 */
	private Integer fractionDigits;
	/**
	 * 字段引用的代码表，当type为CODE时有意义
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private CodeTable codeTable;
	/**
	 * 媒体类型字段，暂时想不起来是什么意义了，与图片等多媒体信息有关
	 */
	private String mediaTypeField;
	/**
	 * 字段单位，用于显示
	 */
	private String unit;

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

	/**
	 * 目前认为，主键且为数字型的字段应由数据库生成。如日后有GUID等其它类型的库生成字段，还需修改此段逻辑。
	 * 
	 * @return 该字段是否需由数据库生成
	 */
	public boolean isGeneratedByDatabase() {
		return this.isPrimaryKey() && this.type == Type.NUMBER;
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
