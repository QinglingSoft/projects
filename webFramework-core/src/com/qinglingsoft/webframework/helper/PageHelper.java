package com.qinglingsoft.webframework.helper;

import java.util.List;

import com.qinglingsoft.webframework.transfer.ResultFragment;

/**
 * 分页助手，ViewHelper模式应用。负责保存关于分页的信息和获取当前页内的数据，获取数据的功能定义为抽象方法，由子类实现。
 * 
 * @author guoqiang
 * @param <T>
 *            针对类型T的分页助手
 */
public abstract class PageHelper<T> {

	private static int DEFAULT_PAGE_SIZE = 15;
	private static final int CURRENT_PAGE_START = 0;// currentPage属性起始值
	protected int pageSize = DEFAULT_PAGE_SIZE;
	private long totalPages = 1;// 从1开始
	private long totalCount;// 总记录数
	private int currentPage = 0;// 从0开始

	public static void setDefaultPageSize(int defaultPageSize) {
		DEFAULT_PAGE_SIZE = defaultPageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalPages() {
		return totalPages;
	}

	protected void setTotalPages(int totalPage) {
		this.totalPages = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	protected void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageStart() {
		return pageSize * currentPage;
	}

	public boolean isMorePages() {
		return totalPages > 1;
	}

	public boolean isFirstPage() {
		return currentPage <= 0;
	}

	public boolean isLastPage() {
		return currentPage >= totalPages - 1;
	}

	public List<T> getList() {
		ResultFragment<T> resultFragment = getResultFragment();
		totalCount = resultFragment.getTotalCount();
		totalPages = calculateTotalPage();
		return resultFragment.getFragment();
	}

	protected abstract ResultFragment<T> getResultFragment();

	private long calculateTotalPage() {
		long tp = totalCount / pageSize;
		tp += totalCount % pageSize > 0 ? 1 : 0;
		return tp == 0 ? 1 : tp;
	}

	public boolean isPageNumberOverRange() {
		return currentPage > 1 && currentPage >= totalPages;
	}

	public void resetPage() {
		currentPage = CURRENT_PAGE_START;
	}
}
