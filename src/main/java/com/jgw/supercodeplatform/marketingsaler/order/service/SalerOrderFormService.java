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
    public void alterOrCreateTableAndUpdateMetadata(List<SalerOrderFormSettingDto> salerOrderForms) {
        Asserts.check(!CollectionUtils.isEmpty(salerOrderForms),"表单设置失败");
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
                // 产品需求
                throw new RuntimeException("请输入中文或英文");
            }
        }else{
            // 不是第一次新建表单 修改表 删除旧的元数据 新增新的元数据
// todo 有id的列进行修改
//            List<ChangeColumDto> changeColums = new ArrayList();
//            salerOrderForms.forEach(salerOrderForm->changeColums.add(new ChangeColumDto(salerOrderForm.getId(),null,null,null,null)));
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
            baseMapper.delete(query().eq("OrganizationId",commonUtil.getOrganizationId()).getWrapper());

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
        int count = dynamicMapper.selectCount(tableName);
        // 表名即组织
        Integer pageSize = daoSearch.getPageSize();
        int current = (daoSearch.getCurrent() -1)*pageSize;
        List<Map<String,Object>> pageData = dynamicMapper.selectPageData(tableName,current, pageSize);
        Page pageInfo = new Page(pageSize,daoSearch.getCurrent(),count);
        AbstractPageService.PageResults<List<Map<String,Object>>> pageResult =
                new AbstractPageService.PageResults<List<Map<String,Object>>>(pageData,pageInfo);
        return pageResult;
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
        SalerOrderTransfer.initDefaultColumnValue(columnnameAndValues, user, address.toString());
        dynamicMapper.saveOrder(columnnameAndValues, SalerOrderTransfer.initTableName(user.getOrganizationId()));
    }
}
