package com.jgw.supercodeplatform.marketing.service.integral;

import com.alibaba.fastjson.JSONArray;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.dao.integral.ProductUnsaleMapperExt;
import com.jgw.supercodeplatform.marketing.dto.SkuInfo;
import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UnsaleProductService {
    private Logger logger = LoggerFactory.getLogger(UnsaleProductService.class);
    @Value("${rest.user.url}")
    private  String baseService;

    private static String SERVICE_BASE_INFO_URL = "";
    @Autowired
    private ProductUnsaleMapperExt mapper;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    /**
     * 基于组织id:非自卖产品查询
     * @param organizationId
     * @return
     */
    public List<Map<String, Object>> selectUnsale(String organizationId) throws SuperCodeException{
        if(StringUtils.isBlank(organizationId)){
            throw  new SuperCodeException("组织id获取失败");
        }
        // 获取所有的非自卖产品
        List<ProductUnsale> productUnsales = mapper.selectAll(organizationId);

        List<Map<String,Object>> productUnsaleMaps = new ArrayList<>();
        if(CollectionUtils.isEmpty(productUnsales)){
            return  productUnsaleMaps;
        }
        // 将非自卖的sku转化成子节点信息
        ModelMapper modelMapper = new ModelMapper();

        for(ProductUnsale productUnsale : productUnsales){
            String unsaleProductSkuInfo = productUnsale.getUnsaleProductSkuInfo();
            // [{k1:v1,k2,v2}] 属性name pic
            List<SkuInfo> skuChilds = JSONArray.parseArray(unsaleProductSkuInfo, SkuInfo.class);
            productUnsale.setSkuChild(skuChilds);
            Map map = modelMapper.map(productUnsale, Map.class);
            productUnsaleMaps.add(map);
        }
        return  productUnsaleMaps;
    }
    /**
     * 基于组织id:自卖产品查询
     * @param organizationId
     * @return
     */
    public List<Map<String, Object>> selectPruduct(String organizationId) {
        // TODO 查询基础平台
        return  null;

    }
}
