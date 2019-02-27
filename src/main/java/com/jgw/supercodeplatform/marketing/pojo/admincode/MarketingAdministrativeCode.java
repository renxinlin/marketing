package com.jgw.supercodeplatform.marketing.pojo.admincode;
 /**
   * 功能描述：行政地区实体
   * @Author corbett
   * @Description //TODO
   * @Date 15:54 2018/10/29
   **/
public class MarketingAdministrativeCode {
    private Integer id;
    /**
     * 功能描述：城市编码
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    private String areaCode;
    /**
     * 功能描述：城市名
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    private String cityName;
    /**
     * 功能描述：父级城市编码
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    private String parentAreaCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getParentAreaCode() {
        return parentAreaCode;
    }

    public void setParentAreaCode(String parentAreaCode) {
        this.parentAreaCode = parentAreaCode == null ? null : parentAreaCode.trim();
    }
}