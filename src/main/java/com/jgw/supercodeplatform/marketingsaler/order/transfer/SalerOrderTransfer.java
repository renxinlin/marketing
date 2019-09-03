package com.jgw.supercodeplatform.marketingsaler.order.transfer;

import com.jgw.supercodeplatform.marketingsaler.order.constants.FormType;
import com.jgw.supercodeplatform.marketingsaler.order.dto.SalerOrderFormDto;
import com.jgw.supercodeplatform.marketingsaler.util.HanzhiToPinyinUtil;

import java.util.Arrays;
import java.util.List;

public class SalerOrderTransfer {
    public static List<String> deafultColumnNames = Arrays.asList(new String[]{"id","shouhuodizhi", "dinghuoren", "dinghuorendianhua", "suoshumendian", "suoshumendianid","dinghuoshijian"});
    public static List<String> deafultFormNames = Arrays.asList(new String[]{"id","收货地址", "订货人", "订货人电话", "所属门店","所属门店id", "订货时间"});
    public static String deafultColumnType = "varchar";
    public static String PrimaryKey = "id";

    public static List<SalerOrderFormDto> setDefaultForms(List<SalerOrderFormDto> salerOrderForms,String organizationId,String organizationName) {
        // 赋值网页字段
        salerOrderForms.forEach(salerOrderFormDto -> {
            salerOrderFormDto.setTableName(initTableName(organizationId));
            salerOrderFormDto.setColumnName(HanzhiToPinyinUtil.getPingYin(salerOrderFormDto.getFormName()));
            salerOrderFormDto.setColumnType(deafultColumnType);
            salerOrderFormDto.setOrganizationId(organizationId);
            salerOrderFormDto.setOrganizationName(organizationName);
        });
        // 赋值默认字段
        for(int i = 0;i<deafultColumnNames.size();i++) {

            SalerOrderFormDto deafultElement = new SalerOrderFormDto();
            deafultElement      .setOrganizationName(organizationName);
            deafultElement          .setOrganizationId(organizationId);
            deafultElement           .setColumnType(deafultColumnType);
            deafultElement       .setFormName(deafultFormNames.get(i));
            deafultElement   .setColumnName(deafultColumnNames.get(i));
            deafultElement.setTableName(initTableName(organizationId));
            deafultElement.setFormType(FormType.wenben);
            salerOrderForms                       .add(deafultElement);
        }
        return salerOrderForms;
    }



    public static List<SalerOrderFormDto> setForms(List<SalerOrderFormDto> salerOrderForms,String organizationId,String organizationName) {
        // 赋值网页字段
        salerOrderForms.forEach(salerOrderFormDto -> {
            salerOrderFormDto.setTableName(initTableName(organizationId));
            salerOrderFormDto.setColumnName(HanzhiToPinyinUtil.getPingYin(salerOrderFormDto.getFormName()));
            salerOrderFormDto.setColumnType(deafultColumnType);
            salerOrderFormDto.setOrganizationId(organizationId);
            salerOrderFormDto.setOrganizationName(organizationName);
        });

        return salerOrderForms;
    }



    public static String initTableName(String organizationId){
        // 一个企业暂时只有一个模板
        return "templateId_" + organizationId +"_"+"0000001";
    }
}
