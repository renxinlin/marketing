package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jgw.supercodeplatform.prizewheels.domain.constants.RewardTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.http.util.Asserts;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 * 转盘奖励
 * @author renxinlin
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WheelsReward implements Serializable {


    private Long id;

    private Long prizeWheelId;

    /**
     * 奖励类型:1 虚拟2 实物  //TODO 剔除
     */
    private Integer type = (int) RewardTypeConstant.virtual;

    private String name;

    /**
     * 概率
     */
    private double probability;

    private Integer num;

    private String picture;

    /**
     * 图片规格
     */
    private String pictureSpecs;


    private Byte loseAward;


    private String cdkName;

    private String cdkUuid;


    private Integer sendDay;

    /**
     * 库存
     */
    private Integer stock;
    /**
     * 初始化库存
     */
    private Integer initialStock;


    private Double randLowMoney;

    private Double randHighMoney;

    private Double fixedMoney;

    private Integer moneyType;


    public void initInitialStock(){
        // 业务分开写
        if(RewardTypeConstant.real == type.byteValue()){
            Asserts.check(stock!= null && stock > 0 ,"实物奖项初始库存大于0");
            this.initialStock = stock;
        }

        if(RewardTypeConstant.money == type.byteValue()){
            Asserts.check(stock!= null && stock > 0 ,"金额奖项初始库存大于0");
            this.initialStock = stock;
        }

    }


}
