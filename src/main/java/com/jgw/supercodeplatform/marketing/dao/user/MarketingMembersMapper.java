package com.jgw.supercodeplatform.marketing.dao.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;

/**
 * 会员类
 */
@Mapper
public interface MarketingMembersMapper {
    String selectSql = " a.Id as id, a.WxName as wxName,a.Openid as openid,a.Mobile as mobile,"
            + " a.UserId as userId,a.UserName as userName,"
            + " a.Sex as sex,DATE_FORMAT(a.Birthday ,'%Y-%m-%d') as birthday,a.PCCcode as pCCcode,"
            + " DATE_FORMAT(a.RegistDate,'%Y-%m-%d') as registDate,"
            + " a.State as state,a.OrganizationId as organizationId,"
            + " a.NewRegisterFlag as newRegisterFlag ,"
            + " DATE_FORMAT(a.CreateDate,'%Y-%m-%d') as createDate,DATE_FORMAT(a.UpdateDate,'%Y-%m-%d') as updateDate,"
            + " a.CustomerName as customerName,a.CustomerId as customerId,"
            + " DATE_FORMAT(a.BabyBirthday ,'%Y-%m-%d') as babyBirthday,  a.IsRegistered as isRegistered , "
            + " a.HaveIntegral as haveIntegral , MemberType memberType, a.IntegralReceiveDate as integralReceiveDate, "
            + " a.WechatHeadImgUrl as wechatHeadImgUrl, "
            + " a.UserSource, a.ProvinceCode, a.ProvinceName, a.DeviceType, " +
              " a.ProvinceCode,a.CityCode,a.CountyCode,a.ProvinceName,a.CityName,a.CountyName  ";

