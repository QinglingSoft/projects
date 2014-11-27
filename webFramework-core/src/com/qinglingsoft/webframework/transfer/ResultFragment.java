package com.qinglingsoft.webframework.transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 结果片段，用于分页策略，保存结果总数和指定范围的结果集
 * 
 * @author guoqiang
 * 
 * @param <T>
 *            本结果片段包含的实体类
 */
public class ResultFragment<T> implements Serializable {

	private static final long serialVersionUID = 2L;
	/**
	 * 实体列表片段
	 */
	private List<T> fragment = new ArrayList<T>(0);
	/**
	 * 满足条件的实体总数
	 */
	private long totalCount = 0;

	/**
	 * 建构方法
	 * 
	 * @param totalCount
	 *            实体总数
	 * @param fragment
	 *            列表片段
	 */
	public ResultFragment(long totalCount, List<T> fragment) {
		this.totalCount = totalCount;
		this.fragment = fragment;
	}

	public List<T> getFragment() {
		return fragment;
	}

	public long getTotalCount() {
		return totalCount;
	}
}
