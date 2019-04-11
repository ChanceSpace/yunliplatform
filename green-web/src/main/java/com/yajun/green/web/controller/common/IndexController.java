package com.yajun.green.web.controller.common;

/*import com.yajun.green.domain.user.User;*/
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack Wang
 */
@Controller
public class IndexController {

    /**********************************************Login******************************************************/

    /*@RequestMapping("/index.html")
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = SecurityUtils.currentUser();
        if (user != null) {
            return new ModelAndView(new RedirectView("dashboard.html"));
        }

        Map<String, Object> model = new HashMap<String, Object>();
        return new ModelAndView("login/login", model);
    }*/
}
