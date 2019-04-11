package com.yajun.green.common.utils;

/**
 * User: Jack Wang
 * Date: 14-11-20
 * Time: 下午5:01
 */
public class PagingUtils {

    /**
     * 当前的页数
     */
    private int currentPage = 1;
    /**
     * 分页每一页的大小
     */
    public int pageSize = 50;
    /**
     * 总共的数据
     */
    private int totalSize = 1;

    public PagingUtils(int totalSize, int pageSize) {
        setTotalSize(totalSize);
        setPageSize(pageSize);
    }

    public void setTotalSize(int totalSize) {
        if (totalSize >= 0) this.totalSize = totalSize;
    }

    private void setPageSize(int pageSize) {
        if (pageSize > 0) this.pageSize = pageSize;
    }

    public void setCurrentPage(int currentPage) {
        final int numPages = getNumPages();
        if (currentPage > numPages) currentPage = numPages;
        if (currentPage < 1) currentPage = 1;
        this.currentPage = currentPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getStartPosition() {
        return (currentPage - 1) * pageSize;
    }

    public int getEndPosition() {
        int end = currentPage * pageSize;
        return end > totalSize ? totalSize : end;
    }

    public int getNumPages() {
        int num = totalSize / pageSize;
        if (totalSize % pageSize > 0) num++;
        return num;
    }

    public int getNextPage() {
        int next = currentPage + 1;
        if (next > getNumPages()) return -1;
        return currentPage + 1;
    }

    public int getPrevPage() {
        final int prev = currentPage - 1;
        if (prev < 1) return -1;
        return prev;
    }

}
