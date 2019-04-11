package com.yajun.green.web.tag;

import com.yajun.green.security.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 17-11-14
 * Time: 下午2:41
 */
public class SecurityTag extends TagSupport {

    private String grantRoles;

    @Override
    public int doStartTag() throws JspException {
        List<String> roles = SecurityUtils.currentLoginUser().getRoles();
        List<String> ownRoles = new ArrayList<>();
        for (String role : roles) {
            ownRoles.add(role);
        }

        String[] needRoles = StringUtils.delimitedListToStringArray(grantRoles, ",");
        for (String needRole : needRoles) {
            if (ownRoles.contains(needRole)) {
                return EVAL_BODY_INCLUDE;
            }
        }

        return SKIP_BODY;
    }

    /*********************************GET/SET*********************************/

    public String getGrantRoles() {
        return grantRoles;
    }

    public void setGrantRoles(String grantRoles) {
        this.grantRoles = grantRoles;
    }
}
