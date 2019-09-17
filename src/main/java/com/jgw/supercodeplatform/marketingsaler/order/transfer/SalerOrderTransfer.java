package com.jgw.supercodeplatform.marketingsaler.order.transfer;

import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.order.constants.FormType;
import com.jgw.supercodeplatform.marketingsaler.order.dto.ChangeColumDto;
import com.jgw.supercodeplatform.marketingsaler.order.dto.ColumnnameAndValueDto;
import com.jgw.supercodeplatform.marketingsaler.order.dto.SalerOrderFormDto;
import com.jgw.supercodeplatform.marketingsaler.order.dto.SalerOrderFormSettingDto;
import com.jgw.supercodeplatform.marketingsaler.order.pojo.SalerOrderForm;
import com.jgw.supercodeplatform.marketingsaler.order.vo.H5SalerOrderFormVo;
import com.jgw.supercodeplatform.marketingsaler.integral.infrastructure.util.HanzhiToPinyinUtil;
import org.modelmapper.ModelMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

public class SalerOrderTransfer {
    // TODO 分隔符是否正确
    private static final String TEXT_AREA_LABEL_SYMBOL = "\\n" ;
    private static final String COLUMN_DEFAULT_VALUE = "";
//    public static List<String> deafultColumnNames = Arrays.asList(new String[]{"id","shouhuodizhi", "dinghuoren", "dinghuorendianhua", "suoshumendian", "suoshumendianid","dinghuoshijian"});
//    public static List<String> deafultFormNames = Arrays.asList(new String[]{"id","收货地址", "订货人", "订货人电话", "所属门店","所属门店id", "订货时间"});

    public static List<String> deafultColumnNames = Arrays.asList(new String[]{"shouhuodizhi", "dinghuoren", "dinghuorendianhua", "suoshumendian","dinghuoshijian"});
    public static List<String> deafultFormNames = Arrays.asList(new String[]{"收货地址", "订货人", "订货人电话", "所属门店", "订货时间"});
    public static String deafultColumnType = "varchar";
    public static String PrimaryKey = "id";
    public static List<SalerOrderFormDto> setDefaultForms(List<SalerOrderFormSettingDto> salerOrderForms, String organizationId, String organizationName) {
        // 赋值网页字段
        List<SalerOrderFormDto>  salerOrderFormsResults = new ArrayList<SalerOrderFormDto>();
        salerOrderForms.forEach(salerOrderFormDto -> {
            SalerOrderFormDto salerOrderFormsResult = new SalerOrderFormDto();
            salerOrderFormsResult.setFormName(salerOrderFormDto.getFormName());
            salerOrderFormsResult.setFormType(salerOrderFormDto.getFormType());
            salerOrderFormsResult.setValue(salerOrderFormDto.getValue());
            salerOrderFormsResult.setTableName(initTableName(organizationId));
            salerOrderFormsResult.setColumnName(HanzhiToPinyinUtil.getPingYin(salerOrderFormDto.getFormName()));
            salerOrderFormsResult.setColumnType(deafultColumnType);
            salerOrderFormsResult.setOrganizationId(organizationId);
            salerOrderFormsResult.setOrganizationName(organizationName);
            salerOrderFormsResults.add(salerOrderFormsResult);
        });

        return salerOrderFormsResults;
    }


    public static List<SalerOrderFormDto> firstSetDefaultForms(String organizationId, String organizationName) {
        // 赋值网页字段
        List<SalerOrderFormDto>  salerOrderFormsResults = new ArrayList<>();

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
            salerOrderFormsResults                       .add(deafultElement);
        }
        return salerOrderFormsResults;
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

    public static List<SalerOrderForm> modelMapper(ModelMapper modelMapper, List<SalerOrderFormDto> withDefaultsalerOrderFormDtos) {
        List<SalerOrderForm> pojos = new ArrayList<>(withDefaultsalerOrderFormDtos.size());
        if(!CollectionUtils.isEmpty(withDefaultsalerOrderFormDtos)){
            withDefaultsalerOrderFormDtos.forEach(withDefaultsalerOrderFormDto->{
                SalerOrderForm pojo = modelMapper.map(withDefaultsalerOrderFormDto, SalerOrderForm.class);
                pojos.add(pojo);
            });        }

        return pojos;
    }


