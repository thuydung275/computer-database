package com.excilys.cdp.apiback.service;

public class Pagination {
	
	private int limit;
	private int page;
	
	private static final int DEFAULT_LIMIT = 5;
	private static final int DEFAULT_PAGE = 1;
	
	public Pagination() {
		this.limit = DEFAULT_LIMIT;
		this.page = DEFAULT_PAGE;
	}
	
	public Pagination(int limit, int page) {
		this.limit = limit;
		this.page = page;
	}
	
	public Pagination(int page) {
		this.limit = DEFAULT_LIMIT;
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getTotalPage(int nbComputer) {
		return (int) Math.ceil(((double) nbComputer / (double) this.limit));
	}
}
