package com.jgw.supercodeplatform.marketing.service.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralExchangeMapperExt;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分兑换
 */
@Service
public class IntegralExchangeService extends AbstractPageService<IntegralExchange> {
    private static Logger logger = LoggerFactory.getLogger(IntegralExchangeService.class);
    @Autowired
    private IntegralExchangeMapperExt mapper;

    /**
     * 分页数据获取
     * @param searchParams
     * @return
     */
    @Override
    protected List<IntegralExchange> searchResult(IntegralExchange searchParams)   {
       return  mapper.list(searchParams);
    }

    /**
     * 分页count
     * @param searchParams
     * @return
     * @throws Exception
     */
    @Override
    protected int count(IntegralExchange searchParams) throws Exception {
        return  mapper.count(searchParams);
    }

    /**
     * 兑换记录删除
     * @param id
     * @param organizationId
     * @return
     * @throws SuperCodeException
     */
    public int deleteByOrganizationId(Long id, String organizationId) throws SuperCodeException {
        int i = mapper.deleteByOrganizationId(id, organizationId);
        if(i != 1){
            logger.error("{组织" + organizationId + "删除积分兑换记录记录" + id + " 共"+i+"条}" );
            throw new SuperCodeException("兑换记录不存在",500);
        }
        return i;
    }

    /**
     * 上下架操作
     * @param id
     * @param organizationId
     * @param status
     * @return
     * @throws SuperCodeException
     */
    public int updateStatus(Long id, String organizationId,Byte status) throws SuperCodeException {
        // 校验
        validateUpdateStatus(id,organizationId,status);

        // 获取记录看看能否上架
        IntegralExchange updateStatus = new IntegralExchange();
        updateStatus.setId(id);
        updateStatus.setOrganizationId(organizationId);

        // 兑换活动状态0上架1手动下架2自动下架
        int i = 0;
        if(status == (byte) 0){
            // 准备上架
            i =  mapper.updateStatusUp(updateStatus);
            if(i != 1){
                logger.error("{组织" + organizationId + "上架" + id + " 共"+i+"条}" );
                throw new SuperCodeException("上架失败",500);
            }

        }else {
            // 准备下架status 为 1手动下架
            i =  mapper.updateStatusLowwer(updateStatus);
            if(i != 1){
                logger.error("{组织" + organizationId + "下架" + id + " 共"+i+"条}" );
                throw new SuperCodeException("下架失败",500);
            }
        }
        return i;

    }

    /**
     * 查看详情
     * @param id
     * @return
     * @throws SuperCodeException
     */
    public IntegralExchange selectById(Long id, String organizationId) throws SuperCodeException{
        validateSelectById(id,organizationId);
        IntegralExchange integralExchange = mapper.selectByPrimaryKey(id);
        if(organizationId.equals(integralExchange.getOrganizationId())){
            return integralExchange;
        }else {
            logger.error("组织"+organizationId+"发生数据越权,数据id"+id );
            throw new SuperCodeException("组织" + organizationId + "无法查看" +id +"数据");
        }
    }

    /**
     * 兑换编辑
     * @param integralExchange
     * @param organizationId
     */
    public void updateByOrganizationId(IntegralExchange integralExchange, String organizationId) throws SuperCodeException {
        // 校验
        Long id = integralExchange.getId();
        validateUpdateByOrganizationId(id,organizationId,integralExchange);
        integralExchange  =addFieldWhenUpdate(integralExchange);
        // TODO 优化写个sql去除这个select
        IntegralExchange result = mapper.selectByPrimaryKey(id);
        if(organizationId.equals(organizationId)){
            int i = mapper.updateByPrimaryKeySelective(integralExchange);
            if(i != 1){
                logger.error("{组织" + organizationId + "标记积分兑换记录" + id + " 共"+i+"条}" );
                throw new SuperCodeException("编辑积分记录失败",500);
            }
        }else {
            logger.error("组织"+organizationId+"发生数据越权,数据id"+ id);
            throw new SuperCodeException("组织" + organizationId + "无法查看" + id +"数据");
        }
    }



    /**
     * 新增兑换
     * @param integralExchange
     */
    public void add(IntegralExchange integralExchange) throws SuperCodeException{
        validateAdd(integralExchange);
        // 根据业务补充数据
        integralExchange = addFeildByBuzWhenAdd(integralExchange);
        int i = mapper.insertSelective(integralExchange);
        if(1 != i){
            throw new SuperCodeException("插入兑换记录失败",500);
        }

    }

    /**
     * 编辑兑换记录的属性转换与添加
     * @param integralExchange
     * @return
     */
    private IntegralExchange addFieldWhenUpdate(IntegralExchange integralExchange) {
        // TODO 编辑兑换记录的属性转换与添加
        return  integralExchange;
    }

    /**
     * 新增兑换时候补充数据
     * @param integralExchange
     * @return
     */
    private IntegralExchange addFeildByBuzWhenAdd(IntegralExchange integralExchange) {
        // TODO 新增兑换时候补充数据

        return integralExchange;
    }


    /**
     * 查看详情校验
     * @param id
     * @param organizationId
      */
    private void validateSelectById(Long id, String organizationId) throws SuperCodeException{
        if(id == null || id <= 0 ){
            throw new SuperCodeException("兑换记录不合法",500);
        }
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("组织id不存在",500);
        }

    }

    /**
     * 上下架校验
     * @param id
     * @param organizationId
     * @param status
     */
    private void validateUpdateStatus(Long id, String organizationId, Byte status) throws SuperCodeException{
        if(id == null || id <= 0 ){
            throw new SuperCodeException("兑换记录不合法",500);
        }
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("组织id不存在",500);
        }

        if(status.intValue() != 0 && status.intValue() != 1){
            throw new SuperCodeException("状态不在合法范围",500);

        }
    }




    /**
     * 兑换记录更新校验
     * @param id
     * @param organizationId
     */
    private void validateUpdateByOrganizationId(Long id, String organizationId,IntegralExchange integralExchange) throws SuperCodeException{
        if(integralExchange == null){
            throw new SuperCodeException("兑换记录不合法",500);
        }
        if(id == null || id <= 0 ){
            throw new SuperCodeException("兑换记录不合法",500);
        }
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("组织id不存在",500);
        }

    }



    /**
     * 新增兑换校验
     * @param id
     * @param organizationId
     */
    private void validateAdd( IntegralExchange integralExchange) throws SuperCodeException{

        if(StringUtils.isBlank(integralExchange.getOrganizationId())){
            throw new SuperCodeException("组织id不存在",500);
        }

    }



}
