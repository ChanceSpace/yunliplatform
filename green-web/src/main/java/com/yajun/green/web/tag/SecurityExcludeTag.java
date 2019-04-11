package com.yajun.green.web.tag;

import com.yajun.green.security.SecurityUtils;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-11-14
 * Time: 下午2:41
 */
public class SecurityExcludeTag extends TagSupport {

    private String excludeRoles;

    @Override
    public int doStartTag() throws JspException {
        List<String> roles = SecurityUtils.currentLoginUser().getRoles();
        List<String> ownRoles = new ArrayList<>();
        for (String role : roles) {
            ownRoles.add(role);
        }

        String[] needRoles = StringUtils.delimitedListToStringArray(excludeRoles, ",");
        for (String needRole : needRoles) {
            if (ownRoles.contains(needRole)) {
                return SKIP_BODY;
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    /*********************************GET/SET*********************************/

    public String getExcludeRoles() {
        return excludeRoles;
    }

    public void setExcludeRoles(String excludeRoles) {
        this.excludeRoles = excludeRoles;
    }
}
