package com.jgw.supercodeplatform.marketingsaler.order.service;


import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.page.Page;
import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.marketing.exception.TableSaveException;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.dynamic.mapper.DynamicMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.application.group.BaseCustomerService;
import com.jgw.supercodeplatform.marketingsaler.order.constants.FormType;
import com.jgw.supercodeplatform.marketingsaler.order.dto.ChangeColumDto;
import com.jgw.supercodeplatform.marketingsaler.order.dto.ColumnnameAndValueDto;
import com.jgw.supercodeplatform.marketingsaler.order.dto.SalerOrderFormDto;
import com.jgw.supercodeplatform.marketingsaler.order.dto.SalerOrderFormSettingDto;
import com.jgw.supercodeplatform.marketingsaler.order.mapper.SalerOrderFormMapper;
import com.jgw.supercodeplatform.marketingsaler.order.pojo.SalerOrderForm;
import com.jgw.supercodeplatform.marketingsaler.order.transfer.SalerOrderTransfer;
import com.jgw.supercodeplatform.marketingsaler.order.vo.H5SalerOrderFormVo;
import com.jgw.supercodeplatform.marketingsaler.order.vo.SalerPreFillInfoVo;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.dto.CustomerInfoView;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品积分规则表 服务实现类
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Service
@Slf4j
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
        valid(salerOrderForms);
        List<SalerOrderFormSettingDto> updateOrderForms = new ArrayList<>();
        List<SalerOrderFormSettingDto> deleteOrAddForms = new ArrayList<>();
        log.info("动态表接收的网页参数{}",salerOrderForms);
        List updateids = new ArrayList();
        for(SalerOrderFormSettingDto salerOrderForm : salerOrderForms){
            if(salerOrderForm.getId() == null || salerOrderForm.getId() <= 0 ){
                deleteOrAddForms.add(salerOrderForm);
            }else {
                updateOrderForms.add(salerOrderForm);
                updateids.add(salerOrderForm.getId());
            }
        };

        deleteOrAddOrUpdate(deleteOrAddForms,updateids,updateOrderForms);

    }

    /**
     * 业务开始前的验证
     * @param salerOrderForms
     */
    private void valid(List<SalerOrderFormSettingDto> salerOrderForms) {

        List<SalerOrderFormDto> salerOrderFormDtos = SalerOrderTransfer.defaultForms(commonUtil.getOrganizationId(), commonUtil.getOrganizationName());
        List<SalerOrderFormDto> salerOrderFormDtos1 = SalerOrderTransfer.setFormsOtherField(salerOrderForms, commonUtil.getOrganizationId(), commonUtil.getOrganizationName());
        salerOrderFormDtos.addAll(salerOrderFormDtos1);
        Set<String> columnNames = new HashSet();
        salerOrderFormDtos.forEach(salerOrderFormDto ->{
            columnNames.add(salerOrderFormDto.getColumnName());
        });
        Asserts.check(columnNames.size() == salerOrderFormDtos.size(),"存在重复表单名或与预定义名称冲突");
        salerOrderForms.forEach(salerOrderFormSettingDto -> {
            if(salerOrderFormSettingDto.getFormType().byteValue() == FormType.xiala){
                Asserts.check(!StringUtils.isEmpty(salerOrderFormSettingDto.getValue()),"下拉框必须存在默认值");
            }
        });

    }


    /**
     * 根据新增和更新找出需要删除的
     * @param salerOrderForms 新增
     * @param updateids 更新
     *
     * @param updateOrderForms 更新
     */
    private void deleteOrAddOrUpdate(List<SalerOrderFormSettingDto> salerOrderForms,List<Long> updateids,List<SalerOrderFormSettingDto> updateOrderForms) {
        if(CollectionUtils.isEmpty(salerOrderForms) && CollectionUtils.isEmpty(updateOrderForms)){
            return;
        }
        // 被更新的数据不删除

        List<SalerOrderForm> undeleteBecauseofUpdates = new ArrayList<>();
        if(!CollectionUtils.isEmpty(updateids)){
            undeleteBecauseofUpdates = baseMapper.selectBatchIds(updateids);
        }

        // 网页新增  赋值默认表单和结构化名称补充
        List<SalerOrderFormDto> withDefaultsalerOrderFormDtos = SalerOrderTransfer.setFormsOtherField(salerOrderForms, commonUtil.getOrganizationId(), commonUtil.getOrganizationName());
        // 检查表单重复
        checkrepeat(withDefaultsalerOrderFormDtos);
        // 数据库订货字段
        List<SalerOrderForm> createsMetadatas = baseMapper.selectList(query().eq("OrganizationId", commonUtil.getOrganizationId()).getWrapper());
        // 新增的字段集
        List<SalerOrderForm> pojos = SalerOrderTransfer.modelMapper(modelMapper,withDefaultsalerOrderFormDtos);


        List<SalerOrderFormDto> defaultforms = SalerOrderTransfer.defaultForms(commonUtil.getOrganizationId(), commonUtil.getOrganizationName());
        if(CollectionUtils.isEmpty(createsMetadatas)){
            // 默认字段赋值
            withDefaultsalerOrderFormDtos.addAll(defaultforms);
            pojos = SalerOrderTransfer.modelMapper(modelMapper,withDefaultsalerOrderFormDtos);
            // 第一次新建表单
            List<String> newColumns = withDefaultsalerOrderFormDtos.stream().map(dto -> dto.getColumnName()).collect(Collectors.toList());
            // 主键特殊处理
            newColumns.removeIf(column-> column.equalsIgnoreCase(SalerOrderTransfer.PrimaryKey));
            try {
                dynamicMapper.createTable(withDefaultsalerOrderFormDtos.get(0).getTableName(),newColumns);
            } catch (Exception e) {
                e.printStackTrace();
                // 产品需求..........................................
                throw new BizRuntimeException("请输入中文或英文或其他合法字符");
            }
            if(!CollectionUtils.isEmpty(pojos)){
                log.info("1创建表单时候的POJO=>{}", JSONObject.toJSONString(pojos));
                this.saveBatch(pojos);
            }
        }else{
            // 数据库字段名
            List<String> createsMetadatasColumnName = createsMetadatas.stream().map(createsMetadata -> createsMetadata.getColumnName()).collect(Collectors.toList());
            // 网页新增
            List<String> withDefaultsalerOrderFormColumnNames = withDefaultsalerOrderFormDtos.stream().map(withDefaultsalerOrderFormDto -> withDefaultsalerOrderFormDto.getColumnName()).collect(Collectors.toList());

            // add column元信息实体集合
            List<String> addColumns = modelMapper.map(withDefaultsalerOrderFormColumnNames,List.class);
            addColumns.removeIf(addColumn->createsMetadatasColumnName.contains(addColumn));
            List<SalerOrderForm> addColumnPojos = new ArrayList<>();
            if(!CollectionUtils.isEmpty(addColumns)){
                for(SalerOrderFormDto salerOrderFormDto:withDefaultsalerOrderFormDtos){
                    if(addColumns.contains(salerOrderFormDto.getColumnName())){
                        SalerOrderForm add = modelMapper.map(salerOrderFormDto, SalerOrderForm.class);
                        addColumnPojos.add(add);
                    }

                }
            }

            List<String> deleteColumns = modelMapper.map(createsMetadatasColumnName,List.class);
            // 去除不需要删除的字段
            removeDefaultAndUpdate(undeleteBecauseofUpdates, defaultforms, deleteColumns);
            // 删除字段和新增字段
            log(addColumns, deleteColumns);


            List<ChangeColumDto> updateColumns = null;
            if(!CollectionUtils.isEmpty(updateOrderForms)){
                List<Long> ids = updateOrderForms.stream().map(data -> data.getId()).collect(Collectors.toList());
                List<SalerOrderForm> oldSalerOrderForms = baseMapper.selectBatchIds(ids);
                Asserts.check(ids.size() == oldSalerOrderForms.size(),"字段id不存在");
                updateColumns  =  SalerOrderTransfer.setColumnInfoWhenUpdate(updateOrderForms, oldSalerOrderForms,commonUtil.getOrganizationId());

            }


            try {
                // 原子化保障事物
                log.info("动态表修改 tableName ==>{}，deleteColumnNames ==>{}，addcolumnNames ==>{}，updateColumnNames ==> {}"
                ,SalerOrderTransfer.initTableName(commonUtil.getOrganizationId())
                        ,deleteColumns
                        ,addColumns
                        ,updateColumns
                );
                dynamicMapper.alterTableAndDropOrAddOrUpdate(SalerOrderTransfer.initTableName(commonUtil.getOrganizationId()),deleteColumns,addColumns,updateColumns);

            } catch (Exception e) {
                e.printStackTrace();

                throw new BizRuntimeException("请输入中文或英文或其他合法字符");
            }

            if(!CollectionUtils.isEmpty(deleteColumns)){
                log.info("2删除表单时相关的POJO=>{}", JSONObject.toJSONString(pojos));
                baseMapper.delete(query().eq("OrganizationId",commonUtil.getOrganizationId()).in("ColumnName",deleteColumns).notIn(!CollectionUtils.isEmpty(updateids),"id",updateids).getWrapper());
            }

            if(!CollectionUtils.isEmpty(updateColumns)){
                log.info("3更新表单时相关的POJO=>{}", JSONObject.toJSONString(pojos));
                this.updateBatchById(SalerOrderTransfer.initUpdateSalerOrderFormInfo(updateColumns));
            }

            if(!CollectionUtils.isEmpty(addColumnPojos)){
                log.info("4修改表单时新增的POJO=>{}", JSONObject.toJSONString(pojos));
                this.saveBatch(addColumnPojos);
            }
        }


    }

    private void log(List<String> addColumns, List<String> deleteColumns) {
        StringBuffer sbadd =new StringBuffer("");
        addColumns.forEach(data->sbadd.append(data).append("  "));
        log.info("add columns 如下{}" ,sbadd.toString());
        StringBuffer sb =new StringBuffer("");
        deleteColumns.forEach(data->sb.append(data).append("  "));
        log.info("delete columns 如下{}" ,sb.toString());
    }

    private void removeDefaultAndUpdate(List<SalerOrderForm> undeleteBecauseofUpdates, List<SalerOrderFormDto> defaultforms, List<String> deleteColumns) {
        List<String> undeleteBecauseofUpdateColumnNames = undeleteBecauseofUpdates.stream().map(undeleteBecauseofUpdate -> undeleteBecauseofUpdate.getColumnName()).collect(Collectors.toList());
        List<String> defaultformColumnNames = defaultforms.stream().map(defaultform -> defaultform.getColumnName()).collect(Collectors.toList());
        deleteColumns.removeIf(deleteColumn-> defaultformColumnNames.contains(deleteColumn)); // 删除不能包含默认
        deleteColumns.removeIf(deleteColumn->undeleteBecauseofUpdateColumnNames.contains(deleteColumn)); // 删除不能包含更新
    }

    private void checkrepeat(List<SalerOrderFormDto> withDefaultsalerOrderFormDtos) {
        Set<@NotEmpty(message = "表单名称不可为空") String> formNames = withDefaultsalerOrderFormDtos.stream().map(salerOrderForm -> salerOrderForm.getFormName()).collect(Collectors.toSet());
        Asserts.check(formNames.size() == withDefaultsalerOrderFormDtos.size(),"存在重名表单名，或表单名与预定义表单名冲突");
        for(SalerOrderFormDto salerOrderFormDto:withDefaultsalerOrderFormDtos){
            if(SalerOrderTransfer.deafultColumnNames.contains(salerOrderFormDto.getColumnName())){
                throw new BizRuntimeException("该表单名为默认字段");
            }
            if(SalerOrderTransfer.PrimaryKey.equalsIgnoreCase(salerOrderFormDto.getColumnName())){
                throw new BizRuntimeException("id字段为特殊字段，不可设置");
            }

        }
    }

    public List<SalerOrderForm> detail() {
        List<SalerOrderForm> salerOrderForms = baseMapper.selectList(query().eq("OrganizationId", commonUtil.getOrganizationId()).getWrapper());
        if(CollectionUtils.isEmpty(salerOrderForms)){
            return new ArrayList<>();
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


    /**
     * TODO 处理必填字段 不为空业务
     * @param columnnameAndValues
     * @param user
     */
    public void saveOrder(List<ColumnnameAndValueDto> columnnameAndValues, H5LoginVO user) {
        Asserts.check(!StringUtils.isEmpty(user.getOrganizationId()), "未获取对应组织");
        Asserts.check(!CollectionUtils.isEmpty(columnnameAndValues), "未获取订货信息");
       // validAllHaveColumn(columnnameAndValues,user.getOrganizationId());
        StringBuffer address = new StringBuffer("");
        if (!StringUtils.isEmpty(user.getCustomerId())) {
            CustomerInfoView customerInfo = baseCustomerService.getCustomerInfo(user.getCustomerId());
            log.info("准备从基础信息获取地址customerInfo {}",customerInfo);
            getAddress(address, customerInfo);
        }
        SalerOrderTransfer.initDefaultColumnValue(columnnameAndValues, user, address.toString());
        try {
            // 企业可以未配置动态模板，导致动态表不存在
            dynamicMapper.saveOrder(columnnameAndValues, SalerOrderTransfer.initTableName(user.getOrganizationId()));
        } catch (RuntimeException e) {
            throw new BizRuntimeException("管理员未配置表单内容...");
        }
    }


    public void updateOrder(List<ColumnnameAndValueDto> columnnameAndValues) {
        Asserts.check(!StringUtils.isEmpty(commonUtil.getOrganizationId()), "未获取对应组织");
        Asserts.check(!CollectionUtils.isEmpty(columnnameAndValues), "未获取订货信息");
        //
        AtomicReference<String> id = new AtomicReference<String> ();
        columnnameAndValues.forEach(columnnameAndValueDto -> {
            if(columnnameAndValueDto.getColumnName().equalsIgnoreCase("id")){
                id.set(columnnameAndValueDto.getColumnValue());
            }
        });
        if(id.get() ==null || id.get() .equals("")){
            throw new BizRuntimeException("ID 不存在");
        }
        dynamicMapper.updateOrder(columnnameAndValues, SalerOrderTransfer.initTableName(commonUtil.getOrganizationId()),id.get());


    }

    public void saveOrder(List<ColumnnameAndValueDto> columnnameAndValues) {
        Asserts.check(!StringUtils.isEmpty(commonUtil.getOrganizationId()), "未获取对应组织");
        Asserts.check(!CollectionUtils.isEmpty(columnnameAndValues), "未获取订货信息");
        // validAllHaveColumn(columnnameAndValues,commonUtil.getOrganizationId());
        try {
            dynamicMapper.saveOrder(columnnameAndValues, SalerOrderTransfer.initTableName(commonUtil.getOrganizationId()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new TableSaveException(e.getMessage());
        }
    }




    private void validAllHaveColumn(List<ColumnnameAndValueDto> columnnameAndValues,String organizationId) {
        int orderSize = baseMapper.selectCount(query().eq("organizationId", organizationId).getWrapper());
        int size = orderSize-SalerOrderTransfer.deafultColumnNames.size();
        if(columnnameAndValues.size() != size){
            throw new BizRuntimeException("数据为必填...");
        }

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


    public void updateStatus(Long id, byte status) {
        dynamicMapper.updateStatus(id, status, SalerOrderTransfer.initTableName(commonUtil.getOrganizationId()));

    }

    public void delete(Long id) {
        dynamicMapper.delete(id, SalerOrderTransfer.initTableName(commonUtil.getOrganizationId()));

    }

    public List<Map<String, Object>> detailbyId(Long id) {
        return dynamicMapper.selectById(id,SalerOrderTransfer.initTableName(commonUtil.getOrganizationId()));
    }

    /**
     * 导购员中心预填信息
     * @param jwtUser
     * @return
     */
    public SalerPreFillInfoVo getPreFill(H5LoginVO jwtUser){
        SalerPreFillInfoVo salerPreFillInfoVo= new SalerPreFillInfoVo();
        salerPreFillInfoVo.setDinghuoren(jwtUser.getMemberName());
        salerPreFillInfoVo.setDinghuorendianhua(jwtUser.getMobile());
        StringBuffer address = new StringBuffer("");
        if (org.apache.commons.lang.StringUtils.isNotBlank(jwtUser.getCustomerId())){
            CustomerInfoView customerInfoView=baseCustomerService.getCustomerInfo(jwtUser.getCustomerId());
            log.info("准备从基础信息获取地址customerInfoView-{}",customerInfoView);
            getAddress(address,customerInfoView);
            String detailedAddress = customerInfoView == null ? "" : customerInfoView.getDetailedAddress()  ;
            address.append(detailedAddress != null ? detailedAddress:"");
            salerPreFillInfoVo.setShouhuodizhi(address.toString());
        }
        return salerPreFillInfoVo;
    }
}