    /**
     * 会员注册
     * @param map
     * @return
     */
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="Id")
    @Insert(" INSERT INTO marketing_members(WxName,Openid,Mobile,UserId,UserName,"
            + " Sex,Birthday,RegistDate,OrganizationId,CustomerName,CustomerId,BabyBirthday,PCCcode,State,IsRegistered,WechatHeadImgUrl, " +
              " ProvinceCode,CityCode,CountyCode,ProvinceName,CityName,CountyName ) "
            + " VALUES("
            + " #{wxName},#{openid},#{mobile},#{userId},#{userName},#{sex},#{birthday}"
            + " ,NOW(),#{organizationId},"
            + " #{customerName},#{customerId},#{babyBirthday},#{pCCcode},#{state},1,#{wechatHeadImgUrl}," +
              " provinceCode,cityCode,countyCode,provinceName,cityName,countyName )")
    int insert(MarketingMembers marketingMembers);


    /**
     * 条件查询会员
     * @return
     */
    @Select(" <script>"
            + " SELECT  ${portraitsList}  FROM marketing_members "
            + " <where>"
            + "  OrganizationId = #{ organizationId }  "
            + "  and state != 2  "
            + " <if test='mobile != null and mobile != &apos;&apos;'> AND Mobile like &apos;%${mobile}%&apos; </if>"
            + " <if test='wxName != null and wxName != &apos;&apos;'> AND WxName like &apos;%${wxName}%&apos; </if>"
            + " <if test='openid != null and openid != &apos;&apos;'> AND Openid like &apos;%${openid}%&apos; </if>"
            + " <if test='userName != null and userName != &apos;&apos;'> AND UserName like &apos;%${userName}%&apos; </if>"
            + " <if test='sex != null and sex != &apos;&apos;'> AND Sex like &apos;%${sex}%&apos; </if>"
            + " <if test='birthday != null and birthday != &apos;&apos;'> AND Birthday like &apos;%${birthday}%&apos; </if>"
            + " <if test='provinceName != null and provinceName != &apos;&apos;'> AND ProvinceName like &apos;%${provinceName}%&apos; </if>"
            + " <if test='countyName != null and countyName != &apos;&apos;'> AND CountyName like &apos;%${countyName}%&apos; </if>"
            + " <if test='cityName != null and cityName != &apos;&apos;'> AND CityName like &apos;%${cityName}%&apos; </if>"
            + " <if test='customerName != null and customerName != &apos;&apos;'> AND CustomerName like &apos;%${customerName}%&apos; </if>"
            + " <if test='newRegisterFlag != null '> AND NewRegisterFlag like &apos;%${newRegisterFlag}%&apos; </if>"
            + " <if test='registDate != null and registDate != &apos;&apos;'> AND RegistDate like &apos;%${registDate}%&apos; </if>"
            + " <if test='babyBirthday != null and babyBirthday != &apos;&apos;'> AND BabyBirthday like &apos;%${babyBirthday}%&apos; </if>"
            + " <if test='state != null and state != &apos;&apos;'> AND State like &apos;%${state}%&apos; </if>"
            + " <if test='search !=null and search != &apos;&apos;'>"
            + " AND ("
            + " Mobile LIKE binary CONCAT('%',#{search},'%')  "
            + " OR WxName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Openid LIKE binary CONCAT('%',#{search},'%') "
            + " OR UserName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Sex LIKE binary CONCAT('%',#{search},'%') "
            + " OR Birthday LIKE binary CONCAT('%',#{search},'%') "
            + " OR ProvinceName LIKE binary CONCAT('%',#{search},'%') "
            + " OR CountyName LIKE binary CONCAT('%',#{search},'%') "
            + " OR CityName LIKE binary CONCAT('%',#{search},'%') "
            + " OR CustomerName LIKE binary CONCAT('%',#{search},'%') "
            + " OR NewRegisterFlag LIKE binary CONCAT('%',#{search},'%') "
            + " OR RegistDate LIKE binary CONCAT('%',#{search},'%') "
            + " OR BabyBirthday LIKE binary CONCAT('%',#{search},'%') "
            + " OR State LIKE binary CONCAT('%',#{search},'%') "
            + ")"
            + " </if>"
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            + " </where>"
            + " </script>")
    List<MarketingMembers> getAllMarketingMembersLikeParams(Map<String,Object> map);

    /**
     * 条件查询会员数量
     * @return
     */
    @Select(" <script>"
            + " SELECT  COUNT(1)  FROM marketing_members "
            + " <where>"
            + "  OrganizationId = #{ organizationId} "
            + "  and state !=2"
            + " <if test='mobile != null and mobile != &apos;&apos;'> AND Mobile like &apos;%${mobile}%&apos; </if>"
            + " <if test='wxName != null and wxName != &apos;&apos;'> AND WxName like &apos;%${wxName}%&apos; </if>"
            + " <if test='openid != null and openid != &apos;&apos;'> AND Openid like &apos;%${openid}%&apos; </if>"
            + " <if test='userName != null and userName != &apos;&apos;'> AND UserName like &apos;%${userName}%&apos; </if>"
            + " <if test='sex != null and sex != &apos;&apos;'> AND Sex like &apos;%${sex}%&apos; </if>"
            + " <if test='birthday != null and birthday != &apos;&apos;'> AND Birthday like &apos;%${birthday}%&apos; </if>"
            + " <if test='customerName != null and customerName != &apos;&apos;'> AND CustomerName like &apos;%${customerName}%&apos; </if>"
            + " <if test='newRegisterFlag != null '> AND NewRegisterFlag like &apos;%${newRegisterFlag}%&apos; </if>"
            + " <if test='registDate != null and registDate != &apos;&apos;'> AND RegistDate like &apos;%${registDate}%&apos; </if>"
            + " <if test='babyBirthday != null and babyBirthday != &apos;&apos;'> AND BabyBirthday like &apos;%${babyBirthday}%&apos; </if>"
            + " <if test='state != null and state != &apos;&apos;'> AND State like &apos;%${state}%&apos; </if>"
            + " <if test='search !=null and search != &apos;&apos;'>"
            + " AND ("
            + " Mobile LIKE binary CONCAT('%',#{search},'%')  "
            + " OR WxName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Openid LIKE binary CONCAT('%',#{search},'%') "
            + " OR UserName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Sex LIKE binary CONCAT('%',#{search},'%') "
            + " OR Birthday LIKE binary CONCAT('%',#{search},'%') "
            + " OR PCCcode LIKE binary CONCAT('%',#{search},'%') "
            + " OR CustomerName LIKE binary CONCAT('%',#{search},'%') "
            + " OR NewRegisterFlag LIKE binary CONCAT('%',#{search},'%') "
            + " OR RegistDate LIKE binary CONCAT('%',#{search},'%') "
            + " OR BabyBirthday LIKE binary CONCAT('%',#{search},'%') "
            + " OR State LIKE binary CONCAT('%',#{search},'%') "
            + ")"
            + " </if>"
            + " </where>"
            + " </script>")
    Integer getAllMarketingMembersCount(Map<String,Object> map);



    @Select(" <script>"
            + " SELECT  COUNT(1)  FROM marketing_members "
            + " <where>"
            + "  OrganizationId = #{ organizationId} "
            + "  and state !=2"
            + "  and CreateDate &lt; #{createDate}"
            + " <if test='mobile != null and mobile != &apos;&apos;'> AND Mobile like &apos;%${mobile}%&apos; </if>"
            + " <if test='wxName != null and wxName != &apos;&apos;'> AND WxName like &apos;%${wxName}%&apos; </if>"
            + " <if test='openid != null and openid != &apos;&apos;'> AND Openid like &apos;%${openid}%&apos; </if>"
            + " <if test='userName != null and userName != &apos;&apos;'> AND UserName like &apos;%${userName}%&apos; </if>"
            + " <if test='sex != null and sex != &apos;&apos;'> AND Sex like &apos;%${sex}%&apos; </if>"
            + " <if test='birthday != null and birthday != &apos;&apos;'> AND Birthday like &apos;%${birthday}%&apos; </if>"
            + " <if test='customerName != null and customerName != &apos;&apos;'> AND CustomerName like &apos;%${customerName}%&apos; </if>"
            + " <if test='newRegisterFlag != null '> AND NewRegisterFlag like &apos;%${newRegisterFlag}%&apos; </if>"
            + " <if test='registDate != null and registDate != &apos;&apos;'> AND RegistDate like &apos;%${registDate}%&apos; </if>"
            + " <if test='babyBirthday != null and babyBirthday != &apos;&apos;'> AND BabyBirthday like &apos;%${babyBirthday}%&apos; </if>"
            + " <if test='state != null and state != &apos;&apos;'> AND State like &apos;%${state}%&apos; </if>"
            + " <if test='search !=null and search != &apos;&apos;'>"
            + " AND ("
            + " Mobile LIKE binary CONCAT('%',#{search},'%')  "
            + " OR WxName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Openid LIKE binary CONCAT('%',#{search},'%') "
            + " OR UserName LIKE binary CONCAT('%',#{search},'%') "
            + " OR Sex LIKE binary CONCAT('%',#{search},'%') "
            + " OR Birthday LIKE binary CONCAT('%',#{search},'%') "
            + " OR PCCcode LIKE binary CONCAT('%',#{search},'%') "
            + " OR CustomerName LIKE binary CONCAT('%',#{search},'%') "
            + " OR NewRegisterFlag LIKE binary CONCAT('%',#{search},'%') "
            + " OR RegistDate LIKE binary CONCAT('%',#{search},'%') "
            + " OR BabyBirthday LIKE binary CONCAT('%',#{search},'%') "
            + " OR State LIKE binary CONCAT('%',#{search},'%') "
            + ")"
            + " </if>"
            + " </where>"
            + " </script>")
    Integer getAllMarketingMembersCountWithOutToday(Map<String,Object> map);

    /**
     * 根据id获取单个会员信息
     * @param id
     * @return
     */
    @Select(" SELECT "+selectSql+" FROM marketing_members a WHERE Id = #{id}")
    MarketingMembers getMemberById(@Param("id")Long id);


    @Delete("delete from marketing_members where Id=#{id}")
	void deleteById(@Param("id")Long id);


    @Select("${sql}")
	List<Map<String, Object>> dynamicList(@Param("sql")String listSQl);

    @Select("${sql}")
	Integer dynamicCount(@Param("sql")String listSQl);

    /**
     * 修改会员信息
     * @param map
     * @return
     */
    @Update(" <script>"
            + " UPDATE marketing_members "
            + " <set>"
            + " <if test='userName !=null and userName != &apos;&apos; '> UserName = #{userName} ,</if> "
            + " <if test='sex !=null and sex != &apos;&apos; '> Sex = #{sex} ,</if> "
            + " <if test='birthday !=null and birthday != &apos;&apos; '> Birthday = #{birthday} ,</if> "
            + " <if test='newRegisterFlag !=null'> NewRegisterFlag = #{newRegisterFlag} ,</if> "
            + " <if test='state !=null'> State = #{state} ,</if> "
            + " <if test='haveIntegral !=null '> HaveIntegral = #{haveIntegral} ,</if> "
            + " <if test='customerName !=null and customerName != &apos;&apos; '> CustomerName = #{customerName} ,</if> "
            + " <if test='customerId !=null and customerId != &apos;&apos; '> CustomerId = #{customerId} ,</if> "
            + " <if test='babyBirthday !=null and babyBirthday != &apos;&apos; '> BabyBirthday = #{babyBirthday} ,</if> "
            + " <if test='mobile !=null and mobile != &apos;&apos; '> Mobile = #{mobile} ,</if> "
            + " <if test='pCCcode !=null and pCCcode != &apos;&apos; '> PCCcode = #{pCCcode} ,</if> "
            + " <if test='wxName !=null and wxName != &apos;&apos; '> WxName = #{wxName} ,</if> "
            + " <if test='openid !=null and openid != &apos;&apos; '> Openid = #{openid} ,</if> "
            + " <if test='wechatHeadImgUrl !=null and wechatHeadImgUrl != &apos;&apos; '> WechatHeadImgUrl = #{wechatHeadImgUrl} ,</if> "
            + " <if test=' isRegistered !=null'> IsRegistered = #{isRegistered} ,</if> "
            + " <if test=' integralReceiveDate !=null'> IntegralReceiveDate = #{integralReceiveDate} ,</if> "
            + " UpdateDate = NOW() ,"
            + " </set>"
            + " <where> "
            + "  Id = #{id} "
            + " </where>"
            + " </script>")
	int update(MarketingMembers members);

    @Update("update marketing_members set State = #{state} where Id=#{id}")
	int updateMembersStatus(@Param("id")Long id, @Param("state")int state);
    @Update("update marketing_members set  HaveIntegral = HaveIntegral - #{ingetralNum} where Id=#{id} ")
    int deleteIntegral(@Param("ingetralNum") Integer ingetralNum,@Param("id")Long id);


    @Select(" SELECT "+selectSql+" FROM marketing_members a WHERE a.Openid = #{openid} AND OrganizationId = #{organizationId} and State != 2 ")
    MarketingMembers selectByOpenIdAndOrgId(@Param("openid")String openid, @Param("organizationId")String  organizationId);

    @Select(" SELECT "+selectSql+" FROM marketing_members a WHERE a.Mobile = #{mobile} AND OrganizationId = #{organizationId} ")
    MarketingMembers selectByMobileAndOrgId(@Param("mobile")String mobile,  @Param("organizationId")String organizationId);

    @Select(" SELECT "+selectSql+" FROM marketing_members a WHERE a.Mobile = #{mobile} AND OrganizationId = #{organizationId} and Id !=#{id} ")
	MarketingMembers selectByPhoneAndOrgIdExcludeId(@Param("mobile")String mobile,  @Param("organizationId")String organizationId, @Param("id")Long id);

    /**
     * 查询所有会员类型:临时数据【2】上线【1】 下线【0】
     * @param state
     * @param openid
     * @param organizationId
     * @return
     */
    @Select(" SELECT "+selectSql+" FROM marketing_members a WHERE a.Openid = #{openid} AND OrganizationId = #{organizationId} ")
	MarketingMembers selectByOpenIdAndOrgIdWithTemp(@Param("openid")String openid, @Param("organizationId")String  organizationId);

    /**
     * 招募会员入口注册的会员
     * @param organizationId
     * @param startDate
     * @param endDate
     * @return
     */
    @Select(" SELECT "+selectSql+" FROM marketing_members a WHERE a.OrganizationId = #{organizationId} and a.UserSource = 1 and a.CreateDate between #{startDate} and #{endDate} and state !=2  ")
    List<MarketingMembers> getRegisterNum(String organizationId, Date startDate, Date endDate);

    /**
      * 会员地图
     * @param organizationId
     * @param startDate
     * @param endDate
     * @return
     */
    @Select(" SELECT "+selectSql+" FROM marketing_members a WHERE a.OrganizationId = #{organizationId} and a.CreateDate between #{startDate} and #{endDate} and state !=2 ")
    List<MarketingMembers> getOrganizationAllMemberWithDate(String organizationId, Date startDate, Date endDate);
}
