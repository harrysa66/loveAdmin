package com.love.framework.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

//@XmlRootElement(name="page")
public class PageBean<T> {
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	private int pageNumber = 1;

	private int pageSize = 1;
	private String orderBy = "";
	private String order = "asc";
	private boolean autoCount = true;

	private List<T> result;// = Collections.emptyList();

	private long totalCount = -1L;

	public PageBean() {
	}

	public PageBean(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;

		if (pageNumber < 1)
			this.pageNumber = 1;
	}

	public PageBean<T> pageNumber(int thePageNumber) {
		setPageNumber(thePageNumber);
		return this;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1)
			this.pageSize = 1;
	}

	public PageBean<T> pageSize(int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	public int getFirst() {
		return ((this.pageNumber - 1) * this.pageSize + 1);
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public PageBean<T> orderBy(String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if ((!(StringUtils.equals("desc", orderStr)))
					&& (!(StringUtils.equals("asc", orderStr)))) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}
		this.order = StringUtils.lowerCase(order);
	}

	public PageBean<T> order(String theOrder) {
		setOrder(theOrder);
		return this;
	}

	public boolean isOrderBySetted() {
		return ((StringUtils.isNotBlank(this.orderBy)) && (StringUtils
				.isNotBlank(this.order)));
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public PageBean<T> autoCount(boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPages() {
		if (this.totalCount < 0L) {
			return -1L;
		}
		long count = this.totalCount / this.pageSize;
		if (this.totalCount % this.pageSize > 0L) {
			count += 1L;
		}
		return count;
	}

	public boolean isHasNext() {
		return (this.pageNumber + 1 <= getTotalPages());
	}

	public int getNextPage() {
		if (isHasNext()) {
			return (this.pageNumber + 1);
		}
		return this.pageNumber;
	}

	public boolean isHasPre() {
		return (this.pageNumber - 1 >= 1);
	}

	public int getPrePage() {
		if (isHasPre()) {
			return (this.pageNumber - 1);
		}
		return this.pageNumber;
	}
}