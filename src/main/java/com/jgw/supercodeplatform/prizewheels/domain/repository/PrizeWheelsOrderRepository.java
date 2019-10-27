package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.prizewheels.domain.model.PrizeWheelsOrder;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.PrizeWheelsOrderPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-22
 */
 @Repository
public interface PrizeWheelsOrderRepository   {

    IPage<PrizeWheelsOrderPojo> selectPage(IPage<PrizeWheelsOrderPojo> page, Wrapper<PrizeWheelsOrderPojo> pageParam);
    void addOrder(PrizeWheelsOrder prizeWheelsOrder);
}
