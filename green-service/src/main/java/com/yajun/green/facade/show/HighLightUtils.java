package com.yajun.green.facade.show;

import org.springframework.util.StringUtils;

/**
 * User: Jack Wang
 * Date: 17-10-9
 * Time: 下午2:54
 */
public class HighLightUtils {

    private final static String REPLACE_HTML_PREFIX = "<span class=\"highlighted\">";
    private final static String REPLACE_HTML_SUFIX = "</span>";

    public static String highLightWords(String words, String replace) {
        if (StringUtils.hasText(words)) {
            return words.replace(replace, REPLACE_HTML_PREFIX + replace + REPLACE_HTML_SUFIX);
        }
        return null;
    }
}
