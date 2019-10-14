package com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-09
 */
public interface WheelsMapper extends BaseMapper<WheelsPojo> {

    /**
     * 根据组织id和组织名称获取大转盘详情
     * @param OrganizationId
     * @param OrganizationName
     * @return
     */
    @Select("select Id,templateId,title1,title2,title3,logo,startTime,endTime,wxErcode,thirdUrlButton,thirdUrl,prizeNum from marketing_activity_prize_wheels where OrganizationId=#{OrganizationId and OrganizationName=#{OrganizationName}}")
    WheelsDetailsVo getWheelsDetails(@Param("OrganizationId")String OrganizationId,@Param("OrganizationName")String OrganizationName);
}
