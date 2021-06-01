package com.excilys.service;

public class Pagination {

    private Integer limit;
    private Integer page;
    private Integer totalItem;

    // show 25 item per page by default
    private static final int DEFAULT_LIMIT = 10;
    private static final int DEFAULT_PAGE = 1;

    public enum PageLimit {
        MIN(10), MID(50), MAX(100);

        public Integer limit;

        private PageLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getLimit() {
            return this.limit;
        }
    }

    /**
     *
     * @param totalItem
     */
    public Pagination(Integer totalItem) {
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
    public Pagination(Integer limit, Integer page, Integer totalItem) {
        this.limit = limit;
        this.page = page;
        this.totalItem = totalItem;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public Integer getTotalPage() {
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

    @Override
    public String toString() {
        return "Pagination [limit=" + limit + ", page=" + page + ", totalItem=" + totalItem + "]";
    }

}
