package com.yajun.green.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午5:34
 */
public class SortTag extends TagSupport {
    private String name;
    private String field;
    private String sortBy;
    private String sortWay;
    private String ascImgUrl;
    private String descImgUrl;

    @Override
    public int doStartTag() throws JspException {
        String subString = generateSubString();
        try {
            writeMessage(subString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    protected String generateSubString() {
        StringBuilder builder = new StringBuilder();
        if (sortBy.equals(field)) {
            builder.append("<font color=\"red\">" + name + "</font>");
            if (sortWay.equals("asc")) {
                builder.append("<img src=\"" + ascImgUrl + "\"/>");
            } else {
                builder.append("<img src=\"" + descImgUrl + "\"/>");
            }
        } else {
            builder.append(name + "<img src=\"" + descImgUrl + "\"/>");

        }
        return builder.toString();
    }

    protected void writeMessage(String urlInfo) throws IOException {
        pageContext.getOut().write(urlInfo);
    }

    //************ GETTERS / SETTERS *************//


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortWay() {
        return sortWay;
    }

    public void setSortWay(String sortWay) {
        this.sortWay = sortWay;
    }

    public String getAscImgUrl() {
        return ascImgUrl;
    }

    public void setAscImgUrl(String ascImgUrl) {
        this.ascImgUrl = ascImgUrl;
    }

    public String getDescImgUrl() {
        return descImgUrl;
    }

    public void setDescImgUrl(String dscImgUrl) {
        this.descImgUrl = dscImgUrl;
    }
}
