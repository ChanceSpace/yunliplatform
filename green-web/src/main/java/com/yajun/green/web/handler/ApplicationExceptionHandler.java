package com.yajun.green.web.handler;

import com.yajun.green.common.exception.PaymentConcurrentConflictException;
import com.yajun.green.common.log.ApplicationLog;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-24
 * Time: 上午9:24
 */
public class ApplicationExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex) {
        ApplicationLog.error(ApplicationExceptionHandler.class, "Catch Exception : ", ex);

        if (ex instanceof PaymentConcurrentConflictException) {
            return new ModelAndView("pay/paymentconflict");
        }

        return new ModelAndView(new RedirectView("error.html"));
	}
}
