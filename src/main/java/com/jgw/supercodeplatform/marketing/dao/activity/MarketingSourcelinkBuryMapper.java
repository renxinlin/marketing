package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.pojo.MarketingSourcelinkBury;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MarketingSourcelinkBuryMapper {

    @Insert({"INSERT INTO marketing_sourcelink_bury (OrganizationId, OrganizationFullName, SourceLink) ",
        " VALUES (#{organizationId},#{organizationFullName},#{sourceLink})"})
    int insertSourceLinkBury(MarketingSourcelinkBury marketingSourcelinkBury);

}
