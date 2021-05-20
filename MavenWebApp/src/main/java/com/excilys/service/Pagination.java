package com.excilys.service;

public class Pagination {

    private int limit;
    private int page;
    private int totalItem;

    // show 25 item per page by default
    private static final int DEFAULT_LIMIT = 25;
    private static final int DEFAULT_PAGE = 1;

    /**
     *
     * @param totalItem
     */
    public Pagination(int totalItem) {
        this.limit = DEFAULT_LIMIT;
        this.page = DEFAULT_PAGE;
        this.totalItem = totalItem;
    }

    /**
     *
     * @param limit
     * @param page
     * @param totalItem
     */
    public Pagination(int limit, int page, int totalItem) {
        this.limit = limit;
        this.page = page;
        this.totalItem = totalItem;
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

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalPage() {
        return (int) Math.ceil(((double) this.totalItem / (double) this.limit));
    }

    /**
     * go to the next page.
     */
    public void next() {
        if (this.page < this.getTotalPage()) {
            this.page++;
        }
    }

    /**
     * go to the previous page.
     */
    public void prev() {
        if (this.page > 1) {
            this.page--;
        }
    }
}
