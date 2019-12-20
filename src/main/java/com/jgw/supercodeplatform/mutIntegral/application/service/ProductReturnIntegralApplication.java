package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductReturnIntegral;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.repository.ProductReturnIntegralRepository;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.ProductReturnIntegralDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductReturnIntegralApplication {

    @Autowired
    private ProductReturnIntegralRepository returnIntegralRepository;
    /**
     * 多级积分退货
     * @param productReturnIntegralDto
     */
    public void returnBack(ProductReturnIntegralDto productReturnIntegralDto) {
    }


    public  AbstractPageService.PageResults<List<ProductReturnIntegral>> returnBackList(DaoSearch returnBackList) {
        return returnIntegralRepository.returnBackList(returnBackList);
    }
}
