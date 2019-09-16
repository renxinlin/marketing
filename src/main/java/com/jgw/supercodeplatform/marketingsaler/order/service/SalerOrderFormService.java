package com.jgw.supercodeplatform.marketingsaler.order.service;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.page.Page;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.dynamic.mapper.DynamicMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.application.group.BaseCustomerService;
import com.jgw.supercodeplatform.marketingsaler.order.dto.ChangeColumDto;
import com.jgw.supercodeplatform.marketingsaler.order.dto.ColumnnameAndValueDto;
import com.jgw.supercodeplatform.marketingsaler.order.dto.SalerOrderFormDto;
import com.jgw.supercodeplatform.marketingsaler.order.dto.SalerOrderFormSettingDto;
import com.jgw.supercodeplatform.marketingsaler.order.mapper.SalerOrderFormMapper;
import com.jgw.supercodeplatform.marketingsaler.order.pojo.SalerOrderForm;
import com.jgw.supercodeplatform.marketingsaler.order.transfer.SalerOrderTransfer;
import com.jgw.supercodeplatform.marketingsaler.order.vo.H5SalerOrderFormVo;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.dto.CustomerInfoView;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品积分规则表 服务实现类
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Slf4j
@Service
public class SalerOrderFormService extends SalerCommonService<SalerOrderFormMapper, SalerOrderForm> {

    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private BaseCustomerService baseCustomerService;

    /**
     * 相关非空校验
     * 校验表单源信息 不可重复名称 不可同预留字段相同
     *  保存表单元信息
     *  动态建表【元信息存在修改表  元信息不存在建表】
     * @param salerOrderForms
     */
    // TODO 需要分布式事务
    @Transactional // 对应的是主库事务
    public void alterOrCreateTableAndUpdateMetadata(List<SalerOrderFormSettingDto> salerOrderForms) {
        Asserts.check(!CollectionUtils.isEmpty(salerOrderForms),"表单设置失败");

        List<SalerOrderFormSettingDto> updateOrderForms = new ArrayList<>();
        List<SalerOrderFormSettingDto> deleteOrAddForms = new ArrayList<>();
        log.info("接收的参数{}",salerOrderForms);
        for(SalerOrderFormSettingDto salerOrderForm : salerOrderForms){
            if(salerOrderForm.getId() == null || salerOrderForm.getId() <= 0 ){
                deleteOrAddForms.add(salerOrderForm);
            }else {
                updateOrderForms.add(salerOrderForm);
            }
        };
        updateName(updateOrderForms); // 只能更新名称 数据库类型字段等都不可以
        deleteOrAdd(deleteOrAddForms);

    }

    private void updateName(List<SalerOrderFormSettingDto> salerOrderForms) {
        if(CollectionUtils.isEmpty(salerOrderForms)){
            return;
        }
        List<Long> ids = salerOrderForms.stream().map(data -> data.getId()).collect(Collectors.toList());
        List<SalerOrderForm> oldSalerOrderForms = baseMapper.selectBatchIds(ids);
        Asserts.check(ids.size() == oldSalerOrderForms.size(),"字段id不存在");
        List<ChangeColumDto> updateColumns =  SalerOrderTransfer.setColumnInfoWhenUpdate(salerOrderForms, oldSalerOrderForms,commonUtil.getOrganizationId());
        dynamicMapper.alterTableAndUpdateColumns(updateColumns.get(0).getTableName(),updateColumns);
        this.updateBatchById(SalerOrderTransfer.initUpdateSalerOrderFormInfo(updateColumns));
     }


    private void deleteOrAdd(List<SalerOrderFormSettingDto> salerOrderForms) {
        if(CollectionUtils.isEmpty(salerOrderForms)){
            return;
        }
        // 赋值默认表单和结构化名称补充
        List<SalerOrderFormDto> withDefaultsalerOrderFormDtos = SalerOrderTransfer.setDefaultForms(salerOrderForms, commonUtil.getOrganizationId(), commonUtil.getOrganizationName());
        Set<@NotEmpty(message = "表单名称不可为空") String> formNames = withDefaultsalerOrderFormDtos.stream().map(salerOrderForm -> salerOrderForm.getFormName()).collect(Collectors.toSet());
        Asserts.check(formNames.size() == withDefaultsalerOrderFormDtos.size(),"存在重名表单名，或表单名与预定义表单名冲突");
        // 数据库数据
        List<SalerOrderForm> createsMetadatas = baseMapper.selectList(query().eq("OrganizationId", commonUtil.getOrganizationId()).getWrapper());
        // 网页数据
        List<SalerOrderForm> pojos = SalerOrderTransfer.modelMapper(modelMapper,withDefaultsalerOrderFormDtos);

        //  检查有没有浅拷贝->结论：虽然是浅拷贝但不会影响list
        if(CollectionUtils.isEmpty(createsMetadatas)){
            // 第一次新建表单
            List<String> newColumns = withDefaultsalerOrderFormDtos.stream().map(dto -> dto.getColumnName()).collect(Collectors.toList());
            // 主键特殊处理
            newColumns.removeIf(column-> column.equalsIgnoreCase(SalerOrderTransfer.PrimaryKey));
            try {
                dynamicMapper.createTable(withDefaultsalerOrderFormDtos.get(0).getTableName(),newColumns);
            } catch (Exception e) {
                e.printStackTrace();
                // 产品需求..........................................
                throw new RuntimeException("请输入中文或英文");
            }
        }else{



            List<String> createsMetadatasColumnName = createsMetadatas.stream().map(createsMetadata -> createsMetadata.getColumnName()).collect(Collectors.toList());
            List<String> withDefaultsalerOrderFormColumnNames = withDefaultsalerOrderFormDtos.stream().map(withDefaultsalerOrderFormDto -> withDefaultsalerOrderFormDto.getColumnName()).collect(Collectors.toList());

            List<String> addColumns = modelMapper.map(withDefaultsalerOrderFormColumnNames,List.class);
            addColumns.removeIf(addColumn->createsMetadatasColumnName.contains(addColumn));

            List<String> deleteColumns = modelMapper.map(createsMetadatasColumnName,List.class);
            deleteColumns.removeIf(deleteColumn->withDefaultsalerOrderFormColumnNames.contains(deleteColumn));
            // 删除字段和新增字段
            try {
                dynamicMapper.alterTableAndDropOrAddColumns(withDefaultsalerOrderFormDtos.get(0).getTableName(),deleteColumns,addColumns);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("请输入中文或英文");
            }
            baseMapper.delete(query().eq("OrganizationId",commonUtil.getOrganizationId()).in("ColumnName",addColumns).getWrapper());

        }
        this.saveBatch(pojos);
    }

