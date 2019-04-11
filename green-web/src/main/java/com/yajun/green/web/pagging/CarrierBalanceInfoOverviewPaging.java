package com.yajun.green.web.pagging;

import com.yajun.green.security.SecurityUtils;
import com.yajun.user.facade.dto.CompanyDTO;
import com.yajun.user.service.UserDubboService;

import java.util.List;
import java.util.Map;

/**
 * Created by chance on 2017/10/20.
 */
public class CarrierBalanceInfoOverviewPaging extends AbstractPaging<CompanyDTO> {

    private UserDubboService userDubboService;

    private String keywords;
    private int qianFeiTotal;
    private int qianFeiDate;

    public CarrierBalanceInfoOverviewPaging(UserDubboService userDubboService) {
        this.userDubboService = userDubboService;
    }

    @Override
    public String getParameterValues() {
        return "&keywords=" + getKeywords() + "&qianFeiTotal=" + getQianFeiTotal() + "&qianFeiDate=" + getQianFeiDate();
    }

    @Override
    public List<CompanyDTO> getItems() {
        return userDubboService.obtainTongBuPagingSearchCompanies(SecurityUtils.userToken.get(), totalItemSize, getPageSize(), getCurrentPageNumber(), keywords, qianFeiTotal, qianFeiDate);
    }

    @Override
    public long getTotalItemSize() {
         if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = userDubboService.obtainTongBuPagingSearchCompanySize(SecurityUtils.userToken.get(), keywords, qianFeiTotal, qianFeiDate);

        return totalItemSize;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getQianFeiTotal() {
        return qianFeiTotal;
    }

    public void setQianFeiTotal(int qianFeiTotal) {
        this.qianFeiTotal = qianFeiTotal;
    }

    public int getQianFeiDate() {
        return qianFeiDate;
    }

    public void setQianFeiDate(int qianFeiDate) {
        this.qianFeiDate = qianFeiDate;
    }
}