    public static List<H5SalerOrderFormVo> modelMapperVo(ModelMapper modelMapper, List<SalerOrderForm> pojos) {
        List<H5SalerOrderFormVo> vos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(pojos)){
            pojos.forEach(pojo->{
                H5SalerOrderFormVo vo = modelMapper.map(pojo, H5SalerOrderFormVo.class);
                if(pojo.getFormType().intValue() == FormType.xiala){
                    if(!StringUtils.isEmpty(pojo.getValue())){
                        // 下拉框的值
                        List<String> strings = Arrays.asList(pojo.getValue().split(TEXT_AREA_LABEL_SYMBOL));
                        vo.setValue(strings);
                    }
                }
                vos.add(vo);
            });
        }

        return vos;
    }

    public static void initDefaultColumnValue(List<ColumnnameAndValueDto> columnnameAndValues, H5LoginVO user,String address) {
        // 默认字段id自增，无须处理
        ColumnnameAndValueDto columnnameAndValue1 = new ColumnnameAndValueDto("shouhuodizhi",StringUtils.isEmpty(address)? COLUMN_DEFAULT_VALUE:address);
        ColumnnameAndValueDto columnnameAndValue2 = new ColumnnameAndValueDto("dinghuoren",StringUtils.isEmpty(user.getMemberName())? COLUMN_DEFAULT_VALUE:user.getMemberName());
        ColumnnameAndValueDto columnnameAndValue3 = new ColumnnameAndValueDto("dinghuorendianhua",StringUtils.isEmpty(user.getMobile())? COLUMN_DEFAULT_VALUE:user.getMobile());
        ColumnnameAndValueDto columnnameAndValue4 = new ColumnnameAndValueDto("suoshumendian",StringUtils.isEmpty(user.getCustomerName())? COLUMN_DEFAULT_VALUE:user.getCustomerName());
//        ColumnnameAndValueDto columnnameAndValue5 = new ColumnnameAndValueDto("suoshumendianid",StringUtils.isEmpty(user.getCustomerId())? COLUMN_DEFAULT_VALUE:user.getCustomerId());
        ColumnnameAndValueDto columnnameAndValue6 = new ColumnnameAndValueDto("dinghuoshijian"
                , DateUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
        columnnameAndValues.add(columnnameAndValue1);
        columnnameAndValues.add(columnnameAndValue2);
        columnnameAndValues.add(columnnameAndValue3);
        columnnameAndValues.add(columnnameAndValue4);
//        columnnameAndValues.add(columnnameAndValue5);
        columnnameAndValues.add(columnnameAndValue6);

    }

    public static List<ChangeColumDto> setColumnInfoWhenUpdate(List<SalerOrderFormSettingDto> salerOrderForms, List<SalerOrderForm> oldSalerOrderForms, String organizationId) {
        List<ChangeColumDto> updateColumns =  new ArrayList<>();
        for(SalerOrderFormSettingDto newSalerOrderForm :salerOrderForms){
            for(SalerOrderForm oldSalerOrderForm :oldSalerOrderForms){
                if(oldSalerOrderForm.getId().longValue() == newSalerOrderForm.getId().longValue()){
                    ChangeColumDto updateColumn = new ChangeColumDto();
                    updateColumn.setId(oldSalerOrderForm.getId());
                    updateColumn.setNewFormName(newSalerOrderForm.getFormName());
                    updateColumn.setNewColumnName(HanzhiToPinyinUtil.getPingYin(newSalerOrderForm.getFormName()));

                    updateColumn.setOldColumnName(oldSalerOrderForm.getColumnName());
                    updateColumn.setOldFormName(oldSalerOrderForm.getFormName());

                    updateColumn.setTableName(initTableName(organizationId));
                    updateColumns.add(updateColumn);

                }
            }
        }
        return updateColumns;
    }

    public static Collection<SalerOrderForm> initUpdateSalerOrderFormInfo(List<ChangeColumDto> updateColumns) {
        List<SalerOrderForm> list = new ArrayList<>();
        updateColumns.forEach(updateColumn->{
            SalerOrderForm salerOrderForm = new SalerOrderForm();
            salerOrderForm.setId(updateColumn.getId());
            salerOrderForm.setColumnName(updateColumn.getNewColumnName());
            salerOrderForm.setFormName(updateColumn.getNewFormName());
            list.add(salerOrderForm);
        });
        return list;
    }
}