    public List<SalerOrderForm> detail() {
        List<SalerOrderForm> salerOrderForms = baseMapper.selectList(query().eq("OrganizationId", commonUtil.getOrganizationId()).getWrapper());
        if(CollectionUtils.isEmpty(salerOrderForms)){
            return new ArrayList<SalerOrderForm>();
        }else{
            // 移除预定义字段
            salerOrderForms.removeIf(salerOrderForm -> SalerOrderTransfer.deafultColumnNames.contains(salerOrderForm.getColumnName()));
        }
        return salerOrderForms;
    }

    /**
     * 动态表分页
     * @param daoSearch
     */
    public  AbstractPageService.PageResults<List<Map<String,Object>>> selectPage(DaoSearch daoSearch) throws SuperCodeException {
        // 传入表名
        String tableName = SalerOrderTransfer.initTableName(commonUtil.getOrganizationId());
        // 表名即组织
        Integer pageSize = daoSearch.getPageSize();
        int current = (daoSearch.getCurrent() -1)*pageSize;
        List<SalerOrderForm> columnConfigs = getColumnNamesByOrganizationId(commonUtil.getOrganizationId());
        List<String> columnNames = columnConfigs.stream().map(columnconfig -> columnconfig.getColumnName()).collect(Collectors.toList());
        int count = dynamicMapper.selectCount(tableName,columnNames,daoSearch.getSearch());
        List<Map<String,Object>> pageData = dynamicMapper.selectPageData(tableName,current, pageSize,columnNames,daoSearch.getSearch());
        Page pageInfo = new Page(pageSize,daoSearch.getCurrent(),count);
        AbstractPageService.PageResults<List<Map<String,Object>>> pageResult =
                new AbstractPageService.PageResults<>(pageData,pageInfo);
        return pageResult;
    }

    private List<SalerOrderForm> getColumnNamesByOrganizationId(String organizationId) {
        List<SalerOrderForm> columnConfigs = baseMapper.selectList(query().eq("organizationId", organizationId).getWrapper());
        return  columnConfigs;
    }

    public  List<H5SalerOrderFormVo> showOrder(H5LoginVO user) {
        Asserts.check(!StringUtils.isEmpty(user.getOrganizationId()),"未获取对应组织");
        // 待优化:剔除 notin
        List<SalerOrderForm> salerOrderForms = baseMapper.selectList(query().eq("OrganizationId", user.getOrganizationId())
                .notIn("ColumnName", SalerOrderTransfer.deafultColumnNames).getWrapper());
        List<H5SalerOrderFormVo> vos = SalerOrderTransfer.modelMapperVo(modelMapper,salerOrderForms);
        return vos;
    }


    public void saveOrder(List<ColumnnameAndValueDto> columnnameAndValues, H5LoginVO user) {
        Asserts.check(!StringUtils.isEmpty(user.getOrganizationId()), "未获取对应组织");
        Asserts.check(!CollectionUtils.isEmpty(columnnameAndValues), "未获取订货信息");
        StringBuffer address = new StringBuffer("");
        if (!StringUtils.isEmpty(user.getCustomerId())) {
            CustomerInfoView customerInfo = baseCustomerService.getCustomerInfo(user.getCustomerId());
            getAddress(address, customerInfo);
        }
        SalerOrderTransfer.initDefaultColumnValue(columnnameAndValues, user, address.toString());
        dynamicMapper.saveOrder(columnnameAndValues, SalerOrderTransfer.initTableName(user.getOrganizationId()));
    }

    /**
     * 拼接收货地址
     * @param address
     * @param customerInfo
     */
    private void getAddress(StringBuffer address, CustomerInfoView customerInfo) {
        if (customerInfo != null) {
            if (!StringUtils.isEmpty(customerInfo.getProvinceName())) {
                address.append(customerInfo.getProvinceName());
            }
            if (!StringUtils.isEmpty(customerInfo.getCityName())) {
                address.append(customerInfo.getCityName());
            }
            if (!StringUtils.isEmpty(customerInfo.getCountyName())) {
                address.append(customerInfo.getCountyName());
            }
        }
    }
}
