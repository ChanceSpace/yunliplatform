package com.yajun.green.common.web.view;

/**
 * User: Jack Wang
 * Date: 16-7-8
 * Time: 下午2:28
 */
public class SelectView {

    private String value;

    private String label;

    public SelectView(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
