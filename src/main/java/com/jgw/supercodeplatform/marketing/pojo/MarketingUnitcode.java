package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingUnitcode {

    private int id;//序号
    private int typeId;//类型
    private String codeName;//编码名称
    private String codeId;//编码code

    public MarketingUnitcode() {
    }

    public MarketingUnitcode(int id, int typeId, String codeName, String codeId) {
        this.id = id;
        this.typeId = typeId;
        this.codeName = codeName;
        this.codeId = codeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
}
