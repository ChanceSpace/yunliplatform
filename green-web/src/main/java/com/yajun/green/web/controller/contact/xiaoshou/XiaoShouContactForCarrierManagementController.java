package com.yajun.green.web.controller.contact.xiaoshou;

import com.yajun.green.application.ApplicationEventPublisher;
import com.yajun.green.facade.dto.contact.ZuPinContactDTO;
import com.yajun.green.facade.dto.contact.xiaoshou.ZuPinXiaoShouContactDTO;
import com.yajun.green.manager.RedisCacheManager;
import com.yajun.green.security.SecurityUtils;
import com.yajun.green.service.XiaoShouContactForCarrierService;
import com.yajun.green.service.XiaoShouContactService;
import com.yajun.green.service.ZupinContactForCarrierService;
import com.yajun.green.web.pagging.ZuPinContactForCarrierOverviewPaging;
import com.yajun.green.web.pagging.xiaoshou.XiaoShouContactForCarrierOverviewPaging;
import com.yajun.green.web.pagging.xiaoshou.XiaoShouContactOverviewPaging;
import com.yajun.green.web.utils.xiaoshou.XiaoShouContactSearchObject;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by LiuMengKe on 2017/9/18.
 * 专供 承运商页面的 合同 controller
 */
@Controller
public class XiaoShouContactForCarrierManagementController {

    @Autowired
    private XiaoShouContactForCarrierService xiaoShouContactForCarrierService;

    /*********************************************订单查询部分*****************************************/

    @RequestMapping("/contact/xiaoshoucontactforcarrieroverview.html")
    public String getAllZuPinContact(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        String pagKey = "XIAOSHOU_FOR_PAGE_"+ SecurityUtils.currentLoginUser().getUserUuid();
        RedisCacheManager redis = (RedisCacheManager) ApplicationEventPublisher.getBean("redisCacheManager");

        //接手参数
        boolean init = ServletRequestUtils.getBooleanParameter(request, "init", false);

        //使用序列化过的对象封装查询参数并保存在redis 中 维持一个小时
        XiaoShouContactSearchObject searchObject = (XiaoShouContactSearchObject)redis.fetch(pagKey);
        XiaoShouContactForCarrierOverviewPaging paging = null;

        if (init || searchObject == null) {
            int current = ServletRequestUtils.getIntParameter(request, "current", 1);
            String keyWords = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "keyWords", "")).toUpperCase();
            String userUuid = ServletRequestUtils.getStringParameter(request, "userUuid", "");
            String startTime = ServletRequestUtils.getStringParameter(request, "startTime", new LocalDate().minusYears(1).toString());
            String endTime = ServletRequestUtils.getStringParameter(request, "endTime", new LocalDate().plusMonths(1).toString());
            String sortBy = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortBy", "createTime"));
            String sortWay = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortWay", "desc"));
            String contactStatus = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "contactStatus", "all"));

            searchObject = new XiaoShouContactSearchObject(current,keyWords,userUuid,startTime,endTime,sortBy,sortWay,contactStatus);

            paging = new XiaoShouContactForCarrierOverviewPaging(xiaoShouContactForCarrierService);
            constructVehicleInfoPaging(paging,searchObject );
        } else {
            //重新设置总数，防止添加返回的操作数量不对
            paging = new XiaoShouContactForCarrierOverviewPaging(xiaoShouContactForCarrierService);
            constructVehicleInfoPaging(paging,searchObject );
            paging.totalItemSize = -1;
        }

        redis.cache(pagKey,searchObject,1, TimeUnit.HOURS);

        //设置牌勋
        boolean addSort = ServletRequestUtils.getBooleanParameter(request, "addSort", false);
        if (addSort) {
            String sortBy = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortBy", "contactNumber"));
            String sortWay = StringUtils.trimWhitespace(ServletRequestUtils.getStringParameter(request, "sortWay", "desc"));
            paging.setSortBy(sortBy);
            paging.setSortWay(sortWay);
        }

        //查询数据部分
        List<ZuPinXiaoShouContactDTO> contacts = paging.getItems();
        model.put("contacts", contacts);
        model.put("paging", paging);

        return "xiaoshou/xiaoshoucontactforcarrieroverview";
    }

    private void constructVehicleInfoPaging(XiaoShouContactForCarrierOverviewPaging paging,XiaoShouContactSearchObject searchObject) {
        int current = searchObject.getCurrent();
        String keyWords = searchObject.getKeyWords();
        String startTime = searchObject.getStartTime();
        String endTime = searchObject.getEndTime();
        String sortBy = searchObject.getSortBy();
        String sortWay = searchObject.getSortWay();
        String contactStatus = searchObject.getContactStatus();

        paging.setCurrentPageNumber(current);
        paging.setKeyWords(keyWords);
        paging.setStartTime(startTime);
        paging.setEndTime(endTime);
        paging.setSortBy(sortBy);
        paging.setSortWay(sortWay);
        paging.setContactStatus(contactStatus);

        String carrierUuid = SecurityUtils.currentLoginUser().getCompanyUuid();
        if(SecurityUtils.hasAdminRole()) {
            carrierUuid = null;
        }
        paging.setUserUuid(carrierUuid);
    }
}
