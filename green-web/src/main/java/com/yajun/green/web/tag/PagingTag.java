package com.yajun.green.web.tag;

import com.yajun.green.web.pagging.Paging;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午11:35
 *
 <div id="layui-laypage-1" class="layui-box layui-laypage layui-laypage-default">
 <a class="layui-laypage-prev layui-disabled" data-page="0" href="javascript:;">上一页</a>
 <span class="layui-laypage-curr">
 <a data-page="2" href="javascript:;">2</a>
 <a data-page="3" href="javascript:;">3</a>
 <a class="layui-laypage-next" data-page="2" href="javascript:;">下一页</a>
 </div>
 */
public class PagingTag extends TagSupport {

    private Paging paging;
    private String urlMapping;
    private String first = "第一页";
    private String previous = "上一页";
    private String next = "下一页";
    private String last = "后一页";
    private String goToSpecificPage = "确定";
    private String currentPageNumberParameter = "current";
    private String delimiter = "";
    private boolean showPageInformation = Boolean.TRUE;
    private boolean showFirstPageLink = Boolean.TRUE;
    private boolean showLastPageLink = Boolean.TRUE;
    private boolean showGoTo = Boolean.TRUE;
    private String customInputBoxClass = "goto";
    private String function = "";

    @Override
    public int doStartTag() throws JspException {
        String pagingLinks;
        if (paging == null || paging.getTotalItemSize() <= 0) {
            pagingLinks = "";
        } else {
            pagingLinks = createPaingLinks();
        }
        try {
            writeMessage(pagingLinks);
        } catch (IOException e) {

        }
        return EVAL_BODY_INCLUDE;
    }

    private String createPaingLinks() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" <div id=\"layui-laypage-1\" class=\"layui-box layui-laypage layui-laypage-default\">");

        buffer.append(showFirstPageLink ? createFirstPageLink() : "");
        buffer.append(createPreviousPageLink());
        buffer.append(createNextPageLink());
        buffer.append(showLastPageLink ? createLastPageLink() : "");
        buffer.append(showPageInformation ? createPageInformation() : "");
        buffer.append(showGoTo ? createGoToSpecificPageLink() : "");

        buffer.append("</div>");
        return buffer.toString();
    }

     private String createFirstPageLink() {
        String firstLink = "<a class=\"layui-laypage-prev\" data-page=\"0\" href=\"" + createHref(1) + "\"" + createLinkClick(1) + " >" + first + "</a>";
        return firstLink + delimiter;
    }

    private String createPreviousPageLink() {
        String prevLink = "";
        if (paging.hasPreviousPage()) {
            prevLink = "<a class=\"layui-laypage-prev\" data-page=\"0\" href=\"" + createHref(paging.getPreviousPageNumber()) + "\"" + createLinkClick(paging.getPreviousPageNumber()) + " >" + previous + "</a>";
        } else {
            prevLink = "<a class=\"layui-laypage-prev\" data-page=\"0\" href=\"javascript:void(0);\">" + previous + "</a>";
        }
        return prevLink + delimiter;
    }

    private String createNextPageLink() {
        if (paging.hasNextPage()) {
            return "<a class=\"layui-laypage-prev\" data-page=\"0\" href=\"" + createHref(paging.getNextPageNumber()) + "\"" + createLinkClick(paging.getNextPageNumber()) + " >" + next + "</a>" + delimiter;
        } else {
            return "<a class=\"layui-laypage-prev\" data-page=\"0\" href=\"javascript:void(0);\">" + next + "</a>" + delimiter;
        }
    }

    private String createLastPageLink() {
        String lastLink = "<a class=\"layui-laypage-prev\" data-page=\"0\" href=\"" + createHref(paging.getTotalPages()) + "\"" + createLinkClick(paging.getTotalPages()) + " >" + last + "</a>";
        return lastLink;
    }

    private String createPageInformation() {
        return "<a class=\"layui-laypage-prev\" data-page=\"0\" href=\"javascript:void(0);\">共" + paging.getTotalItemSize() + "条记录，每页" + paging.getPageSize() + "条</a>" + delimiter + "<a href=\"javascript:void(0);\">(" + paging.getCurrentPageNumber() + " / " + paging.getTotalPages() + ")</a>" + delimiter;
    }

    private String createGoToSpecificPageLink() {
        String goToSpecificPageLink = "<span class=\"layui-laypage-skip\"><input id=\"specificPageNumber\" class=\"layui-input\" type=\"text\"  value=\"" + paging.getCurrentPageNumber() + "\"/><a class=\"layui-laypage-btn\" data-page=\"0\" href=\"javascript:void(0);\" onclick=\"" + createGotoClick() + "\">" + goToSpecificPage + "</a></span>";
        return goToSpecificPageLink;
    }

    private String createGotoClick() {
        if (StringUtils.hasText(function)) {
            return function + "('" + urlMapping + getCurrentPageNumberParameter() + "'+document.getElementById('specificPageNumber').value)" + "+'" + getParameterValues() + "'";
        } else {
            String defaultAction = "this.href='" + urlMapping + getCurrentPageNumberParameter() + "'+document.getElementById('specificPageNumber').value" + "+'" + getParameterValues() + "'";
            String errorhandleAction = "this.href='" + urlMapping + getCurrentPageNumberParameter() + 1 + "'";
            return "if(document.getElementById('specificPageNumber').value.match(/\\D+/) != null || " +
                    " document.getElementById('specificPageNumber').value == '' || " +
                    "document.getElementById('specificPageNumber').value == 0 || " +
                    "document.getElementById('specificPageNumber').value > " + paging.getTotalPages() + "){" + errorhandleAction + " ;return ;}"
                    + defaultAction;
        }
    }

    private String createUrl(int pageNumber) {
        return urlMapping + getCurrentPageNumberParameter() + pageNumber + getParameterValues();
    }

    private String createHref(int pageNumber) {
        return StringUtils.hasText(function) ? "javascript:void(0);" : createUrl(pageNumber);
    }

    private String createLinkClick(int pageNumber) {
        return StringUtils.hasText(function) ? " onclick=\"" + function + "('" + createUrl(pageNumber) + "');\"" : "";
    }

    private String getCurrentPageNumberParameter() {
        if (urlMapping.contains("?")) {
            return "&" + currentPageNumberParameter + "=";
        } else {
            return "?" + currentPageNumberParameter + "=";
        }
    }

    public String getParameterValues() {
        return  paging.getParameterValues();
    }

    protected void writeMessage(String urlInfo) throws IOException {
        pageContext.getOut().write(urlInfo);
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public void setUrlMapping(String urlMapping) {
        this.urlMapping = urlMapping;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setCurrentPageNumberParameter(String currentPageNumberParameter) {
        this.currentPageNumberParameter = currentPageNumberParameter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }


    public void setShowPageInformation(boolean showPageInformation) {
        this.showPageInformation = showPageInformation;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setShowFirstPageLink(boolean showFirstPageLink) {
        this.showFirstPageLink = showFirstPageLink;
    }

    public void setShowLastPageLink(boolean showLastPageLink) {
        this.showLastPageLink = showLastPageLink;
    }

    public void setShowGoTo(boolean showGoTo) {
        this.showGoTo = showGoTo;
    }

    public void setGoToSpecificPage(String goToSpecificPage) {
        this.goToSpecificPage = goToSpecificPage;
    }

    public void setCustomInputBoxClass(String customInputBoxClass) {
        this.customInputBoxClass = customInputBoxClass;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}


