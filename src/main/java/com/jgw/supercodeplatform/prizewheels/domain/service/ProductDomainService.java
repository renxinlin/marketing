package com.jgw.supercodeplatform.prizewheels.domain.service;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 该服务涉及技术实现,接口提供
 */
@Service
public interface ProductDomainService {

    /**
     * 调用码管理完成生码批次绑定
     * @param products
     */
    List<Product> initSbatchIds(List<Product> products);

    /**
     * 完成大转盘在码管理的逻辑
     * @param products
     */
    void executeBizWhichCodeManagerWant(List<Product> products);

    void removeOldProduct(List<Product> byPrizeWheelsId);
}
